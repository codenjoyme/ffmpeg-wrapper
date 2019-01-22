package com.apofig.ffmpeg;

public class Main {

    public static void main(String[] args) {
        Runner runner = new RunnerImpl();

        new Splitter2(runner)
                .input("work/input.flv")
                .time("00:00:00")
                .save("work/output1.flv")
                .time("00:30:00")
                .remove()
                .time("01:00:00")
                .save("work/output2.flv")
                .time("01:30:00")
                .save("work/output3.flv")
                .time("01:40:23")
                .run();

        new Joiner(runner)
                .output("work/done.flv")
                .part("output1.flv")
                .part("output2.flv")
                .part("output3.flv")
                .run();

    }
}
