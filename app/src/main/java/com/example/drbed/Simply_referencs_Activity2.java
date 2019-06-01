package com.example.drbed;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
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
import android.view.WindowManager;
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
import java.util.Calendar;
import java.util.Date;

public class Simply_referencs_Activity2 extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnChartValueSelectedListener {

    public String PW = "";
    public String Name = "";
    public String Phone = "";
    //public String Status = "";
    private LineChart chart;
    public String ID1 = "";
    public String ID2 = "";
    String stop_time="";
    String start_time="";

    String Status1="";
    String Status2="";
    SleepStep sleepStep=new SleepStep();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simply_referencs_2);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
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



        Log.e(this.getClass().getName(), "@@@@@@@@@@@@@@@@@@@@@@@쓰레드 시작유");
         /*
        start = 취침 지점
        end = 기상 시점
        구하는 코드 추가 필요
         */

        long now = System.currentTimeMillis();
        Date date = new Date(now);
        DateFormat df =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar=Calendar.getInstance();

        calendar.setTime(date);
        calendar.add(Calendar.SECOND,-1);

        String getTime=df.format(calendar.getTime());
        //String start_time=Check_sleep_start_Time(getTime);
        if(Static_setting.Status.contains("Guardian"))
        {
           Check_sleep_start_Time(Static_setting.Protected_ID,getTime);

        }
        else if(Static_setting.Status.contains("Ward"))
        {
            Check_sleep_start_Time(Static_setting.ID,getTime);

        }

//        Log.e(this.getClass().getName(), "@@@@@@@@@@@@@@@@@@@@@@@@@@@@"+start_time);
//        Log.e(this.getClass().getName(), "@@@@@@@@@@@@@@@@@@@@@@@@@@@@"+stop_time);
//        Log.e(this.getClass().getName(), "Start_time"+start_time);
//        Log.e(this.getClass().getName(), "stop_time"+stop_time);

       // new BackgroundTask().execute(Static_setting.ID,getTime);



       // Log.e(this.getClass().getName(), "@@@@@@@@@@@@@@@@@@@@@@@쓰레드 끝남유");

    }

    private void Check_sleep_stop_Time(String ID,String Time) {

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
                         ID1 =jsonResponse.getString("ID");
                         stop_time =jsonResponse.getString("Time");
                         Status1 =jsonResponse.getString("Status");
                            //Log.e(this.getClass().getName(), "Start_time"+start_time);

                            startSleepQulity();



                    } else {
                        Log.e(this.getClass().getName(), "Dataset fail");

                        AlertDialog.Builder builder = new AlertDialog.Builder(Simply_referencs_Activity2.this);
                        builder.setMessage("기상하지 않아 확인이 어렵습니다.")
                                .setNegativeButton("확인", null)
                                .create()
                                .show();


                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        Sleep_stop_Time_Request sleep_stop_time_request = new Sleep_stop_Time_Request(ID, Time, responseListener);
        RequestQueue queue = Volley.newRequestQueue(Simply_referencs_Activity2.this);
        queue.add(sleep_stop_time_request);

    }

    private void startSleepQulity() {
        Log.e(this.getClass().getName(), "@@@@@@@@@@@@@@@@@@@@@@@");
        Log.e(this.getClass().getName(), "stop_time"+stop_time);
        Log.e(this.getClass().getName(), "Start_time"+start_time);
    }


    private void Check_sleep_start_Time(final String ID, final String Time) {
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {


                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    Log.e(this.getClass().getName(), "success!" + success);

                    if (success) {

                        Log.e(this.getClass().getName(), "Check_sleep_start_Time!" + jsonResponse);
                        ID2 =jsonResponse.getString("ID");
                        start_time =jsonResponse.getString("Time");
                        Status2 =jsonResponse.getString("Status");
                        Log.e(this.getClass().getName(), "오늘날짜에 취침시간이 판단됨");
                        Log.e(this.getClass().getName(), "Start_time"+start_time);
                        long now = System.currentTimeMillis();
                        Date date = new Date(now);
                        DateFormat df =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        Calendar calendar=Calendar.getInstance();

                        calendar.setTime(date);
                        calendar.add(Calendar.SECOND,-1);

                        String getTime=df.format(calendar.getTime());
                        if(Static_setting.Status.contains("Guardian"))
                        {
                            Check_sleep_stop_Time(Static_setting.Protected_ID,getTime);
                        }
                        else if(Static_setting.Status.contains("Ward"))
                        {

                            Check_sleep_stop_Time(Static_setting.ID,getTime);
                        }

                    } else {
                        Log.e(this.getClass().getName(), "오늘날짜에 취침시간이 판단이 안됨 전날으로 넘어감");
                        Check_sleep_start_Time_before(ID,Time);



                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };


        Sleep_start_Time_Request sleep_start_time_request = new Sleep_start_Time_Request(ID, Time, responseListener);
        RequestQueue queue = Volley.newRequestQueue(Simply_referencs_Activity2.this);
        queue.add(sleep_start_time_request);

    }

    private void Check_sleep_start_Time_before(String ID,String Time) {
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {


                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    Log.e(this.getClass().getName(), "success!" + success);

                    if (success) {

                        Log.e(this.getClass().getName(), "Check_sleep_start_Time_before!" + jsonResponse);
                        ID2 =jsonResponse.getString("ID");
                        start_time =jsonResponse.getString("Time");
                        Status2 =jsonResponse.getString("Status");
                        Log.e(this.getClass().getName(), "전날날짜에 취침시간이 판단됨");
                        Log.e(this.getClass().getName(), "Start_time"+start_time);
                        long now = System.currentTimeMillis();
                        Date date = new Date(now);
                        DateFormat df =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        Calendar calendar=Calendar.getInstance();

                        calendar.setTime(date);
                        calendar.add(Calendar.SECOND,-1);

                        String getTime=df.format(calendar.getTime());
                        if(Static_setting.Status.contains("Guardian"))
                        {
                            Check_sleep_stop_Time(Static_setting.Protected_ID,getTime);
                        }
                        else if(Static_setting.Status.contains("Ward"))
                        {

                            Check_sleep_stop_Time(Static_setting.ID,getTime);
                        }

                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(Simply_referencs_Activity2.this);
                        builder.setMessage("실패 시발")
                                .setNegativeButton("확인", null)
                                .create()
                                .show();


                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };


        Sleep_start_Time_before_Request sleep_start_time_before_request = new Sleep_start_Time_before_Request(ID, Time, responseListener);
        RequestQueue queue = Volley.newRequestQueue(Simply_referencs_Activity2.this);
        queue.add(sleep_start_time_before_request);

    }

    class BackgroundTask extends AsyncTask<String, Void, String> {
        String target;



        @Override
        protected void onPreExecute() {
            target = "http://dbwo4011.cafe24.com/DRbed/dataRequest.php";

        }
        String sendMsg;
        @Override
        protected String doInBackground(String... params) {
            try {
                String ID=params[0];
                String Time = params[1];
                String data = URLEncoder.encode("ID","UTF-8")+"="+URLEncoder.encode(ID,"UTF-8");// UTF-8로  설정 실제로 string 상으로 봤을땐, tmsg="String" 요런식으로 설정 된다.
                data+= "&"+URLEncoder.encode("Time","UTF-8")+"="+URLEncoder.encode(Time,"UTF-8");
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
            try {
                JSONObject jsonObject = new JSONObject(res);
                JSONArray jsonArray = jsonObject.getJSONArray("response");
                int count = 0;
                while(count < jsonArray.length()){
                    JSONObject object = jsonArray.getJSONObject(count);
                    sleepStep.setIDList(object.getString("ID"));
                    sleepStep.setTimeList(object.getString("Time"));
                    sleepStep.setSleep_stepList(object.getString("Sleep_step"));

                    Log.e(this.getClass().getName(), "HR"+sleepStep.getIDList(count));
                    Log.e(this.getClass().getName(), "RR"+sleepStep.getTimeList(count));
                    Log.e(this.getClass().getName(), "SV"+sleepStep.getSleep_stepList(count));

                    count++;
                }
            } catch (Exception e){
                e.printStackTrace();
            }
            chartstart();
        }
    }

    private void chartstart() {
        Log.e(this.getClass().getName(), "@@@@@@@@@@@@@@@@@@@@@@@쓰레드 시작에서 딴 메소드부름");

        chart = findViewById(R.id.chart1);
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
        //l.setTypeface(tfLight);
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
        leftAxis.setAxisMaximum(100f);
        leftAxis.setAxisMinimum(0f);
        leftAxis.setDrawGridLines(true);

        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setEnabled(false);

        for(int i=0;i<sleepStep.getTotalLength();i++) {
            setHRData(i, 200);
        }
        //setRRData(totaldata_info.getTotalLength(),200);
        //setSVData(totaldata_info.getTotalLength(),200);
        //setHRVData(totaldata_info.getTotalLength(),200);
    }

    private LineDataSet createSet() {

        LineDataSet set = new LineDataSet(null, "Dynamic Data");
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        set.setColor(ColorTemplate.getHoloBlue());
        set.setCircleColor(Color.WHITE);
        set.setLineWidth(2f);
        set.setCircleRadius(4f);
        set.setFillAlpha(65);
        set.setFillColor(ColorTemplate.getHoloBlue());
        set.setHighLightColor(Color.rgb(244, 117, 117));
        set.setValueTextColor(Color.WHITE);
        set.setValueTextSize(9f);
        set.setDrawValues(false);
        return set;
    }

    private void setHRData(int count, float range) {

        LineData data = chart.getData();

        if (data != null) {

            ILineDataSet set = data.getDataSetByIndex(0);
            // set.addEntry(...); // can be called as well

            if (set == null) {
                set = createSet();
                data.addDataSet(set);
            }


               // data.addEntry(new Entry(set.getEntryCount(), totaldata_info.getHRList(count)), 0);

            data.notifyDataChanged();

            // let the chart know it's data has changed
            chart.notifyDataSetChanged();

            // limit the number of visible entries
            chart.setVisibleXRangeMaximum(120);
            // chart.setVisibleYRange(30, AxisDependency.LEFT);

            // move to the latest entry
            chart.moveViewToX(data.getEntryCount());

            // this automatically refreshes the chart (calls invalidate())
            // chart.moveViewTo(data.getXValCount()-7, 55f,
            // AxisDependency.LEFT);
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
        getMenuInflater().inflate(R.menu.simply_referencs__activity2, menu);
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


    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }
}
