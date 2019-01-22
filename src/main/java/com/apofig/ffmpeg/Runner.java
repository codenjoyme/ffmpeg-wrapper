package com.apofig.ffmpeg;

import java.util.List;

public interface Runner {
    List<String> exec(String command);

    default void execOutput(String command) {
        System.out.println(command);
        List<String> messages = this.exec(command);
        messages.forEach(System.out::println);
        System.out.println("-------------------------------------------------------");
    }
}
