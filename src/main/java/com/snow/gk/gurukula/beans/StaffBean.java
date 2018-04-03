package com.snow.gk.gurukula.beans;

import com.snow.gk.core.utils.StringUtil;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class StaffBean {
    private String name;
    private BranchBean branchBean;
    private int id;
    private List<String> lstOfBranches;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BranchBean getBranchBean() {
        return branchBean;
    }

    public void setBranchBean(BranchBean branchBean) {
        this.branchBean = branchBean;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<String> getLstOfBranches() {
        return lstOfBranches;
    }

    public void setLstOfBranches(List<String> lstOfBranches) {
        this.lstOfBranches = lstOfBranches;
    }

    public void setRandomStringToName() {
        int len = ThreadLocalRandom.current().nextInt(1, 50);
        String staffStaff = RandomStringUtils.randomAlphabetic(len);
        setName(staffStaff);
    }
}
