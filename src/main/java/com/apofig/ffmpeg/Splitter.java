package com.apofig.ffmpeg;

import java.util.LinkedList;
import java.util.List;

public class Splitter {

    private Runner runner;
    private String input;
    private List<Part> parts;

    public Splitter(Runner runner) {
        parts = new LinkedList<>();
        this.runner = runner;
    }



    public void run() {
        parts.forEach(this::execPart);
    }

    private void execPart(Part part) {
        String template = "ffmpeg -i %s -vcodec copy -acodec copy -ss %s -to %s %s";

        String command = String.format(template,
                input,
                part.getFrom(),
                part.getTo(),
                part.getOutput());

        runner.execOutput(command);
    }

    public Splitter part(Part part) {
        parts.add(part);
        return this;
    }

    public Splitter input(String input) {
        this.input = input;
        return this;
    }

    public static class Part {

        private final String from;
        private final String to;
        private String output;

        private Part(String from, String to) {
            this.from = from;
            this.to = to;
        }

        public static Part time(String from, String to) {
            return new Part(from, to);
        }

        public Part to(String output) {
            this.output = output;
            return this;
        }

        public String getFrom() {
            return from;
        }

        public String getTo() {
            return to;
        }

        public String getOutput() {
            return output;
        }

    }
}
