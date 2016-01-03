package com.example.vasiliy.vkmess.Classes;

import android.content.Context;
import android.graphics.Path;
import android.os.Environment;
import android.util.Log;

import com.example.vasiliy.vkmess.Datas.VKMessFriend;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.FileHandler;

/**
 * Created by vasiliy on 02.01.16.
 */
public class RequestGetFriends {

    private final String ID_REQUEST = "id";
    private final String FIRST_NAME_REQUEST = "first_name";
    private final String LAST_NAME_REQUEST = "last_name";
    private final String PHOTO_REQUEST = "photo_100";
    private final String ONLINE_REQUEST = "online";
    private final String ONLINE_MOBILE_REQUEST = "online_mobile";



    private String requestString = "https://api.vk.com/method/friends.get?order=name&fields=online,first_name,last_name," + PHOTO_REQUEST + "&name_case=nom&v=5.42&access_token=";

    public RequestGetFriends() {
        requestString += UserToken.getInstance().accessToken;
    }

    protected String request() {
        BufferedReader reader = null;
        try {
            URL url = new URL(requestString);
            HttpURLConnection c = (HttpURLConnection) url.openConnection();
            c.setRequestMethod("GET");
            c.setDoInput(true);
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

    protected List<VKMessFriend> parseFromJson() {
        JSONObject dataJsonObject = null;
        List<VKMessFriend> listFriends = new ArrayList<>();
        String jsonString = request();
        //Log.d("QWERTY", jsonString);
        try {
            dataJsonObject = new JSONObject(jsonString);
            JSONObject response = dataJsonObject.getJSONObject("response");
            JSONArray friends = response.getJSONArray("items");

            for (int i = 0; i < friends.length(); ++i) {
                JSONObject friend = friends.getJSONObject(i);
                int onlimeMob;
                try {
                    onlimeMob = friend.getInt(ONLINE_MOBILE_REQUEST);
                } catch (Exception e) {
                    onlimeMob = 0;
                }
                listFriends.add(new VKMessFriend(
                        friend.getString(ID_REQUEST),
                        friend.getString(FIRST_NAME_REQUEST),
                        friend.getString(LAST_NAME_REQUEST),
                        friend.getString(PHOTO_REQUEST),
                        friend.getInt(ONLINE_REQUEST),
                        onlimeMob
                ));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        //showList(listFriends);
        return listFriends;
    }

    private static void fileSave(InputStream is, FileOutputStream outputStream) throws IOException {
        int i;
        try {
            while ((i = is.read()) != -1) {
                outputStream.write(i);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            is.close();
        }
    }

    public void downloadAvatars(List<VKMessFriend> friends) {
        for (VKMessFriend friend : friends) {
            String photoName = friend.getPhoto().substring(friend.getPhoto().lastIndexOf("/") + 1, friend.getPhoto().length());

            //Log.d("QWERTY", Environment.getExternalStorageDirectory().toString());
            String path = Environment.getExternalStorageDirectory().toString() + "/VKMess";
            File file = new File(path);
            if (!file.exists()) {
                file.mkdir();
            }
            file = new File(path, photoName);
            if (!file.exists()) {
                try {
                    file.createNewFile();
                    fileSave(new URL(friend.getPhoto()).openStream(), new FileOutputStream(file));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            friend.setPhoto(photoName);
        }
    }

    public void downloadAvatar(VKMessFriend friend) {

        String photoName = friend.getPhoto().substring(friend.getPhoto().lastIndexOf("/") + 1, friend.getPhoto().length());

        //Log.d("QWERTY", Environment.getExternalStorageDirectory().toString());
        String path = Environment.getExternalStorageDirectory().toString() + "/VKMess";
        File file = new File(path);
        if (!file.exists()) {
            file.mkdir();
        }
        file = new File(path, photoName);
        if (!file.exists()) {
            try {
                file.createNewFile();
                fileSave(new URL(friend.getPhoto()).openStream(), new FileOutputStream(file));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        friend.setPhoto(photoName);
    }

    public List<VKMessFriend> getFriendsList() {
        List<VKMessFriend> list = parseFromJson();
        //downloadAvatars(list);
        return list;
    }

    private void showList(List<VKMessFriend> listFriends) {
        for (int i = 0; i < listFriends.size(); ++i) {
            Log.d("QWERTY", listFriends.get(i).getId());
            Log.d("QWERTY", listFriends.get(i).getFirstName());
            Log.d("QWERTY", listFriends.get(i).getLastName());
            Log.d("QWERTY", listFriends.get(i).getPhoto());
            Log.d("QWERTY", String.valueOf(listFriends.get(i).getOnline()));
            Log.d("QWERTY", "\n");
        }
    }

}
