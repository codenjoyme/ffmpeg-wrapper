package com.apofig.ffmpeg;

import java.io.File;

public class Cutter {
    private Joiner joiner;
    private Splitter2 splitter;

    public Cutter(Runner runner) {
        splitter = new Splitter2(runner);
        joiner = new Joiner(runner);
    }

    public Cutter output(String output) {
        joiner.output(output);
        return this;
    }

    public Cutter input(String input) {
        splitter.input(input);
        return this;
    }

    public Cutter start(String from) {
        splitter.time(from);
        return this;
    }

    public Cutter end(String to) {
        String part = getPartName(splitter.getLast(), to);

        splitter.save(part)
                .time(to);

        joiner.part(part);

        return this;
    }

    private String getPartName(String from, String to) {
        return String.format("%spart_%s_%s.flv",
                getParent(),
                from.replaceAll(":", "-"),
                to.replaceAll(":", "-"));
    }

    private String getParent() {
        return joiner.getWork();
    }

    public Cutter cut(String from, String to) {
        end(from);

        splitter.remove()
                .time(to);

        return this;
    }

    public void run() {
        clean();
        splitter.run();

        joiner.run();
        clean();
    }

    private void clean() {
        joiner.getParts().forEach(part -> new File(part).delete());
    }
}
