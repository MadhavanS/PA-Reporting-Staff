package com.snow.gk.core.log;

import org.apache.log4j.Level;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class LoggerLevel extends Level {

    protected LoggerLevel( int level, String levelStr, int syslogEquivalent )
    {
        super( level, levelStr, syslogEquivalent );
        LOGGER_LEVELS.put( level, this );
    }

    final static public int VERIFY_FAILED_INT = FATAL_INT - 1;

    final static public Level VERIFY_FAILED = new LoggerLevel( VERIFY_FAILED_INT, "VERIFY_FAILED", 0 );



    private static final Map<Integer, LoggerLevel> LOGGER_LEVELS;

    static
    {
        LOGGER_LEVELS = Collections.synchronizedMap( new HashMap<Integer, LoggerLevel>() );
    }
}
