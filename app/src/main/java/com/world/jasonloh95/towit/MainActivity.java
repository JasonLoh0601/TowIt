package com.world.jasonloh95.towit;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

public class MainActivity extends AppCompatActivity{
        private Button mDriver, mCustomer;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            mDriver = (Button) findViewById(R.id.driver);
            mCustomer = (Button) findViewById(R.id.customer);

            startService(new Intent(MainActivity.this, onAppKilled.class));
            mDriver.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, DriverLoginActivity.class);
                    startActivity(intent);
                    finish();
                    return;
                }
            });

            mCustomer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, CustomerLoginActivity.class);
                    startActivity(intent);
                    finish();
                    return;
                }
            });
        }
}
