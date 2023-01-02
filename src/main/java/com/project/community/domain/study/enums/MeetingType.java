package com.project.community.domain.study.enums;

public enum MeetingType {

    ONLINE("온라인"), OFFLINE("오프라인");

    private final String label;

    MeetingType(String label) {
        this.label = label;
    }

    public String label(){
        return label;
    }
}
