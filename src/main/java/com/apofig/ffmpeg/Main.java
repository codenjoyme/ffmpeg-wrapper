package com.apofig.ffmpeg;

public class Main {

    public static void main(String[] args) {
        Runner runner = new RunnerImpl();

        new Splitter(runner)
                .input("work/input.flv")
                .part(Splitter.Part.time("00:00:00", "00:30:00").to("work/1output1.flv"))
                .part(Splitter.Part.time("01:00:00", "01:30:00").to("work/1output2.flv"))
                .part(Splitter.Part.time("01:30:00", "01:40:23").to("work/1output3.flv"))
                .run();

        new Splitter2(runner)
                .input("work/input.flv")
                .time("00:00:00")
                .save("work/2output1.flv")
                .time("00:30:00")
                .remove()
                .time("01:00:00")
                .save("work/2output2.flv")
                .time("01:30:00")
                .save("work/2output3.flv")
                .time("01:40:23")
                .run();
    }
}
