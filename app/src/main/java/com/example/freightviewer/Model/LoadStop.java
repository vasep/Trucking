package com.example.freightviewer.Model;

public class LoadStop {
    private String id;
    private StopType stopType;
    private String dateTime;
    private String appNr;
    private String puNr;
    private String refNr;
    private String bol;
    private String company;
    private String contact;
    private String street;
    private String city;
    private USAStates state;
    private String zip;
    private String comment;
    private int status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public StopType getStopType() {
        return stopType;
    }

    public void setStopType(StopType stopType) {
        this.stopType = stopType;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getAppNr() {
        return appNr;
    }

    public void setAppNr(String appNr) {
        this.appNr = appNr;
    }

    public String getPuNr() {
        return puNr;
    }

    public void setPuNr(String puNr) {
        this.puNr = puNr;
    }

    public String getRefNr() {
        return refNr;
    }

    public void setRefNr(String refNr) {
        this.refNr = refNr;
    }

    public String getBol() {
        return bol;
    }

    public void setBol(String bol) {
        this.bol = bol;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public USAStates getState() {
        return state;
    }

    public void setState(USAStates state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
