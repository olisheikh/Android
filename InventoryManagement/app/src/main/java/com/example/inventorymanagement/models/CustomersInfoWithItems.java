package com.example.inventorymanagement.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.Date;
import java.util.List;

@Entity(tableName = "customers_purchased_item")
public class CustomersInfoWithItems {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="customer_id")
    private long customerId;

    @ColumnInfo(name="customer_name")
    private String customerName;

    @ColumnInfo(name="customer_mobile")
    private String customerMobile;

    @ColumnInfo(name="invoice_number")
    private long invoiceNumber;

    @ColumnInfo(name="sales_date")
    private Date salesDate;

    @ColumnInfo(name="total")
    private double total;

    @Ignore
    public CustomersInfoWithItems(long customerId, String customerName, String customerMobile, long invoiceNumber, Date salesDate, double total) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerMobile = customerMobile;
        this.invoiceNumber = invoiceNumber;
        this.salesDate = salesDate;
        this.total = total;
    }
    @Ignore
    public CustomersInfoWithItems( String customerName, String customerMobile, long invoiceNumber, Date salesDate, double total) {
        this.customerName = customerName;
        this.customerMobile = customerMobile;
        this.invoiceNumber = invoiceNumber;
        this.salesDate = salesDate;
        this.total = total;
    }

    public CustomersInfoWithItems() {
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerMobile() {
        return customerMobile;
    }

    public void setCustomerMobile(String customerMobile) {
        this.customerMobile = customerMobile;
    }

    public long getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(long invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public Date getSalesDate() {
        return salesDate;
    }

    public void setSalesDate(Date salesDate) {
        this.salesDate = salesDate;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

}
