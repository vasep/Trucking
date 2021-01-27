package com.example.trucking.Model;


public class LoadModel {
    private Long id;
    private LoadCustomerDTO customer;
    private String customerLoad;
    private String customerContact;
    private LoadStatus loadStatus;
    private Double freightRate;
    private Double tripRate;
    private Double temperature;
    private TruckLoadDTO truck;
    private DriverTruckDTO driver;
    private DriverTruckDTO driver2;
    private EquipmentTruckDTO trailer;
    private Double emptyMiles;
    private Double loadedMiles;
    private Long insertTime;
    private Long updateTime;
    private LoadStops loadStops;
    private String dateOfInvoicing;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LoadCustomerDTO getCustomer() {
        return customer;
    }

    public void setCustomer(LoadCustomerDTO customer) {
        this.customer = customer;
    }

    public String getCustomerLoad() {
        return customerLoad;
    }

    public void setCustomerLoad(String customerLoad) {
        this.customerLoad = customerLoad;
    }

    public String getCustomerContact() {
        return customerContact;
    }

    public void setCustomerContact(String customerContact) {
        this.customerContact = customerContact;
    }

    public LoadStatus getLoadStatus() {
        return loadStatus;
    }

    public void setLoadStatus(LoadStatus loadStatus) {
        this.loadStatus = loadStatus;
    }

    public Double getFreightRate() {
        return freightRate;
    }

    public void setFreightRate(Double freightRate) {
        this.freightRate = freightRate;
    }

    public Double getTripRate() {
        return tripRate;
    }

    public void setTripRate(Double tripRate) {
        this.tripRate = tripRate;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public TruckLoadDTO getTruck() {
        return truck;
    }

    public void setTruck(TruckLoadDTO truck) {
        this.truck = truck;
    }

    public DriverTruckDTO getDriver() {
        return driver;
    }

    public void setDriver(DriverTruckDTO driver) {
        this.driver = driver;
    }

    public DriverTruckDTO getDriver2() {
        return driver2;
    }

    public void setDriver2(DriverTruckDTO driver2) {
        this.driver2 = driver2;
    }

    public EquipmentTruckDTO getTrailer() {
        return trailer;
    }

    public void setTrailer(EquipmentTruckDTO trailer) {
        this.trailer = trailer;
    }

    public Double getEmptyMiles() {
        return emptyMiles;
    }

    public void setEmptyMiles(Double emptyMiles) {
        this.emptyMiles = emptyMiles;
    }

    public Double getLoadedMiles() {
        return loadedMiles;
    }

    public void setLoadedMiles(Double loadedMiles) {
        this.loadedMiles = loadedMiles;
    }

    public Long getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(Long insertTime) {
        this.insertTime = insertTime;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    public LoadStops getLoadStops() {
        return loadStops;
    }

    public void setLoadStops(LoadStops loadStops) {
        this.loadStops = loadStops;
    }

    public String getDateOfInvoicing() {
        return dateOfInvoicing;
    }

    public void setDateOfInvoicing(String dateOfInvoicing) {
        this.dateOfInvoicing = dateOfInvoicing;
    }
}