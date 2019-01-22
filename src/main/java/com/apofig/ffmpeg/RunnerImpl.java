package com.apofig.ffmpeg;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

public class RunnerImpl implements Runner {

    public static void main(String[] args) {
        List<String> info = new RunnerImpl().exec("ffmpeg -version");
        info.forEach(System.out::println);
    }

    @Override
    public List<String> exec(String command) {
        List<String> messages = new LinkedList<>();
        try {
            Process process = new ProcessBuilder(command.split(" ")).redirectErrorStream(true).start();

            try (BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = input.readLine()) != null) {
                    messages.add(line);
                }
                process.waitFor();
            }
        } catch (IOException | InterruptedException e) {
            e.fillInStackTrace();
            messages.add("Error: " + e.toString());
        }
        return messages;
    }
}
