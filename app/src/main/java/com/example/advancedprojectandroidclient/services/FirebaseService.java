package com.example.advancedprojectandroidclient.services;

import android.app.ActivityManager;
import android.app.NotificationChannel;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.advancedprojectandroidclient.MyApplication;
import com.example.advancedprojectandroidclient.R;
import com.example.advancedprojectandroidclient.activities.ChatActivity;
import com.example.advancedprojectandroidclient.api.PendingUserApi;
import com.example.advancedprojectandroidclient.api.RegisteredUserApi;
import com.google.firebase.messaging.FirebaseMessagingService;

import java.util.List;

public class FirebaseService extends FirebaseMessagingService {

    public static String token;

    public FirebaseService() {
        MyApplication.refreshTokenRepository.autoRenewTokens(1, 100);
    }

    public static void sendRegistrationToServer(String token) {
        RegisteredUserApi api = new RegisteredUserApi();
        api.setPhoneToken(token);
    }

    public static void sendRegistrationToServerOnSignup(String username, String token){
        PendingUserApi api = new PendingUserApi();
        api.setPhoneToken(username, token);
    }

    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
        FirebaseService.token = token;
        sendRegistrationToServer(token);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onMessageReceived(@NonNull com.google.firebase.messaging.RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        String username = remoteMessage.getData().get("username");
        String nickname = remoteMessage.getData().get("nickname");
        String content = remoteMessage.getData().get("content");
        String current = MyApplication.messagesRepository.getWith();
        ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(Integer.MAX_VALUE);
        // Second condition is to prevent the notification from being sent when the user is in the chat activity
        // With the user who sent the message
        if (!current.equals(username) || !tasks.get(0).topActivity.getClassName().equals(ChatActivity.class.getName())) {
            createNotificationChannel();
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

            Intent i = new Intent(this, ChatActivity.class);
            i.putExtra("contactName", nickname);
            i.putExtra("contactId", username);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, i, PendingIntent.FLAG_IMMUTABLE);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, getString(R.string.channel_id))
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .setContentTitle(nickname)
                    .setContentText(content)
                    .setStyle(new NotificationCompat.BigTextStyle()
                            .bigText(content))
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent);

            notificationManager.notify(String.valueOf(current).hashCode(), builder.build());
        }
        else if (tasks.get(0).topActivity.getClassName().equals(ChatActivity.class.getName())){
            MyApplication.messagesRepository.getAll();
        }
    }

    private void createNotificationChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            // Create the NotificationChannel, but only on API 26+ because
            // the NotificationChannel class is new and not in the support library

            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = android.app.NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(getString(R.string.channel_id), name, importance);
            channel.setDescription(description);

            // Register the channel with the system
            android.app.NotificationManager notificationManager = getSystemService(android.app.NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}