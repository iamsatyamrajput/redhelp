package com.example.satyam.redhelp;

/**
 * Created by satyam on 22-03-2017.
 */
 import android.content.Context;
 import android.content.Intent;
 import android.util.Log;
 import android.view.LayoutInflater;
 import android.view.View;
 import android.view.ViewGroup;
 import android.widget.ArrayAdapter;
 import android.widget.BaseAdapter;
 import android.widget.ImageView;
 import android.widget.TextView;
 import android.widget.Toast;

 import com.android.volley.toolbox.ImageLoader;

 import java.util.ArrayList;
 import java.util.HashMap;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ListViewAdapter extends BaseAdapter  {

        private Activity activity;
        private ArrayList<HashMap<String, String>>data;
        private static LayoutInflater inflater=null;
        public ImageLoader imageLoader;
    private static final String TAG = ListViewAdapter.class.getSimpleName();
        public ListViewAdapter(Activity a, ArrayList<HashMap<String,String>> d) {
            activity = a;
            data=d;
            inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        }

        public int getCount() {
            return data.size();
        }

        public Object getItem(int position) {
            return position;
        }

    @Override
    public boolean isEnabled(int position) {
        return true;
    }

    public long getItemId(int position) {
            return position;
        }

        public View getView(final int position, View convertView, ViewGroup parent) {
            View vi=convertView;
            if(convertView==null)
                vi = inflater.inflate(R.layout.listview_item, parent,false);

            TextView username = (TextView)vi.findViewById(R.id.username_list); // title
            TextView b_group = (TextView)vi.findViewById(R.id.b_group_list); // artist name
            TextView phone = (TextView)vi.findViewById(R.id.phone_list); // duration
            //ImageView thumb_image=(ImageView)vi.findViewById(R.id.list_image); // thumb image

            HashMap<String, String> user = new HashMap<String, String>();
            user = data.get(position);

            // Setting all values in listview

            username.setText(user.get("name"));
            b_group.setText(user.get("b_group"));
            phone.setText(user.get("end_date"));
            //imageLoader.DisplayImage(song.get(CustomizedListView.KEY_THUMB_URL), thumb_image);
            return vi;
        }
    }
