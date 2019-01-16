package com.apofig.ffmpeg;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        List<String> info = exec("ffmpeg -version");
        info.forEach(System.out::println);
    }

    private static List<String> exec(String command) throws IOException {
        Process process = Runtime.getRuntime().exec(command);

        List<String> messages = new LinkedList<>();
        try (BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = input.readLine()) != null) {
                messages.add(line);
            }
        }
        return messages;
    }
}
