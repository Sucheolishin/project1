package com.chobo.please;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private String id, password;
}
