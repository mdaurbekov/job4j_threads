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
        accounts.put(account.id(), account);
        return true;
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
        int valueFrom = getById(fromId).get().amount();
        int valueTo = getById(toId).get().amount();
        if ((valueFrom - amount) >= 0) {
            Account accountFrom = new Account(getById(fromId).get().id(), valueFrom - amount);
            update(accountFrom);
            Account accountTo = new Account(getById(toId).get().id(), valueTo + amount);
            update(accountTo);
            rezult = true;
        }

        return rezult;
    }


}