package com.example.inventorymanagement.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inventorymanagement.R;
import com.example.inventorymanagement.models.OptionsModel;

import java.util.List;

public class OptionsRecycleViewAdp extends RecyclerView.Adapter<OptionsRecycleViewAdp.ViewHolder>{

    private List<OptionsModel> optionsModels;

    public OptionsRecycleViewAdp(List<OptionsModel> optionsModels) {
        this.optionsModels = optionsModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.options_of_category, parent, true);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        OptionsModel options = optionsModels.get(position);
        viewHolder.imageView.setImageResource(options.getOptionsImgSource());
        viewHolder.textView.setText(options.getOptionsDescription());
    }

    @Override
    public int getItemCount() {
        return optionsModels.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.optionsLogo);
            textView = itemView.findViewById(R.id.optionsTextView);
        }
    }
}
