package com.example.priyanka.mediator2.Models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by priyanka on 1/28/18.
 */

public class PickupRequestResponse implements Serializable {

    @SerializedName("pickup_request_list")
    private List<PickupRequest> requestList;

    public List<PickupRequest> getPickupRequests() {
        return requestList;
    }

    public void setPickupRequests(List<PickupRequest> requestList) {
        this.requestList = requestList;
    }

}
