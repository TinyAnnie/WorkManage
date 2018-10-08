package com.example.tinyannie.workmanage;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class JobInWeek {
private String title;
private String description;
private Date dateFinish;
private Date hourFinish;
//get set
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDateFinish(Date dateFinish) {
        this.dateFinish = dateFinish;
    }

    public void setHourFinish(Date hourFinish) {
        this.hourFinish = hourFinish;
    }

    public String getDescription() {
        return description;
    }

    public Date getDateFinish() {
        return dateFinish;
    }

    public Date getHourFinish() {
        return hourFinish;
    }
//constructor
    public JobInWeek(String title, String description, Date dateFinish,
                     Date hourFinish) {
        super();
        this.title = title;
        this.description = description;
        this.dateFinish = dateFinish;
        this.hourFinish = hourFinish;
    }
//default constructor
    public JobInWeek(){
        super();
    }

    public String getDateFormat(Date d){
        SimpleDateFormat dft=new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        return dft.format(d);
    }
    public String getHourFormat(Date d){
        SimpleDateFormat dft=new SimpleDateFormat("hh:mm a",Locale.getDefault());
        return dft.format(d);
    }
    public String toString(){
        return this.title+"-"+getDateFormat(this.dateFinish)+"-"+getDateFormat(this.hourFinish);
    }

}
