package com.nandohusni.sayaguru.ui.home.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nandohusni.sayaguru.R;
import com.nandohusni.sayaguru.network.NetworkClient;
import com.nandohusni.sayaguru.ui.home.adapter.PaketAdapter;
import com.nandohusni.sayaguru.ui.home.model.DataItem;
import com.nandohusni.sayaguru.ui.home.model.ResultRequest;
import com.nandohusni.sayaguru.utils.SessionManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    RecyclerView recyclerView;


    public HomeFragment() {
    }

    SessionManager sesi;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sesi = new SessionManager(getContext());
        recyclerView = view.findViewById(R.id.paketrecyelrview);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        getData();

    }


    private void getData() {

        NetworkClient.service.actionRequest(sesi.getIdUser()).enqueue(new Callback<ResultRequest>() {
            @Override
            public void onResponse(Call<ResultRequest> call, Response<ResultRequest> response) {


                if(response.isSuccessful()){
                    Boolean status = response.body().isStatus();

                    if(status){

                        List<DataItem> data = response.body().getData();

                        showData(data);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResultRequest> call, Throwable t) {

            }
        });

    }

    private void showData(List<DataItem> data) {
        recyclerView.setAdapter(new PaketAdapter(data, getContext()));
    }
}
