package com.example.drbed;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;


public class StatusCheckActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cctv_info_);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        WebView webView = (WebView)findViewById(R.id.activity_main_webview);
        webView.setPadding(0,0,0,0);
        webView.getSettings().setBuiltInZoomControls(false);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        String url ="http://117.16.174.25:8080/javascript_simple.html";
        webView.loadUrl(url);

        Button buttonServiceStart_btn=(Button)findViewById(R.id.buttonServiceStart);
        buttonServiceStart_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();



        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Log.e(this.getClass().getName(), "@@@@@@@@@@@@@@@@@@@@@@@@@@@@!"+id);
        if (id == R.id.nav_Main) {
            // Handle the camera action
            Log.e(this.getClass().getName(), "nav_Check_real_time_status!");
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_sleep_report_previous) {
            /* 전날 리포트 */

            // thread.interrupt();
            Intent intent = new Intent(this, Simply_referencs_Activity2.class);
            startActivity(intent);
        } else if (id == R.id.nav_sleep_report_week) {
            /* 주간 리포트*/

            // thread.interrupt();
            Intent intent = new Intent(this, Check_sleep_quality_Activity.class);
            startActivity(intent);
        } else if (id == R.id.nav_check_ward) {
            /* ward check */

            //thread.interrupt();
            Intent intent = new Intent(this, StatusCheckActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_body_information_settings) {
            /* 신체정보세팅  */

            // thread.interrupt();
            Intent intent = new Intent(this, UserSettingActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_guardian_ward_setting) {
            /* 부가 세팅 */

            Intent intent = new Intent(this, Changing_info_Activity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        /* nav_header에서 간병인 이름 환자이름 */

        return true;
    }
}
