package com.gamerappa.squarebracket;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;

public class VideoAdapter extends BaseAdapter {

    private final Activity activity;
    private final ArrayList<HashMap<String, String>> data;

    public VideoAdapter(Activity activity, ArrayList<HashMap<String, String>> data) {
        this.activity = activity;
        this.data = data;
    }

    @Override
    public int getCount() { return data.size(); }

    @Override
    public HashMap<String, String> getItem(int position) { return data.get(position); }

    @Override
    public long getItemId(int position) { return position; }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(activity).inflate(R.layout.list_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        HashMap<String, String> video = getItem(position);

        holder.title.setText(video.get("title"));
        holder.author.setText(video.get("author"));
        holder.description.setText(video.get("description"));

        Glide.with(activity)
                .load(video.get("preview"))
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .into(holder.preview);

        return convertView;
    }

    private static class ViewHolder {
        final TextView title;
        final TextView author;
        final TextView description;
        final ImageView preview;

        ViewHolder(View view) {
            title       = view.findViewById(R.id.video_title);
            author      = view.findViewById(R.id.video_author);
            description = view.findViewById(R.id.video_description);
            preview     = view.findViewById(R.id.video_preview);
        }
    }
}