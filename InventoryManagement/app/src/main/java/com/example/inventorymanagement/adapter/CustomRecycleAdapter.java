package com.example.inventorymanagement.adapter;

import android.content.Context;
import android.content.Intent;
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
import com.example.inventorymanagement.view.ItemPurchased;
import com.example.inventorymanagement.view.MemoActivity;
import com.example.inventorymanagement.view.ProductListActivity;
import com.example.inventorymanagement.view.SalesActivity;
import com.example.inventorymanagement.view.StockActivity;

import java.util.List;

public class CustomRecycleAdapter extends RecyclerView.Adapter<CustomRecycleAdapter.ViewHolder> {

    private Context context;
    private List<OptionsModel> options;

    public CustomRecycleAdapter(Context context, List<OptionsModel> options) {
        this.context = context;
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
        * Calling the function to set action on click on the menu
        * */
        viewHolder.imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                optionsClicked(context, options.get(viewHolder.getAdapterPosition()));
            }
        });

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

    public static void optionsClicked(Context context, OptionsModel currentOption) {
//        if (currentOption.getOptionsDescription().equals("Products")) {
//            Intent productListIntent = new Intent(context, ProductListActivity.class);
//            context.startActivity(productListIntent);
//        } else if (currentOption.getOptionsDescription().equals("Purchases")) {
//            Intent purchaseIntent = new Intent(context, ItemPurchased.class);
//            context.startActivity(purchaseIntent);
//        }
//        else if (currentOption.getOptionsDescription().equals("Memo")) {
//            Intent memoIntent = new Intent(context, MemoActivity.class);
//            context.startActivity(memoIntent);
//        } else if (currentOption.getOptionsDescription().equals("Sales")) {
//            Intent salesIntent = new Intent(context, SalesActivity.class);
//            context.startActivity(salesIntent);
//        } else if (currentOption.getOptionsDescription().equals("Stock")) {
//            Intent stockIntent = new Intent(context, StockActivity.class);
//            context.startActivity(stockIntent);
//        }
    }
}
