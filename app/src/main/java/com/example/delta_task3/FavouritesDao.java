package com.example.delta_task3;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface FavouritesDao {
@Insert
    public void addPoke(Favourites favourites);

@Query("select * from Favourites")
    public List<Favourites> getPokes();
}
