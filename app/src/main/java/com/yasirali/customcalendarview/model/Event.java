package com.yasirali.customcalendarview.model;

public class Event {

    private String id;
    private String text;
    private String description;
    private String start_date;
    private String end_date;
    private String location;
    private String rec_type;
    private String rec_pattern;
    private String event_length;
    private String is_clinical;
    private String is_rec;
    private String eventType;
    private String color;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStartDate() {
        return start_date;
    }

    public void setStartDate(String start_date) {
        this.start_date = start_date;
    }

    public String getEndDate() {
        return end_date;
    }

    public void setEndDate(String end_date) {
        this.end_date = end_date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getRecType() {
        return rec_type;
    }

    public void setRecType(String rec_type) {
        this.rec_type = rec_type;
    }

    public String getRecPattern() {
        return rec_pattern;
    }

    public void setRecPattern(String rec_pattern) {
        this.rec_pattern = rec_pattern;
    }

    public String getEventLength() {
        return event_length;
    }

    public void setEventLength(String event_length) {
        this.event_length = event_length;
    }

    public String getIsClinical() {
        return is_clinical;
    }

    public void setIsClinical(String is_clinical) {
        this.is_clinical = is_clinical;
    }

    public String getIsRec() {
        return is_rec;
    }

    public void setIsRec(String is_rec) {
        this.is_rec = is_rec;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
