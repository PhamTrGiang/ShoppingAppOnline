package com.example.onlineshopingapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.example.onlineshopingapp.Fragment.CartFragment;
import com.example.onlineshopingapp.Fragment.HomeFragment;
import com.example.onlineshopingapp.Fragment.SearchFragment;
import com.example.onlineshopingapp.Fragment.SettingFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
    FrameLayout frameLayout;
    BottomNavigationView bottomNavView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        frameLayout = findViewById(R.id.frameLayout);
        replaceFrg̣̣(new HomeFragment());

        bottomNavView = findViewById(R.id.bottomNav);
        bottomNavView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId()==R.id.nav_home){
                    replaceFrg̣̣(new HomeFragment());
                }
                if (item.getItemId()==R.id.nav_search) {
                    replaceFrg̣̣(new SearchFragment());
                }
                if (item.getItemId()==R.id.nav_cart) {
                    replaceFrg̣̣(new CartFragment());
                }
                if (item.getItemId()==R.id.nav_setting) {
                    replaceFrg̣̣(new SettingFragment());
                }
                return true;
            }
        });
    }
    private void replaceFrg̣̣(Fragment frg){
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.frameLayout,frg).commit();
    }

}