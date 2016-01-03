package com.example.vasiliy.vkmess.Adapters;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vasiliy.vkmess.Datas.VKMessFriend;
import com.example.vasiliy.vkmess.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by vasiliy on 02.01.16.
 */
public class FriendsListAdapter extends BaseAdapter {

    Context context;
    LayoutInflater layoutInflater;
    List<VKMessFriend> list;
    List<VKMessFriend> allDatasList;

    public FriendsListAdapter(Context context, List<VKMessFriend> list) {
        this.list = list;
        this.allDatasList = list;
        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        if(view == null) {
            view = layoutInflater.inflate(R.layout.item_friend, parent, false);
        }

        VKMessFriend friend = (VKMessFriend) getItem(position);

        ((TextView) view.findViewById(R.id.name)).setText(friend.getFirstName() + " " + friend.getLastName());

        try {
            if(!friend.getPhoto().startsWith("http://") && !friend.getPhoto().startsWith("https://")) {
                ((ImageView) view.findViewById(R.id.avatarImg)).setImageBitmap(BitmapFactory.decodeStream(new FileInputStream(new File(Environment.getExternalStorageDirectory().toString() + "/VKMess", friend.getPhoto()))));
                //Log.d("QWERTY", "rastr");
            } else {
                ((ImageView) view.findViewById(R.id.avatarImg)).setImageResource(R.drawable.ic_launcher);
                //Log.d("QWERTY", "default");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        if(friend.getOnline() == 1) {
            ((LinearLayout) view.findViewById(R.id.linOnline)).setVisibility(View.VISIBLE);
        }
        if(friend.getOnlineMobile() == 1) {
            ((ImageView) view.findViewById(R.id.imgMobOnline)).setVisibility(View.VISIBLE);
        }

        view.setTag(friend.getId());
        view.setOnClickListener(friendsListClickListener);

        return view;
    }

    View.OnClickListener friendsListClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(context, String.valueOf(v.getTag()), Toast.LENGTH_SHORT).show();
        }
    };

    public void filter(String searchString) {
        searchString = searchString.toLowerCase(Locale.getDefault());
        list = new ArrayList<VKMessFriend>();
        if(searchString.length() == 0) {
            list.addAll(allDatasList);
        } else {
            for(VKMessFriend item : allDatasList) {
                if((item.getFirstName() + item.getLastName()).toLowerCase(Locale.getDefault()).contains(searchString)) {
                    list.add(item);
                }
            }
        }
        notifyDataSetChanged();
    }

}
