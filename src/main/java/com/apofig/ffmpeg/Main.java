package com.apofig.ffmpeg;

public class Main {

    public static void main(String[] args) {
        Runner runner = new RunnerImpl();

        new Cutter(runner)
                .input("work/input.flv")
                .output("work/done.flv")
                .start("00:00:00")
                .cut("00:30:00", "01:00:00")
                .end("01:40:23")
                .run();
    }
}
