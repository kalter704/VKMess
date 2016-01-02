package com.example.vasiliy.vkmess.Classes;

import android.util.Log;

import com.example.vasiliy.vkmess.Datas.VKMessFriend;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vasiliy on 02.01.16.
 */
public class RequestGetFriends {

    private String requestString = "https://api.vk.com/method/friends.get?order=name&fields=first_name,last_name,photo_50&name_case=nom&v=5.42&access_token=";

    public RequestGetFriends() {
        requestString += UserToken.getInstance().accessToken;
    }

    private String request() {
        BufferedReader reader = null;
        try {
            URL url = new URL(requestString);
            HttpURLConnection c = (HttpURLConnection) url.openConnection();
            c.setRequestMethod("GET");
            c.setReadTimeout(10000);
            c.connect();
            reader = new BufferedReader(new InputStreamReader(c.getInputStream()));

            StringBuilder buf = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                buf.append(line);
                //buf.append(line + "\n");
            }
            c.disconnect();
            return (buf.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public List<VKMessFriend> getFriendsList() {
        JSONObject dataJsonObject = null;
        List<VKMessFriend> listFriends = new ArrayList<>();
        String jsonString = request();
        Log.d("QWERTY", jsonString);
        try {
            dataJsonObject = new JSONObject(jsonString);
            JSONObject response = dataJsonObject.getJSONObject("response");
            JSONArray friends = response.getJSONArray("items");

            for(int i = 0; i < friends.length(); ++i) {
                JSONObject friend = friends.getJSONObject(i);
                listFriends.add(new VKMessFriend(
                        friend.getString("id"),
                        friend.getString("first_name"),
                        friend.getString("last_name"),
                        friend.getString("photo_50"),
                        friend.getInt("online")
                ));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        showList(listFriends);
        return listFriends;
    }

    private  void showList(List<VKMessFriend> listFriends) {
        for(int i = 0; i < listFriends.size(); ++i) {
            Log.d("QWERTY", listFriends.get(i).getId());
            Log.d("QWERTY", listFriends.get(i).getFirstName());
            Log.d("QWERTY", listFriends.get(i).getLastName());
            Log.d("QWERTY", listFriends.get(i).getPhoto());
            Log.d("QWERTY", String.valueOf(listFriends.get(i).getOnline()));
            Log.d("QWERTY", "\n");
        }
    }

}
