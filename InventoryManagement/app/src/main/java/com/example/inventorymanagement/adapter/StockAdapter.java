package com.example.inventorymanagement.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inventorymanagement.R;
import com.example.inventorymanagement.models.Stock;

import java.util.ArrayList;
import java.util.List;

public class StockAdapter extends RecyclerView.Adapter<StockAdapter.StockViewHolder> implements Filterable {

    Context context;
    List<Stock> stocks;
    List<Stock> filterStocks;

    public StockAdapter(Context context, List<Stock> stocks) {
        this.context = context;
        this.stocks = stocks;
        this.filterStocks = new ArrayList<> (stocks);
    }

    @NonNull
    @Override
    public StockViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.stock_product_list_view, viewGroup, false);
        return new StockViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StockViewHolder stockViewHolder, int position) {
        Stock stockSingleItem = filterStocks.get(position);

        stockViewHolder.stockItemSerialNumber.setText(position + 1 + ".");
        stockViewHolder.stockItemName.setText(stockSingleItem.getProductName());
        stockViewHolder.stockItemQuantity.setText(stockSingleItem.getProductAvailable() + "");
    }

    @Override
    public int getItemCount() {
        return filterStocks.size();
    }

    @Override
    public Filter getFilter() {
        return itemFilter;
    }

    private Filter itemFilter = new Filter() {
      @Override
      protected FilterResults performFiltering(CharSequence charSequence) {
          List<Stock> filteredList = new ArrayList<> ();

          if (charSequence == null || charSequence.length() == 0) {
              filteredList.addAll(stocks);
          } else {
              String filterPattern = charSequence.toString().toLowerCase().trim();

              for (Stock stock: stocks) {
                  if (stock.getProductName().toLowerCase().contains(filterPattern)) {
                      filteredList.add(stock);
                  }
              }
          }
          FilterResults filterResults = new FilterResults();
          filterResults.values = filteredList;
          return filterResults;
      }
        @Override
        public void publishResults(CharSequence charSequence, FilterResults filterResults) {
            filterStocks.clear();
            filterStocks.addAll((List) filterResults.values);
            notifyDataSetChanged();
        }
    };
    public class StockViewHolder extends RecyclerView.ViewHolder {
        TextView stockItemSerialNumber, stockItemName, stockItemQuantity;
        public StockViewHolder(View view) {
            super(view);

            stockItemSerialNumber = view.findViewById(R.id.stockSerialToShow);
            stockItemName = view.findViewById(R.id.stockNameToShow);
            stockItemQuantity = view.findViewById(R.id.stockQuantityToShow);
        }
    }
}
