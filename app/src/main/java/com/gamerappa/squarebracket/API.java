package com.gamerappa.squarebracket;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class API {

    private static final String TAG = API.class.getSimpleName();
    private static final String BASE_URL = "https://squarebracket.pw";

    public ArrayList<HashMap<String, String>> getLastVideos(int limit, int offset) {
        ArrayList<HashMap<String, String>> videoList = new ArrayList<>();
        String url = BASE_URL + "/api/v3/get_uploads?limit=" + limit + "&offset=" + offset;

        Log.d(TAG, "Fetching uploads from: " + url);

        String jsonStr = new HttpHandler().makeServiceCall(url);

        if (jsonStr == null) {
            Log.e(TAG, "No response from server.");
            return videoList;
        }

        try {
            JSONArray uploads = new JSONObject(jsonStr).getJSONArray("uploads");

            for (int i = 0; i < uploads.length(); i++) {
                JSONObject upload = uploads.getJSONObject(i);

                String id = upload.getString("upload_id");

                HashMap<String, String> video = new HashMap<>();
                video.put("id", id);
                video.put("title",       upload.getString("title"));
                video.put("author",      upload.getString("author"));
                video.put("preview",     BASE_URL + "/dynamic/thumbnails/" + id + ".png");

                videoList.add(video);
            }
        } catch (JSONException e) {
            Log.e(TAG, "JSON parsing error: " + e.getMessage());
        }

        return videoList;
    }
}