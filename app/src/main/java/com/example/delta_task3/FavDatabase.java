package com.example.delta_task3;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
@Database(entities = {Favourites.class},version = 1)
public abstract class FavDatabase extends RoomDatabase {

    public abstract FavouritesDao favouritesDao();
}
