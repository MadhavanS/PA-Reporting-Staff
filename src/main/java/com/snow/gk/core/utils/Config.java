package com.snow.gk.core.utils;

import com.snow.gk.core.exception.CustomException;
import com.snow.gk.core.log.Logger;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Config {
    private Map<String, String> content;
    private static Config config;

    private Config() {
        Logger.trace("Creating config object");
        getConfigContent();
    }

    public static Config getConfig() {
        config = new Config();
        return config;
    }

    private Map<String, String> getConfigContent() {
        String propPath = FileUtility.getFileSeperatedPath("src/main/resources/config.properties");
        Logger.trace("Reading the config.xml file from " + propPath);

        content = new HashMap<>();

        try {
            FileReader reader = new FileReader(propPath);
            Properties properties = new Properties();
            properties.load(reader);

            Set<Map.Entry<Object, Object>> set = properties.entrySet();

            for(Map.Entry<Object, Object> entry : set)
            {
                content.put((String)entry.getKey(), (String)entry.getValue());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }

    public String getConfigProperty(String key) {
        Logger.trace("Try to fetch the content form Configuration file with key "+ key );
        if(null == key) {
            Logger.error("The specified '"+ key +"'configuartion property is not available");
            throw new CustomException("The required config value '"+ key +"' is not available");
        }
        return content.get(key);
    }
}
