package com.snow.gk.gurukula.beans;

import com.snow.gk.core.utils.StringUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

public class BranchBean {
    private String name;
    private String code;
    private int id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String setRandomStringToName(int len) {
        String branchName = RandomStringUtils.randomAlphabetic(len);
        setName(branchName);
        return branchName;
    }

    public String setRandomStringToCode(int len) {
        String branchCode = RandomStringUtils.randomAlphabetic(1).toUpperCase() +
                StringUtil.capitalize(RandomStringUtils.randomAlphanumeric(len-1));
        setCode(branchCode);
        return branchCode;
    }
}
