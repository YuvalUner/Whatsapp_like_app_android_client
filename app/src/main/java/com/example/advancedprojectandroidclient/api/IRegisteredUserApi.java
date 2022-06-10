package com.example.advancedprojectandroidclient.api;

import com.example.advancedprojectandroidclient.entities.AccessToken;
import com.example.advancedprojectandroidclient.entities.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IRegisteredUserApi {

    @POST("RegisteredUsers")
    Call<AccessToken> logIn(@Body User user);

    @PUT("RegisteredUsers/editDescription/{newDescription}")
    Call<Void> editDescription(@Path("newDescription") String newDescription, @Header("Authorization") String token);

    @PUT("RegisteredUsers/changeServer/{newServer}")
    Call<Void> changeServer(@Path("newServer") String newServer, @Header("Authorization") String token);

    @PUT("RegisteredUsers/editNickName/{newNickName}")
    Call<Void> editNickName(@Path("newNickName") String newNickName, @Header("Authorization") String token);

    @PUT("RegisteredUsers/setPhoneToken")
    Call<Void> setPhoneToken(@Header("Authorization") String token, @Query("phoneToken") String phoneToken);

    @PUT("RegisteredUsers/logOut")
    Call<Void> logOut(@Header("Authorization") String token);
}
