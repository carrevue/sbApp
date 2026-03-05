package com.gamerappa.squarebracket;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.gamerappa.squarebracket.databinding.ActivityFirstBinding;

public class FirstActivity extends AppCompatActivity {

    private ActivityFirstBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityFirstBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_first);

        binding.navigationLastVideosBtn.setOnClickListener(v -> {
            navController.navigate(R.id.navigation_last_videos);
            updateNavUI(R.id.navigation_last_videos);
        });
        binding.navigationDashboardBtn.setOnClickListener(v -> {
            navController.navigate(R.id.navigation_dashboard);
            updateNavUI(R.id.navigation_dashboard);
        });
        binding.navigationNotificationsBtn.setOnClickListener(v -> {
            navController.navigate(R.id.navigation_notifications);
            updateNavUI(R.id.navigation_notifications);
        });

        // set initial state
        updateNavUI(R.id.navigation_last_videos);
    }

    private void updateNavUI(int selectedId) {
        binding.navigationLastVideosBtn.setSelected(selectedId == R.id.navigation_last_videos);
        binding.navigationLastVideosIcon.setSelected(selectedId == R.id.navigation_last_videos);
        binding.navigationLastVideosText.setSelected(selectedId == R.id.navigation_last_videos);

        binding.navigationDashboardBtn.setSelected(selectedId == R.id.navigation_dashboard);
        binding.navigationDashboardIcon.setSelected(selectedId == R.id.navigation_dashboard);
        binding.navigationDashboardText.setSelected(selectedId == R.id.navigation_dashboard);

        binding.navigationNotificationsBtn.setSelected(selectedId == R.id.navigation_notifications);
        binding.navigationNotificationsIcon.setSelected(selectedId == R.id.navigation_notifications);
        binding.navigationNotificationsText.setSelected(selectedId == R.id.navigation_notifications);
    }
}