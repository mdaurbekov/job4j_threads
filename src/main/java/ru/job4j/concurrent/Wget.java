package ru.job4j.concurrent;

public class Wget {
    public static void main(String[] args) {
        Thread thread = new Thread(
                () -> {
                    try {
                        for (int index = 1; index <= 100; index++) {
                            Thread.sleep(1000);
                            System.out.print("\rLoading : " + index + "%");
                        }

                        System.out.println("Loaded.");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
        );
        thread.start();

    }
}
