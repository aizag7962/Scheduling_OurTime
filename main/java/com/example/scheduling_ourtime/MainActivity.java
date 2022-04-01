package com.example.scheduling_ourtime;

import android.os.Bundle;
import com.example.scheduling_ourtime.databinding.ActivityMainBinding;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


public class MainActivity extends AppCompatActivity {

   ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new HomeFragment());

        binding.botNavigationView.setOnItemSelectedListener(item -> {

            switch (item.getItemId()) {

                case R.id.action_home:
                    replaceFragment(new HomeFragment());
                    break;
                case R.id.action_chat:
                    replaceFragment(new ChatFragment());
                    break;
                case R.id.action_contacts:
                    replaceFragment(new PeopleFragment());
                    break;
                case R.id.action_calendar:
                    replaceFragment(new CalendarFragment());
                    break;
            }

            return true;
        });
    }


    public void replaceFragment(Fragment fragment) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout,fragment);
        fragmentTransaction.commit();
    }

}