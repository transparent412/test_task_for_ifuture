package ru.senderov.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.senderov.server.models.Balance;

@Repository
public interface BalanceRepository extends JpaRepository<Balance, Long> {

}