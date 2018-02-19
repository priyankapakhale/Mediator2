package com.example.priyanka.mediator2.Adaptors;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.priyanka.mediator2.Models.MedicineRecord;
import com.example.priyanka.mediator2.Models.PickupRequest;
import com.example.priyanka.mediator2.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

/**
 * Created by priyanka on 1/30/18.
 */

public class PickupRequestsAdaptor extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private FirebaseAuth mAuth;
    private List<PickupRequest> records;
    private String email;
    public PickupRequestsAdaptor()
    {}

    public PickupRequestsAdaptor(List<PickupRequest> records)
    {
        this.records = records;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.pickup_requests_card,
                parent, false);
        PickupRequestsAdaptor.MRViewHolder vh = new PickupRequestsAdaptor.MRViewHolder(itemView);
        mAuth = FirebaseAuth.getInstance();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        email = currentUser.getEmail();
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder1, int position) {
        if (holder1 instanceof PickupRequestsAdaptor.MRViewHolder ) {

            final PickupRequestsAdaptor.MRViewHolder holder = (PickupRequestsAdaptor.MRViewHolder) holder1;
            Log.d("records",records.toString());

            if(records.size() > 0 && position < records.size()) {

                PickupRequest t = records.get(position);
                Log.d("pickuprequest",t.toString());

                holder.name.setText(t.getName());
                holder.status.setText(t.getStatus());
                holder.start_date.setText(t.getStartDate());
                holder.quantity.setText(t.getQuantity()+"x");

            }

        }


    }

    public class MRViewHolder extends RecyclerView.ViewHolder {

        protected TextView name;
        protected TextView start_date;
        protected TextView status;
        protected TextView quantity;

        public MRViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.med_name);
            start_date = itemView.findViewById(R.id.start_date);
            quantity = itemView.findViewById(R.id.quantity);
            status = itemView.findViewById(R.id.status);
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
