package ru.senderov.server.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.senderov.server.services.BalanceService;

@RestController
public class BalanceController {

    private final BalanceService balanceService;


    public BalanceController(BalanceService balanceService) {
        this.balanceService = balanceService;
    }

    @GetMapping("/getBalance")
    public ResponseEntity<?> getBalance(@RequestParam("id") Long id) {
        return ResponseEntity.ok().body(balanceService.getBalance(id));
    }

    @GetMapping("/changeBalance")
    public void changeBalance(@RequestParam("id") Long id,
                              @RequestParam("amount") Long amount) {
        balanceService.changeBalance(id, amount);
    }

}
