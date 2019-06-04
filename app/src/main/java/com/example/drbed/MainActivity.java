package com.example.drbed;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnChartValueSelectedListener {
    private LineChart chart;
    private Thread thread;
    private GUIthread guIthread;
    private Health_info health_info=new Health_info();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent=getIntent();
        String name=intent.getStringExtra("ID");
        Log.e(this.getClass().getName(),"ID!"+name);
        if(intent.getStringExtra("ID")!=null) {
            Log.e(this.getClass().getName(),"다시한번 메인에 들어올떄");
            Static_setting.ID = intent.getStringExtra("ID");
            Static_setting.PW = intent.getStringExtra("PW");
            Static_setting.Name = intent.getStringExtra("Name");
            Static_setting.Phone = intent.getStringExtra("Phone");
            Static_setting.Status = intent.getStringExtra("Status");
            Static_setting.Protected_ID = intent.getStringExtra("Protected_ID");
            Static_setting.Age = intent.getStringExtra("Age");
        }
        Log.e(this.getClass().getName(),"Main!");
        Log.e(this.getClass().getName(),"ID!"+Static_setting.ID);
        Log.e(this.getClass().getName(),"PW!"+Static_setting.PW);
        Log.e(this.getClass().getName(),"Name!"+Static_setting.Name);
        Log.e(this.getClass().getName(),"Phone!"+Static_setting.Phone);
        Log.e(this.getClass().getName(),"Status!"+Static_setting.Status);
        Log.e(this.getClass().getName(),"Protect_ID!"+Static_setting.Protected_ID);

   //     TextView textView=(TextView)findViewById(R.id.test1);






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
        gui thread사용해서 실시간 데이터 그래프
         */
        chart = findViewById(R.id.line_chart1);
        chart.setOnChartValueSelectedListener(this);

        // enable description text
        chart.getDescription().setEnabled(true);

        // enable touch gestures
        chart.setTouchEnabled(true);

        // enable scaling and dragging
        chart.setDragEnabled(true);
        chart.setScaleEnabled(true);
        chart.setDrawGridBackground(false);

        // if disabled, scaling can be done on x- and y-axis separately
        chart.setPinchZoom(true);

        // set an alternative background color
        chart.setBackgroundColor(Color.LTGRAY);

        LineData data = new LineData();
        data.setValueTextColor(Color.WHITE);

        // add empty data
        chart.setData(data);

        // get the legend (only possible after setting data)
        Legend l = chart.getLegend();

        // modify the legend ...
        l.setForm(Legend.LegendForm.LINE);
       // l.setTypeface(tfLight);
        l.setTextColor(Color.WHITE);

        XAxis xl = chart.getXAxis();
        //xl.setTypeface(tfLight);
        xl.setTextColor(Color.WHITE);
        xl.setDrawGridLines(false);
        xl.setAvoidFirstLastClipping(true);
        xl.setEnabled(true);

        YAxis leftAxis = chart.getAxisLeft();
        //leftAxis.setTypeface(tfLight);
        leftAxis.setTextColor(Color.WHITE);
        leftAxis.setAxisMaximum(150f);
        leftAxis.setAxisMinimum(30f);
        leftAxis.setDrawGridLines(true);

        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setEnabled(false);
        //dbwofeedMultiple();
        Static_setting.flag=true;
        guIthread=new GUIthread();
        guIthread.start();

    }

    private LineDataSet createSet() {

        LineDataSet set = new LineDataSet(null, "HR Data");
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        set.setColor(ColorTemplate.getHoloBlue());
        set.setCircleColor(Color.WHITE);
        set.setLineWidth(1f);
        set.setCircleRadius(4f);
        set.setFillAlpha(65);
        set.setFillColor(ColorTemplate.getHoloBlue());
        set.setHighLightColor(Color.rgb(244, 117, 117));
        set.setValueTextColor(Color.WHITE);
        set.setValueTextSize(9f);
        set.setDrawValues(false);
        return set;
    }




    private void datarequest(String time)
    {
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


                        } else {
                            Log.e(this.getClass().getName(), "Dataset fail");
                            thread.interrupt();
                            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
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

            Log.e(this.getClass().getName(), "확인 건너뜀");
            DataGuardianRequest dataGuardianRequest = new DataGuardianRequest(Static_setting.Protected_ID, time, responseListener);
            RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
            queue.add(dataGuardianRequest);
        }

        else if(Static_setting.Status.contains("Ward")) {
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
                            health_info.setTime(jsonResponse.getString("Time"));
                            health_info.setHR(jsonResponse.getString("HR"));
                            health_info.setRR(jsonResponse.getString("RR"));
                            health_info.setSV(jsonResponse.getString("SV"));
                            health_info.setHRV(jsonResponse.getString("HRV"));
                            health_info.setSignal_Strength(jsonResponse.getString("Signal_Strength"));
                            health_info.setStatus(jsonResponse.getString("Status"));


                        } else {
                            Log.e(this.getClass().getName(), "Dataset fail");
                            thread.interrupt();
                            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
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

            Log.e(this.getClass().getName(), "확인 건너뜀");
            DataRequest dataRequest = new DataRequest(Static_setting.ID, time, responseListener);
            RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
            queue.add(dataRequest);
        }

    }

    private void addEntry() {

        LineData data = chart.getData();

        if (data != null) {


            // set.addEntry(...); // can be called as well
            ILineDataSet set = data.getDataSetByIndex(0);

            if (set == null) {
                set = createSet();
                data.addDataSet(set);

            }



            long now = System.currentTimeMillis();
            Date date = new Date(now);
            DateFormat df =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Calendar calendar=Calendar.getInstance();

            calendar.setTime(date);
            calendar.add(Calendar.SECOND,-1);

            String getTime=df.format(calendar.getTime());
            //String getTime="2019-05-25_20-03-12"; //평상시 확인용
            Log.e(this.getClass().getName(), "디비에 넣을 현재 시간 "+ getTime+"///////"+getTime);

            datarequest(getTime);
            Log.e(this.getClass().getName(), "디비에 가져온 현재 시간 "+ health_info.getTime());
            Log.e(this.getClass().getName(), "디비에 가져온값은 "+ health_info.getHR());
            Log.e(this.getClass().getName(), "디비에 가져온값은 "+ health_info.getRR());
            Log.e(this.getClass().getName(), "디비에 가져온값은 "+health_info.getSV());
            Log.e(this.getClass().getName(), "디비에 가져온값은 "+ health_info.getHRV());
            Log.e(this.getClass().getName(), "디비에 가져온값은 "+ health_info.getSignal_Strength());
            Log.e(this.getClass().getName(), "디비에 가져온값은 "+ health_info.getStatus());
            data.addEntry(new Entry(set.getEntryCount(), health_info.getHR()), 0);
             TextView text_HR= (TextView) findViewById(R.id.text_HR);
             TextView text_RR= (TextView) findViewById(R.id.text_RR);
             TextView text_SV= (TextView) findViewById(R.id.text_SV);
             TextView text_HRV= (TextView) findViewById(R.id.text_HRV);
             String textHR=String.valueOf(health_info.getHR());
            String textRR=String.valueOf(health_info.getRR());
            String textSV=String.valueOf(health_info.getSV());
            String textHRV=String.valueOf(health_info.getHRV());

            text_HR.setText( textHR);
            text_RR.setText( textRR);
            text_SV.setText( textSV);
            text_HRV.setText( textHRV);
            /*
            차트에서 값을 표시
             */
            List<ILineDataSet> sets = chart.getData()
                    .getDataSets();

            for (ILineDataSet iSet : sets) {

                LineDataSet set1 = (LineDataSet) iSet;
                set.setDrawValues(!set1.isDrawValuesEnabled());
            }
           // data.addEntry(new Entry(set.getEntryCount(), HR), 0);
            data.notifyDataChanged();

            // let the chart know it's data has changed
            chart.notifyDataSetChanged();

            // limit the number of visible entries
            chart.setVisibleXRangeMaximum(10);
            // chart.setVisibleYRange(30, AxisDependency.LEFT);

            // move to the latest entry
            chart.moveViewToX(data.getEntryCount());

            // this automatically refreshes the chart (calls invalidate())
            // chart.moveViewTo(data.getXValCount()-7, 55f,
            // AxisDependency.LEFT);
        }
    }

    private void feedMultiple() {
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (Static_setting.flag) {
                            // Don't generate garbage runnables inside the loop.
                        runOnUiThread(new Runnable()
                        {
                            public void run() {
                                    addEntry();
                            }
                        });
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

    class GUIthread extends Thread{

        @Override
        public void run() {
            while (Static_setting.flag) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            addEntry();
                        }

                    });
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }
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
        getMenuInflater().inflate(R.menu.main, menu);
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

           // return true;
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
            chart.clearValues();
            Static_setting.flag=false;
           // thread.interrupt();
            Intent intent = new Intent(this, Simply_referencs_Activity2.class);
            startActivity(intent);
        } else if (id == R.id.nav_sleep_report_week) {
            /* 주간 리포트*/
            chart.clearValues();
            Static_setting.flag=false;
           // thread.interrupt();
            Intent intent = new Intent(this, Check_sleep_quality_Activity.class);
            startActivity(intent);
        } else if (id == R.id.nav_check_ward) {
            /* ward check */
            chart.clearValues();
            Static_setting.flag=false;
            //thread.interrupt();
            Intent intent = new Intent(this, StatusCheckActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_body_information_settings) {
            /* 신체정보세팅  */
            chart.clearValues();
            Static_setting.flag=false;
           // thread.interrupt();
            Intent intent = new Intent(this, UserSettingActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_guardian_ward_setting) {
            /* 부가 세팅 */
            Static_setting.flag=false;
            chart.clearValues();
            Intent intent = new Intent(this, Changing_info_Activity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        /* nav_header에서 간병인 이름 환자이름 */

        return true;
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }
}
