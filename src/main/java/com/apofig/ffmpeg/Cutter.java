package com.apofig.ffmpeg;

import java.io.File;

public class Cutter {

    private Joiner joiner;
    Splitter2 splitter;

    public Cutter(Runner runner) {
        this(runner, new Joiner(runner));
    }

    Cutter(Runner runner, Joiner joiner) {
        splitter = new Splitter2(runner);
        this.joiner = joiner;
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
        return String.format("%spart%s_%s_%s.flv",
                getParent(),
                suffix(),
                from.replaceAll(":", "-"),
                to.replaceAll(":", "-"));
    }

    // будет переопределено у наследника
    String suffix() {
        return "";
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
