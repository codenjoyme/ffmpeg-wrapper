package com.apofig.ffmpeg;

public class Main {

    public static void main(String[] args) {
        Runner runner = new RunnerImpl();

        new Worker(runner)
                .output("work/done.mp4")

                .input("work/input1.flv")
                    .start("00:00:00")
                    .cut("00:00:55", "00:01:00")
                    .cut("00:05:13", "00:07:02")
                    .cut("00:11:21", "00:11:23")
                    .cut("00:21:44", "00:21:48")
                    .cut("00:25:55", "00:26:55")
                    .end("00:41:29")
                .input("work/input2.flv")
                    .start("00:00:00")
                    .end("00:01:52")
                .replaceAudio()
                .run();
    }
}
