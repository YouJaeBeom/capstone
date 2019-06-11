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

                Static_setting.AVG_HR=(int)Static_setting.sumHR/60;
                Static_setting.AVG_RR=(int)Static_setting.sumRR/60;
                Static_setting.AVG_SV=(int)Static_setting.sumSV/60;
                Static_setting.AVG_HRV=(int)Static_setting.sumHRV/60;
                text_HR.setText( String.valueOf(Static_setting.AVG_HR));
                text_RR.setText( String.valueOf(Static_setting.AVG_RR));
                text_SV.setText( String.valueOf(Static_setting.AVG_SV));

                insertAVGdata();
                showdata();

            }
        });


    }

    private void showdata() {
        TextView text_standardHR= (TextView) findViewById(R.id.text_standardHR);
        TextView text_standardRR= (TextView) findViewById(R.id.text_standardRR);
        TextView text_standardSV= (TextView) findViewById(R.id.text_standardSV);

        TextView text_explainText= (TextView) findViewById(R.id.explainText);
        TextView text_explainText1= (TextView) findViewById(R.id.explainText1);
        if(Integer.parseInt(Static_setting.Age)<25){
            text_standardHR.setText("70~73");
            if(49<=Static_setting.AVG_HR&&Static_setting.AVG_HR<=55){
                text_explainText.setText(
                        "유재범님의 현재 심박수는 운동선수급입니다.");
            }
            if(56<=Static_setting.AVG_HR&&Static_setting.AVG_HR<=61){
                text_explainText.setText(

                        "유재범님의 현재 심박수는 매우좋음입니다.");
            }
            if(62<=Static_setting.AVG_HR&&Static_setting.AVG_HR<=65){
                text_explainText.setText(

                        "유재범님의 현재 심박수는 좋음입니다.");
            }
            if(66<=Static_setting.AVG_HR&&Static_setting.AVG_HR<=69){
                text_explainText.setText(
                        "유재범님의 현재 심박수는 평균이상입니다.");
            }
            if(70<=Static_setting.AVG_HR&&Static_setting.AVG_HR<=73){
                    text_explainText.setText(

                        "유재범님의 현재 심박수는 평균입니다.");
            }
            if(74<=Static_setting.AVG_HR&&Static_setting.AVG_HR<=81){
                text_explainText.setText(
                        "유재범님의 현재 심박수는 평균이하입니다.");
            }
            if(82<=Static_setting.AVG_HR){
                text_explainText.setText(

                        "유재범님의 현재 심박수는 나쁨입니다.");
            }
        }
        if(25<=Integer.parseInt(Static_setting.Age)&&Integer.parseInt(Static_setting.Age)<35){
            text_standardHR.setText("71~74");
            if(49<=Static_setting.AVG_HR&&Static_setting.AVG_HR<=54){
                text_explainText.setText(
                        "유재범님의 현재 심박수는 운동선수급입니다.");
            }
            if(55<=Static_setting.AVG_HR&&Static_setting.AVG_HR<=61){
                text_explainText.setText(

                        "유재범님의 현재 심박수는 매우좋음입니다.");
            }
            if(62<=Static_setting.AVG_HR&&Static_setting.AVG_HR<=65){
                text_explainText.setText(

                        "유재범님의 현재 심박수는 좋음입니다.");
            }
            if(66<=Static_setting.AVG_HR&&Static_setting.AVG_HR<=70){
                text_explainText.setText(

                        "유재범님의 현재 심박수는 평균이상입니다.");
            }
            if(71<=Static_setting.AVG_HR&&Static_setting.AVG_HR<=74){
                text_explainText.setText(
                        "유재범님의 현재 심박수는 평균입니다.");
            }
            if(75<=Static_setting.AVG_HR&&Static_setting.AVG_HR<=81){
                text_explainText.setText(
                        "유재범님의 현재 심박수는 평균이하입니다.");
            }
            if(82<=Static_setting.AVG_HR){
                text_explainText.setText(

                        "유재범님의 현재 심박수는 나쁨입니다.");
            }
        }
        if(35<=Integer.parseInt(Static_setting.Age)&&Integer.parseInt(Static_setting.Age)<45){
            text_standardHR.setText("71~75");
            if(50<=Static_setting.AVG_HR&&Static_setting.AVG_HR<=56){
                text_explainText.setText(

                        "유재범님의 현재 심박수는 운동선수급입니다.");
            }
            if(57<=Static_setting.AVG_HR&&Static_setting.AVG_HR<=62){
                text_explainText.setText(

                        "유재범님의 현재 심박수는 매우좋음입니다.");
            }
            if(63<=Static_setting.AVG_HR&&Static_setting.AVG_HR<=66){
                text_explainText.setText(
                        "유재범님의 현재 심박수는 좋음입니다.");
            }
            if(67<=Static_setting.AVG_HR&&Static_setting.AVG_HR<=70){
                text_explainText.setText(

                        "유재범님의 현재 심박수는 평균이상입니다.");
            }
            if(71<=Static_setting.AVG_HR&&Static_setting.AVG_HR<=75){
                text_explainText.setText(

                        "유재범님의 현재 심박수는 평균입니다.");
            }
            if(76<=Static_setting.AVG_HR&&Static_setting.AVG_HR<=82){
                text_explainText.setText(

                        "유재범님의 현재 심박수는 평균이하입니다.");
            }
            if(83<=Static_setting.AVG_HR){
                text_explainText.setText(

                        "유재범님의 현재 심박수는 나쁨입니다.");
            }
        }
        if(45<=Integer.parseInt(Static_setting.Age)&&Integer.parseInt(Static_setting.Age)<55){
            text_standardHR.setText("72~76");
            if(50<=Static_setting.AVG_HR&&Static_setting.AVG_HR<=57){
                text_explainText.setText("유재범님의 현재 심박수는 운동선수급입니다.");
            }
            if(58<=Static_setting.AVG_HR&&Static_setting.AVG_HR<=63){
                text_explainText.setText(
                        "유재범님의 현재 심박수는 매우좋음입니다.");
            }
            if(64<=Static_setting.AVG_HR&&Static_setting.AVG_HR<=67){
                text_explainText.setText(

                        "유재범님의 현재 심박수는 좋음입니다.");
            }
            if(68<=Static_setting.AVG_HR&&Static_setting.AVG_HR<=71){
                text_explainText.setText(
                        "유재범님의 현재 심박수는 평균이상입니다.");
            }
            if(72<=Static_setting.AVG_HR&&Static_setting.AVG_HR<=76){
                text_explainText.setText(

                        "유재범님의 현재 심박수는 평균입니다.");
            }
            if(77<=Static_setting.AVG_HR&&Static_setting.AVG_HR<=83){
                text_explainText.setText(

                        "유재범님의 현재 심박수는 평균이하입니다.");
            }
            if(84<=Static_setting.AVG_HR){
                text_explainText.setText(

                        "유재범님의 현재 심박수는 나쁨입니다.");
            }
            else
            {
                    text_explainText.setText("범위 밖에 값");
            }
        }
        if(55<=Integer.parseInt(Static_setting.Age)&&Integer.parseInt(Static_setting.Age)<65){
            text_standardHR.setText("72~75");
            if(51<=Static_setting.AVG_HR&&Static_setting.AVG_HR<=56){
                text_explainText.setText(

                        "유재범님의 현재 심박수는 운동선수급입니다.");
            }
            if(57<=Static_setting.AVG_HR&&Static_setting.AVG_HR
                    <=61){
                text_explainText.setText(

                        "유재범님의 현재 심박수는 매우좋음입니다.");
            }
            if(62<=Static_setting.AVG_HR&&Static_setting.AVG_HR<=67){
                text_explainText.setText(
                        "유재범님의 현재 심박수는 좋음입니다.");
            }
            if(68<=Static_setting.AVG_HR&&Static_setting.AVG_HR<=71){
                text_explainText.setText(
                        "유재범님의 현재 심박수는 평균이상입니다.");
            }
            if(72<=Static_setting.AVG_HR&&Static_setting.AVG_HR<=75){
                text_explainText.setText(

                        "유재범님의 현재 심박수는 평균입니다.");
            }
            if(76<=Static_setting.AVG_HR&&Static_setting.AVG_HR<=81){
                text_explainText.setText(

                        "유재범님의 현재 심박수는 평균이하입니다.");
            }
            if(82<=Static_setting.AVG_HR){
                text_explainText.setText(
                        "유재범님의 현재 심박수는 나쁨입니다.");
            }
            else
            {
                text_explainText.setText("범위 밖에 값");
            }
        }
        if(65<=Static_setting.AVG_HR){
            if(50<=Static_setting.AVG_HR&&Static_setting.AVG_HR<=55){
                text_explainText.setText(
                        "유재범님의 현재 심박수는 운동선수급입니다.");
            }
            if(56<=Static_setting.AVG_HR&&Static_setting.AVG_HR<=61){
                text_explainText.setText(
                        "유재범님의 현재 심박수는 매우좋음입니다.");
            }
            if(62<=Static_setting.AVG_HR&&Static_setting.AVG_HR<=65){
                text_explainText.setText(

                        "유재범님의 현재 심박수는 좋음입니다.");
            }
            if(66<=Static_setting.AVG_HR&&Static_setting.AVG_HR<=69){
                text_explainText.setText(
                        "유재범님의 현재 심박수는 평균이상입니다.");
            }
            if(70<=Static_setting.AVG_HR&&Static_setting.AVG_HR<=73){
                text_explainText.setText(

                        "유재범님의 현재 심박수는 평균입니다.");
            }
            if(74<=Static_setting.AVG_HR&&Static_setting.AVG_HR<=79){
                text_explainText.setText(

                        "유재범님의 현재 심박수는 평균이하입니다.");
            }
            if(80<=Static_setting.AVG_HR){
                text_explainText.setText(
                        "유재범님의 현재 심박수는 나쁨입니다.");
            }
            else
            {
                text_explainText.setText("범위 밖에 값");
            }
            text_standardHR.setText("70~73");
        }

        if(50<=Integer.parseInt(Static_setting.Age)){
            if(Static_setting.AVG_RR<=11) {
                text_explainText1.setText(
                        "유재범님의 현재 호흡수는 저호흡수 상태입니다.\n"+
                        "약물 과다 복용, 폐쇄성 수면 무호흡증, 두부 손상 등이 원인이 될 수 있습니다."
                );
            }
            if(12<=Static_setting.AVG_RR&&Static_setting.AVG_RR<=30) {
                text_explainText1.setText(
                        "유재범님의 현재 호흡수는 정상입니다.\n"
                        );
            }
            if(28<=Static_setting.AVG_RR) {
                text_explainText1.setText(
                        "유재범님의 현재 호흡수는 고호흡수 상태입니다.\n" +
                        "불안, 발열, 호흡기 질환, 심장문제, 탈수 등이 원인이 될 수 있습니다.\n"
                        );
            }
            text_standardRR.setText("분당 12-18 회 호흡");

        }
        if(Integer.parseInt(Static_setting.Age)<50)
        {
            if(Static_setting.AVG_RR<=11) {
                text_explainText1.setText(
                        "유재범님의 현재 호흡수는 저호흡수 상태입니다.\n"+
                        "약물 과다 복용, 폐쇄성 수면 무호흡증, 두부 손상 등이 원인이 될 수 있습니다."
                );
            }
            if(12<=Static_setting.AVG_RR&&Static_setting.AVG_RR<=20) {
                text_explainText1.setText(
                        "유재범님의 현재 호흡수는 정상입니다.\n"
                );
            }
            if(21<=Static_setting.AVG_RR) {
                text_explainText1.setText(
                        "유재범님의 현재 호흡수는 고호흡수 상태입니다.\n" +
                        "\n" +
                        "불안, 발열, 호흡기 질환, 심장문제, 탈수 등이 원인이 될 수 있습니다.\n"
                );
            }
            text_standardRR.setText("분당 12-28 회 호흡. ");
        }

        text_standardSV.setText("70~80ml");

    }


    public void insertAVGdata()
    {
        if(Static_setting.Status.contains("Ward")) {
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

            insertAVGdataReqest insertAVGdataReqest = new insertAVGdataReqest(Static_setting.ID, String.valueOf(Static_setting.AVG_HR), String.valueOf(Static_setting.AVG_RR), String.valueOf(Static_setting.AVG_SV), String.valueOf(Static_setting.AVG_HRV), responseListener);
            RequestQueue queue = Volley.newRequestQueue(UserSettingActivity.this);
            queue.add(insertAVGdataReqest);
        }

    }

    public void SimpleThread()
    {
        CheckTypesTask task = new CheckTypesTask();
        task.execute();
        thread = new Thread(new Runnable() {
            @Override
            public void run() {

                for (i = 0; i < count
                        ; i++) {
                    StartAVG();

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
        if(Static_setting.Status.contains("Ward")) {
            Response.Listener<String> responseListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e(this.getClass().getName(), "트라이문out");
                    try {

                        Log.e(this.getClass().getName(), "try in!");
                        JSONObject jsonResponse = new JSONObject(response);
                        boolean success = jsonResponse.getBoolean("success");
                        Log.e(this.getClass().getName(), "success!" + success);
                        Log.e(this.getClass().getName(), "time " + time);
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
                            if (health_info.getHR() == 0) {
                                Log.e(this.getClass().getName(), "0이야");
                                count++;
                                hr0++;
                                Log.e(this.getClass().getName(), "현재 i값 감소 " + i);
                            } else {
                                Static_setting.sumHR += health_info.getHR();
                                Static_setting.sumRR += health_info.getRR();
                                Static_setting.sumSV += health_info.getSV();
                                Static_setting.sumHRV += health_info.getHRV();
                                Log.e(this.getClass().getName(), "현재 i값 " + i);
                                Log.e(this.getClass().getName(), "Static_setting.sumHR " + Static_setting.sumHR);
                                Log.e(this.getClass().getName(), "Static_setting.sumRR " + Static_setting.sumRR);
                                Log.e(this.getClass().getName(), "Static_setting.sumSV " + Static_setting.sumSV);
                                Log.e(this.getClass().getName(), "Static_setting.sumHRV " + Static_setting.sumHRV);
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
            DataAVGRequest dataAVGRequest = new DataAVGRequest(Static_setting.ID, time, responseListener);
            RequestQueue queue = Volley.newRequestQueue(UserSettingActivity.this);
            queue.add(dataAVGRequest);
        }

        if(Static_setting.Status.contains("Guardian")) {
            Response.Listener<String> responseListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e(this.getClass().getName(), "트라이문out");
                    try {

                        Log.e(this.getClass().getName(), "try in!");
                        JSONObject jsonResponse = new JSONObject(response);
                        boolean success = jsonResponse.getBoolean("success");
                        Log.e(this.getClass().getName(), "success!" + success);
                        Log.e(this.getClass().getName(), "time " + time);
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
                            Log.e(this.getClass().getName(), "디비에 가져온값은 " + health_info.getTime());
                            Log.e(this.getClass().getName(), "디비에 가져온값은 " + health_info.getHR());
                            Log.e(this.getClass().getName(), "디비에 가져온값은 " + health_info.getRR());
                            Log.e(this.getClass().getName(), "디비에 가져온값은 " + health_info.getSV());
                            Log.e(this.getClass().getName(), "디비에 가져온값은 " + health_info.getHRV());
                            Log.e(this.getClass().getName(), "디비에 가져온값은 " + health_info.getSignal_Strength());
                            Log.e(this.getClass().getName(), "디비에 가져온값은 " + health_info.getStatus());

                            if (health_info.getHR() == 0) {
                                Log.e(this.getClass().getName(), "0이야");
                                count++;
                                hr0++;
                                Log.e(this.getClass().getName(), "현재 i값 감소 " + i);
                            } else {
                                Static_setting.sumHR += health_info.getHR();
                                Static_setting.sumRR += health_info.getRR();
                                Static_setting.sumSV += health_info.getSV();
                                Static_setting.sumHRV += health_info.getHRV();
                                Log.e(this.getClass().getName(), "현재 i값 " + i);
                                Log.e(this.getClass().getName(), "Static_setting.sumHR " + Static_setting.sumHR);
                                Log.e(this.getClass().getName(), "Static_setting.sumRR " + Static_setting.sumRR);
                                Log.e(this.getClass().getName(), "Static_setting.sumSV " + Static_setting.sumSV);
                                Log.e(this.getClass().getName(), "Static_setting.sumHRV " + Static_setting.sumHRV);

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
            DataAVGRequest dataAVGRequest = new DataAVGRequest(Static_setting.Protected_ID, time, responseListener);
            RequestQueue queue = Volley.newRequestQueue(UserSettingActivity.this);
            queue.add(dataAVGRequest);
        }

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
                for (int i = 0; i < 60; i++) {
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
