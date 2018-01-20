package com.tochka.testtask.domain;

public enum ResourceType {

    HTML(0),
    RSS(1);

    private final int id;

    ResourceType(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static ResourceType fromId(int id){
        if (id == 0) {
            return HTML;
        } else if (id == 1) {
            return RSS;
        }
        return HTML;
    }
}
