package ru.job4j.concurrent;

public class ThreadState {
    public static void main(String[] args) {
        Thread first = new Thread(
                () -> {}, "first"
        );
        Thread second = new Thread(
                () -> {}, "second"
        );
        System.out.println("Нить: " + first.getName() + " - " + first.getState());
        System.out.println("Нить: " + second.getName() + " - " + second.getState());
        first.start();
        second.start();
        while (first.getState() != Thread.State.TERMINATED || second.getState() != Thread.State.TERMINATED) {
            System.out.println("Нить: " + first.getName() + " - " + first.getState());
            System.out.println("Нить: " + second.getName() + " - " + second.getState());

        }
        System.out.println("Нить: " + first.getName() + " - " + first.getState());
        System.out.println("Нить: " + second.getName() + " - " + second.getState());
    }
}