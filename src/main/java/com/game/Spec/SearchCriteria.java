package com.game.Spec;

public class SearchCriteria {
    private String key;
    private String operation;
    private String type;
    private Object value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public SearchCriteria(String key, String operation, String type, Object value) {
        this.key = key;
        this.operation = operation;
        this.type = type;
        this.value = value;
    }
}
