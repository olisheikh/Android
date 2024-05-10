package com.example.inventorymanagement.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inventorymanagement.R;
import com.example.inventorymanagement.models.SelectedItem;

import java.util.List;

public class SelectedItemRecycler extends RecyclerView.Adapter<SelectedItemRecycler.SelectedItemViewHolder>{

    List<SelectedItem> selectedItems;
    Context context;

    public SelectedItemRecycler(List<SelectedItem> selectedItems, Context context) {
        this.selectedItems = selectedItems;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return selectedItems.size();
    }

    @Override
    public SelectedItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.customer_selected_items_recyclerv, viewGroup, false);
        SelectedItemViewHolder selectedItemViewHolder = new SelectedItemViewHolder(view);

        return selectedItemViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SelectedItemViewHolder selectedItemViewHolder, int position) {
        SelectedItem selectedItem = selectedItems.get(position);

        selectedItemViewHolder.productNameTxtV.setText(selectedItem.getItemName());
        selectedItemViewHolder.productQuantityTxtV.setText(selectedItem.getQuantity() + "");
        selectedItemViewHolder.priceTxtV.setText(selectedItem.getPrice() + "");
        selectedItemViewHolder.totalTxtV.setText(selectedItem.getTotal() + "");
    }
    public class SelectedItemViewHolder extends RecyclerView.ViewHolder {
        TextView productNameTxtV, productQuantityTxtV, priceTxtV, totalTxtV;
        ImageView delImgView, editImgView;
        public SelectedItemViewHolder(View itemView) {
            super(itemView);

            productNameTxtV = itemView.findViewById(R.id.itemNameInRecyclerV);
            delImgView = itemView.findViewById(R.id.delSalesItem);
            editImgView = itemView.findViewById(R.id.editSalesItem);
            productQuantityTxtV = itemView.findViewById(R.id.quantitySalesItem);
            priceTxtV = itemView.findViewById(R.id.priceSalesItem);
            totalTxtV = itemView.findViewById(R.id.totalPriceSalesItem);
        }
    }
}
