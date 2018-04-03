package com.snow.gk.core.exception;

import com.snow.gk.core.log.Logger;

public class CustomException extends RuntimeException {
    final String errorMessage;

    public CustomException(String message) {
        super(message);
        errorMessage = message;
    }


    /**
     * Creates an instance of the class.
     *
     * @param page
     *            the page
     * @param object
     *            the object
     */
    public CustomException(String page, String object) {
        super();
        StringBuilder message = new StringBuilder();

        try {
            if((page != null) && !("".equals(page))  && (object != null) && !("".equals(object)) ) {
                String key = page.toUpperCase() + " . " + object.toUpperCase();
                message.append(key);
            }
        } catch (Exception e) {
            message.append(e.getMessage());
            Logger.error("Exception occurred in framework exception constructor ", e);
        }
        this.errorMessage = message.toString();
    }


    public CustomException(Throwable e) {
        super(e.getLocalizedMessage().split("\n")[0]);
        errorMessage = e.getMessage();
    }

    public CustomException(String page, Throwable e) {
        super(page, e);
        errorMessage = e.getMessage();
    }
}
