package ru.job4j.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.function.Predicate;

public final class ParseFile {
    private final File file;

    public ParseFile(File file) {
        this.file = file;
    }

    public synchronized File getFile() {
        return file;
    }

    public String getContentPredicate(Predicate<Character> filter) throws IOException {
        try (BufferedReader in = new BufferedReader(new FileReader(file))) {
            StringBuilder output = new StringBuilder();
            int data;
            while ((data = in.read()) != -1) {
                char s = (char) data;
                if (filter.test(s)) {
                    output.append(s);
                }
            }
            return output.toString();
        }

    }

    public String getContent() throws IOException {
        return getContentPredicate(data -> true);
    }

    public String getContentWithoutUnicode() throws IOException {
        return getContentPredicate(data -> data < 0x80);
    }


}