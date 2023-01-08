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

    public String getOpen_time() {return open_time;}

    public String getMarket_Info() {return market_Info;}

    private String market_name, open_time, market_Info;
    private int market_id;
    private double longitude, latitude;
}
