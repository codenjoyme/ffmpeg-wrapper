package com.apofig.ffmpeg;

import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.mockito.ArgumentCaptor;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public abstract class AbstractRunnerTest {

    protected Runner runner;

    @Before
    public void setUp() {
        runner = mock(Runner.class);
    }

    public void assertExec(String expected) {
        ArgumentCaptor<String> commandCaptor = ArgumentCaptor.forClass(String.class);
        verify(runner, atLeastOnce()).execOutput(commandCaptor.capture());
        List<String> values = commandCaptor.getAllValues();
        assertEquals(expected, join(values));
    }

    public void assertFile(String file, String expected) throws IOException {
        List<String> values = IOUtils.readLines(new InputStreamReader(new FileInputStream(file), "utf-8"));
        assertEquals(expected, join(values));
    }

    private String join(List<String> values) {
        return values.stream().collect(Collectors.joining("\n"));
    }
}
