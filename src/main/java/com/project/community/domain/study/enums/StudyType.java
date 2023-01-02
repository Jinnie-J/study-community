package com.project.community.domain.study.enums;

public enum StudyType {

    PROJECT("프로젝트"), STUDY("스터디");

    private final String label;

    StudyType(String label) {
        this.label = label;
    }

    public String label(){
        return label;
    }
}
