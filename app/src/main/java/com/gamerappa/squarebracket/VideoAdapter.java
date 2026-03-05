package com.gamerappa.squarebracket;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

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

        Glide.with(activity)
                .asBitmap()
                .load(video.get("preview"))
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.preview);

        convertView.setOnClickListener(v -> {
            Intent intent = new Intent(activity, VideoPlayerActivity.class);
            intent.putExtra("video_id", video.get("id"));
            activity.startActivity(intent);
        });

        return convertView;
    }

    private static class ViewHolder {
        final TextView title;
        final TextView author;
        final ImageView preview;

        ViewHolder(View view) {
            title       = view.findViewById(R.id.video_title);
            author      = view.findViewById(R.id.video_author);
            preview     = view.findViewById(R.id.video_preview);
        }
    }
}