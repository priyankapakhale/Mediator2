package com.example.priyanka.mediator2.Models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by priyanka on 1/31/18.
 */

public class MedicineInfoResponse implements Serializable {

    @SerializedName("message")
    private String msg;

    @SerializedName("med_name")
    private String med_name;

    @SerializedName("med_use")
    private String med_use;

    public String getName() {return med_name;}
    public String getUse() {return med_use;}
    public String getMessage() {return msg;}

}
