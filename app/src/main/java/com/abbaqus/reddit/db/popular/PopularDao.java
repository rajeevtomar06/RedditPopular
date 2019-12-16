package com.abbaqus.reddit.db.popular;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.abbaqus.reddit.popular.model.PopularModel;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;

@Dao
public interface PopularDao {

    @Query("SELECT * FROM popular ORDER BY createdDateTime ASC")
    Observable<List<PopularModel>> getAll();

    /**
     * Insert a user in the database. If the user already exists, replace it.
     *
     * @param popularModel the user to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(PopularModel popularModel);

    @Delete
    void delete(PopularModel popularModel);

}
