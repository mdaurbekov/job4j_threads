package ru.job4j.cash;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashMap;
import java.util.Optional;

@ThreadSafe
public class AccountStorage {

    @GuardedBy("this")
    private final HashMap<Integer, Account> accounts = new HashMap<>();

    public synchronized boolean add(Account account) {
        return accounts.putIfAbsent(account.id(), account) != null;
    }

    public synchronized boolean update(Account account) {

        return accounts.replace(account.id(), account) != null;
    }

    public synchronized boolean delete(int id) {
        return accounts.remove(id) != null;
    }

    public synchronized Optional<Account> getById(int id) {
        return accounts.get(id) == null ? Optional.empty() : Optional.of(accounts.get(id));
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        boolean rezult = false;
        Account accountFrom = getById(fromId).get();
        Account accountTo = getById(toId).get();
        if (accountFrom.amount() >= amount) {
            update(new Account(accountFrom.id(),  accountFrom.amount() - amount));
            update(new Account(accountTo.id(),  accountTo.amount() + amount));
            rezult = true;
        }

        return rezult;
    }


}