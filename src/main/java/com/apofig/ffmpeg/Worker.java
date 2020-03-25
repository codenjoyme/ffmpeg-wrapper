package com.apofig.ffmpeg;

import java.util.LinkedList;
import java.util.List;

public class Worker {

    private Joiner joiner;
    private Runner runner;
    private List<CutterEx> cutters = new LinkedList<>();
    private int index;

    public Worker(Runner runner) {
        this.runner = runner;
        joiner = new Joiner(runner);
        index = 1;
    }

    public Worker output(String output) {
        joiner.output(output);
        return this;
    }

    public CutterEx input(String input) {
        CutterEx cutter = new CutterEx(runner);
        cutters.add(cutter);
        cutter.input(input);
        return cutter;
    }

    public void run() {
        cutters.forEach(cutter -> {
            cutter.cutter.splitter.run();
        });

        joiner.run();
        clean();
    }

    void clean() {
        joiner.clean();
    }

    public class CutterEx {

        private Cutter cutter;

        public CutterEx(Runner runner) {
            cutter = new Cutter(runner, joiner){
                @Override
                String suffix() {
                    return "_" + index;
                }
            };
        }

        private void input(String input) {
            cutter.input(input);
        }

        public CutterEx start(String from) {
            cutter.start(from);
            return this;
        }

        public CutterEx cut(String from, String to) {
            cutter.cut(from, to);
            return this;
        }

        public Worker end(String to) {
            cutter.end(to);
            index++;
            return Worker.this;
        }
    }

}
