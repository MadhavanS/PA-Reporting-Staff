package com.snow.gk.core.utils;

import java.io.File;

public class FileUtility {
    private FileUtility(){}

    public static String getProjectRootPath() {
        return System.getProperty("user.dir");
    }

    // Get File Separated Path
    public static String getFileSeperatedPath(String slashedPath) {
        String fullPath = getProjectRootPath() + File.separator + slashedPath;
        return fullPath.replace("/", File.separator);
    }
}
