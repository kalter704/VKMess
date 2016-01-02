package com.example.vasiliy.vkmess.Activitys;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.example.vasiliy.vkmess.Adapters.FriendsListAdapter;
import com.example.vasiliy.vkmess.Classes.RequestGetFriends;
import com.example.vasiliy.vkmess.Datas.VKMessFriend;
import com.example.vasiliy.vkmess.R;
import com.example.vasiliy.vkmess.Classes.UserToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;

public class FriendsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);

        UserToken userToken = UserToken.getInstance();

        if(!userToken.isReady) {
            Log.d("QWERTY", "UserToken.getInstance().isReady == false");
        } else {
            Log.d("QWERTY", "UserToken.getInstance().isReady == true");
        }

        /*
        Log.d("QWERTY", userToken.accessToken);
        Log.d("QWERTY", userToken.userId);
        Log.d("QWERTY", String.valueOf(userToken.expiresIn));
        Log.d("QWERTY", String.valueOf(userToken.isExpired()));
        Log.d("QWERTY", String.valueOf(userToken.created));
        */

        //String address = "https://api.vk.com/method/friends.get?order=name&fields=first_name,last_name,photo_50&name_case=nom&v=5.42&access_token=" + userToken.accessToken;

        new BackgroundTask().execute();

    }

    private void fillList(List<VKMessFriend> friendsList) {
        FriendsListAdapter friendsAdapter = new FriendsListAdapter(getApplicationContext(), friendsList);
        ListView listView = (ListView) findViewById(R.id.friendsList);
        listView.setAdapter(friendsAdapter);
        Log.d("QWERTY", "!!!!!!!!!!!!!!");
    }

    class BackgroundTask extends AsyncTask<Void, Void, List<VKMessFriend>> {

        @Override
        protected List<VKMessFriend> doInBackground(Void... params) {
            return new RequestGetFriends().getFriendsList();
        }

        protected void onPostExecute(List<VKMessFriend> friendsList) {
            super.onPostExecute(friendsList);
            fillList(friendsList);
            Log.d("QWERTY", "1234567890");
        }
    }


}
