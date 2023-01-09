package com.chobo.please;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Review {
    public String getReview_text() {return review_text;}

    public void setReview_text(String review_text) {this.review_text = review_text;}


    public String getDate() {return date;}

    public String getMarket_name() {return market_name;}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDate(String date) {this.date = date;}

    public void setMarket_name(String market_name) {this.market_name = market_name;}

    private String id, date, market_name, review_text;
}
