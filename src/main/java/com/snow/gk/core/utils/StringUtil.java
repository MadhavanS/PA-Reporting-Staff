package com.snow.gk.core.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StringUtil {
    public static String capitalize(String sequences) {
        List<String> lst = Arrays.asList(sequences);
        List<String> result = new ArrayList<>();
        lst.forEach((c) -> {
            if(StringUtils.isNumeric(c)) {
                result.add(c.toString());
            } else {
                result.add(c.toUpperCase());
            }
        });
        String str = String.join("", result);


        return str;
    }
}
