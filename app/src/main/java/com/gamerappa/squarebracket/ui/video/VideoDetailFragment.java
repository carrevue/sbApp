package com.gamerappa.squarebracket.ui.video;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.gamerappa.squarebracket.R;

public class VideoDetailFragment extends Fragment {

    private String videoId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            videoId = getArguments().getString("video_id");
            Log.d("VideoDetailFragment", "Received ID: " + videoId);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_video_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView title = view.findViewById(R.id.video_detail_title);
        TextView author = view.findViewById(R.id.video_detail_author);
        TextView description = view.findViewById(R.id.video_detail_description);

        // temporary data for now, this should fetch from the api
        title.setText("Upload " + videoId);
        author.setText("Author");
        description.setText("This is a placeholder description.");
    }
}