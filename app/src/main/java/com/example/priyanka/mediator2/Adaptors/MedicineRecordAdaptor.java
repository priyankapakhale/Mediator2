package com.example.priyanka.mediator2.Adaptors;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.priyanka.mediator2.Fragments.MedicineRecordFragment;
import com.example.priyanka.mediator2.Models.MedicineRecord;
import com.example.priyanka.mediator2.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by priyanka on 1/25/18.
 */

public class MedicineRecordAdaptor extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<MedicineRecord> records;

    public MedicineRecordAdaptor()
    {}

    public MedicineRecordAdaptor(List<MedicineRecord> records)
    {
        this.records = records;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.medicine_records_card,
                parent, false);
        MedicineRecordAdaptor.MRViewHolder vh = new MedicineRecordAdaptor.MRViewHolder(itemView);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder1, int position) {
        if (holder1 instanceof MedicineRecordAdaptor.MRViewHolder ) {

            final MedicineRecordAdaptor.MRViewHolder holder = (MedicineRecordAdaptor.MRViewHolder) holder1;
            Log.d("records",records.toString());

            if(records.size() > 0 && position < records.size()) {

                MedicineRecord t = records.get(position);
                Log.d("medicinerecord",t.toString());

                holder.name.setText(t.getName());
                holder.use.setText(t.getUse());
                holder.dosage.setText(t.getDosage()+ " times a day");
                holder.expiry_date.setText("Expiry date : "+t.getExpiryDate());
                holder.duration.setText(t.getDuration());

                /*
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //2017-02-06T09:43:37.203
                simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
                TimeZone tz = TimeZone.getTimeZone("IST");
                SimpleDateFormat destFormat = new SimpleDateFormat("dd MMM hh:mm a");
                destFormat.setTimeZone(tz);

                try {
                    String updatedtime = t.getDate();
                    Date myDate = simpleDateFormat.parse(updatedtime);
                    String formatted_date = destFormat.format(myDate);

                    holder.date.setText(formatted_date);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                */

            }

        }


    }

    public class MRViewHolder extends RecyclerView.ViewHolder {

        protected TextView name;
        protected TextView barcode;
        protected TextView use;
        protected TextView dosage;
        protected TextView duration;
        protected TextView expiry_date;

        public MRViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.med_name);
            expiry_date = itemView.findViewById(R.id.expiry_date);
            use = itemView.findViewById(R.id.use);
            duration = itemView.findViewById(R.id.duration);
            dosage = itemView.findViewById(R.id.dosage);
        }
    }

    @Override
    public int getItemCount() {
        if (records == null) {
            return 0;
        }

        if (records.size() == 0) {
            return 0;
        }

        // Add extra view to show the footer view
        return records.size();
    }

}
