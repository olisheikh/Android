package com.example.inventorymanagement.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inventorymanagement.fragment.OptionsOfCategoryFrag;
import com.example.inventorymanagement.models.CategoryOfGridView;
import com.example.inventorymanagement.R;

import java.util.List;

public class CustomRecycleAdapter extends RecyclerView.Adapter<CustomRecycleAdapter.ViewHolder> {

    private List<CategoryOfGridView> categories;

    public CustomRecycleAdapter(List<CategoryOfGridView> categories) {
        this.categories = categories;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_grid, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        CategoryOfGridView categoryOfGridView = categories.get(position);
        viewHolder.txtView.setText(categoryOfGridView.getCategoryName());
        viewHolder.imgView.setImageResource(categoryOfGridView.getImgSource());

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtView;
        ImageView imgView;
        CardView cdView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtView = itemView.findViewById(R.id.txtGridView);
            imgView = itemView.findViewById(R.id.imgGridView);
        }
    }

}
