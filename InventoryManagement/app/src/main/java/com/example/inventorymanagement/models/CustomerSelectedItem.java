package com.example.inventorymanagement.models;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class CustomerSelectedItem {
    @Embedded private CustomersInfoWithItems customersInfoWithItems;

    @Relation(
            parentColumn = "customer_id",
            entityColumn = "customer_id"
    )
    private List<SelectedItem> selectedItems;
}
