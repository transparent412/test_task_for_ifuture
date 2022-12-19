package ru.senderov.clientpart.util;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@PropertySource(value = {"classpath:application.properties"})
@Data
public class ClientPropertyStorage {

    private final Integer threadCount;

    private final Double readQuota;

    private final Double writeQuota;

    private final List<Long> readIdList;

    private final List<Long> writeIdList;

    public ClientPropertyStorage(@Value("${client_app.threadCount}") Integer threadCount,
                                 @Value("${client_app.readQuota}") Double readQuota,
                                 @Value("${client_app.writeQuota}") Double writeQuota) {
        this.threadCount = threadCount;
        this.readQuota = readQuota;
        this.writeQuota = writeQuota;
        this.readIdList = Arrays.asList(1L, 2L, 3L, 4L);
        this.writeIdList = Arrays.asList(1L, 2L, 3L, 4L);
    }

}
