package com.google.eca;

public class UserDetails {

    String name;
    String year;
    String sec;
    String phone;
    String email;

    public UserDetails() {
    }

    public UserDetails(String name, String year, String sec, String phone, String email) {
        this.name = name;
        this.year = year;
        this.sec = sec;
        this.phone = phone;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String postId) {
        this.name = name;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String strBookName) {
        this.year = year;
    }

    public String getSec() {
        return sec;
    }

    public void setSet(String strLoc) {
        this.sec = sec;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String strCategory) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String strSubcat) {
        this.email = email;
    }


}
