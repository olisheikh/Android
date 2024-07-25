package com.example.inventorymanagement.view;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inventorymanagement.R;
import com.example.inventorymanagement.adapter.CustomRecycleAdapter;
import com.example.inventorymanagement.adapter.ProductClickListener;
import com.example.inventorymanagement.models.OptionsModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private List<OptionsModel> options;

    private BluetoothAdapter bluetoothAdapter;
    private ActivityResultLauncher<Intent> enableBluetoothLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.categoryRecyclerView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(gridLayoutManager);

        options = new ArrayList<>();
        options.add(new OptionsModel(R.drawable.accounting, "Memo"));
        options.add(new OptionsModel(R.drawable.loan, "Purchases"));
        options.add(new OptionsModel(R.drawable.salary, "Sales"));
        options.add(new OptionsModel(R.drawable.portfolio, "Stock"));
        options.add(new OptionsModel(R.drawable.add_product, "Products"));

        CustomRecycleAdapter customGridViewAdapter = new CustomRecycleAdapter(MainActivity.this, options);
        mRecyclerView.setAdapter(customGridViewAdapter);
        mRecyclerView.addOnItemTouchListener(
                new ProductClickListener(MainActivity.this, mRecyclerView, new ProductClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent = null;

                        switch (position) {
                            case 0:
                                intent = new Intent(MainActivity.this, MemoActivity.class);
                                break;
                            case 1:
                                intent = new Intent(MainActivity.this, ItemPurchased.class);
                                break;
                            case 2:
                                intent = new Intent(MainActivity.this, SalesActivity.class);
                                break;
                            case 3:
                                intent = new Intent(MainActivity.this, StockActivity.class);
                                break;
                            case 4:
                                intent = new Intent(MainActivity.this, ProductListActivity.class);
                                break;
                        }
                        if (intent != null) {
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        // Handle long item click if needed
                    }
                })
        );

        /*
         * Creating bluetooth connection---------*/
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

//        enableBluetoothLauncher = registerForActivityResult(
//                new ActivityResultContracts.StartActivityForResult(),
//                result -> {
//                    if (result.getResultCode() == RESULT_OK) {
//                        discoverAndConnectToPrinter();
//                    } else {
//                        Toast.makeText(this, "Bluetooth not enabled", Toast.LENGTH_SHORT).show();
//                    }
//                }
//        );
//        if (bluetoothAdapter == null) {
//            Toast.makeText(this, "Bluetooth doesn't support", Toast.LENGTH_SHORT).show();
//        } else {
//            if (!bluetoothAdapter.isEnabled()) {
//                Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
//                enableBluetoothLauncher.launch(enableIntent);
//            } else {
//                discoverAndConnectToPrinter();
//            }
//        }
    }

//    public void discoverAndConnectToPrinter() {
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }
//        Set<BluetoothDevice> pairDevices = bluetoothAdapter.getBondedDevices();
//        if (pairDevices.size() > 0) {
//            for (BluetoothDevice device : pairDevices) {
//                String deviceName
//            }
//        }
//    }
}