package com.project.community.domain.study.enums;

public enum ContactType {

    KAKAO("카카오톡 오픈채팅"), EMAIL("이메일"), GOOGLE("구글 폼");

    private final String label;

    ContactType(String label) {
        this.label = label;
    }

    public String label(){
        return label;
    }
}
