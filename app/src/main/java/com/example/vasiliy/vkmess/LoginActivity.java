package com.example.vasiliy.vkmess;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKError;

public class LoginActivity extends AppCompatActivity {

    String[] scopes = {
            VKScope.MESSAGES,
            VKScope.PHOTOS,
            VKScope.WALL,
            VKScope.FRIENDS
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        VKSdk.login(this, scopes);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken res) {
                Toast.makeText(LoginActivity.this, "Good", Toast.LENGTH_SHORT).show();

                VKAccessToken vkAccessToken = VKAccessToken.currentToken();
                UserToken userToken = UserToken.getInstance();

                userToken.accessToken = vkAccessToken.accessToken;
                userToken.expiresIn = vkAccessToken.expiresIn;
                userToken.userId = vkAccessToken.userId;
                userToken.created = vkAccessToken.created;

                userToken.saveUserTokenFromFile(LoginActivity.this);

                //Log.d("QWERTY", vkAccessToken.accessToken);
                //accToken = vkAccessToken.accessToken;
            }

            @Override
            public void onError(VKError error) {
                Toast.makeText(LoginActivity.this, "Bad", Toast.LENGTH_SHORT).show();
            }
        })) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
