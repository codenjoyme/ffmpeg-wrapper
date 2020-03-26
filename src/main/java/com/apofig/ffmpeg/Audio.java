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

        String output = video.replace(".", "-done.");
        String command = String.format(template,
                video,
                audio,
                output);

        runner.execOutput(command);
        return output;
    }

    public static void main(String[] args) {
        Audio worker = new Audio(new RunnerImpl());
        String video = "work/done.mp4";
        String audio = worker.extractWave(video);

        Worker.printWait("Please update '" + audio + "' audio, then press Enter.");

        String output = worker.join(video, audio);
        System.out.println("Please check result '" + output + "'");

    }
}
