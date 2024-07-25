package com.example.inventorymanagement.view;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import com.example.inventorymanagement.models.SelectedItem;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class BluetoothController implements Runnable {
    private static final String TAG = "BluetoothController";
    private static final int REQUEST_CONNECT_DEVICE = 1;
    private static final int REQUEST_ENABLE_BT = 2;
    private static final int REQUEST_BLUETOOTH_PERMISSIONS = 3;

    private BluetoothAdapter mBluetoothAdapter;
    private UUID applicationUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private ProgressDialog mBluetoothConnectProgressDialog;
    private BluetoothSocket mBluetoothSocket;
    private BluetoothDevice mBluetoothDevice;
    private Context context;

    public BluetoothController(Context context) {
        this.context = context;
        this.mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    public void scanDevices() {
        if (mBluetoothAdapter == null) {
            Toast.makeText(context, "Bluetooth not supported", Toast.LENGTH_SHORT).show();
        } else {
            if (!mBluetoothAdapter.isEnabled()) {
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
                    mBluetoothAdapter.cancelDiscovery();
                    Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    ((Activity) context).startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);

                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
            } else {
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                    mBluetoothAdapter.disable();

                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                if (checkBluetoothPermissions()) {
                    listPairedDevices();
                    Intent connectIntent = new Intent(context, DeviceListActivity.class);
                    ((Activity) context).startActivityForResult(connectIntent, REQUEST_CONNECT_DEVICE);
                }
            }
        }
    }

    private boolean checkBluetoothPermissions() {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_ADMIN) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) context,
                    new String[]{Manifest.permission.BLUETOOTH_CONNECT, Manifest.permission.BLUETOOTH_ADMIN},
                    REQUEST_BLUETOOTH_PERMISSIONS);
            return false;
        }
        return true;
    }

    public void printBill(String customerName, String customerNumber, List<SelectedItem> selectedItems, Date date) {
        new Thread() {
            public void run() {
                try {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMMM, yyyy");

                    OutputStream os = mBluetoothSocket.getOutputStream();
                    String BILL = "                  Yasin Store    \n"
                            +     "                 Moulovi Bazar      \n"
                            +     "              Mobile: 01715017518      \n"
                            + "-----------------------------------------------\n"

                            +     " Customer Name: " + customerName+"\n"
                            +     " Customer Number: " + customerNumber + "\n"
                            +     " Date: " + simpleDateFormat.format(date) + "\n";
                    BILL += "-----------------------------------------------\n";

                    List<String[]> items = new ArrayList<>();
                    double grandTotal = 0;
                    double totalItem = 0;
                    for (int i = 0; i < selectedItems.size(); i++) {
                        items.add(new String[]{selectedItems.get(i).getItemName(),
                        selectedItems.get(i).getQuantity() + "",
                        selectedItems.get(i).getPrice() + "",
                        selectedItems.get(i).getTotal() + ""});
                        grandTotal += selectedItems.get(i).getTotal();
                        totalItem += selectedItems.get(i).getQuantity();
                    }

                    int[] maxLengths = new int[4];
                    for (String[] item : items) {
                        for (int i = 0; i < item.length; i++) {
                            if (item[i].length() > maxLengths[i]) {
                                maxLengths[i] = item[i].length();
                            }
                        }
                    }

                    maxLengths[0] = Math.max(maxLengths[0], 15);
                    maxLengths[1] = Math.max(maxLengths[1], 5);
                    maxLengths[2] = Math.max(maxLengths[2], 10);
                    maxLengths[3] = Math.max(maxLengths[3], 10);

                    BILL += String.format("%-" + maxLengths[0] + "s %" + maxLengths[1] + "s %" + maxLengths[2] + "s %" + maxLengths[3] + "s", "Item", "Qty", "Rate", "Total");
                    BILL += "\n";
                    BILL += "-----------------------------------------------\n";
                    for (String[] item : items) {
                        BILL += String.format("%-" + maxLengths[0] + "s %" + maxLengths[1] + "s %" + maxLengths[2] + "s %" + maxLengths[3] + "s", item[0], item[1], item[2], item[3]);
                        BILL += "\n";
                    }
                    BILL += "-----------------------------------------------\n";
                    BILL += " Total Quantity: " + totalItem + "      Total: " + grandTotal + "\n";
                    BILL += "-----------------------------------------------\n";
                    BILL += "\n\n\n\n\n";
                    BILL += "------------------\n";
                    BILL += "     Signature\n";

                    BILL += "\n\n\n\n\n\n";

                    os.write(BILL.getBytes());

                    int gs = 29, h = 104, n = 162, gs_width = 29, w = 119, n_width = 2;
                    os.write(new byte[]{(byte) gs, (byte) h, (byte) n, (byte) gs_width, (byte) w, (byte) n_width});

                } catch (Exception e) {
                    Log.e(TAG, "Exception", e);
                }
            }
        }.start();
    }

    public void disconnect() {
        if (mBluetoothAdapter != null) {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                mBluetoothAdapter.disable();

                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
        }
    }

    public void handleActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CONNECT_DEVICE:
                if (resultCode == Activity.RESULT_OK) {
                    Bundle mExtra = data.getExtras();
                    String mDeviceAddress = mExtra.getString("DeviceAddress");
                    Log.v(TAG, "Incoming address: " + mDeviceAddress);
                    mBluetoothDevice = mBluetoothAdapter.getRemoteDevice(mDeviceAddress);
                    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                        mBluetoothConnectProgressDialog = ProgressDialog.show(context,
                                "Connecting...", mBluetoothDevice.getName() + " : " + mBluetoothDevice.getAddress(), true, false);
                        new Thread(this).start();
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }

                }
                break;
            case REQUEST_ENABLE_BT:
                if (resultCode == Activity.RESULT_OK) {
                    listPairedDevices();
                    Intent connectIntent = new Intent(context, DeviceListActivity.class);
                    ((Activity) context).startActivityForResult(connectIntent, REQUEST_CONNECT_DEVICE);
                } else {
                    Toast.makeText(context, "Bluetooth not enabled", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void listPairedDevices() {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
            Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
            if (pairedDevices.size() > 0) {
                for (BluetoothDevice device : pairedDevices) {
                    Log.v(TAG, "PairedDevices: " + device.getName() + "  " + device.getAddress());
                }
            }// TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

    }

    public void run() {
        try {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                mBluetoothSocket = mBluetoothDevice.createRfcommSocketToServiceRecord(applicationUUID);
                mBluetoothAdapter.cancelDiscovery();
                mBluetoothSocket.connect();
                mHandler.sendEmptyMessage(0);
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }

        } catch (IOException eConnectException) {
            Log.e(TAG, "CouldNotConnectToSocket", eConnectException);
            closeSocket(mBluetoothSocket);
        }
    }

    private void closeSocket(BluetoothSocket socket) {
        try {
            socket.close();
            Log.d(TAG, "Socket closed");
        } catch (IOException e) {
            Log.e(TAG, "Could not close socket", e);
        }
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (mBluetoothConnectProgressDialog != null) {
                mBluetoothConnectProgressDialog.dismiss();
            }
            Toast.makeText(context, "Device Connected", Toast.LENGTH_SHORT).show();
        }
    };

    public static byte intToByteArray(int value) {
        byte[] b = ByteBuffer.allocate(4).putInt(value).array();
        return b[3];
    }

    public byte[] sel(int val) {
        ByteBuffer buffer = ByteBuffer.allocate(2);
        buffer.putInt(val);
        buffer.flip();
        return buffer.array();
    }
}


