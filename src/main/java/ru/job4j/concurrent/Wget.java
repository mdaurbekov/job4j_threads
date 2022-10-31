package ru.job4j.concurrent;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.regex.Pattern;

public class Wget implements Runnable {

    public static final String URL_REGEX = "^((https?|ftp)://|(www|ftp)\\.)?[a-z0-9-]+(\\.[a-z0-9-]+)+([/?].*)?$";

    public static final String EXTENSION_REGEX = "\\.[^\\.]+";
    private final String url;

    private final String fileName;
    private final int speed;


    public Wget(String url, int speed, String fileName) {
        this.url = url;
        this.speed = speed;
        this.fileName = fileName;
    }

    @Override
    public void run() {

        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(fileName)) {
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
        Pattern patternUrl = Pattern.compile(URL_REGEX);
        Pattern patternExt = Pattern.compile(EXTENSION_REGEX);
        if (args.length != 3 || args[0].isEmpty() || args[1].isEmpty() || args[2].isEmpty()) {
            throw new IllegalArgumentException("Не заполнены параметры");
        }

        if (!patternUrl.matcher(args[0]).find()) {
            throw new IllegalArgumentException("Введите корректную ссылку на файл");
        }
        if (Integer.parseInt(args[1]) < 0) {
            throw new IllegalArgumentException("Некорректное значение параметра");
        }

        if (!patternExt.matcher(args[2]).find()) {
            throw new IllegalArgumentException("Неверное расширение файла");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        checkArgs(args);
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        String fileName = args[2];
        Thread wget = new Thread(new Wget(url, speed, fileName));
        wget.start();
        wget.join();
    }
}
