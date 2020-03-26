package com.apofig.ffmpeg;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

public class Joiner {

    public static final String LIST_FILE = "list.txt";

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

        cleanList();
        String list = getListFile();

        try (PrintWriter writer = new PrintWriter(list)) {
            parts.forEach(part -> {
                part = part.replaceAll(getWork(), "");
                String line = String.format("file '%s'", part);
                System.out.println(line);
                writer.println(line);
            });

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }

        String command = String.format(template,
                list, output);

        runner.execOutput(command);
    }

    private String getListFile() {
        return getWork() + LIST_FILE;
    }

    public String getWork() {
        return new File(output).getParent() + "/";
    }

    public void clean() {
        cleanList();
        parts.forEach(part -> new File(part).delete());
    }

    private void cleanList() {
        new File(getListFile()).delete();
    }

    public String getOutput() {
        return output;
    }
}
