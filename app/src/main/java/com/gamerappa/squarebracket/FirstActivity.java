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

        binding.navigationLastVideosBtn.setOnClickListener(v -> navController.navigate(R.id.navigation_last_videos));
        binding.navigationDashboardBtn.setOnClickListener(v -> navController.navigate(R.id.navigation_dashboard));
        binding.navigationNotificationsBtn.setOnClickListener(v -> navController.navigate(R.id.navigation_notifications));
    }
}