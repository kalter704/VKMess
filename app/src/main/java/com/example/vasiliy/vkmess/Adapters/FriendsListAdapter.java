package com.example.vasiliy.vkmess.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vasiliy.vkmess.Datas.VKMessFriend;
import com.example.vasiliy.vkmess.R;

import java.util.List;

/**
 * Created by vasiliy on 02.01.16.
 */
public class FriendsListAdapter extends BaseAdapter {

    Context context;
    LayoutInflater layoutInflater;
    List<VKMessFriend> list;

    public FriendsListAdapter(Context context, List<VKMessFriend> list) {
        this.list = list;
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

        ((TextView) view.findViewById(R.id.name)).setText(friend.getFirstName() + friend.getLastName());

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

}
