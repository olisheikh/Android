package com.example.inventorymanagement.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inventorymanagement.R;
import com.example.inventorymanagement.models.SelectedItem;

import java.text.SimpleDateFormat;
import java.util.List;

public class SalesAdapter extends RecyclerView.Adapter<SalesAdapter.SalesViewHolder>{
    private Context context;
    private List<SelectedItem> selectedItems;

    public SalesAdapter(Context context, List<SelectedItem> selectedItems) {
        this.context = context;
        this.selectedItems = selectedItems;
    }

    @NonNull
    @Override
    public SalesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sales_recycler_view, parent, false);
        return new SalesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SalesViewHolder holder, int position) {
        SelectedItem itemSold = selectedItems.get(position);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMMM, yyyy");
        String dateStr = simpleDateFormat.format(itemSold.getPurchasedDate());

        holder.salesSerialNumber.setText((position + 1) + ".");
        holder.salesName.setText(itemSold.getItemName());
        holder.salesQuantity.setText(itemSold.getQuantity() + "");
        holder.salesDate.setText(dateStr);
        holder.salesAmount.setText(itemSold.getTotal() + "");
    }

    @Override
    public int getItemCount() {
        return selectedItems.size();
    }

    public class SalesViewHolder extends RecyclerView.ViewHolder {
        TextView salesSerialNumber, salesName, salesQuantity, salesDate, salesAmount;
        public SalesViewHolder(View view) {
            super(view);

            salesSerialNumber =  view.findViewById(R.id.saleItemSerialNum);
            salesName = view.findViewById(R.id.saleItemName);
            salesQuantity = view.findViewById(R.id.salesQuantity);
            salesDate = view.findViewById(R.id.salesDate);
            salesAmount = view.findViewById(R.id.salesAmount);
        }
    }
}
