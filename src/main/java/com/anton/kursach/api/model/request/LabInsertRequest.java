package com.anton.kursach.api.model.request;

public class LabInsertRequest {

    private String name;

    private Long deadline;

    private Long days;

    private boolean need;

    private Long subjId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getDeadline() {
        return deadline;
    }

    public void setDeadline(Long deadline) {
        this.deadline = deadline;
    }

    public Long getDays() {
        return days;
    }

    public void setDays(Long days) {
        this.days = days;
    }

    public boolean isNeed() {
        return need;
    }

    public void setNeed(boolean need) {
        this.need = need;
    }

    public Long getSubjId() {
        return subjId;
    }

    public void setSubjId(Long subjId) {
        this.subjId = subjId;
    }

}
