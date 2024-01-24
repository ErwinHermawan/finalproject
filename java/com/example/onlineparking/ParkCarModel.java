package com.example.onlineparking;

public class ParkCarModel {
    private String id;
    private String driverName;
    private String driverNumber;
    private String numberPlate;
    private String vehicleType;
    private String fee;
    private long time;
    private String userId;
    private String status;


    public ParkCarModel() {
    }

    public ParkCarModel(String id, String driverName, String driverNumber, String numberPlate, String vehicleType, String fee, long time, String userId,String status) {
        this.id = id;
        this.driverName = driverName;
        this.driverNumber = driverNumber;
        this.numberPlate = numberPlate;
        this.vehicleType = vehicleType;
        this.fee = fee;
        this.time = time;
        this.userId = userId;
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getDriverNumber() {
        return driverNumber;
    }

    public void setDriverNumber(String driverNumber) {
        this.driverNumber = driverNumber;
    }

    public String getNumberPlate() {
        return numberPlate;
    }

    public void setNumberPlate(String numberPlate) {
        this.numberPlate = numberPlate;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }


}
