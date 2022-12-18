package ru.senderov.server.services.impl;

import com.codahale.metrics.Counter;
import com.codahale.metrics.MetricRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.senderov.server.models.Balance;
import ru.senderov.server.repository.BalanceRepository;
import ru.senderov.server.services.BalanceService;

import java.util.Optional;

@Service
@Slf4j
@CacheConfig(cacheNames = "balance")
@EnableScheduling
public class BalanceServiceImpl implements BalanceService {
    private final BalanceRepository balanceRepository;

    private final MetricRegistry metricRegistry = new MetricRegistry();

    private final Counter getBalanceCounter = metricRegistry
            .counter(MetricRegistry.name(getClass(), "getBalanceCount"));

    private final Counter changeBalanceCounter = metricRegistry
            .counter(MetricRegistry.name(getClass(), "changeBalance"));

    public BalanceServiceImpl(BalanceRepository balanceRepository) {
        this.balanceRepository = balanceRepository;
    }

    @Transactional(readOnly = true)
    @Override
    @Cacheable
    public Optional<Long> getBalance(Long id) {
        getBalanceCounter.inc();
        return Optional.of(balanceRepository.findById(id).orElseThrow().getAmount());
    }

    @Transactional
    @Override
    @CachePut
    public void changeBalance(Long id, Long amount) {
        changeBalanceCounter.inc();

        Balance balance = balanceRepository.findById(id).orElseThrow();
        long l = balance.getAmount() + amount;
        balance.setAmount(l);

        balanceRepository.save(balance);
    }

    @Scheduled(fixedDelay = 1000)
    void logging() {
        log.info("There were {} requests to the getBalance endpoint in one second", getBalanceCounter.getCount());
        log.info("There were {} requests to the changeBalance endpoint in one second", changeBalanceCounter.getCount());
        log.info("Sum {}", getBalanceCounter.getCount() + changeBalanceCounter.getCount());
        getBalanceCounter.dec(getBalanceCounter.getCount());
        changeBalanceCounter.dec(changeBalanceCounter.getCount());
    }
}
