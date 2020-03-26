package com.apofig.ffmpeg;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Worker {

    private Audio audio;
    private Joiner joiner;
    private Runner runner;
    private List<CutterEx> cutters = new LinkedList<>();
    private int index;
    private boolean changeAudio;

    public Worker(Runner runner) {
        this.runner = runner;
        joiner = new Joiner(runner);
        index = 1;
        changeAudio = false;
        audio = new Audio(runner);
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

        if (changeAudio) {
            processAudio();
        }
    }

    private void processAudio() {
        String file = audio.extract(joiner.getOutput());

        waitAudio();

        String output = audio.join(joiner.getOutput(), file);

        new File(file).delete();

        System.out.println("Please check result '" + output + "'");
    }

    void waitAudio() {
        printWait("Please update '" + audio + "' audio, then press Enter.");
    }

    public static void printWait(String message) {
        System.out.println(message);
        new Scanner(System.in).nextLine();
    }

    void clean() {
        joiner.clean();
    }

    public Worker replaceAudio() {
        changeAudio = true;
        return this;
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
