package ru.job4j.concurrent;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.regex.Pattern;

public class Wget implements Runnable {

    public static final String URL_REGEX = "^((https?|ftp)://|(www|ftp)\\.)?[a-z0-9-]+(\\.[a-z0-9-]+)+([/?].*)?$";
    private final String url;
    private final int speed;


    public Wget(String url, int speed) {
        this.url = url;
        this.speed = speed;
    }

    @Override
    public void run() {

        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream("download_file.xml")) {
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            long startTime = System.currentTimeMillis();
            int bytesData = 0;
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                bytesData += bytesRead;
                if (bytesData >= speed) {
                    long lengthOfTime = System.currentTimeMillis() - startTime;
                    if (lengthOfTime < 1000) {
                        Thread.sleep(1000 - lengthOfTime);
                    }
                    bytesData = 0;
                    startTime = System.currentTimeMillis();
                }
                fileOutputStream.write(dataBuffer, 0, bytesRead);

            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void checkArgs(String[] args) {
        Pattern p = Pattern.compile(URL_REGEX);
        if (args.length != 2 || args[0].isEmpty() || args[1].isEmpty()) {
            throw new IllegalArgumentException("Не заполнены параметры");
        }

        if (Integer.parseInt(args[1]) < 0) {
            throw new IllegalArgumentException("Некорректное значение параметра");
        }
        if (!p.matcher(args[0]).find()) {
            throw new IllegalArgumentException("Введите корректную ссылку на файл");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        checkArgs(args);
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        Thread wget = new Thread(new Wget(url, speed));
        wget.start();
        wget.join();
    }
}
