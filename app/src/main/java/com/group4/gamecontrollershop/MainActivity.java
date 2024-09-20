package com.group4.gamecontrollershop;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.group4.gamecontrollershop.adapter.ViewPagerAdapter;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView navigationView;
    private ViewPager2 viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        navigationView = findViewById(R.id.bottomNavigationView);
        viewPager = findViewById(R.id.viewPager);

        ViewPagerAdapter adapter = new ViewPagerAdapter(this);
        viewPager.setAdapter(adapter);
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        navigationView.getMenu().findItem(R.id.mHome).setChecked(true);
                        break;
                    case 1:
                        navigationView.getMenu().findItem(R.id.mSearch).setChecked(true);
                        break;
                    case 2:
                        navigationView.getMenu().findItem(R.id.mHistory).setChecked(true);
                        break;
                    case 3:
                        navigationView.getMenu().findItem(R.id.mProfile).setChecked(true);
                        break;
                }
            }
        });

        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.mHome) {
                    viewPager.setCurrentItem(0);
                } else if (itemId == R.id.mSearch) {
                    viewPager.setCurrentItem(1);
                } else if (itemId == R.id.mHistory) {
                    viewPager.setCurrentItem(2);
                } else if (itemId == R.id.mProfile) {
                    viewPager.setCurrentItem(3);
                }
                return true;
            }
        });
    }
}