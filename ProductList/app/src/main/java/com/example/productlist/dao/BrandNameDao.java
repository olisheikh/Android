package com.example.productlist.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.productlist.entity.BrandNames;

import java.util.List;

@Dao
public interface BrandNameDao {
    @Query("select * from brand_names")
    LiveData<List<BrandNames>> getAllBrandNames();

    @Insert
    void addNewBrand(BrandNames brandNames);

    @Update
    void updateBrand(BrandNames brandNames);

    @Delete
    void deleteBrand(BrandNames brandNames);
}
