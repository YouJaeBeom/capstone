package com.example.drbed;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SeekBar;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.drbed.custom.MyValueFormatter;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.StackedValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.example.drbed.custom.MyValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

public class Check_sleep_quality_Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public String ID = "";
    public String PW = "";
    public String Name = "";
    public String Phone = "";
    public String Status = "";
    private BarChart chart;
    private SeekBar seekBarX, seekBarY;
    private TextView tvX, tvY;



    public int total_time0;
    public String ID10 = "";
    public String ID20 = "";
    String stop_time0="";
    String start_time0="";
    String Status10="";
    String Status20="";


    public int total_time1;
    public String ID11 = "";
    public String ID21 = "";
    String stop_time1="";
    String start_time1="";
    String Status11="";
    String Status21="";


    public int total_time2;
    public String ID12 = "";
    public String ID22 = "";
    String stop_time2="";
    String start_time2="";
    String Status12="";
    String Status22="";


    public int total_time3;
    public String ID13 = "";
    public String ID23 = "";
    String stop_time3="";
    String start_time3="";
    String Status13="";
    String Status23="";



    public int total_time4;

    //public String Status = "";
    public String ID14 = "";
    public String ID24 = "";
    String stop_time4="";
    String start_time4="";
    String Status14="";
    String Status24="";



    public int total_time5;

    public String ID15 = "";
    public String ID25= "";
    String stop_time5="";
    String start_time5="";
    String Status15="";
    String Status25="";



    public int total_time6;
    public String ID16 = "";
    public String ID26 = "";
    String stop_time6="";
    String start_time6="";
    String Status16="";
    String Status26="";


    SleepStep_0 sleepStep_0=new SleepStep_0();
    SleepStep_1 sleepStep_1=new SleepStep_1();
    SleepStep_2 sleepStep_2=new SleepStep_2();
    SleepStep_3 sleepStep_3=new SleepStep_3();
    SleepStep_4 sleepStep_4=new SleepStep_4();
    SleepStep_5 sleepStep_5=new SleepStep_5();
    SleepStep_6 sleepStep_6=new SleepStep_6();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_sleep_quality_);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
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
        /*
        하루마다 평균 신체정보를 가지고오도록
        하루마다 수면시간 가지고오도록
         */
        //new BackgroundTask().execute("2019-05-30 07:20:00","2019-05-30 07:50:00");

        long now = System.currentTimeMillis();
        Date date = new Date(now);
        DateFormat df =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.SECOND,-1);
        String getTime=df.format(calendar.getTime());

        Calendar calendar1=Calendar.getInstance();
        calendar1.add(Calendar.DATE,-1);


        String getTime1=df.format(calendar1.getTime());
        Log.e(this.getClass().getName(), "getTime1!" + getTime1);
        Calendar calendar2=Calendar.getInstance();

        calendar2.add(Calendar.DATE,-2);
        String getTime2=df.format(calendar2.getTime());

        Calendar calendar3=Calendar.getInstance();

        calendar3.add(Calendar.DATE,-3);
        String getTime3=df.format(calendar3.getTime());

        Calendar calendar4=Calendar.getInstance();

        calendar4.add(Calendar.DATE,-4);
        String getTime4=df.format(calendar4.getTime());

        Calendar calendar5=Calendar.getInstance();

        calendar5.add(Calendar.DATE,-5);
        String getTime5=df.format(calendar5.getTime());

        Calendar calendar6=Calendar.getInstance();

        calendar6.add(Calendar.DATE,-6);
        String getTime6=df.format(calendar6.getTime());
        //String start_time=Check_sleep_start_Time(getTime);
        Log.e(this.getClass().getName(), "getTime!" + getTime);
        Log.e(this.getClass().getName(), "getTime1!" + getTime1);
        Log.e(this.getClass().getName(), "getTime2!" + getTime2);
        Log.e(this.getClass().getName(), "getTime3!" + getTime3);
        Log.e(this.getClass().getName(), "getTime4!" + getTime4);
        Log.e(this.getClass().getName(), "getTime5!" + getTime5);
        Log.e(this.getClass().getName(), "getTime6!" + getTime6);


        if(Static_setting.Status.contains("Guardian"))
        {
              Check_sleep_start_Time0(Static_setting.Protected_ID,getTime);
              Check_sleep_start_Time1(Static_setting.Protected_ID,getTime1);
            Check_sleep_start_Time2(Static_setting.Protected_ID,getTime2);
            Check_sleep_start_Time3(Static_setting.Protected_ID,getTime3);
            Check_sleep_start_Time4(Static_setting.Protected_ID,getTime4);
            Check_sleep_start_Time5(Static_setting.Protected_ID,getTime5);
            Check_sleep_start_Time6(Static_setting.Protected_ID,getTime6);
        }
        else if(Static_setting.Status.contains("Ward"))
        {
             Check_sleep_start_Time0(Static_setting.ID,getTime);
            Check_sleep_start_Time1(Static_setting.ID,getTime1);
            Check_sleep_start_Time2(Static_setting.ID,getTime2);
            Check_sleep_start_Time3(Static_setting.ID,getTime3);
            Check_sleep_start_Time4(Static_setting.ID,getTime4);
            Check_sleep_start_Time5(Static_setting.ID,getTime5);
            Check_sleep_start_Time6(Static_setting.ID,getTime6);
        }


    }

    public void Check_sleep_start_Time0(final String ID, final String Time) {
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {


                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    Log.e(this.getClass().getName(), "success!" + success);

                    if (success) {

                        Log.e(this.getClass().getName(), "Check_sleep_start_Time!" + jsonResponse);
                        ID20 =jsonResponse.getString("ID");

                        start_time0 =jsonResponse.getString("Time");
                        Status20 =jsonResponse.getString("Status");
                        Log.e(this.getClass().getName(), "000000000000000000000000000000000000000000000000000000000 취침시간이 판단됨");
                        Log.e(this.getClass().getName(), "Start_time"+start_time0);
                        long now = System.currentTimeMillis();
                        Date date = new Date(now);
                        DateFormat df =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        Calendar calendar=Calendar.getInstance();

                        calendar.add(Calendar.SECOND,-1);

                        String getTime=df.format(calendar.getTime());
                        if(Static_setting.Status.contains("Guardian"))
                        {
                            Check_sleep_stop_Time0(Static_setting.Protected_ID,getTime);
                        }
                        else if(Static_setting.Status.contains("Ward"))
                        {

                            Check_sleep_stop_Time0(Static_setting.ID,getTime);
                        }

                    } else {
                        Log.e(this.getClass().getName(), "000000000000000000000000000000000000000000000000000000000 취침시간이 판단안되어 전날로");
                        Check_sleep_start_Time_before0(ID,Time);



                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        long now = System.currentTimeMillis();
        Date date = new Date(now);
        DateFormat df =new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar=Calendar.getInstance();

        calendar.setTime(date);


        String date1=df.format(calendar.getTime());
        Log.e(this.getClass().getName(), "Sleep_start_Time_Request @@@@@@@@@@ date1!" + date1);
        Sleep_start_Time_Request sleep_start_time_request = new Sleep_start_Time_Request(ID, Time,date1, responseListener);
        RequestQueue queue = Volley.newRequestQueue(Check_sleep_quality_Activity.this);
        queue.add(sleep_start_time_request);

    }

    private void Check_sleep_start_Time_before0(String ID,String Time) {
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {


                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    Log.e(this.getClass().getName(), "success!" + success);

                    if (success) {

                        Log.e(this.getClass().getName(), "Check_sleep_start_Time_before!" + jsonResponse);
                        ID20 =jsonResponse.getString("ID");
                        start_time0 =jsonResponse.getString("Time");
                        Status20 =jsonResponse.getString("Status");
                        Log.e(this.getClass().getName(), "000000000000000000000000000000000000000000000000000000000 취침시간이 판단됨");
                        Log.e(this.getClass().getName(), "Start_time"+start_time0);
                        long now = System.currentTimeMillis();
                        Date date = new Date(now);
                        DateFormat df =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        Calendar calendar=Calendar.getInstance();

                        calendar.add(Calendar.SECOND,-1);

                        String getTime=df.format(calendar.getTime());
                        if(Static_setting.Status.contains("Guardian"))
                        {
                            Check_sleep_stop_Time0(Static_setting.Protected_ID,getTime);
                        }
                        else if(Static_setting.Status.contains("Ward"))
                        {

                            Check_sleep_stop_Time0(Static_setting.ID,getTime);
                        }

                    } else {
                        Log.e(this.getClass().getName(), "000000000000000000000000000000000000000000000000000000000 취침시간이 판단안됨");


                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        long now = System.currentTimeMillis();
        Date date = new Date(now);
        DateFormat df =new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar=Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);



        String date1=df.format(calendar.getTime());
        Log.e(this.getClass().getName(), "Sleep_start_Time_before_Request @@@@@@@@@@ date1!" + date1);
        Sleep_start_Time_before_Request sleep_start_time_before_request = new Sleep_start_Time_before_Request(ID, Time,date1, responseListener);
        RequestQueue queue = Volley.newRequestQueue(Check_sleep_quality_Activity.this);
        queue.add(sleep_start_time_before_request);

    }

    private void Check_sleep_stop_Time0(String ID,String Time) {

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {


                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    Log.e(this.getClass().getName(), "success!" + success);

                    if (success) {
                        Log.e(this.getClass().getName(), "성공함!");
                        Log.e(this.getClass().getName(), "jsonResponse!" + jsonResponse);
                        ID10 =jsonResponse.getString("ID");
                        stop_time0 =jsonResponse.getString("Time");
                        Status10 =jsonResponse.getString("Status");
                        //Log.e(this.getClass().getName(), "Start_time"+start_time);

                        startSleepQulity0();



                    } else {
                        Log.e(this.getClass().getName(), "000000000000000000000000000000000000000000000000000000000 시간찾지못함");



                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        DateFormat df =new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar=Calendar.getInstance();

        calendar.setTime(date);



        String date1=df.format(calendar.getTime());
        Sleep_stop_Time_Request sleep_stop_time_request = new Sleep_stop_Time_Request(ID, Time,date1, responseListener);
        RequestQueue queue = Volley.newRequestQueue(Check_sleep_quality_Activity.this);
        queue.add(sleep_stop_time_request);

    }

    private void startSleepQulity0() {
        Log.e(this.getClass().getName(), "000000000000000000000000000000000000000000000000000000000");
        Log.e(this.getClass().getName(), "stop_time"+stop_time0);
        Log.e(this.getClass().getName(), "Start_time"+start_time0);
        if(Static_setting.Status.contains("Guardian"))
        {
            new BackgroundTask0().execute(Static_setting.Protected_ID,start_time0,stop_time0);
        }
        else if(Static_setting.Status.contains("Ward"))
        {
            new BackgroundTask0().execute(Static_setting.ID,start_time0,stop_time0);
        }
//        TextView text_total_sleep= (TextView) findViewById(R.id.total_sleep);
//        TextView text_sleep= (TextView) findViewById(R.id.sleep);
//        TextView text_wake= (TextView) findViewById(R.id.wake);
//
//
//
//        SimpleDateFormat  dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm");
//        Date start_time1= null; ///9
//        Date stop_time1=null;
//        try {
//            start_time1 = dateFormat.parse(start_time);
//            stop_time1= dateFormat.parse(stop_time); //10
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        long duration = stop_time1.getTime()-start_time1.getTime();
//        long min = duration/60000;
//        int hour= (int) (min/60);
//        int min1= (int) (min%60);
//        String total_time=String.valueOf(min);
//        text_total_sleep.setText(hour+"시간"+min1+"분");
//        text_sleep.setText(start_time);
//        text_wake.setText(stop_time);

    }

    public void Check_sleep_start_Time1(final String ID, final String Time) {
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {


                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    Log.e(this.getClass().getName(), "success!" + success);

                    if (success) {

                        Log.e(this.getClass().getName(), "Check_sleep_start_Time!" + jsonResponse);
                        ID21 =jsonResponse.getString("ID");
                        start_time1 =jsonResponse.getString("Time");
                        Status21 =jsonResponse.getString("Status");
                        Log.e(this.getClass().getName(), "111111111111111111111111111111111111111111111111111 시작찾음");
                        Log.e(this.getClass().getName(), "Start_time"+start_time1);
                        long now = System.currentTimeMillis();
                        Date date = new Date(now);
                        DateFormat df =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        Calendar calendar=Calendar.getInstance();


                        calendar.add(Calendar.DATE, -1);

                        String getTime=df.format(calendar.getTime());
                        if(Static_setting.Status.contains("Guardian"))
                        {
                            Check_sleep_stop_Time1(Static_setting.Protected_ID,getTime);
                        }
                        else if(Static_setting.Status.contains("Ward"))
                        {

                            Check_sleep_stop_Time1(Static_setting.ID,getTime);
                        }

                    } else {
                        Log.e(this.getClass().getName(), "111111111111111111111111111111111111111111111111111 전날로 검색");
                        Check_sleep_start_Time_before1(ID,Time);



                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        long now = System.currentTimeMillis();
        Date date = new Date(now);
        DateFormat df =new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar=Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);



        String date1=df.format(calendar.getTime());
        Log.e(this.getClass().getName(), "Sleep_start_Time_Request @@@@@@@@@@ date1!" + date1);
        Sleep_start_Time_Request sleep_start_time_request = new Sleep_start_Time_Request(ID, Time,date1, responseListener);
        RequestQueue queue = Volley.newRequestQueue(Check_sleep_quality_Activity.this);
        queue.add(sleep_start_time_request);

    }

    private void Check_sleep_start_Time_before1(String ID,String Time) {
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {


                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    Log.e(this.getClass().getName(), "success!" + success);

                    if (success) {

                        Log.e(this.getClass().getName(), "Check_sleep_start_Time_before!" + jsonResponse);
                        ID21 =jsonResponse.getString("ID");
                        start_time1 =jsonResponse.getString("Time");
                        Status21 =jsonResponse.getString("Status");
                        Log.e(this.getClass().getName(), "111111111111111111111111111111111111111111111111111 전날에서 시작점 찾음");
                        Log.e(this.getClass().getName(), "Start_time"+start_time1);
                        long now = System.currentTimeMillis();
                        Date date = new Date(now);
                        DateFormat df =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        Calendar calendar=Calendar.getInstance();

                        calendar.add(Calendar.DATE, -1);

                        String getTime=df.format(calendar.getTime());
                        if(Static_setting.Status.contains("Guardian"))
                        {
                            Check_sleep_stop_Time1(Static_setting.Protected_ID,getTime);
                        }
                        else if(Static_setting.Status.contains("Ward"))
                        {

                            Check_sleep_stop_Time1(Static_setting.ID,getTime);
                        }

                    } else {

                        Log.e(this.getClass().getName(), "111111111111111111111111111111111111111111111111111 시작시점 못찾음");

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        long now = System.currentTimeMillis();
        Date date = new Date(now);
        DateFormat df =new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar=Calendar.getInstance();
        calendar.add(Calendar.DATE, -2);




        String date1=df.format(calendar.getTime());
        Log.e(this.getClass().getName(), "Sleep_start_Time_before_Request @@@@@@@@@@ date1!" + date1);
        Sleep_start_Time_before_Request sleep_start_time_before_request = new Sleep_start_Time_before_Request(ID, Time,date1, responseListener);
        RequestQueue queue = Volley.newRequestQueue(Check_sleep_quality_Activity.this);
        queue.add(sleep_start_time_before_request);

    }

    private void Check_sleep_stop_Time1(String ID,String Time) {

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {


                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    Log.e(this.getClass().getName(), "success!" + success);

                    if (success) {
                        Log.e(this.getClass().getName(), "성공함!");
                        Log.e(this.getClass().getName(), "jsonResponse!" + jsonResponse);
                        ID11=jsonResponse.getString("ID");
                        stop_time1 =jsonResponse.getString("Time");
                        Status11 =jsonResponse.getString("Status");
                        //Log.e(this.getClass().getName(), "Start_time"+start_time);

                        startSleepQulity1();



                    } else {
                        Log.e(this.getClass().getName(), "111111111111111111111111111111111111111111111111111 시간 찾지못함");



                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        DateFormat df =new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar=Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);


        String date1=df.format(calendar.getTime());
        Sleep_stop_Time_Request sleep_stop_time_request = new Sleep_stop_Time_Request(ID, Time,date1, responseListener);
        RequestQueue queue = Volley.newRequestQueue(Check_sleep_quality_Activity.this);
        queue.add(sleep_stop_time_request);

    }

    private void startSleepQulity1() {
        Log.e(this.getClass().getName(), "111111111111111111111111111111111111111111111111111");
        Log.e(this.getClass().getName(), "stop_time"+stop_time1);
        Log.e(this.getClass().getName(), "Start_time"+start_time1);
        if(Static_setting.Status.contains("Guardian"))
        {

            new BackgroundTask1().execute(Static_setting.Protected_ID,start_time1,stop_time1);
        }
        else if(Static_setting.Status.contains("Ward"))
        {

            new BackgroundTask1().execute(Static_setting.ID,start_time1,stop_time1);
        }
//        TextView text_total_sleep= (TextView) findViewById(R.id.total_sleep);
//        TextView text_sleep= (TextView) findViewById(R.id.sleep);
//        TextView text_wake= (TextView) findViewById(R.id.wake);
//
//
//
//        SimpleDateFormat  dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm");
//        Date start_time1= null; ///9
//        Date stop_time1=null;
//        try {
//            start_time1 = dateFormat.parse(start_time);
//            stop_time1= dateFormat.parse(stop_time); //10
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        long duration = stop_time1.getTime()-start_time1.getTime();
//        long min = duration/60000;
//        int hour= (int) (min/60);
//        int min1= (int) (min%60);
//        String total_time=String.valueOf(min);
//        text_total_sleep.setText(hour+"시간"+min1+"분");
//        text_sleep.setText(start_time);
//        text_wake.setText(stop_time);

    }

    public void Check_sleep_start_Time2(final String ID, final String Time) {
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {


                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    Log.e(this.getClass().getName(), "success!" + success);

                    if (success) {

                        Log.e(this.getClass().getName(), "Check_sleep_start_Time!" + jsonResponse);
                        ID22 =jsonResponse.getString("ID");
                        start_time2 =jsonResponse.getString("Time");
                        Status22 =jsonResponse.getString("Status");
                        Log.e(this.getClass().getName(), "22222222222222222222222222222222222222 시작점찾음");
                        Log.e(this.getClass().getName(), "Start_time"+start_time2);
                        long now = System.currentTimeMillis();
                        Date date = new Date(now);
                        DateFormat df =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        Calendar calendar=Calendar.getInstance();


                        calendar.add(Calendar.DATE, -2);

                        String getTime=df.format(calendar.getTime());
                        if(Static_setting.Status.contains("Guardian"))
                        {
                            Check_sleep_stop_Time2(Static_setting.Protected_ID,getTime);
                        }
                        else if(Static_setting.Status.contains("Ward"))
                        {

                            Check_sleep_stop_Time2(Static_setting.ID,getTime);
                        }

                    } else {
                        Log.e(this.getClass().getName(), "22222222222222222222222222222222222222  전날로");
                        Check_sleep_start_Time_before2(ID,Time);



                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        long now = System.currentTimeMillis();
        Date date = new Date(now);
        DateFormat df =new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar=Calendar.getInstance();


        calendar.add(Calendar.DATE, -2);

        String date1=df.format(calendar.getTime());
        Log.e(this.getClass().getName(), "Sleep_start_Time_Request @@@@@@@@@@ date1!" + date1);
        Sleep_start_Time_Request sleep_start_time_request = new Sleep_start_Time_Request(ID, Time,date1, responseListener);
        RequestQueue queue = Volley.newRequestQueue(Check_sleep_quality_Activity.this);
        queue.add(sleep_start_time_request);

    }

    private void Check_sleep_start_Time_before2(String ID,String Time) {
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {


                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    Log.e(this.getClass().getName(), "success!" + success);

                    if (success) {

                        Log.e(this.getClass().getName(), "Check_sleep_start_Time_before!" + jsonResponse);
                        ID22 =jsonResponse.getString("ID");
                        start_time2 =jsonResponse.getString("Time");
                        Status22 =jsonResponse.getString("Status");
                        Log.e(this.getClass().getName(), "22222222222222222222222222222222222222 전날에서 시작점찾음");
                        Log.e(this.getClass().getName(), "Start_time"+start_time2);
                        long now = System.currentTimeMillis();
                        Date date = new Date(now);
                        DateFormat df =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        Calendar calendar=Calendar.getInstance();



                        calendar.add(Calendar.DATE, -2);

                        String getTime=df.format(calendar.getTime());
                        if(Static_setting.Status.contains("Guardian"))
                        {
                            Check_sleep_stop_Time2(Static_setting.Protected_ID,getTime);
                        }
                        else if(Static_setting.Status.contains("Ward"))
                        {

                            Check_sleep_stop_Time2(Static_setting.ID,getTime);
                        }

                    } else {
                        Log.e(this.getClass().getName(), "22222222222222222222222222222222222222 시작점을아예찾지못함");

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        long now = System.currentTimeMillis();
        Date date = new Date(now);
        DateFormat df =new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar=Calendar.getInstance();
        calendar.add(Calendar.DATE, -3);



        String date1=df.format(calendar.getTime());
        Log.e(this.getClass().getName(), "Sleep_start_Time_before_Request @@@@@@@@@@ date1!" + date1);
        Sleep_start_Time_before_Request sleep_start_time_before_request = new Sleep_start_Time_before_Request(ID, Time,date1, responseListener);
        RequestQueue queue = Volley.newRequestQueue(Check_sleep_quality_Activity.this);
        queue.add(sleep_start_time_before_request);

    }

    private void Check_sleep_stop_Time2(String ID,String Time) {

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {


                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    Log.e(this.getClass().getName(), "success!" + success);

                    if (success) {
                        Log.e(this.getClass().getName(), "성공함!");
                        Log.e(this.getClass().getName(), "jsonResponse!" + jsonResponse);
                        ID12 =jsonResponse.getString("ID");
                        stop_time2 =jsonResponse.getString("Time");
                        Status12 =jsonResponse.getString("Status");
                        //Log.e(this.getClass().getName(), "Start_time"+start_time);

                        startSleepQulity2();



                    } else {
                        Log.e(this.getClass().getName(), "22222222222222222222222222222222222222 stop을 못찾음");


                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        DateFormat df =new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar=Calendar.getInstance();
        calendar.add(Calendar.DATE, -2);



        String date1=df.format(calendar.getTime());
        Sleep_stop_Time_Request sleep_stop_time_request = new Sleep_stop_Time_Request(ID, Time,date1, responseListener);
        RequestQueue queue = Volley.newRequestQueue(Check_sleep_quality_Activity.this);
        queue.add(sleep_stop_time_request);

    }

    private void startSleepQulity2() {
        Log.e(this.getClass().getName(), "22222222222222222222222222222222222222");
        Log.e(this.getClass().getName(), "stop_time"+stop_time2);
        Log.e(this.getClass().getName(), "Start_time"+start_time2);


        if(Static_setting.Status.contains("Guardian"))
        {

            new BackgroundTask2().execute(Static_setting.Protected_ID,start_time2,stop_time2);
        }
        else if(Static_setting.Status.contains("Ward"))
        {

            new BackgroundTask2().execute(Static_setting.ID,start_time2,stop_time2);
        }
//        TextView text_total_sleep= (TextView) findViewById(R.id.total_sleep);
//        TextView text_sleep= (TextView) findViewById(R.id.sleep);
//        TextView text_wake= (TextView) findViewById(R.id.wake);
//
//        SimpleDateFormat  dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm");
//        Date start_time1= null; ///9
//        Date stop_time1=null;
//        try {
//            start_time1 = dateFormat.parse(start_time);
//            stop_time1= dateFormat.parse(stop_time); //10
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        long duration = stop_time1.getTime()-start_time1.getTime();
//        long min = duration/60000;
//        int hour= (int) (min/60);
//        int min1= (int) (min%60);
//        String total_time=String.valueOf(min);
//        text_total_sleep.setText(hour+"시간"+min1+"분");
//        text_sleep.setText(start_time);
//        text_wake.setText(stop_time);
//
    }

    public void Check_sleep_start_Time3(final String ID, final String Time) {
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {


                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    Log.e(this.getClass().getName(), "success!" + success);

                    if (success) {

                        Log.e(this.getClass().getName(), "Check_sleep_start_Time!" + jsonResponse);
                        ID23 =jsonResponse.getString("ID");
                        start_time3 =jsonResponse.getString("Time");
                        Status23 =jsonResponse.getString("Status");
                        Log.e(this.getClass().getName(), "33333333333333333333333333333333333 시작점 찾ㅁ음");
                        Log.e(this.getClass().getName(), "Start_time"+start_time3);
                        long now = System.currentTimeMillis();
                        Date date = new Date(now);
                        DateFormat df =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        Calendar calendar=Calendar.getInstance();



                        calendar.add(Calendar.DATE, -3);

                        String getTime=df.format(calendar.getTime());
                        if(Static_setting.Status.contains("Guardian"))
                        {
                            Check_sleep_stop_Time3(Static_setting.Protected_ID,getTime);
                        }
                        else if(Static_setting.Status.contains("Ward"))
                        {

                            Check_sleep_stop_Time3(Static_setting.ID,getTime);
                        }

                    } else {
                        Log.e(this.getClass().getName(), "33333333333333333333333333333333333 전날ㄹ로");
                        Check_sleep_start_Time_before3(ID,Time);



                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        long now = System.currentTimeMillis();
        Date date = new Date(now);
        DateFormat df =new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar=Calendar.getInstance();



        calendar.add(Calendar.DATE, -3);

        String date1=df.format(calendar.getTime());
        Log.e(this.getClass().getName(), "Sleep_start_Time_Request @@@@@@@@@@ date1!" + date1);
        Sleep_start_Time_Request sleep_start_time_request = new Sleep_start_Time_Request(ID, Time,date1, responseListener);
        RequestQueue queue = Volley.newRequestQueue(Check_sleep_quality_Activity.this);
        queue.add(sleep_start_time_request);

    }

    private void Check_sleep_start_Time_before3(String ID,String Time) {
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {


                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    Log.e(this.getClass().getName(), "success!" + success);

                    if (success) {

                        Log.e(this.getClass().getName(), "Check_sleep_start_Time_before!" + jsonResponse);
                        ID23 =jsonResponse.getString("ID");
                        start_time3 =jsonResponse.getString("Time");
                        Status23 =jsonResponse.getString("Status");
                        Log.e(this.getClass().getName(), "33333333333333333333333333333333333 전날에서 시작점");
                        Log.e(this.getClass().getName(), "Start_time"+start_time3);
                        long now = System.currentTimeMillis();
                        Date date = new Date(now);
                        DateFormat df =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        Calendar calendar=Calendar.getInstance();



                        calendar.add(Calendar.DATE, -4);

                        String getTime=df.format(calendar.getTime());
                        if(Static_setting.Status.contains("Guardian"))
                        {
                            Check_sleep_stop_Time3(Static_setting.Protected_ID,getTime);
                        }
                        else if(Static_setting.Status.contains("Ward"))
                        {

                            Check_sleep_stop_Time3(Static_setting.ID,getTime);
                        }

                    } else {
                        Log.e(this.getClass().getName(), "33333333333333333333333333333333333 시작점을 못찾음");

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        long now = System.currentTimeMillis();
        Date date = new Date(now);
        DateFormat df =new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar=Calendar.getInstance();
        calendar.add(Calendar.DATE, -3);



        String date1=df.format(calendar.getTime());
        Log.e(this.getClass().getName(), "Sleep_start_Time_before_Request @@@@@@@@@@ date1!" + date1);
        Sleep_start_Time_before_Request sleep_start_time_before_request = new Sleep_start_Time_before_Request(ID, Time,date1, responseListener);
        RequestQueue queue = Volley.newRequestQueue(Check_sleep_quality_Activity.this);
        queue.add(sleep_start_time_before_request);

    }

    private void Check_sleep_stop_Time3(String ID,String Time) {

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {


                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    Log.e(this.getClass().getName(), "success!" + success);

                    if (success) {
                        Log.e(this.getClass().getName(), "성공함!");
                        Log.e(this.getClass().getName(), "jsonResponse!" + jsonResponse);
                        ID13 =jsonResponse.getString("ID");
                        stop_time3 =jsonResponse.getString("Time");
                        Status13 =jsonResponse.getString("Status");
                        //Log.e(this.getClass().getName(), "Start_time"+start_time);

                        startSleepQulity3();



                    } else {
                        Log.e(this.getClass().getName(), "33333333333333333333333333333333333 stop점 못찾음");


                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        DateFormat df =new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar=Calendar.getInstance();



        calendar.add(Calendar.DATE, -3);

        String date1=df.format(calendar.getTime());
        Sleep_stop_Time_Request sleep_stop_time_request = new Sleep_stop_Time_Request(ID, Time,date1, responseListener);
        RequestQueue queue = Volley.newRequestQueue(Check_sleep_quality_Activity.this);
        queue.add(sleep_stop_time_request);

    }

    private void startSleepQulity3() {
        Log.e(this.getClass().getName(), "33333333333333333333333333333333333");
        Log.e(this.getClass().getName(), "stop_time"+stop_time3);
        Log.e(this.getClass().getName(), "Start_time"+start_time3);
        if(Static_setting.Status.contains("Guardian"))
        {

            new BackgroundTask3().execute(Static_setting.Protected_ID,start_time3,stop_time3);
        }
        else if(Static_setting.Status.contains("Ward"))
        {

            new BackgroundTask3().execute(Static_setting.ID,start_time3,stop_time3);
        }
//        TextView text_total_sleep= (TextView) findViewById(R.id.total_sleep);
//        TextView text_sleep= (TextView) findViewById(R.id.sleep);
//        TextView text_wake= (TextView) findViewById(R.id.wake);
//
//
//
//        SimpleDateFormat  dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm");
//        Date start_time1= null; ///9
//        Date stop_time1=null;
//        try {
//            start_time1 = dateFormat.parse(start_time);
//            stop_time1= dateFormat.parse(stop_time); //10
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        long duration = stop_time1.getTime()-start_time1.getTime();
//        long min = duration/60000;
//        int hour= (int) (min/60);
//        int min1= (int) (min%60);
//        String total_time=String.valueOf(min);
//        text_total_sleep.setText(hour+"시간"+min1+"분");
//        text_sleep.setText(start_time);
//        text_wake.setText(stop_time);

    }

    public void Check_sleep_start_Time4(final String ID, final String Time) {
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {


                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    Log.e(this.getClass().getName(), "success!" + success);

                    if (success) {

                        Log.e(this.getClass().getName(), "Check_sleep_start_Time!" + jsonResponse);
                        ID24 =jsonResponse.getString("ID");
                        start_time4 =jsonResponse.getString("Time");
                        Status24 =jsonResponse.getString("Status");
                        Log.e(this.getClass().getName(), "4444444444444444444444444444444444444444 시작점 바로 찾음");
                        Log.e(this.getClass().getName(), "Start_time"+start_time4);
                        long now = System.currentTimeMillis();
                        Date date = new Date(now);
                        DateFormat df =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        Calendar calendar=Calendar.getInstance();

                        calendar.add(Calendar.DATE, -4);

                        String getTime=df.format(calendar.getTime());
                        if(Static_setting.Status.contains("Guardian"))
                        {
                            Check_sleep_stop_Time4(Static_setting.Protected_ID,getTime);
                        }
                        else if(Static_setting.Status.contains("Ward"))
                        {

                            Check_sleep_stop_Time4(Static_setting.ID,getTime);
                        }

                    } else {
                        Log.e(this.getClass().getName(), "4444444444444444444444444444444444444444444 시작점 전날로");
                        Check_sleep_start_Time_before4(ID,Time);



                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        long now = System.currentTimeMillis();
        Date date = new Date(now);
        DateFormat df =new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar=Calendar.getInstance();


        calendar.add(Calendar.DATE, -4);

        String date1=df.format(calendar.getTime());
        Log.e(this.getClass().getName(), "Sleep_start_Time_Request @@@@@@@@@@ date1!" + date1);
        Sleep_start_Time_Request sleep_start_time_request = new Sleep_start_Time_Request(ID, Time,date1, responseListener);
        RequestQueue queue = Volley.newRequestQueue(Check_sleep_quality_Activity.this);
        queue.add(sleep_start_time_request);

    }

    private void Check_sleep_start_Time_before4(String ID,String Time) {
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {


                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    Log.e(this.getClass().getName(), "success!" + success);

                    if (success) {

                        Log.e(this.getClass().getName(), "Check_sleep_start_Time_before!" + jsonResponse);
                        ID24 =jsonResponse.getString("ID");
                        start_time4 =jsonResponse.getString("Time");
                        Status24 =jsonResponse.getString("Status");
                        Log.e(this.getClass().getName(), "4444444444444444444444444444444444444444444444444 시작점 전날에ㅓ 찾음");
                        Log.e(this.getClass().getName(), "Start_time"+start_time4);
                        long now = System.currentTimeMillis();
                        Date date = new Date(now);
                        DateFormat df =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        Calendar calendar=Calendar.getInstance();



                        calendar.add(Calendar.DATE, -4);

                        String getTime=df.format(calendar.getTime());
                        if(Static_setting.Status.contains("Guardian"))
                        {
                            Check_sleep_stop_Time4(Static_setting.Protected_ID,getTime);
                        }
                        else if(Static_setting.Status.contains("Ward"))
                        {

                            Check_sleep_stop_Time4(Static_setting.ID,getTime);
                        }

                    } else {
                        Log.e(this.getClass().getName(), "4444444444444444444444444444444444444444444444444 시작점 못찾음");


                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        long now = System.currentTimeMillis();
        Date date = new Date(now);
        DateFormat df =new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar=Calendar.getInstance();
        calendar.add(Calendar.DATE, -5);



        String date1=df.format(calendar.getTime());
        Log.e(this.getClass().getName(), "Sleep_start_Time_before_Request @@@@@@@@@@ date1!" + date1);
        Sleep_start_Time_before_Request sleep_start_time_before_request = new Sleep_start_Time_before_Request(ID, Time,date1, responseListener);
        RequestQueue queue = Volley.newRequestQueue(Check_sleep_quality_Activity.this);
        queue.add(sleep_start_time_before_request);

    }

    private void Check_sleep_stop_Time4(String ID,String Time) {

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {


                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    Log.e(this.getClass().getName(), "success!" + success);

                    if (success) {
                        Log.e(this.getClass().getName(), "성공함!");
                        Log.e(this.getClass().getName(), "jsonResponse!" + jsonResponse);
                        ID14 =jsonResponse.getString("ID");
                        stop_time4 =jsonResponse.getString("Time");
                        Status14 =jsonResponse.getString("Status");
                        //Log.e(this.getClass().getName(), "Start_time"+start_time);

                        startSleepQulity4();



                    } else {
                        Log.e(this.getClass().getName(), "4444444444444444444444444444444444444444444444444 stop점 못찾음");


                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        DateFormat df =new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar=Calendar.getInstance();



        calendar.add(Calendar.DATE, -4);

        String date1=df.format(calendar.getTime());
        Sleep_stop_Time_Request sleep_stop_time_request = new Sleep_stop_Time_Request(ID, Time,date1, responseListener);
        RequestQueue queue = Volley.newRequestQueue(Check_sleep_quality_Activity.this);
        queue.add(sleep_stop_time_request);

    }

    private void startSleepQulity4() {
        Log.e(this.getClass().getName(), "4444444444444444444444444444444444444444444444444 ");
        Log.e(this.getClass().getName(), "stop_time"+stop_time4);
        Log.e(this.getClass().getName(), "Start_time"+start_time4);
        if(Static_setting.Status.contains("Guardian"))
        {

            new BackgroundTask4().execute(Static_setting.Protected_ID,start_time4,stop_time4);
        }
        else if(Static_setting.Status.contains("Ward"))
        {

            new BackgroundTask4().execute(Static_setting.ID,start_time4,stop_time4);
        }
//        TextView text_total_sleep= (TextView) findViewById(R.id.total_sleep);
//        TextView text_sleep= (TextView) findViewById(R.id.sleep);
//        TextView text_wake= (TextView) findViewById(R.id.wake);
//
//
//
//        SimpleDateFormat  dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm");
//        Date start_time1= null; ///9
//        Date stop_time1=null;
//        try {
//            start_time1 = dateFormat.parse(start_time);
//            stop_time1= dateFormat.parse(stop_time); //10
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        long duration = stop_time1.getTime()-start_time1.getTime();
//        long min = duration/60000;
//        int hour= (int) (min/60);
//        int min1= (int) (min%60);
//        String total_time=String.valueOf(min);
//        text_total_sleep.setText(hour+"시간"+min1+"분");
//        text_sleep.setText(start_time);
//        text_wake.setText(stop_time);

    }
    public void Check_sleep_start_Time5(final String ID, final String Time) {
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {


                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    Log.e(this.getClass().getName(), "success!" + success);

                    if (success) {

                        Log.e(this.getClass().getName(), "Check_sleep_start_Time!" + jsonResponse);
                        ID25 =jsonResponse.getString("ID");
                        start_time5 =jsonResponse.getString("Time");
                        Status25 =jsonResponse.getString("Status");
                        Log.e(this.getClass().getName(), "555555555555555555555555555555555555555 시작점 찾음");
                        Log.e(this.getClass().getName(), "Start_time"+start_time5);
                        long now = System.currentTimeMillis();
                        Date date = new Date(now);
                        DateFormat df =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        Calendar calendar=Calendar.getInstance();

                        calendar.add(Calendar.DATE, -5);

                        String getTime=df.format(calendar.getTime());
                        if(Static_setting.Status.contains("Guardian"))
                        {
                            Check_sleep_stop_Time5(Static_setting.Protected_ID,getTime);
                        }
                        else if(Static_setting.Status.contains("Ward"))
                        {

                            Check_sleep_stop_Time5(Static_setting.ID,getTime);
                        }

                    } else {
                        Log.e(this.getClass().getName(), "555555555555555555555555555555555555555 시작점 전날로");
                        Check_sleep_start_Time_before5(ID,Time);



                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        long now = System.currentTimeMillis();
        Date date = new Date(now);
        DateFormat df =new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar=Calendar.getInstance();



        calendar.add(Calendar.DATE, -5);

        String date1=df.format(calendar.getTime());
        Log.e(this.getClass().getName(), "Sleep_start_Time_Request @@@@@@@@@@ date1!" + date1);
        Sleep_start_Time_Request sleep_start_time_request = new Sleep_start_Time_Request(ID, Time,date1, responseListener);
        RequestQueue queue = Volley.newRequestQueue(Check_sleep_quality_Activity.this);
        queue.add(sleep_start_time_request);

    }

    private void Check_sleep_start_Time_before5(String ID,String Time) {
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {


                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    Log.e(this.getClass().getName(), "success!" + success);

                    if (success) {

                        Log.e(this.getClass().getName(), "Check_sleep_start_Time_before!" + jsonResponse);
                        ID25 =jsonResponse.getString("ID");
                        start_time5 =jsonResponse.getString("Time");
                        Status25 =jsonResponse.getString("Status");
                        Log.e(this.getClass().getName(), "555555555555555555555555555555555555555 전날에서 찾음");
                        Log.e(this.getClass().getName(), "Start_time"+start_time5);
                        long now = System.currentTimeMillis();
                        Date date = new Date(now);
                        DateFormat df =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        Calendar calendar=Calendar.getInstance();


                        calendar.add(Calendar.DATE, -5);

                        String getTime=df.format(calendar.getTime());
                        if(Static_setting.Status.contains("Guardian"))
                        {
                            Check_sleep_stop_Time5(Static_setting.Protected_ID,getTime);
                        }
                        else if(Static_setting.Status.contains("Ward"))
                        {

                            Check_sleep_stop_Time5(Static_setting.ID,getTime);
                        }

                    } else {
                        Log.e(this.getClass().getName(), "555555555555555555555555555555555555555 시작점 못찾음");


                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        long now = System.currentTimeMillis();
        Date date = new Date(now);
        DateFormat df =new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar=Calendar.getInstance();
        calendar.add(Calendar.DATE, -6);



        String date1=df.format(calendar.getTime());
        Log.e(this.getClass().getName(), "Sleep_start_Time_before_Request @@@@@@@@@@ date1!" + date1);
        Sleep_start_Time_before_Request sleep_start_time_before_request = new Sleep_start_Time_before_Request(ID, Time,date1, responseListener);
        RequestQueue queue = Volley.newRequestQueue(Check_sleep_quality_Activity.this);
        queue.add(sleep_start_time_before_request);

    }

    private void Check_sleep_stop_Time5(String ID,String Time) {

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {


                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    Log.e(this.getClass().getName(), "success!" + success);

                    if (success) {
                        Log.e(this.getClass().getName(), "성공함!");
                        Log.e(this.getClass().getName(), "jsonResponse!" + jsonResponse);
                        ID15 =jsonResponse.getString("ID");
                        stop_time5 =jsonResponse.getString("Time");
                        Status15 =jsonResponse.getString("Status");
                        //Log.e(this.getClass().getName(), "Start_time"+start_time);

                        startSleepQulity5();



                    } else {
                        Log.e(this.getClass().getName(), "555555555555555555555555555555555555555 stop점못찾음");


                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        DateFormat df =new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar=Calendar.getInstance();


        calendar.add(Calendar.DATE, -5);

        String date1=df.format(calendar.getTime());
        Sleep_stop_Time_Request sleep_stop_time_request = new Sleep_stop_Time_Request(ID, Time,date1, responseListener);
        RequestQueue queue = Volley.newRequestQueue(Check_sleep_quality_Activity.this);
        queue.add(sleep_stop_time_request);

    }

    private void startSleepQulity5() {
        Log.e(this.getClass().getName(), "555555555555555555555555555555555555555 ");
        Log.e(this.getClass().getName(), "stop_time"+stop_time5);
        Log.e(this.getClass().getName(), "Start_time"+start_time5);
        if(Static_setting.Status.contains("Guardian"))
        {

            new BackgroundTask5().execute(Static_setting.Protected_ID,start_time5,stop_time5);
        }
        else if(Static_setting.Status.contains("Ward"))
        {

            new BackgroundTask5().execute(Static_setting.ID,start_time5,stop_time5);
        }
//        TextView text_total_sleep= (TextView) findViewById(R.id.total_sleep);
//        TextView text_sleep= (TextView) findViewById(R.id.sleep);
//        TextView text_wake= (TextView) findViewById(R.id.wake);
//
//
//
//        SimpleDateFormat  dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm");
//        Date start_time1= null; ///9
//        Date stop_time1=null;
//        try {
//            start_time1 = dateFormat.parse(start_time);
//            stop_time1= dateFormat.parse(stop_time); //10
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        long duration = stop_time1.getTime()-start_time1.getTime();
//        long min = duration/60000;
//        int hour= (int) (min/60);
//        int min1= (int) (min%60);
//        String total_time=String.valueOf(min);
//        text_total_sleep.setText(hour+"시간"+min1+"분");
//        text_sleep.setText(start_time);
//        text_wake.setText(stop_time);

    }
    public void Check_sleep_start_Time6(final String ID, final String Time) {
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {


                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    Log.e(this.getClass().getName(), "success!" + success);

                    if (success) {

                        Log.e(this.getClass().getName(), "Check_sleep_start_Time!" + jsonResponse);
                        ID26 =jsonResponse.getString("ID");
                        start_time6 =jsonResponse.getString("Time");
                        Status26=jsonResponse.getString("Status");
                        Log.e(this.getClass().getName(), "66666666666666666666666666666666666666666 시작점 바로찾음");
                        Log.e(this.getClass().getName(), "Start_time"+start_time6);
                        long now = System.currentTimeMillis();
                        Date date = new Date(now);
                        DateFormat df =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        Calendar calendar=Calendar.getInstance();


                        calendar.add(Calendar.DATE, -6);

                        String getTime=df.format(calendar.getTime());
                        if(Static_setting.Status.contains("Guardian"))
                        {
                            Check_sleep_stop_Time6(Static_setting.Protected_ID,getTime);
                        }
                        else if(Static_setting.Status.contains("Ward"))
                        {

                            Check_sleep_stop_Time6(Static_setting.ID,getTime);
                        }

                    } else {
                        Log.e(this.getClass().getName(), "66666666666666666666666666666666666666666 시작점 전날로");
                        Check_sleep_start_Time_before6(ID,Time);



                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        long now = System.currentTimeMillis();
        Date date = new Date(now);
        DateFormat df =new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar=Calendar.getInstance();

        calendar.add(Calendar.DATE, -6);

        String date1=df.format(calendar.getTime());
        Log.e(this.getClass().getName(), "Sleep_start_Time_Request @@@@@@@@@@ date1!" + date1);
        Sleep_start_Time_Request sleep_start_time_request = new Sleep_start_Time_Request(ID, Time,date1, responseListener);
        RequestQueue queue = Volley.newRequestQueue(Check_sleep_quality_Activity.this);
        queue.add(sleep_start_time_request);

    }

    private void Check_sleep_start_Time_before6(String ID,String Time) {
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {


                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    Log.e(this.getClass().getName(), "success!" + success);

                    if (success) {

                        Log.e(this.getClass().getName(), "Check_sleep_start_Time_before!" + jsonResponse);
                        ID26 =jsonResponse.getString("ID");
                        start_time6 =jsonResponse.getString("Time");
                        Status26 =jsonResponse.getString("Status");
                        Log.e(this.getClass().getName(), "66666666666666666666666666666666666666666 전날에서 찾음");
                        Log.e(this.getClass().getName(), "Start_time"+start_time6);
                        long now = System.currentTimeMillis();
                        Date date = new Date(now);
                        DateFormat df =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        Calendar calendar=Calendar.getInstance();

                        calendar.add(Calendar.DATE, -6);

                        String getTime=df.format(calendar.getTime());
                        if(Static_setting.Status.contains("Guardian"))
                        {
                            Check_sleep_stop_Time6(Static_setting.Protected_ID,getTime);
                        }
                        else if(Static_setting.Status.contains("Ward"))
                        {

                            Check_sleep_stop_Time6(Static_setting.ID,getTime);
                        }

                    } else {
                        Log.e(this.getClass().getName(), "66666666666666666666666666666666666666666 시작점못찾음");


                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        long now = System.currentTimeMillis();
        Date date = new Date(now);
        DateFormat df =new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar=Calendar.getInstance();
        calendar.add(Calendar.DATE, -7);



        String date1=df.format(calendar.getTime());
        Log.e(this.getClass().getName(), "Sleep_start_Time_before_Request @@@@@@@@@@ date1!" + date1);
        Sleep_start_Time_before_Request sleep_start_time_before_request = new Sleep_start_Time_before_Request(ID, Time,date1, responseListener);
        RequestQueue queue = Volley.newRequestQueue(Check_sleep_quality_Activity.this);
        queue.add(sleep_start_time_before_request);

    }

    private void Check_sleep_stop_Time6(String ID,String Time) {

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {


                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    Log.e(this.getClass().getName(), "success!" + success);

                    if (success) {
                        Log.e(this.getClass().getName(), "성공함!");
                        Log.e(this.getClass().getName(), "jsonResponse!" + jsonResponse);
                        ID16 =jsonResponse.getString("ID");
                        stop_time6 =jsonResponse.getString("Time");
                        Status16 =jsonResponse.getString("Status");
                        //Log.e(this.getClass().getName(), "Start_time"+start_time);

                        startSleepQulity6();



                    } else {
                        Log.e(this.getClass().getName(), "66666666666666666666666666666666666666666 stop찾지못함");


                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        DateFormat df =new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar=Calendar.getInstance();

        calendar.add(Calendar.DATE, -6);

        String date1=df.format(calendar.getTime());
        Sleep_stop_Time_Request sleep_stop_time_request = new Sleep_stop_Time_Request(ID, Time,date1, responseListener);
        RequestQueue queue = Volley.newRequestQueue(Check_sleep_quality_Activity.this);
        queue.add(sleep_stop_time_request);

    }

    private void startSleepQulity6() {
        Log.e(this.getClass().getName(), "66666666666666666666666666666666666666666");
        Log.e(this.getClass().getName(), "stop_time"+stop_time6);
        Log.e(this.getClass().getName(), "Start_time"+start_time6);
        if(Static_setting.Status.contains("Guardian"))
        {

            new BackgroundTask6().execute(Static_setting.Protected_ID,start_time6,stop_time6);
        }
        else if(Static_setting.Status.contains("Ward"))
        {

            new BackgroundTask6().execute(Static_setting.ID,start_time6,stop_time6);
        }
//        TextView text_total_sleep= (TextView) findViewById(R.id.total_sleep);
//        TextView text_sleep= (TextView) findViewById(R.id.sleep);
//        TextView text_wake= (TextView) findViewById(R.id.wake);
//
//
//
//        SimpleDateFormat  dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm");
//        Date start_time1= null; ///9
//        Date stop_time1=null;
//        try {
//            start_time1 = dateFormat.parse(start_time);
//            stop_time1= dateFormat.parse(stop_time); //10
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        long duration = stop_time1.getTime()-start_time1.getTime();
//        long min = duration/60000;
//        int hour= (int) (min/60);
//        int min1= (int) (min%60);
//        String total_time=String.valueOf(min);
//        text_total_sleep.setText(hour+"시간"+min1+"분");
//        text_sleep.setText(start_time);
//        text_wake.setText(stop_time);

    }
    class BackgroundTask0 extends AsyncTask<String, Void, String> {
        String target;




        @Override
        protected void onPreExecute() {
            target = "http://dbwo4011.cafe24.com/DRbed/sleep_step_request.php";
            Log.e(this.getClass().getName(), "백그라운드로 list뽑기 시작한다.");
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                String ID=params[0];
                String start_time = params[1];
                String stop_time = params[2];
                String data = URLEncoder.encode("ID","UTF-8")+"="+URLEncoder.encode(ID,"UTF-8");// UTF-8로  설정 실제로 string 상으로 봤을땐, tmsg="String" 요런식으로 설정 된다.
                data+= "&"+URLEncoder.encode("start_time","UTF-8")+"="+URLEncoder.encode(start_time,"UTF-8");
                data+= "&"+URLEncoder.encode("stop_time","UTF-8")+"="+URLEncoder.encode(stop_time,"UTF-8");
                URL url = new URL(target);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST"); //post방식으로
                httpURLConnection.setDoInput(true); // server와 통신에서 입력가능상태로 설정
                httpURLConnection.setDoOutput(true);//server와의 통신에서 출력 가능한 상태로
                OutputStreamWriter wr = new OutputStreamWriter(httpURLConnection.getOutputStream());//서버로
                wr.write(data);//아까 String값을 쓱삭쓱삭 넣어서 보내주고!
                wr.flush();//flush!
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String temp;
                StringBuilder stringBuilder = new StringBuilder();
                while ((temp = bufferedReader.readLine()) != null) {
                    stringBuilder.append(temp + "\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        public void onPostExecute(String res) {
            Log.e(this.getClass().getName(), "백그라운드 try문안으로");
            try {
                Log.e(this.getClass().getName(), "백그라운드 try문안으로");
                JSONObject jsonObject = new JSONObject(res);
                JSONArray jsonArray = jsonObject.getJSONArray("response");
                Log.e(this.getClass().getName(), "jsonArray"+jsonArray);
                int count = 0;
                while(count < jsonArray.length()){
                    Log.e(this.getClass().getName(), "들어오긴하냐?");
                    JSONObject object = jsonArray.getJSONObject(count);
                    sleepStep_0.setIDList(object.getString("ID"));
                    sleepStep_0.setTimeList(object.getString("Time"));
                    sleepStep_0.setSleep_stepList(object.getString("Sleep_step"));

                    Log.e(this.getClass().getName(), count+"  ID "+sleepStep_0.getIDList(count));
                    Log.e(this.getClass().getName(), count+"  Time "+sleepStep_0.getTimeList(count));
                    Log.e(this.getClass().getName(), count+"  Sleep_step "+sleepStep_0.getSleep_stepList(count));

                    count++;
                }
            } catch (Exception e){
                e.printStackTrace();
            }

        }
    }

    class BackgroundTask1 extends AsyncTask<String, Void, String> {
        String target;




        @Override
        protected void onPreExecute() {
            target = "http://dbwo4011.cafe24.com/DRbed/sleep_step_request.php";
            Log.e(this.getClass().getName(), "백그라운드로 list뽑기 시작한다.");
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                String ID=params[0];
                String start_time = params[1];
                String stop_time = params[2];
                String data = URLEncoder.encode("ID","UTF-8")+"="+URLEncoder.encode(ID,"UTF-8");// UTF-8로  설정 실제로 string 상으로 봤을땐, tmsg="String" 요런식으로 설정 된다.
                data+= "&"+URLEncoder.encode("start_time","UTF-8")+"="+URLEncoder.encode(start_time,"UTF-8");
                data+= "&"+URLEncoder.encode("stop_time","UTF-8")+"="+URLEncoder.encode(stop_time,"UTF-8");
                URL url = new URL(target);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST"); //post방식으로
                httpURLConnection.setDoInput(true); // server와 통신에서 입력가능상태로 설정
                httpURLConnection.setDoOutput(true);//server와의 통신에서 출력 가능한 상태로
                OutputStreamWriter wr = new OutputStreamWriter(httpURLConnection.getOutputStream());//서버로
                wr.write(data);//아까 String값을 쓱삭쓱삭 넣어서 보내주고!
                wr.flush();//flush!
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String temp;
                StringBuilder stringBuilder = new StringBuilder();
                while ((temp = bufferedReader.readLine()) != null) {
                    stringBuilder.append(temp + "\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        public void onPostExecute(String res) {
            Log.e(this.getClass().getName(), "백그라운드 try문안으로");
            try {
                Log.e(this.getClass().getName(), "백그라운드 try문안으로");
                JSONObject jsonObject = new JSONObject(res);
                JSONArray jsonArray = jsonObject.getJSONArray("response");
                Log.e(this.getClass().getName(), "jsonArray"+jsonArray);
                int count = 0;
                while(count < jsonArray.length()){
                    Log.e(this.getClass().getName(), "들어오긴하냐?");
                    JSONObject object = jsonArray.getJSONObject(count);
                    sleepStep_1.setIDList(object.getString("ID"));
                    sleepStep_1.setTimeList(object.getString("Time"));
                    sleepStep_1.setSleep_stepList(object.getString("Sleep_step"));

                    Log.e(this.getClass().getName(), count+"  ID "+sleepStep_1.getIDList(count));
                    Log.e(this.getClass().getName(), count+"  Time "+sleepStep_1.getTimeList(count));
                    Log.e(this.getClass().getName(), count+"  Sleep_step "+sleepStep_1.getSleep_stepList(count));

                    count++;
                }
            } catch (Exception e){
                e.printStackTrace();
            }

        }
    }


    class BackgroundTask2 extends AsyncTask<String, Void, String> {
        String target;




        @Override
        protected void onPreExecute() {
            target = "http://dbwo4011.cafe24.com/DRbed/sleep_step_request.php";
            Log.e(this.getClass().getName(), "백그라운드로 list뽑기 시작한다.");
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                String ID=params[0];
                String start_time = params[1];
                String stop_time = params[2];
                String data = URLEncoder.encode("ID","UTF-8")+"="+URLEncoder.encode(ID,"UTF-8");// UTF-8로  설정 실제로 string 상으로 봤을땐, tmsg="String" 요런식으로 설정 된다.
                data+= "&"+URLEncoder.encode("start_time","UTF-8")+"="+URLEncoder.encode(start_time,"UTF-8");
                data+= "&"+URLEncoder.encode("stop_time","UTF-8")+"="+URLEncoder.encode(stop_time,"UTF-8");
                URL url = new URL(target);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST"); //post방식으로
                httpURLConnection.setDoInput(true); // server와 통신에서 입력가능상태로 설정
                httpURLConnection.setDoOutput(true);//server와의 통신에서 출력 가능한 상태로
                OutputStreamWriter wr = new OutputStreamWriter(httpURLConnection.getOutputStream());//서버로
                wr.write(data);//아까 String값을 쓱삭쓱삭 넣어서 보내주고!
                wr.flush();//flush!
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String temp;
                StringBuilder stringBuilder = new StringBuilder();
                while ((temp = bufferedReader.readLine()) != null) {
                    stringBuilder.append(temp + "\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        public void onPostExecute(String res) {
            Log.e(this.getClass().getName(), "백그라운드 try문안으로");
            try {
                Log.e(this.getClass().getName(), "백그라운드 try문안으로");
                JSONObject jsonObject = new JSONObject(res);
                JSONArray jsonArray = jsonObject.getJSONArray("response");
                Log.e(this.getClass().getName(), "jsonArray"+jsonArray);
                int count = 0;
                while(count < jsonArray.length()){
                    Log.e(this.getClass().getName(), "들어오긴하냐?");
                    JSONObject object = jsonArray.getJSONObject(count);
                    sleepStep_2.setIDList(object.getString("ID"));
                    sleepStep_2.setTimeList(object.getString("Time"));
                    sleepStep_2.setSleep_stepList(object.getString("Sleep_step"));

                    Log.e(this.getClass().getName(), count+"  ID "+sleepStep_2.getIDList(count));
                    Log.e(this.getClass().getName(), count+"  Time "+sleepStep_2.getTimeList(count));
                    Log.e(this.getClass().getName(), count+"  Sleep_step "+sleepStep_2.getSleep_stepList(count));

                    count++;
                }
            } catch (Exception e){
                e.printStackTrace();
            }

        }
    }

    class BackgroundTask3 extends AsyncTask<String, Void, String> {
        String target;




        @Override
        protected void onPreExecute() {
            target = "http://dbwo4011.cafe24.com/DRbed/sleep_step_request.php";
            Log.e(this.getClass().getName(), "백그라운드로 list뽑기 시작한다.");
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                String ID=params[0];
                String start_time = params[1];
                String stop_time = params[2];
                String data = URLEncoder.encode("ID","UTF-8")+"="+URLEncoder.encode(ID,"UTF-8");// UTF-8로  설정 실제로 string 상으로 봤을땐, tmsg="String" 요런식으로 설정 된다.
                data+= "&"+URLEncoder.encode("start_time","UTF-8")+"="+URLEncoder.encode(start_time,"UTF-8");
                data+= "&"+URLEncoder.encode("stop_time","UTF-8")+"="+URLEncoder.encode(stop_time,"UTF-8");
                URL url = new URL(target);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST"); //post방식으로
                httpURLConnection.setDoInput(true); // server와 통신에서 입력가능상태로 설정
                httpURLConnection.setDoOutput(true);//server와의 통신에서 출력 가능한 상태로
                OutputStreamWriter wr = new OutputStreamWriter(httpURLConnection.getOutputStream());//서버로
                wr.write(data);//아까 String값을 쓱삭쓱삭 넣어서 보내주고!
                wr.flush();//flush!
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String temp;
                StringBuilder stringBuilder = new StringBuilder();
                while ((temp = bufferedReader.readLine()) != null) {
                    stringBuilder.append(temp + "\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        public void onPostExecute(String res) {
            Log.e(this.getClass().getName(), "백그라운드 try문안으로");
            try {
                Log.e(this.getClass().getName(), "백그라운드 try문안으로");
                JSONObject jsonObject = new JSONObject(res);
                JSONArray jsonArray = jsonObject.getJSONArray("response");
                Log.e(this.getClass().getName(), "jsonArray"+jsonArray);
                int count = 0;
                while(count < jsonArray.length()){
                    Log.e(this.getClass().getName(), "들어오긴하냐?");
                    JSONObject object = jsonArray.getJSONObject(count);
                    sleepStep_3.setIDList(object.getString("ID"));
                    sleepStep_3.setTimeList(object.getString("Time"));
                    sleepStep_3.setSleep_stepList(object.getString("Sleep_step"));

                    Log.e(this.getClass().getName(), count+"  ID "+sleepStep_3.getIDList(count));
                    Log.e(this.getClass().getName(), count+"  Time "+sleepStep_3.getTimeList(count));
                    Log.e(this.getClass().getName(), count+"  Sleep_step "+sleepStep_3.getSleep_stepList(count));

                    count++;
                }
            } catch (Exception e){
                e.printStackTrace();
            }

        }
    }

    class BackgroundTask4 extends AsyncTask<String, Void, String> {
        String target;




        @Override
        protected void onPreExecute() {
            target = "http://dbwo4011.cafe24.com/DRbed/sleep_step_request.php";
            Log.e(this.getClass().getName(), "백그라운드로 list뽑기 시작한다.");
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                String ID=params[0];
                String start_time = params[1];
                String stop_time = params[2];
                String data = URLEncoder.encode("ID","UTF-8")+"="+URLEncoder.encode(ID,"UTF-8");// UTF-8로  설정 실제로 string 상으로 봤을땐, tmsg="String" 요런식으로 설정 된다.
                data+= "&"+URLEncoder.encode("start_time","UTF-8")+"="+URLEncoder.encode(start_time,"UTF-8");
                data+= "&"+URLEncoder.encode("stop_time","UTF-8")+"="+URLEncoder.encode(stop_time,"UTF-8");
                URL url = new URL(target);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST"); //post방식으로
                httpURLConnection.setDoInput(true); // server와 통신에서 입력가능상태로 설정
                httpURLConnection.setDoOutput(true);//server와의 통신에서 출력 가능한 상태로
                OutputStreamWriter wr = new OutputStreamWriter(httpURLConnection.getOutputStream());//서버로
                wr.write(data);//아까 String값을 쓱삭쓱삭 넣어서 보내주고!
                wr.flush();//flush!
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String temp;
                StringBuilder stringBuilder = new StringBuilder();
                while ((temp = bufferedReader.readLine()) != null) {
                    stringBuilder.append(temp + "\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        public void onPostExecute(String res) {
            Log.e(this.getClass().getName(), "백그라운드 try문안으로");
            try {
                Log.e(this.getClass().getName(), "백그라운드 try문안으로");
                JSONObject jsonObject = new JSONObject(res);
                JSONArray jsonArray = jsonObject.getJSONArray("response");
                Log.e(this.getClass().getName(), "jsonArray"+jsonArray);
                int count = 0;
                while(count < jsonArray.length()){
                    Log.e(this.getClass().getName(), "들어오긴하냐?");
                    JSONObject object = jsonArray.getJSONObject(count);
                    sleepStep_4.setIDList(object.getString("ID"));
                    sleepStep_4.setTimeList(object.getString("Time"));
                    sleepStep_4.setSleep_stepList(object.getString("Sleep_step"));

                    Log.e(this.getClass().getName(), count+"  ID "+sleepStep_4.getIDList(count));
                    Log.e(this.getClass().getName(), count+"  Time "+sleepStep_4.getTimeList(count));
                    Log.e(this.getClass().getName(), count+"  Sleep_step "+sleepStep_4.getSleep_stepList(count));

                    count++;
                }
            } catch (Exception e){
                e.printStackTrace();
            }

        }
    }

    class BackgroundTask5 extends AsyncTask<String, Void, String> {
        String target;




        @Override
        protected void onPreExecute() {
            target = "http://dbwo4011.cafe24.com/DRbed/sleep_step_request.php";
            Log.e(this.getClass().getName(), "백그라운드로 list뽑기 시작한다.");
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                String ID=params[0];
                String start_time = params[1];
                String stop_time = params[2];
                String data = URLEncoder.encode("ID","UTF-8")+"="+URLEncoder.encode(ID,"UTF-8");// UTF-8로  설정 실제로 string 상으로 봤을땐, tmsg="String" 요런식으로 설정 된다.
                data+= "&"+URLEncoder.encode("start_time","UTF-8")+"="+URLEncoder.encode(start_time,"UTF-8");
                data+= "&"+URLEncoder.encode("stop_time","UTF-8")+"="+URLEncoder.encode(stop_time,"UTF-8");
                URL url = new URL(target);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST"); //post방식으로
                httpURLConnection.setDoInput(true); // server와 통신에서 입력가능상태로 설정
                httpURLConnection.setDoOutput(true);//server와의 통신에서 출력 가능한 상태로
                OutputStreamWriter wr = new OutputStreamWriter(httpURLConnection.getOutputStream());//서버로
                wr.write(data);//아까 String값을 쓱삭쓱삭 넣어서 보내주고!
                wr.flush();//flush!
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String temp;
                StringBuilder stringBuilder = new StringBuilder();
                while ((temp = bufferedReader.readLine()) != null) {
                    stringBuilder.append(temp + "\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        public void onPostExecute(String res) {
            Log.e(this.getClass().getName(), "백그라운드 try문안으로");
            try {
                Log.e(this.getClass().getName(), "백그라운드 try문안으로");
                JSONObject jsonObject = new JSONObject(res);
                JSONArray jsonArray = jsonObject.getJSONArray("response");
                Log.e(this.getClass().getName(), "jsonArray"+jsonArray);
                int count = 0;
                while(count < jsonArray.length()){
                    Log.e(this.getClass().getName(), "들어오긴하냐?");
                    JSONObject object = jsonArray.getJSONObject(count);
                    sleepStep_5.setIDList(object.getString("ID"));
                    sleepStep_5.setTimeList(object.getString("Time"));
                    sleepStep_5.setSleep_stepList(object.getString("Sleep_step"));

                    Log.e(this.getClass().getName(), count+"  ID "+sleepStep_5.getIDList(count));
                    Log.e(this.getClass().getName(), count+"  Time "+sleepStep_5.getTimeList(count));
                    Log.e(this.getClass().getName(), count+"  Sleep_step "+sleepStep_5.getSleep_stepList(count));

                    count++;
                }
            } catch (Exception e){
                e.printStackTrace();
            }

        }
    }

    class BackgroundTask6 extends AsyncTask<String, Void, String> {
        String target;




        @Override
        protected void onPreExecute() {
            target = "http://dbwo4011.cafe24.com/DRbed/sleep_step_request.php";
            Log.e(this.getClass().getName(), "백그라운드로 list뽑기 시작한다.");
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                String ID=params[0];
                String start_time = params[1];
                String stop_time = params[2];
                String data = URLEncoder.encode("ID","UTF-8")+"="+URLEncoder.encode(ID,"UTF-8");// UTF-8로  설정 실제로 string 상으로 봤을땐, tmsg="String" 요런식으로 설정 된다.
                data+= "&"+URLEncoder.encode("start_time","UTF-8")+"="+URLEncoder.encode(start_time,"UTF-8");
                data+= "&"+URLEncoder.encode("stop_time","UTF-8")+"="+URLEncoder.encode(stop_time,"UTF-8");
                URL url = new URL(target);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST"); //post방식으로
                httpURLConnection.setDoInput(true); // server와 통신에서 입력가능상태로 설정
                httpURLConnection.setDoOutput(true);//server와의 통신에서 출력 가능한 상태로
                OutputStreamWriter wr = new OutputStreamWriter(httpURLConnection.getOutputStream());//서버로
                wr.write(data);//아까 String값을 쓱삭쓱삭 넣어서 보내주고!
                wr.flush();//flush!
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String temp;
                StringBuilder stringBuilder = new StringBuilder();
                while ((temp = bufferedReader.readLine()) != null) {
                    stringBuilder.append(temp + "\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        public void onPostExecute(String res) {
            Log.e(this.getClass().getName(), "백그라운드 try문안으로");
            try {
                Log.e(this.getClass().getName(), "백그라운드 try문안으로");
                JSONObject jsonObject = new JSONObject(res);
                JSONArray jsonArray = jsonObject.getJSONArray("response");
                Log.e(this.getClass().getName(), "jsonArray"+jsonArray);
                int count = 0;
                while(count < jsonArray.length()){
                    Log.e(this.getClass().getName(), "들어오긴하냐?");
                    JSONObject object = jsonArray.getJSONObject(count);
                    sleepStep_6.setIDList(object.getString("ID"));
                    sleepStep_6.setTimeList(object.getString("Time"));
                    sleepStep_6.setSleep_stepList(object.getString("Sleep_step"));

                    Log.e(this.getClass().getName(), count+"  ID "+sleepStep_6.getIDList(count));
                    Log.e(this.getClass().getName(), count+"  Time "+sleepStep_6.getTimeList(count));
                    Log.e(this.getClass().getName(), count+"  Sleep_step "+sleepStep_6.getSleep_stepList(count));

                    count++;
                }
            } catch (Exception e){
                e.printStackTrace();
            }

        }
    }

     class CheckTypesTask extends AsyncTask<Void, Void, Void> {

        ProgressDialog asyncDialog = new ProgressDialog(
                Check_sleep_quality_Activity.this);

        @Override
        protected void onPreExecute() {
            asyncDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            asyncDialog.setMessage("1주일치 데이터를 가져오는중입니다...");

            // show dialog
            asyncDialog.show();
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            try {
                for (int i = 0; i < 10; i++) {
                    asyncDialog.setProgress(i * 2);
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
          //  chartstart();
            super.onPostExecute(result);
        }
    }



    private void chartstart() {
        Log.e(this.getClass().getName(), "chartstartchartstartchartstartchartstartchartstartchartstartchartstartchartstartchartstart");
        tvX = findViewById(R.id.tvXMax);
        tvY = findViewById(R.id.tvYMax);

        seekBarX = findViewById(R.id.seekBar1);
        seekBarX.setOnSeekBarChangeListener((SeekBar.OnSeekBarChangeListener) this);

        seekBarY = findViewById(R.id.seekBar2);
        seekBarY.setOnSeekBarChangeListener((SeekBar.OnSeekBarChangeListener) this);

        chart = findViewById(R.id.chart1);
        chart.setOnChartValueSelectedListener((OnChartValueSelectedListener) this);

        chart.getDescription().setEnabled(false);

        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        chart.setMaxVisibleValueCount(40);

        // scaling can now only be done on x- and y-axis separately
        chart.setPinchZoom(false);

        chart.setDrawGridBackground(false);
        chart.setDrawBarShadow(false);

        chart.setDrawValueAboveBar(false);
        chart.setHighlightFullBarEnabled(false);

        // change the position of the y-labels
        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setValueFormatter(new MyValueFormatter("min"));
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)
        chart.getAxisRight().setEnabled(false);

        XAxis xLabels = chart.getXAxis();
        xLabels.setPosition(XAxis.XAxisPosition.TOP);

        // chart.setDrawXLabels(false);
        // chart.setDrawYLabels(false);

        // setting data
        seekBarX.setProgress(12);
        seekBarY.setProgress(100);

        Legend l = chart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setFormSize(8f);
        l.setFormToTextSpace(4f);
        l.setXEntrySpace(6f);

        // chart.setDrawLegend(false);
        setChart();
    }

    private void setChart() {
        tvX.setText(String.valueOf(seekBarX.getProgress()));
        tvY.setText(String.valueOf(seekBarY.getProgress()));

        ArrayList<BarEntry> values = new ArrayList<>();

        for (int i = 0; i < seekBarX.getProgress(); i++) {
            float mul = (seekBarY.getProgress() + 1);
            float val1 = (float) (Math.random() * mul) + mul / 3;
            float val2 = (float) (Math.random() * mul) + mul / 3;
            float val3 = (float) (Math.random() * mul) + mul / 3;

            values.add(new BarEntry(
                    i,
                    new float[]{val1, val2, val3}));
        }

        BarDataSet set1;

        if (chart.getData() != null &&
                chart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) chart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            chart.getData().notifyDataChanged();
            chart.notifyDataSetChanged();
        } else {
            set1 = new BarDataSet(values, "주간 수면 시간 ");
            set1.setDrawIcons(false);
            set1.setColors(getColors());
            set1.setStackLabels(new String[]{"Rem sleep", "Deep sleep", "Light sleep"});

            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);

            BarData data = new BarData(dataSets);
            data.setValueFormatter(new StackedValueFormatter(false, "", 1));
            data.setValueTextColor(Color.WHITE);

            chart.setData(data);
        }

        chart.setFitBars(true);
        chart.invalidate();
    }

    private int[] getColors() {

        // have as many colors as stack-values per entry
        int[] colors = new int[3];

        System.arraycopy(ColorTemplate.MATERIAL_COLORS, 0, colors, 0, 3);

        return colors;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.check_sleep_quality_, menu);
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
