package com.example.priyanka.mediator2.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.priyanka.mediator2.Adaptors.MedicineRecordAdaptor;
import com.example.priyanka.mediator2.Models.MedicineRecord;
import com.example.priyanka.mediator2.Models.MedicineRecordResponse;
import com.example.priyanka.mediator2.Network.ApiClient;
import com.example.priyanka.mediator2.Network.ApiInterface;
import com.example.priyanka.mediator2.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by priyanka on 1/25/18.
 */

public class MedicineRecordFragment extends Fragment {
    private FirebaseAuth mAuth;
    private MedicineRecordAdaptor mrAdaptor;
    private Context context;
    private RecyclerView recList;
    private LinearLayout coordinatorLayout;
    private String email;
    public MedicineRecordFragment()
    {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        // Inflate the layout for this fragment
        context = getContext();
        final View view = inflater.inflate(R.layout.medicine_records_fragment, container, false);
        mAuth = FirebaseAuth.getInstance();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        email = currentUser.getEmail();
        //recList.addItemDecoration(new SimpleListDividerDecorator(ContextCompat.getDrawable(this, R.drawable.list_divider), true));

        recList = (RecyclerView) view.findViewById(R.id.medicine_records_list);
        coordinatorLayout = view.findViewById(R.id.coordinator_layout);
        recList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(view.getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);
        recList.setItemAnimator(new DefaultItemAnimator());
        recList.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));

        // making http call and fetching menu json
        //TODO: Code to call another activity for showing the transaction full details will be called from here



        //Calling Server
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<MedicineRecordResponse> call = apiService.getMedicineRecords(email); //change user id later
        call.enqueue(new Callback<MedicineRecordResponse>() {
            @Override
            public void onResponse(Call<MedicineRecordResponse> call, Response<MedicineRecordResponse> response) {
                List<MedicineRecord> thisa = response.body().getMedicineRecords();

                mrAdaptor = new MedicineRecordAdaptor(thisa);
                recList.setAdapter(mrAdaptor);

            }

            @Override
            public void onFailure(Call<MedicineRecordResponse> call, Throwable t) {

            }
        });


        return view;

    }

}
