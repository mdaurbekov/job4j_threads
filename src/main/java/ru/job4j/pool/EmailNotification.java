package ru.job4j.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EmailNotification {
    ExecutorService pool = Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors());

    public void emailTo(User user) {
        StringBuilder stringBuilderSubject = new StringBuilder();
        StringBuilder stringBuilderBody = new StringBuilder();
        String subject = stringBuilderSubject.append("Notification ")
                .append(user.getUsername()).append(" to email ")
                .append(user.getEmail()).toString();
        String body = stringBuilderBody.append("Add a new event to ")
                .append(user.getUsername()).toString();

    }

    public void close() {

        pool.shutdown();

    }

    void send(String subject, String body, String email) {

    }

    public static void main(String[] args) {
        EmailNotification emailNotification = new EmailNotification();
        emailNotification.pool.submit(new Runnable() {
            @Override
            public void run() {
                User user = new User("Ivan", "email@mail.ru");
                emailNotification.emailTo(user);
            }
        });

        emailNotification.close();
        while (!emailNotification.pool.isTerminated()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
