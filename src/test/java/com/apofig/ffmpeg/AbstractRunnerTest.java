package com.apofig.ffmpeg;

import org.junit.Before;
import org.mockito.ArgumentCaptor;

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
        verify(runner, atLeastOnce()).exec(commandCaptor.capture());
        assertEquals(expected, commandCaptor.getAllValues().toString());
    }
}
