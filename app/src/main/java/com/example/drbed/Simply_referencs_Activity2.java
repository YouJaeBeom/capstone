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
import android.widget.SeekBar;
import android.widget.TextView;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;

import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.drbed.custom.DayAxisValueFormatter;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;

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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Simply_referencs_Activity2 extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnChartValueSelectedListener {

    public int total_time;
    public String PW = "";
    public String Name = "";
    public String Phone = "";
    //public String Status = "";
    private LineChart chart;
    public String ID1 = "";
    public String ID2 = "";
    String stop_time="";
    String start_time="";
    private PieChart chart2;
    private SeekBar seekBarX, seekBarY;
    private TextView tvX, tvY;
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

        long now = System.currentTimeMillis();
        Date date = new Date(now);
        DateFormat df =new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar=Calendar.getInstance();

        calendar.setTime(date);


        String date1=df.format(calendar.getTime());
        Log.e(this.getClass().getName(), "Sleep_start_Time_Request @@@@@@@@@@ date1!" + date1);
        Sleep_start_Time_Request sleep_start_time_request = new Sleep_start_Time_Request(ID, Time,date1, responseListener);
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

        long now = System.currentTimeMillis();
        Date date = new Date(now);
        DateFormat df =new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar=Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);

        String date1=df.format(calendar.getTime());
        Log.e(this.getClass().getName(), "Sleep_start_Time_before_Request @@@@@@@@@@ date1!" + date1);
        Sleep_start_Time_before_Request sleep_start_time_before_request = new Sleep_start_Time_before_Request(ID, Time,date1, responseListener);
        RequestQueue queue = Volley.newRequestQueue(Simply_referencs_Activity2.this);
        queue.add(sleep_start_time_before_request);

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
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        DateFormat df =new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar=Calendar.getInstance();

        calendar.setTime(date);


        String date1=df.format(calendar.getTime());
        Sleep_stop_Time_Request sleep_stop_time_request = new Sleep_stop_Time_Request(ID, Time,date1, responseListener);
        RequestQueue queue = Volley.newRequestQueue(Simply_referencs_Activity2.this);
        queue.add(sleep_stop_time_request);

    }

    private void startSleepQulity() {
        Log.e(this.getClass().getName(), "@@@@@@@@@@@@@@@@@@@@@@@");
        Log.e(this.getClass().getName(), "stop_time"+stop_time);
        Log.e(this.getClass().getName(), "Start_time"+start_time);
        if(Static_setting.Status.contains("Guardian"))
        {

            new BackgroundTask().execute(Static_setting.Protected_ID,start_time,stop_time);
        }
        else if(Static_setting.Status.contains("Ward"))
        {

            new BackgroundTask().execute(Static_setting.ID,start_time,stop_time);
        }
        TextView text_total_sleep= (TextView) findViewById(R.id.total_sleep);
        TextView text_sleep= (TextView) findViewById(R.id.sleep);
        TextView text_wake= (TextView) findViewById(R.id.wake);



        SimpleDateFormat  dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date start_time1= null; ///9
        Date stop_time1=null;
        try {
            start_time1 = dateFormat.parse(start_time);
            stop_time1= dateFormat.parse(stop_time); //10
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long duration = stop_time1.getTime()-start_time1.getTime();
        long min = duration/60000;
        int hour= (int) (min/60);
        int min1= (int) (min%60);
        String total_time=String.valueOf(min);
        text_total_sleep.setText(hour+"시간"+min1+"분");
        text_sleep.setText(start_time);
        text_wake.setText(stop_time);

    }


    class BackgroundTask extends AsyncTask<String, Void, String> {
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
                    sleepStep.setIDList(object.getString("ID"));
                    sleepStep.setTimeList(object.getString("Time"));
                    sleepStep.setSleep_stepList(object.getString("Sleep_step"));

                    Log.e(this.getClass().getName(), count+"  ID "+sleepStep.getIDList(count));
                    Log.e(this.getClass().getName(), count+"  Time "+sleepStep.getTimeList(count));
                    Log.e(this.getClass().getName(), count+"  Sleep_step "+sleepStep.getSleep_stepList(count));

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
        chart.setBackgroundColor(Color.WHITE);

        LineData data = new LineData();
        data.setValueTextColor(Color.BLACK);

        // add empty data
        chart.setData(data);

        // get the legend (only possible after setting data)
        Legend l = chart.getLegend();

        // modify the legend ...
        l.setForm(Legend.LegendForm.LINE);
        //l.setTypeface(tfLight);
        l.setTextColor(Color.BLACK);
        ValueFormatter xAxisFormatter = new DayAxisValueFormatter(chart);

        XAxis xl = chart.getXAxis();
        xl.setPosition(XAxis.XAxisPosition.BOTTOM);
        //xl.setTypeface(tfLight);
        xl.setTextColor(Color.BLACK);
        xl.setDrawGridLines(false);
        xl.setAvoidFirstLastClipping(true);
        xl.setEnabled(true);

        YAxis leftAxis = chart.getAxisLeft();
        //leftAxis.setTypeface(tfLight);
        leftAxis.setTextColor(Color.BLACK);
        leftAxis.setAxisMaximum(4);

        leftAxis.setDrawGridLines(true);

        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setDrawGridLines(false);
       // rightAxis.setTypeface(tfLight);
        rightAxis.setLabelCount(8, false);
       // rightAxis.setValueFormatter(custom);
        rightAxis.setSpaceTop(15f);
        rightAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        rightAxis.setEnabled(false);



        chart2 = findViewById(R.id.chart2);
        chart2.setUsePercentValues(true);
        chart2.getDescription().setEnabled(false);
        chart2.setExtraOffsets(5, 10, 5, 5);

        chart2.setDragDecelerationFrictionCoef(0.95f);

        //chart2.setCenterTextTypeface(tfLight);
        chart2.setCenterText(generateCenterSpannableText());

        chart2.setDrawHoleEnabled(true);
        chart2.setHoleColor(Color.WHITE);

        chart2.setTransparentCircleColor(Color.WHITE);
        chart2.setTransparentCircleAlpha(110);

        chart2.setHoleRadius(58f);
        chart2.setTransparentCircleRadius(61f);

        chart2.setDrawCenterText(true);

        chart2.setRotationAngle(0);
        // enable rotation of the chart by touch
        chart2.setRotationEnabled(true);
        chart2.setHighlightPerTapEnabled(true);

        // chart.setUnit(" €");
        // chart.setDrawUnitsInChart(true);

        // add a selection listener
        chart2.setOnChartValueSelectedListener(this);

        chart2.animateY(1400, Easing.EaseInOutQuad);
        // chart.spin(2000, 0, 360);

        Legend l2 = chart2.getLegend();
        l2.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l2.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l2.setOrientation(Legend.LegendOrientation.VERTICAL);
        l2.setDrawInside(false);
        l2.setXEntrySpace(7f);
        l2.setYEntrySpace(0f);
        l2.setYOffset(0f);

        // entry label styling
        chart2.setEntryLabelColor(Color.BLACK);
       // chart2.setEntryLabelTypeface(tfRegular);
        chart2.setEntryLabelTextSize(12f);



        try {
            Get_time_diff(sleepStep.getTotalLength());
        } catch (ParseException e) {
            e.printStackTrace();
        }


        //setRRData(totaldata_info.getTotalLength(),200);
        //setSVData(totaldata_info.getTotalLength(),200);
        //setHRVData(totaldata_info.getTotalLength(),200);
    }

    private CharSequence generateCenterSpannableText() {
        SpannableString s = new SpannableString("DR.BED\nSleep phase percentage ");
        s.setSpan(new RelativeSizeSpan(1.7f), 0, 14, 0);
        s.setSpan(new StyleSpan(Typeface.NORMAL), 14, s.length() - 15, 0);
        s.setSpan(new ForegroundColorSpan(Color.GRAY), 14, s.length() - 15, 0);
        s.setSpan(new RelativeSizeSpan(.8f), 14, s.length() - 15, 0);
        s.setSpan(new StyleSpan(Typeface.ITALIC), s.length() - 14, s.length(), 0);
        s.setSpan(new ForegroundColorSpan(ColorTemplate.getHoloBlue()), s.length() - 14, s.length(), 0);
        return s;
    }

    private void Get_time_diff(int totalLength) throws ParseException {
        Log.e(this.getClass().getName(), "totalLength"+totalLength);
        SimpleDateFormat  dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm");
        for(int i=0;i<totalLength-1;i++)
        {
            Date first= dateFormat.parse(sleepStep.getTimeList(i)); ///9
            Date second= dateFormat.parse(sleepStep.getTimeList(i+1)); //10
            long duration = second.getTime()-first.getTime();
            long min = duration/60000;
            Log.e(this.getClass().getName(), i+"각 단계별로의 시간구하기"+min);
            String dif_min=String.valueOf(min);
            sleepStep.setSleep_time_diffList(dif_min);
        }
        Log.e(this.getClass().getName(), "시간차 list길이"+sleepStep.getSleep_time_diffListTotalLength());
        for(int i=0;i<sleepStep.getSleep_time_diffListTotalLength();i++){
            for(int l=0;l<Integer.parseInt(sleepStep.getSleep_time_diffList(i));l++){
                setData(i,200);
            }
        }
        setpieData(sleepStep.getSleep_time_diffListTotalLength());

    }

    private LineDataSet createSet() {

        LineDataSet set = new LineDataSet(null, "수면의 단계 : 1 (Deep sleep) , 2 (Light sleep), 3 (Rem sleep)");
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        set.setColor(ColorTemplate.getHoloBlue());
        set.setCircleColor(Color.BLACK);
        set.setLineWidth(1f);
        set.setCircleRadius(1f);
        set.setFillAlpha(65);
        set.setFillColor(ColorTemplate.getHoloBlue());
        set.setHighLightColor(Color.rgb(244, 117, 117));
        set.setValueTextColor(Color.BLACK);
        set.setValueTextSize(9f);
        set.setDrawValues(false);
        return set;
    }

    private void setData(int count, float range) {

        LineData data = chart.getData();

        if (data != null) {

            ILineDataSet set = data.getDataSetByIndex(0);
            // set.addEntry(...); // can be called as well

            if (set == null) {
                set = createSet();
                data.addDataSet(set);
            }


            data.addEntry(new Entry(set.getEntryCount(), Float.parseFloat(sleepStep.getSleep_stepList(count))), 0);

            data.notifyDataChanged();

            // let the chart know it's data has changed
            chart.notifyDataSetChanged();

            // limit the number of visible entries
            chart.setVisibleXRangeMaximum(120);
            // chart.setVisibleYRange(30, AxisDependency.LEFT);

            // move to the latest entry


            // this automatically refreshes the chart (calls invalidate())
            // chart.moveViewTo(data.getXValCount()-7, 55f,
            // AxisDependency.LEFT);

        }

    }


    private void setpieData(int count) {
        ArrayList<PieEntry> entries = new ArrayList<>();

        int deeptime=0;
        int lighttime=0;
        int remtime=0;
        for(int i=0;i<count;i++)
        {
            if(sleepStep.getSleep_stepList(i).contains("1"))//DEEP
            {
                deeptime+=Integer.parseInt(sleepStep.getSleep_time_diffList(i));
            }
            if(sleepStep.getSleep_stepList(i).contains("2"))//light
            {
                lighttime+=Integer.parseInt(sleepStep.getSleep_time_diffList(i));
            }
            if(sleepStep.getSleep_stepList(i).contains("3"))//rem
            {
                remtime+=Integer.parseInt(sleepStep.getSleep_time_diffList(i));
            }
        }

        SimpleDateFormat  dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date before= null; ///9
        Date starttime=null;
        Date st_time=null;
        Date star_time=null;
        try {
            starttime=dateFormat.parse(sleepStep.getTimeList(0));
            before = dateFormat.parse(sleepStep.getTimeList(count));
            st_time= dateFormat.parse(stop_time); //10
            star_time= dateFormat.parse(start_time); //10
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long duration11 = starttime.getTime()-star_time.getTime();
        long min33 = duration11/60000;
        long duration = st_time.getTime()-before.getTime();
        long min22 = duration/60000;
        remtime+=min22+min33;
        Log.e(this.getClass().getName(), "deeptime"+deeptime);
        Log.e(this.getClass().getName(), "lighttime"+lighttime);
        Log.e(this.getClass().getName(), "remtime"+remtime);
        int total_time = deeptime + lighttime + remtime;
        Log.e(this.getClass().getName(), "total_time"+total_time);
        Log.e(this.getClass().getName(), "deeptime per"+(float)deeptime/total_time);
        Log.e(this.getClass().getName(), "lighttime per"+(float)lighttime/total_time);
        Log.e(this.getClass().getName(), "remtime per"+(float)remtime/total_time);
        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
        entries.add(new PieEntry(deeptime,"Deep sleep"));
        entries.add(new PieEntry(lighttime,"Light sleep"));
        entries.add(new PieEntry(remtime,"Rem sleep" ));

        TextView min= (TextView) findViewById(R.id.min);
        min.setText("Deep sleep:"+deeptime+"\nLight sleep: "+lighttime+"\nRem sleep: "+remtime);





        TextView text_score= (TextView) findViewById(R.id.score);
        int sum=deeptime+lighttime;
        float sum1=(float)sum/total_time;
        text_score.setText(sum1*100+"점/100점");

        PieDataSet dataSet = new PieDataSet(entries, "수면단계별 %");

        dataSet.setDrawIcons(false);

        dataSet.setSliceSpace(3f);
        dataSet.setIconsOffset(new MPPointF(0, 40));
        dataSet.setSelectionShift(5f);

        // add a lot of colors

        ArrayList<Integer> colors = new ArrayList<>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(colors);
        //dataSet.setSelectionShift(0f);

        PieData data2 = new PieData(dataSet);
        data2.setValueFormatter(new PercentFormatter(chart2));
        data2.setValueTextSize(11f);
        data2.setValueTextColor(Color.BLACK);
        //data2.setValueTypeface(tfLight);
        chart2.setData(data2);

        // undo all highlights
        chart2.highlightValues(null);

        chart2.invalidate();
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
        if (e == null)
            return;
        Log.i("VAL SELECTED",
                "Value: " + e.getY() + ", index: " + h.getX()
                        + ", DataSet index: " + h.getDataSetIndex());
    }

    @Override
    public void onNothingSelected() {

    }
}
