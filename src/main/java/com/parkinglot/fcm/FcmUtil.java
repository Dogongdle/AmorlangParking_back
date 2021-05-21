package com.parkinglot.fcm;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.*;
import com.parkinglot.domain.Platform;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;

@Component
public class FcmUtil {
    public void sendFcm(String tokenId, String title, String content, Platform platform){
        try{
            FileInputStream refreshToken = new FileInputStream("fcm/amorangparking-firebase-adminsdk-iwccx-44f5db4b44.json");
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(refreshToken))
                    .setDatabaseUrl("https://amorangparking.firebaseio.com")
                    .build();

            if(FirebaseApp.getApps().isEmpty()){
                FirebaseApp.initializeApp(options);
            }

            String registrationToken = tokenId;

            if (platform == Platform.ANDROID) {
                Message message = Message.builder()
                        .setAndroidConfig(AndroidConfig.builder()
                                .setTtl(3600 * 1000)
                                .setPriority(AndroidConfig.Priority.NORMAL)
                                .setNotification(AndroidNotification.builder()
                                        .setTitle(title)
                                        .setBody(content)
                                        .build())
                                .build())
                        .setToken(registrationToken)
                        .build();
                FirebaseMessaging.getInstance().send(message);
            }else if (platform == Platform.IOS) {
                Message message = Message.builder()
                        .setApnsConfig(ApnsConfig.builder()
                                .setAps(Aps.builder().setAlert(ApsAlert.builder()
                                        .setTitle(title)
                                        .setBody(content)
                                        .build())
                                        .build())
                                .build())
                        .setToken(registrationToken)
                        .build();
                FirebaseMessaging.getInstance().send(message);
            }

        } catch(Exception e){
            e.printStackTrace();
        }
    }
}
