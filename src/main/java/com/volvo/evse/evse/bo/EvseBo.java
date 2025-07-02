package com.volvo.evse.evse.bo;

public class EvseBo {
    private int id;
    private String evseCode;
    private String status;
    private int locationId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEvseCode() {
        return evseCode;
    }

    public void setEvseCode(String evseCode) {
        this.evseCode = evseCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }
}
