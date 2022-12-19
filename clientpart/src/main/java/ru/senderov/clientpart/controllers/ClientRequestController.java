package ru.senderov.clientpart.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.senderov.clientpart.services.ClientRequestService;

@RestController
public class ClientRequestController {

    private final ClientRequestService clientRequestService;

    public ClientRequestController(ClientRequestService clientRequestService) {
        this.clientRequestService = clientRequestService;
    }

    @GetMapping()
    public void sendRequests() {
        clientRequestService.generationClientsRequests();
    }

}
