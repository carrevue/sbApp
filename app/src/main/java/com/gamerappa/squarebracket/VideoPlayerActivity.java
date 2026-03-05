package com.gamerappa.squarebracket;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.fragment.NavHostFragment;

public class VideoPlayerActivity extends AppCompatActivity {
    private static final String TAG = VideoPlayerActivity.class.getSimpleName();
    String video_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);

        video_id = getIntent().getStringExtra("video_id");
        if (video_id != null) {
            Log.d(TAG, video_id);
        } else {
            Log.e(TAG, "video_id is null");
        }

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment_video);
        if (navHostFragment != null) {
            NavController navController = navHostFragment.getNavController();

            // avoid loading new videos list when pressing back
            NavGraph navGraph = navController.getNavInflater().inflate(R.navigation.mobile_navigation);
            navGraph.setStartDestination(R.id.navigation_video_detail);

            Bundle args = new Bundle();
            args.putString("video_id", video_id);
            navController.setGraph(navGraph, args);
        }
    }
}