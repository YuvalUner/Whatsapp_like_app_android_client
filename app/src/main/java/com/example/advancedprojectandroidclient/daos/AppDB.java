package com.example.advancedprojectandroidclient.daos;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.advancedprojectandroidclient.entities.Contact;
import com.example.advancedprojectandroidclient.entities.Message;
import com.example.advancedprojectandroidclient.entities.RefreshToken;
import com.example.advancedprojectandroidclient.entities.Image;

/**
 * The database for the application.
 */
@Database(entities = {Contact.class, Message.class, RefreshToken.class, Image.class}, version = 15)
public abstract class AppDB extends RoomDatabase {
    public abstract ContactDao contactDao();

    public abstract MessageDao messageDao();

    public abstract RefreshTokenDao refreshTokenDao();

    public abstract ImageDao imageDao();
}
