package org.example.transfer;

import org.springframework.stereotype.Service;

@Service
public class AccountTransferService {

    private final AccountTransferRepository repository;

    public AccountTransferService(AccountTransferRepository repository) {
        this.repository = repository;
    }

    public boolean transfer(String fromUser, String toUser, long amount) {
        return repository.transfer(fromUser, toUser, amount);
    }
}