package com.example.vasiliy.vkmess.Activitys;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.vasiliy.vkmess.Adapters.FriendsListAdapter;
import com.example.vasiliy.vkmess.Classes.RequestGetFriends;
import com.example.vasiliy.vkmess.Datas.VKMessFriend;
import com.example.vasiliy.vkmess.R;
import com.example.vasiliy.vkmess.Classes.UserToken;

import java.util.List;

public class FriendsActivity extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener {

    private ListView listView;
    private FriendsListAdapter friendsAdapter;
    private EditText search;
    private ImageButton imgBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);

        search = (EditText) findViewById(R.id.searchEdit);

        imgBtn = (ImageButton) findViewById(R.id.imageButton);
        imgBtn.setOnTouchListener(this);

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

        listView = (ListView) findViewById(R.id.friendsList);

        new BackgroundTaskDownloadDatas().execute();



    }

    private void settingEditText() {
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                FriendsActivity.this.friendsAdapter.filter(String.valueOf(s));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void fillList(List<VKMessFriend> friendsList) {
        friendsAdapter = new FriendsListAdapter(getApplicationContext(), friendsList);
        listView.setAdapter(friendsAdapter);
    }

    @Override
    public void onClick(View v) {
        FriendsActivity.this.friendsAdapter.filter(search.getText().toString());
        search.clearFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(search.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                imgBtn.setBackgroundResource(R.drawable.search_down);
                break;
            case MotionEvent.ACTION_UP:
                FriendsActivity.this.friendsAdapter.filter(search.getText().toString());
                search.clearFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(search.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            case MotionEvent.ACTION_CANCEL:
                imgBtn.setBackgroundResource(R.drawable.search);
                break;
        }
        return true;
    }

    class BackgroundTaskDownloadDatas extends AsyncTask<Void, Void, List<VKMessFriend>> {

        @Override
        protected List<VKMessFriend> doInBackground(Void... params) {
            return new RequestGetFriends().getFriendsList();
        }

        protected void onPostExecute(List<VKMessFriend> friendsList) {
            super.onPostExecute(friendsList);
            fillList(friendsList);
            settingEditText();
            new BackgroundTaskDownloadImages().execute(friendsList);
            Log.d("QWERTY", "1234567890");
        }
    }

    class BackgroundTaskDownloadImages extends AsyncTask<List<VKMessFriend>, Void, Void> {

        @Override
        protected Void doInBackground(List<VKMessFriend>... params) {
            new RequestGetFriends().downloadAvatars(params[0]);
            return null;
        }

        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            friendsAdapter.notifyDataSetChanged();
            Log.d("QWERTY", "1234567890");
        }

    }


}
