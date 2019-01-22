package com.apofig.ffmpeg;

import java.util.LinkedList;
import java.util.List;

public class Splitter2 {

    public static final String EMPTY = "";

    private String input;
    private List<String> times;
    private List<String> saves;
    private Runner runner;

    public Splitter2(Runner runner) {
        this.runner = runner;
        times = new LinkedList<>();
        saves = new LinkedList<>();
    }

    public Splitter2 input(String input) {
        this.input = input;
        return this;
    }

    public Splitter2 time(String time) {
        times.add(time);
        return this;
    }

    public Splitter2 save(String output) {
        saves.add(output);
        return this;
    }

    public Splitter2 remove() {
        saves.add(EMPTY);
        return this;
    }

    public void run() {
        if (times.size() != saves.size() + 1) {
            throw new IllegalArgumentException("Неверный формат");
        }

        Splitter splitter = new Splitter(runner).input(input);
        for (int i = 0; i < saves.size(); i++) {
            String from = times.get(i);
            String to = times.get(i + 1);
            String output = saves.get(i);

            if (!EMPTY.equals(output)) {
                splitter = splitter.part(Splitter.Part.time(from, to).to(output));
            }
        }
        splitter.run();
    }
}
