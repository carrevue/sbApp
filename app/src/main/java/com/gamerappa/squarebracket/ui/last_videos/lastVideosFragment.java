package com.gamerappa.squarebracket.ui.last_videos;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.HeaderViewListAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.gamerappa.squarebracket.R;
import com.gamerappa.squarebracket.API;
import com.gamerappa.squarebracket.VideoAdapter;
import com.gamerappa.squarebracket.databinding.FragmentLastVideosBinding;
import com.gamerappa.squarebracket.ui.EndlessScrollListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

public class lastVideosFragment extends Fragment {

    private lastVideosViewModel lastVideosViewModel;
    private FragmentLastVideosBinding binding;
    private ListView lv;
    private View footerLoader;

    public static final API api = new API();
    ArrayList<HashMap<String, String>> videosList = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        lastVideosViewModel =
                new ViewModelProvider(this).get(lastVideosViewModel.class);

        binding = FragmentLastVideosBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lv = (ListView) getView().findViewById(R.id.list_last_videos);

        // inflate and add loader footer
        footerLoader = LayoutInflater.from(getContext()).inflate(R.layout.list_footer_loader, lv, false);
        lv.addFooterView(footerLoader, null, false);
        footerLoader.setVisibility(View.GONE);

        lv.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                new AddLastVideos().execute((page - 1) * 30);
                return true;
            }
        });
        new lastVideosFragment.GetLastVideos().execute();
    }

    private class GetLastVideos extends AsyncTask<Void, Void, Void> {

        private ProgressBar spinner;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (getView() != null) {
                spinner = getView().findViewById(R.id.spinner_last_videos);
            }
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            videosList = api.getLastVideos(30, 0);

            // preload new thumbnails
            if (videosList != null && getContext() != null) {
                for (HashMap<String, String> video : videosList) {
                    try {
                        Glide.with(getContext().getApplicationContext())
                                .asBitmap()
                                .load(video.get("preview"))
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .submit()
                                .get();
                    } catch (ExecutionException | InterruptedException ignored) {
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (getActivity() != null && lv != null) {
                final VideoAdapter lvadapter = new VideoAdapter(getActivity(), videosList);
                lv.setAdapter(lvadapter);
                lv.setVisibility(View.VISIBLE);
                if (spinner != null) spinner.setVisibility(View.GONE);
            }
        }
    }

    private class AddLastVideos extends AsyncTask<Integer, Void, Void> {

        ArrayList<HashMap<String, String>> videosListNew = new ArrayList<>();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (footerLoader != null) {
                footerLoader.setVisibility(View.VISIBLE);
            }
        }

        @Override
        protected Void doInBackground(Integer... arg0) {
            videosListNew = api.getLastVideos(30, arg0[0]);

            // preload new thumbnails
            if (videosListNew != null && getContext() != null) {
                for (HashMap<String, String> video : videosListNew) {
                    try {
                        Glide.with(getContext().getApplicationContext())
                                .asBitmap()
                                .load(video.get("preview"))
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .submit()
                                .get();
                    } catch (ExecutionException | InterruptedException ignored) {
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (videosListNew != null) {
                videosList.addAll(videosListNew);
                if (lv != null && lv.getAdapter() != null) {
                    ListAdapter adapter = lv.getAdapter();
                    if (adapter instanceof HeaderViewListAdapter) {
                        adapter = ((HeaderViewListAdapter) adapter).getWrappedAdapter();
                    }
                    if (adapter instanceof BaseAdapter) {
                        ((BaseAdapter) adapter).notifyDataSetChanged();
                    }
                }
            }
            if (footerLoader != null) {
                footerLoader.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}