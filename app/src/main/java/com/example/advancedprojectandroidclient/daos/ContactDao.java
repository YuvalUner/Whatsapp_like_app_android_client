package com.example.advancedprojectandroidclient.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.advancedprojectandroidclient.entities.Contact;

import java.util.List;

/**
 * Data Access Object for the Contact table.
 */
@Dao
public interface ContactDao {

    @Query("SELECT * FROM contact")
    List<Contact> getAll();

    @Query("SELECT * FROM contact WHERE id = :id")
    Contact getById(String id);

    @Insert
    void insert(Contact contact);

    @Delete
    void delete(Contact contact);

    @Query("DELETE FROM contact")
    void deleteAll();

    @Update
    void update(Contact contact);
}
