package com.example.priyanka.mediator2.Models;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
/**
 * Created by priyanka on 1/25/18.
 */

public class MedicineRecord implements Serializable {

    @SerializedName("med_name")
    @Expose
    private String name;

    @SerializedName("use")
    @Expose
    private String use;

    @SerializedName("expiry_date")
    @Expose
    private String expiry_date;

    @SerializedName("dosage")
    @Expose
    private String dosage;

    @SerializedName("start_date")
    @Expose
    private String start_date;

    @SerializedName("end_date")
    @Expose
    private String end_date;

    private String duration;

    public String getName() {return name;}
    public String getUse() {return use;}
    public String getExpiryDate() {return expiry_date;}
    public String getDosage() {return dosage;}
    public String getStartDate() {return start_date;}
    public String getEndDate() {return end_date;}
    public String getDuration() {
        if (end_date.equalsIgnoreCase("2050-01-01")) {
            return "Everyday";
        } else
            return start_date + " - " + end_date;
    }

    public void setName(String name) {this.name=name;}
    public void setUse(String use) {this.use=use;}
    public void setExpiryDate(String expiry_date){this.expiry_date = expiry_date;}
    public void setDosage(String dosage){this.dosage=dosage;}
    public void setStartDate(String start_date){this.start_date= start_date;}
    public void setEndDate(String end_date){this.end_date=end_date;}
    public void setDuration(String duration){this.duration=duration;}


}
