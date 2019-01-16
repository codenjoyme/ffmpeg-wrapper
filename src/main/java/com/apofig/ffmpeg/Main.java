package com.apofig.ffmpeg;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) throws IOException {
        Process p = Runtime.getRuntime().exec(
                "ffmpeg -version"
        );

        try (BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
            String line;

            while ((line = input.readLine()) != null) {
                System.out.println(line);
            }
        }
    }
}
