package com.example.drbed;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class UserSettingActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private Health_info health_info=new Health_info();
    private Thread thread;
//    private TextView text_HR= (TextView) findViewById(R.id.text_HR);
//    private TextView text_RR= (TextView) findViewById(R.id.text_RR);
//    private TextView text_SV= (TextView) findViewById(R.id.text_SV);
//    private TextView text_HRV= (TextView) findViewById(R.id.text_HRV);
    private int i;
    public int count=60;
    public int hr0=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_setting);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        Button avgstart = (Button)findViewById(R.id.AVGstart);
        avgstart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                // AnotherActivity���� �����͸� �����Ͽ� �ִ� �κ�
                Static_setting.sumHR= (float) 0.0;
                Static_setting.sumRR= (float) 0.0;
                Static_setting.sumSV= (float) 0.0;
                Static_setting.sumHRV= (float) 0.0;
                SimpleThread();
            }
        });

        Button avgcheck = (Button)findViewById(R.id.AVGcheck);
        avgcheck.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                // AnotherActivity���� �����͸� �����Ͽ� �ִ� �κ�
                TextView text_HR= (TextView) findViewById(R.id.text_HR);
                TextView text_RR= (TextView) findViewById(R.id.text_RR);
                TextView text_SV= (TextView) findViewById(R.id.text_SV);
                TextView text_HRV= (TextView) findViewById(R.id.text_HRV);
                Static_setting.AVG_HR=Static_setting.sumHR/60;
                Static_setting.AVG_RR=Static_setting.sumRR/60;
                Static_setting.AVG_SV=Static_setting.sumSV/60;
                Static_setting.AVG_HRV=Static_setting.sumHRV/60;
                text_HR.setText( String.valueOf(Static_setting.AVG_HR));
                text_RR.setText( String.valueOf(Static_setting.AVG_RR));
                text_SV.setText( String.valueOf(Static_setting.AVG_SV));
                text_HRV.setText( String.valueOf(Static_setting.AVG_HRV));
                insertAVGdata();

            }
        });


    }


    public void insertAVGdata()
    {
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e(this.getClass().getName(), "트라이문out");
                try {

                    Log.e(this.getClass().getName(), "try in!");
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    Log.e(this.getClass().getName(), "success!" + success);

                    if (success) {
                        Log.e(this.getClass().getName(), "성공함!");
                        Log.e(this.getClass().getName(), "jsonResponse!" + jsonResponse);
                    } else {
                        Log.e(this.getClass().getName(), "Dataset fail");
                        AlertDialog.Builder builder = new AlertDialog.Builder(UserSettingActivity.this);
                        builder.setMessage("Dataset fail")
                                .setNegativeButton("확인", null)
                                .create()
                                .show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        insertAVGdataReqest insertAVGdataReqest = new insertAVGdataReqest( Static_setting.ID,String.valueOf(Static_setting.AVG_HR),String.valueOf(Static_setting.AVG_RR),String.valueOf(Static_setting.AVG_SV),String.valueOf(Static_setting.AVG_HRV),responseListener);
        RequestQueue queue = Volley.newRequestQueue(UserSettingActivity.this);
        queue.add(insertAVGdataReqest);
    }

    public void SimpleThread()
    {
        CheckTypesTask task = new CheckTypesTask();
        task.execute();
        thread = new Thread(new Runnable() {
            @Override
            public void run() {

                for (i = 0; i < count; i++) {
                    StartAVG();
                    if(hr0>30)
                    {
                        thread.interrupted();
                        hr0=0;

                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
        thread.start();


    }



    private void StartAVG() {

        long now = System.currentTimeMillis();
        Date date = new Date(now);
        DateFormat df =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar=Calendar.getInstance();

        calendar.setTime(date);
        calendar.add(Calendar.SECOND,-1);

        final String time=df.format(calendar.getTime());
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e(this.getClass().getName(), "트라이문out");
                try {

                    Log.e(this.getClass().getName(), "try in!");
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    Log.e(this.getClass().getName(), "success!" + success);
                    Log.e(this.getClass().getName(), "time "+ time);
                    if (success) {
                        Log.e(this.getClass().getName(), "성공함!");
                        Log.e(this.getClass().getName(), "jsonResponse!" + jsonResponse);
                        health_info.setTime(jsonResponse.getString("Time"));
                        health_info.setHR(jsonResponse.getString("HR"));
                        health_info.setRR(jsonResponse.getString("RR"));
                        health_info.setSV(jsonResponse.getString("SV"));
                        health_info.setHRV(jsonResponse.getString("HRV"));
                        health_info.setSignal_Strength(jsonResponse.getString("Signal_Strength"));
                        health_info.setStatus(jsonResponse.getString("Status"));
                        Log.e(this.getClass().getName(), "디비에 가져온값은 "+ health_info.getTime());
                        Log.e(this.getClass().getName(), "디비에 가져온값은 "+ health_info.getHR());
                        Log.e(this.getClass().getName(), "디비에 가져온값은 "+ health_info.getRR());
                        Log.e(this.getClass().getName(), "디비에 가져온값은 "+health_info.getSV());
                        Log.e(this.getClass().getName(), "디비에 가져온값은 "+ health_info.getHRV());
                        Log.e(this.getClass().getName(), "디비에 가져온값은 "+ health_info.getSignal_Strength());
                        Log.e(this.getClass().getName(), "디비에 가져온값은 "+ health_info.getStatus());

                        if(health_info.getHR()==0)
                        {
                            Log.e(this.getClass().getName(), "0이야");
                            count++;
                            hr0++;
                            Log.e(this.getClass().getName(), "현재 i값 감소 "+i);
                        }

                        else {
                            Static_setting.sumHR += health_info.getHR();
                            Static_setting.sumRR += health_info.getRR();
                            Static_setting.sumSV += health_info.getSV();
                            Static_setting.sumHRV += health_info.getHRV();
                            Log.e(this.getClass().getName(), "현재 i값 "+i);
                            Log.e(this.getClass().getName(), "Static_setting.sumHR "+ Static_setting.sumHR);
                            Log.e(this.getClass().getName(), "Static_setting.sumRR "+ Static_setting.sumRR);
                            Log.e(this.getClass().getName(), "Static_setting.sumSV "+ Static_setting.sumSV);
                            Log.e(this.getClass().getName(), "Static_setting.sumHRV "+ Static_setting.sumHRV);

                        }
                    } else {
                        count++;
                            hr0++;

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        Log.e(this.getClass().getName(), "확인 건너뜀");
        DataAVGRequest dataAVGRequest = new DataAVGRequest( Static_setting.ID,time,responseListener);
        RequestQueue queue = Volley.newRequestQueue(UserSettingActivity.this);
        queue.add(dataAVGRequest);


    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.user_setting, menu);
        TextView User_Name_text=(TextView) findViewById(R.id.User_Name);
        TextView User_Info_text=(TextView) findViewById(R.id.User_Info);

        /////////////////////////사용자 타입에 따른 메뉴에 나오는 text////////////////////////////


        User_Name_text.setText(Static_setting.ID+"님 환영합니다.");
        User_Info_text.setText("이름:"+Static_setting.Name+"\n"+"전화번호:"+Static_setting.Phone);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class CheckTypesTask extends AsyncTask<Void, Void, Void> {

        ProgressDialog asyncDialog = new ProgressDialog(
                UserSettingActivity.this);

        @Override
        protected void onPreExecute() {
            asyncDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            asyncDialog.setMessage("약 1분간 측정중입니다...");

            // show dialog
            asyncDialog.show();
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            try {
                for (int i = 0; i < count; i++) {
                    asyncDialog.setProgress(i * 2);
                    if(hr0>30)
                    {
                        asyncDialog.dismiss();
                        Thread.interrupted();
                        hr0=0;
                    }
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            asyncDialog.dismiss();
            if(hr0>30)
            {
                asyncDialog.dismiss();
                Thread.interrupted();
                hr0=0;
                AlertDialog.Builder builder = new AlertDialog.Builder(UserSettingActivity.this);
                builder.setMessage("센서를 연결이 불량입니다\n 조정 후 다시 시도해주세요 ")
                        .setNegativeButton("확인", null)
                        .create()
                        .show();
            }
            super.onPostExecute(result);
        }
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
