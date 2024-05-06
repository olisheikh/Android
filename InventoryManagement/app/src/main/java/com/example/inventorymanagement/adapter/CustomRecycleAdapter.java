package com.example.inventorymanagement.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inventorymanagement.R;
import com.example.inventorymanagement.models.OptionsModel;

import java.util.List;

public class CustomRecycleAdapter extends RecyclerView.Adapter<CustomRecycleAdapter.ViewHolder> {

    private List<OptionsModel> options;

    public CustomRecycleAdapter(List<OptionsModel> options) {
        this.options = options;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_recycler_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return options.size();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        OptionsModel currentOption = options.get(position);

        /*
        * Calling the function to set different background color
        * */
        recyclerViewBackground(viewHolder, position);
        /*
        * Calling text & image view to set the text & image from dataset
        * */
        viewHolder.txtView.setText(currentOption.getOptionsDescription());
        viewHolder.imgView.setImageResource(currentOption.getOptionsImgSource());

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtView;
        ImageView imgView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtView = itemView.findViewById(R.id.txtGridView);
            imgView = itemView.findViewById(R.id.imgGridView);
        }
    }

    /*
    * Function which gonna change the background of the view(itemView) according
    * to their position
    * */
    public static void recyclerViewBackground(ViewHolder viewHolder, int pos) {
        if (pos % 2 == 0) {
            viewHolder.itemView.setBackgroundColor(Color.parseColor("#E0E78A"));
        } else {
            viewHolder.itemView.setBackgroundColor(Color.parseColor("#C3FF43"));
        }
    }
}
