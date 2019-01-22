package com.apofig.ffmpeg;

import java.io.IOException;
import java.util.List;

public interface Runner {
    List<String> exec(String command);
}
