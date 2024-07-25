package com.example.inventorymanagement.adapter;

import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inventorymanagement.R;
import com.example.inventorymanagement.db.DatabaseHelper;
import com.example.inventorymanagement.models.Products;

import java.util.ArrayList;
import java.util.List;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductsViewHolder> {

    Context context;
    List<Products> products;
    DatabaseHelper databaseHelper;

    public ProductsAdapter(Context context, List<Products> products, DatabaseHelper databaseHelper) {
        this.context = context;
        this.products = products;
        this.databaseHelper = databaseHelper;
    }

    public int getItemCount() {
        return products.size();
    }

    public ProductsViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.products_list_recycler_view, viewGroup, false);
        ProductsViewHolder productsViewHolder = new ProductsViewHolder(view);

        return productsViewHolder;
    }

    public void onBindViewHolder(ProductsViewHolder productsViewHolder, int position) {
        Products product = products.get(position);

        productsViewHolder.delImgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteItemFromProduct(productsViewHolder, product, context);
            }
        });

        productsViewHolder.editImgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateItemFromProduct(productsViewHolder, product, context, databaseHelper);
            }
        });
        productsViewHolder.productName.setText(product.getProductName());
        productsViewHolder.productSerialNum.setText(position + 1 + ".");
    }

    public class ProductsViewHolder extends RecyclerView.ViewHolder {
        TextView productName, productSerialNum;
        ImageView delImgView, editImgView;

        public ProductsViewHolder(@NonNull View itemView) {
            super(itemView);

            productName = itemView.findViewById(R.id.productNameTxtView);
            productSerialNum = itemView.findViewById(R.id.serialNumOfProduct);
            delImgView = itemView.findViewById(R.id.deleteProductImgView);
            editImgView = itemView.findViewById(R.id.editProductImgView);
        }
    }

    public void deleteItemFromProduct(ProductsViewHolder viewHolder, Products pro, Context context) {
        databaseHelper.productsDao().deleteProduct(pro);
    }

    public void updateItemFromProduct(ProductsViewHolder viewHolder, Products product, Context context, DatabaseHelper databaseHelper) {

        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.updata_product_list);

        EditText name = dialog.findViewById(R.id.nameUpdate);
        Button update = dialog.findViewById(R.id.updateBtn);
        Button cancel = dialog.findViewById(R.id.cancelBtn);

        name.setText(product.getProductName());

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String updateValue = name.getText().toString();
                product.setProductName(updateValue);
                databaseHelper.productsDao().updateProduct(product);
                Toast.makeText(context, "Updated", Toast.LENGTH_SHORT);
                dialog.dismiss();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        WindowManager windowManger = (WindowManager) dialog.getContext().getSystemService(Context.WINDOW_SERVICE);
        int width = windowManger.getDefaultDisplay().getWidth();
        int height = windowManger.getDefaultDisplay().getHeight();

        dialog.getWindow().setLayout((int)(width * .9), (int)(height * .25));

        dialog.show();
    }
}
