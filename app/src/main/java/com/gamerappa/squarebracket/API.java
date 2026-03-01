package com.gamerappa.squarebracket;

import android.util.Log;
import android.widget.ListAdapter;
import android.widget.Toast;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class API {
    String sharedPrefFile = "sqrconfig";
    String TAG = FirstActivity.class.getSimpleName();
    // TODO: Config file for server sh*t

    public ArrayList<HashMap<String, String>> getLastVideos(int limit, int offset) {
        ArrayList<HashMap<String, String>> videosList = new ArrayList<>();
        HttpHandler sh = new HttpHandler();

        // NOTE: this will be moved from /v3/ to /data/ soon.
        Log.d(TAG, "URL itself: " + "https://squarebracket.pw/api/v3/get_uploads?limit=" + limit + "&offset=" + offset);
        String jsonStr = sh.makeServiceCall("https://squarebracket.pw/api/v3/get_uploads?limit=" + limit + "&offset=" + offset);
        Log.d(TAG, "Response from url: " + jsonStr);
        // TODO: Custom URL setting for other instances

        if (jsonStr != null) {
            try {
                JSONObject jsonObj = new JSONObject(jsonStr);

                // Getting JSON Array node
                JSONArray contacts = jsonObj.getJSONArray("uploads");

                // looping through All Contacts
                for (int i = 0; i < contacts.length(); i++) {
                    JSONObject c = contacts.getJSONObject(i);

                    String id = c.getString("upload_id");
                    String name = c.getString("title");
                    String email = c.getString("description");
                    //JSONObject phone = c.getJSONObject("author");
                    //String mobile = phone.getString("username");
                    String mobile = c.getString("author");

                    HashMap<String, String> video = new HashMap<>();

                    video.put("id", id);
                    video.put("title", name);
                    video.put("author", email);
                    video.put("description", mobile);
                    video.put("preview", "https://squarebracket.pw/dynamic/thumbnails/" + id + ".png");


                    // adding contact to contact list
                    videosList.add(video);
                }
            } catch (final JSONException e) {
                Log.e(TAG, "Json parsing error: " + e.getMessage());
            }
        } else {
            Log.e(TAG, "Couldn't get json from server.");

        }
        return videosList;
    }
}
