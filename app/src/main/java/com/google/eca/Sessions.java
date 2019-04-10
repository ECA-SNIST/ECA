package com.google.eca;

public  class Sessions {
    private String title;
    private String description;
    private String status;
    private String start_date;

    // Default constructor required for calls to
    // DataSnapshot.getValue(Sessions.class)
    public Sessions() {
    }

    public Sessions(String status,String title,String description,String start_date) {
        this.status = status;
        this.start_date=start_date;
        this.title=title;
        this.description=description;
    }

    public void setTitle(String title){
        this.title=title;
    }

    public String getTitle() {
        return title;
    }

    public void setDescription(String description){
        this.description=description;
    }

    public String getDescription() {
        return description;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
    public void setStart_date(String start_date){
        this.start_date=start_date;
    }

    public String getStart_date() {
        return start_date;
    }



}
