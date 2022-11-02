package ru.job4j.io;

import java.io.*;
import java.util.function.Predicate;

public final class ParseFile {
    private final File file;

    public ParseFile(File file) {
        this.file = file;
    }

    public synchronized File getFile() {
        return file;
    }

    public String getContent(Predicate<Character> filter) throws IOException {
        try (BufferedReader in = new BufferedReader(new FileReader(file))) {
            String output = "";
            int data;
            while ((data = in.read()) > 0) {
                char s = (char) data;
                if (filter.test(s)) {
                    output += s;
                }
            }
            return output;
        }

    }

/**/
    public final class SaveFile {

        public void saveContent(String content) throws IOException {
            try (PrintWriter out = new PrintWriter(new BufferedOutputStream(new FileOutputStream(file)))) {
                out.write(content);
            }

        }
    }

}