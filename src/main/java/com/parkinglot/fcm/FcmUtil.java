package com.parkinglot.fcm;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.AndroidConfig;
import com.google.firebase.messaging.AndroidNotification;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;

@Component
public class FcmUtil {
    public void sendFcm(String tokenId, String title, String content){
        try{
            FileInputStream refreshToken = new FileInputStream("src/main/resources/fcm/amorangparking-firebase-adminsdk-iwccx-44f5db4b44.json");
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(refreshToken))
                    .setDatabaseUrl("https://amorangparking.firebaseio.com")
                    .build();

            if(FirebaseApp.getApps().isEmpty()){
                FirebaseApp.initializeApp(options);
            }

            String registrationToken = tokenId;

            Message message = Message.builder()
                    .setAndroidConfig(AndroidConfig.builder()
                            .setTtl(3600*1000)
                            .setPriority(AndroidConfig.Priority.NORMAL)
                            .setNotification(AndroidNotification.builder()
                                    .setTitle(title)
                                    .setBody(content)
                                    .build())
                            .build())
                    .setToken(registrationToken)
                    .build();

            FirebaseMessaging.getInstance().send(message);

        } catch(Exception e){
            e.printStackTrace();
        }
    }
}
