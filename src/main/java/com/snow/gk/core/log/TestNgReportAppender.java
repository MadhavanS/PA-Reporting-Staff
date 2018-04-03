package com.snow.gk.core.log;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.spi.LoggingEvent;
import org.testng.Reporter;

public class TestNgReportAppender extends AppenderSkeleton {
    @Override
    protected void append(LoggingEvent event) {
        String log = this.layout.format(event);
        log = log.replaceAll("\n", "<br>\n");
        Reporter.log(log);
    }

    @Override
    public void close() {
        //Not in use
    }

    @Override
    public boolean requiresLayout() {
        return true;
    }
}
