package org.example.transfer;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transfer")
class AccountTransferController {
    private final AccountTransferService transferService;

    AccountTransferController(AccountTransferService transferService) {
        this.transferService = transferService;
    }

    @PostMapping
    public String transfer(@RequestParam String from,
                           @RequestParam String to,
                           @RequestParam long amount) {
        boolean success = transferService.transfer(from, to, amount);
        return success ? "Transfer successful" : "Transfer failed";
    }
}
