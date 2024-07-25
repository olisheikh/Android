package com.example.inventorymanagement.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inventorymanagement.R;
import com.example.inventorymanagement.models.PurchasedItems;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class PurchasedItemAdapter extends RecyclerView.Adapter<PurchasedItemAdapter.PurchasedViewHolder>{
    List<PurchasedItems> purchasedItems;
    Context context;

    public PurchasedItemAdapter(Context context, List<PurchasedItems> purchasedItems) {
        this.context = context;
        this.purchasedItems = purchasedItems;
    }

    @Override
    public int getItemCount() {
        return purchasedItems.size();
    }

    @Override
    public PurchasedViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.purchased_item_list_recycler, viewGroup, false);
        PurchasedViewHolder viewHoler = new PurchasedViewHolder(view);

        return viewHoler;
    }

    @NonNull
    @Override
    public void onBindViewHolder(@NonNull PurchasedViewHolder purchasedViewHolder, int pos) {
            PurchasedItems purchasedItem = purchasedItems.get(pos);

            purchasedViewHolder.nameOfTheProductTxt.setText(purchasedItem.getProductName());
            purchasedViewHolder.serialNumberTxt.setText(pos + 1 + ".");
            purchasedViewHolder.totalNumTxt.setText(purchasedItem.getQuantity() + "");

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMMM, yyyy");
            String dateString = simpleDateFormat.format(new Date(String.valueOf(purchasedItem.getPurchasedDate())));
            purchasedViewHolder.date.setText(dateString);
            purchasedViewHolder.optionsImgView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PopupMenu popupmenu = new PopupMenu(context, purchasedViewHolder.optionsImgView);
                    popupmenu.inflate(R.menu.edit_delete);
                    popupmenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {

                            if (menuItem.getItemId() == R.id.edit) {
                                Toast.makeText(context.getApplicationContext(), "Edited", Toast.LENGTH_SHORT);
                            } else if (menuItem.getItemId() == R.id.delete) {
                                Toast.makeText(context.getApplicationContext(), "Deleted", Toast.LENGTH_SHORT);
                            }
                            return false;
                        }
                    });
                    popupmenu.show();
                }
            });
    }
    public class PurchasedViewHolder extends RecyclerView.ViewHolder {
        TextView serialNumberTxt, nameOfTheProductTxt, totalNumTxt, date;
        ImageView optionsImgView;
        public PurchasedViewHolder(View view) {
            super(view);

            serialNumberTxt = view.findViewById(R.id.purchasedItemSerialNum);
            nameOfTheProductTxt = view.findViewById(R.id.purchasesItemName);
            totalNumTxt = view.findViewById(R.id.purchasesItemQuantityRV);
            optionsImgView = view.findViewById(R.id.optionForEditDelete);
            date = view.findViewById(R.id.purchasedDateInRecyclerView);
        }
    }
}
