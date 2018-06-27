package com.anton.kursach.util;

public class GeneralTest {

    private String token;
    private Long subjId;
    private Long labId;
    private static GeneralTest instance;

    private GeneralTest(){}

    public static GeneralTest getInstance(){
        if (instance==null){
            instance=new GeneralTest();
        }
        return instance;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getSubjId() {
        return subjId;
    }

    public void setSubjId(Long subjId) {
        this.subjId = subjId;
    }

    public Long getLabId() {
        return labId;
    }

    public void setLabId(Long labId) {
        this.labId = labId;
    }

}
