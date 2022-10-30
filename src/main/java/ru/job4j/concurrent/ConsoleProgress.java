package ru.job4j.concurrent;

public class ConsoleProgress implements Runnable {
    @Override
    public void run() {
        char[] process = {'-', '\\', '|', '/'};
        int i = 0;
        while (!Thread.currentThread().isInterrupted()) {
            if (i == 4) {
                i = 0;
            }
            System.out.print("\r load: " + process[i]);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            i++;

        }

    }

    public static void main(String[] args) {
        Thread progress = new Thread(new ConsoleProgress());
        progress.start();
        try {
            Thread.sleep(5000); /* симулируем выполнение параллельной задачи в течение 1 секунды. */
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        progress.interrupt();
    }
}
