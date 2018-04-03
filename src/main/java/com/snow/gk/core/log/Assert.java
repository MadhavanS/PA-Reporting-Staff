package com.snow.gk.core.log;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.testng.ITestResult;
import org.testng.Reporter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Assert extends org.testng.Assert {
    private static String ERROR_MESSAGE_PATTERN= "%1$s. See stacktrace of error [%2$s] in the end of the test ";

    private static void addVerificationFailure(Throwable e) {
        List<Throwable> verificationFailures = getVerificationFailures();
        getVerificationFailuresMap().put(Reporter.getCurrentTestResult(), verificationFailures);
        verificationFailures.add(e);
    }

    public static List<Throwable> getVerificationFailures() {
        List<Throwable> verificationFailures = getVerificationFailuresMap().get(Reporter.getCurrentTestResult());
        return verificationFailures == null ? new ArrayList<>() : verificationFailures;
    }

    public static void verifyTrue(boolean condition, String message) {
        try {
            assertTrue(condition, message);
        } catch(Throwable e) {
            addVerificationFailure(e);
            Logger.getLogger().log(LoggerLevel.VERIFY_FAILED, String.format(ERROR_MESSAGE_PATTERN, ExceptionUtils.getMessage(e), getVerificationFailures().size()));
        }
    }

    public static void verifyFalse(boolean condition) {
        try {
            assertFalse(condition);
        } catch(Throwable e) {
            addVerificationFailure(e);
            Logger.getLogger().log(LoggerLevel.VERIFY_FAILED, String.format(ERROR_MESSAGE_PATTERN, ExceptionUtils.getMessage(e), getVerificationFailures().size()));
        }
    }

    public static void verifyFalse(boolean condition, String message) {
        try {
            assertFalse(condition, message);
        } catch(Throwable e) {
            addVerificationFailure(e);
            Logger.getLogger().log(LoggerLevel.VERIFY_FAILED, String.format(ERROR_MESSAGE_PATTERN, ExceptionUtils.getMessage(e), getVerificationFailures().size()));
        }
    }

    public static void verifyEquals(boolean actual, boolean expected) {
        try {
            assertEquals(actual, expected);
        } catch(Throwable e) {
            addVerificationFailure(e);
            Logger.getLogger().log(LoggerLevel.VERIFY_FAILED, String.format(ERROR_MESSAGE_PATTERN, ExceptionUtils.getMessage(e), getVerificationFailures().size()));
        }
    }

    public static void verifyNotEquals(Object actual, Object expected, String message) {
        try {
            assertNotEquals(actual, expected, message);
        } catch(Throwable e) {
            addVerificationFailure(e);
            Logger.getLogger().log(LoggerLevel.VERIFY_FAILED, String.format(ERROR_MESSAGE_PATTERN, ExceptionUtils.getMessage(e), getVerificationFailures().size()));
        }
    }

    private static ThreadLocal<Map<ITestResult, List<Throwable>>> verificationFailuresMap = new ThreadLocal<Map<ITestResult, List<Throwable>>>(){
        @Override
        protected Map<ITestResult, List<Throwable>> initialValue() {
            return new HashMap<ITestResult, List<Throwable>>();
        }
    };

    private static Map<ITestResult, List<Throwable>> getVerificationFailuresMap(){
        return verificationFailuresMap.get();
    }
}
