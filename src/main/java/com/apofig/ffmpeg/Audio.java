package com.apofig.ffmpeg;

import java.io.File;
import java.util.Scanner;

public class Audio {

    private Runner runner;

    public Audio(Runner runner) {
        this.runner = runner;
    }

    public String extract(String video) {
        String template = "ffmpeg -i %s -vn -acodec copy %s";

        String audio = video.replace(".", "-audio.");
        String command = String.format(template,
                video,
                audio);

        runner.execOutput(command);
        return audio;
    }

    public String extractWave(String video) {
        String template = "ffmpeg -i %s -acodec pcm_s16le -ac 2 %s";

        String ext = video.substring(video.lastIndexOf('.') + 1);
        String audio = video.replace("." + ext, "-audio.wav");
        String command = String.format(template,
                video,
                audio);

        runner.execOutput(command);
        return audio;
    }

    public String join(String video, String audio) {
        String template = "ffmpeg -i %s -i %s -c:v copy -map 0:v:0 -map 1:a:0 %s";

        String output = video.replace(".", "-merged.");
        String command = String.format(template,
                video,
                audio,
                output);

        runner.execOutput(command);
        return output;
    }

    public static void main(String[] args) {
        Audio worker = new Audio(new RunnerImpl());
        String video = "work/done-done.mp4";
        String audio = worker.delay(video, 0.3);

    }

    public String delay(String video, double delay) {
        String template = "ffmpeg.exe -i %s -itsoffset %s -i %s -map 0:v -map 1:a -c copy %s";

        String output = video.replace(".", "-delayed.");
        String command = String.format(template,
                video,
                delay,
                video,
                output);

        runner.execOutput(command);
        return output;
    }
}
