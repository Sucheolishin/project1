package com.chobo.please;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class Post {
    public int getMarket_id() {return market_id;}

    public void setMarket_id(int market_id) {this.market_id = market_id;}

    public String getMarket_name() {return market_name;}

    public void setMarket_name(String market_name) {this.market_name = market_name;}

    public double getLongitude() {return longitude;}

    public void setLongitude(double longitude) {this.longitude = longitude;}

    public double getLatitude() {return latitude;}

    public void setLatitude(double latitude) {this.latitude = latitude;}

    public void setOpen_time(String open_time) {
        this.open_time = open_time;
    }

    public void setMarket_Info(String market_Info) {
        this.market_Info = market_Info;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public String getOpen_time() {return open_time;}

    public String getMarket_Info() {return market_Info;}

    private String market_name, open_time, market_Info, details, id;
    private int market_id, category;
    private double longitude, latitude;
}
