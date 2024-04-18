package com.example.inventorymanagement.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.inventorymanagement.R;
import com.example.inventorymanagement.adapter.OptionsRecycleViewAdp;
import com.example.inventorymanagement.models.OptionsModel;

import java.util.ArrayList;
import java.util.List;

public class OptionsOfCategoryFrag extends Fragment {
    private RecyclerView optionsRecyclerView;
    private OptionsRecycleViewAdp optionsRecycleViewAdp;
    private List<OptionsModel> optionsModel;
    private int imgResource;
    private String title;

    public OptionsOfCategoryFrag(int imgResource, String title) {
        this.imgResource = imgResource;
        this.title = title;
    }

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle save) {
        View rootView = inflater.inflate(R.layout.fragment_options_of_category, container, false);

        optionsRecyclerView = rootView.findViewById(R.id.optionsOfCategoryFRV);
        optionsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        optionsModel = new ArrayList<>();
        optionsModel.add(new OptionsModel(imgResource, title));

        optionsRecycleViewAdp = new OptionsRecycleViewAdp(optionsModel);

        optionsRecyclerView.setAdapter(optionsRecycleViewAdp);

        return rootView;
    }

}