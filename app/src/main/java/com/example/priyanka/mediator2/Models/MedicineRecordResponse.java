package com.example.priyanka.mediator2.Models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by priyanka on 1/25/18.
 */

public class MedicineRecordResponse implements Serializable {

    @SerializedName("medicine_record_list")
    private List<MedicineRecord> recordList;

    public List<MedicineRecord> getMedicineRecords() {
        return recordList;
    }

    public void setMedicineRecords(List<MedicineRecord> records) {
        this.recordList = records;
    }

}
