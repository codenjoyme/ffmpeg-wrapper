package com.apofig.ffmpeg;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

public class Joiner {

    private Runner runner;
    private String output;
    private List<String> parts;

    public Joiner(Runner runner) {
        this.runner = runner;
        parts = new LinkedList<>();
    }

    public Joiner output(String output) {
        this.output = output;
        return this;
    }

    public Joiner part(String part) {
        parts.add(part);
        return this;
    }

    public void run() {
        String template = "ffmpeg -f concat -safe 0 -i %s -c copy %s";

        String listFile = new File(output).getParent() + "/list.txt";

        new File(listFile).delete();

        try (PrintWriter writer = new PrintWriter(listFile)) {

            parts.forEach(part -> {
                String line = String.format("file '%s'", part);
                System.out.println(line);
                writer.println(line);
            });

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }

        String command = String.format(template,
                listFile, output);

        runner.execOutput(command);
    }

}