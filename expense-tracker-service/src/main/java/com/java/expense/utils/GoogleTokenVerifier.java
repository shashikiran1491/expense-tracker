package com.java.expense.utils;

import com.google.api.client.auth.openidconnect.IdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.java.expense.exception.UserLoginException;
import com.java.expense.model.auth.GoogleLoginRequest;

import java.util.Collections;

public class GoogleTokenVerifier {

    public static IdToken.Payload verifyToken(GoogleLoginRequest loginRequest) {
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new GsonFactory())
                .setAudience(Collections.singletonList("1048959448643-daek0gru58fma69mvf0mqej3c140o8ui.apps.googleusercontent.com"))
                .build();
        try {
            GoogleIdToken idToken = verifier.verify(loginRequest.getIdToken());
            return idToken.getPayload();
        } catch (Exception e) {
            throw new UserLoginException("Invalid Google Token");
        }
    }
}
