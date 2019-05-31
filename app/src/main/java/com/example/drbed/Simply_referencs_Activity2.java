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
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

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
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;

public class Simply_referencs_Activity2 extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnChartValueSelectedListener {
    public String ID = "";
    public String PW = "";
    public String Name = "";
    public String Phone = "";
    public String Status = "";
    private LineChart chartHR,chartRR,chartSV,chartHRV,chart;
    Totaldata_info totaldata_info=new Totaldata_info();
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
        new BackgroundTask().execute("2019-05-30 07:30:00","2019-05-30 12:00:00");



        Log.e(this.getClass().getName(), "@@@@@@@@@@@@@@@@@@@@@@@쓰레드 끝남유");

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
                String start=params[0];
                String end = params[1];
                String data = URLEncoder.encode("start","UTF-8")+"="+URLEncoder.encode(start,"UTF-8");// UTF-8로  설정 실제로 string 상으로 봤을땐, tmsg="String" 요런식으로 설정 된다.
                data+= "&"+URLEncoder.encode("end","UTF-8")+"="+URLEncoder.encode(end,"UTF-8");
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
                    totaldata_info.setHRList(object.getString("HR"));
                    totaldata_info.setRRList(object.getString("RR"));
                    totaldata_info.setSVList(object.getString("SV"));
                    totaldata_info.setHRVList(object.getString("HRV"));
                    Log.e(this.getClass().getName(), "HR"+totaldata_info.getHRList(count));
                    Log.e(this.getClass().getName(), "RR"+totaldata_info.getRRList(count));
                    Log.e(this.getClass().getName(), "SV"+totaldata_info.getSVList(count));
                    Log.e(this.getClass().getName(), "HRV"+totaldata_info.getHRVList(count));
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

        chartHR = findViewById(R.id.chart1);
        chartRR = findViewById(R.id.chart2);
        chartSV = findViewById(R.id.chart3);
        chartHRV = findViewById(R.id.chart4);

        chartHR.setOnChartValueSelectedListener(this);
        chartRR.setOnChartValueSelectedListener(this);
        chartSV.setOnChartValueSelectedListener(this);
        chartHRV.setOnChartValueSelectedListener(this);

        // enable description text
        chartHR.getDescription().setEnabled(true);
        chartRR.getDescription().setEnabled(true);
        chartSV.getDescription().setEnabled(true);
        chartHRV.getDescription().setEnabled(true);
        // enable touch gestures
        chartHR.setTouchEnabled(true);
        chartRR.setTouchEnabled(true);
        chartSV.setTouchEnabled(true);
        chartHRV.setTouchEnabled(true);


        // enable scaling and dragging
        chartHR.setDragEnabled(true);
        chartHR.setScaleEnabled(true);
        chartHR.setDrawGridBackground(false);
        chartRR.setDragEnabled(true);
        chartRR.setScaleEnabled(true);
        chartRR.setDrawGridBackground(false);
        chartSV.setDragEnabled(true);
        chartSV.setScaleEnabled(true);
        chartSV.setDrawGridBackground(false);
        chartHRV.setDragEnabled(true);
        chartHRV.setScaleEnabled(true);
        chartHRV.setDrawGridBackground(false);

        // if disabled, scaling can be done on x- and y-axis separately
        chartHR.setPinchZoom(true);

        // set an alternative background color
        chartHR.setBackgroundColor(Color.LTGRAY);

        LineData data = new LineData();
        data.setValueTextColor(Color.WHITE);

        // add empty data
        chartHR.setData(data);

        // get the legend (only possible after setting data)
        Legend l = chartHR.getLegend();

        // modify the legend ...
        l.setForm(Legend.LegendForm.LINE);
        //l.setTypeface(tfLight);
        l.setTextColor(Color.WHITE);

        XAxis xl = chartHR.getXAxis();
        //xl.setTypeface(tfLight);
        xl.setTextColor(Color.WHITE);
        xl.setDrawGridLines(false);
        xl.setAvoidFirstLastClipping(true);
        xl.setEnabled(true);

        YAxis leftAxis = chartHR.getAxisLeft();
        //leftAxis.setTypeface(tfLight);
        leftAxis.setTextColor(Color.WHITE);
        leftAxis.setAxisMaximum(100f);
        leftAxis.setAxisMinimum(0f);
        leftAxis.setDrawGridLines(true);

        YAxis rightAxis = chartHR.getAxisRight();
        rightAxis.setEnabled(false);

        for(int i=0;i<totaldata_info.getTotalLength();i++) {
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


                data.addEntry(new Entry(set.getEntryCount(), totaldata_info.getHRList(count)), 0);

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
        /*ArrayList<Entry> values1 = new ArrayList<>();

        // chart.setVisibleYRange(30, AxisDependency.LEFT);

        // move to the latest entry

        float sum=0;
        float[] hr=new float[count];
        for (int i = 0; i < count; i++) {
            float val = totaldata_info.getHRList(i);
            hr[i]=val;
            sum+=val;
            values1.add(new Entry(i, val));

        }

        float AVG_HR=sum/count;
        Arrays.sort(hr);
        TextView textView1=(TextView) findViewById(R.id.max_min_hr);
        textView1.setText(String.valueOf(hr[0])+"//"+String.valueOf(hr[count-1]));
        TextView textView=(TextView) findViewById(R.id.avg_hr);
        textView.setText(String.valueOf(AVG_HR));


        LineDataSet set1, set2, set3,set4;

        if (chartHR.getData() != null &&
                chartHR.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) chartHR.getData().getDataSetByIndex(0);
            set1.setValues(values1);

            chartHR.getData().notifyDataChanged();
            chartHR.notifyDataSetChanged();


            // limit the number of visible entries
            chartHR.setVisibleXRangeMaximum(120);
            // chart.setVisibleYRange(30, AxisDependency.LEFT);

            // move to the latest entry
            chartHR.moveViewToX( chartHR.getData().getEntryCount());
        } else {
            // create a dataset and give it a type
            set1 = new LineDataSet(values1, "HR ");

            set1.setAxisDependency(YAxis.AxisDependency.LEFT);
            set1.setColor(ColorTemplate.getHoloBlue());
            set1.setCircleColor(Color.BLUE);
            set1.setColor(Color.BLUE);
            set1.setLineWidth(2f);
            set1.setCircleRadius(3f);
            set1.setFillAlpha(65);
            set1.setFillColor(ColorTemplate.getHoloBlue());
            set1.setHighLightColor(Color.rgb(244, 117, 117));
            set1.setDrawCircleHole(false);
            //set1.setFillFormatter(new MyFillFormatter(0f));
            //set1.setDrawHorizontalHighlightIndicator(false);
            //set1.setVisible(false);
            //set1.setCircleHoleColor(Color.WHITE);


            // create a data object with the data sets
            LineData data = new LineData(set1);
            data.setValueTextColor(Color.WHITE);
            data.setValueTextSize(9f);

            // set data
            chartHR.setData(data);
        }*/
    }

    private void setRRData(int count, float range) {

        ArrayList<Entry> values1 = new ArrayList<>();
        float sum = 0;
        float[] rr=new float[count];
        for (int i = 0; i < count; i++) {
            float val = totaldata_info.getRRList(i);
            sum+=val;
            rr[i]=val;
            values1.add(new Entry(i, val));
        }
        float AVG_RR=sum/count;
        Arrays.sort(rr);
        TextView textView1=(TextView) findViewById(R.id.max_min_rr);
        textView1.setText(String.valueOf(rr[0])+"//"+String.valueOf(rr[count-1]));
        TextView textView=(TextView) findViewById(R.id.avg_rr);
        textView.setText(String.valueOf(AVG_RR));
        LineDataSet set1;

        if (chartRR.getData() != null &&
                chartRR.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) chartRR.getData().getDataSetByIndex(0);

            set1.setValues(values1);

            chartRR.getData().notifyDataChanged();
            chartRR.notifyDataSetChanged();
        } else {
            // create a dataset and give it a type
            set1 = new LineDataSet(values1, "RR ");

            set1.setAxisDependency(YAxis.AxisDependency.LEFT);
            set1.setColor(ColorTemplate.getHoloBlue());
            set1.setCircleColor(Color.YELLOW);
            set1.setColor(Color.YELLOW);
            set1.setLineWidth(2f);
            set1.setCircleRadius(3f);
            set1.setFillAlpha(65);
            set1.setFillColor(ColorTemplate.getHoloBlue());
            set1.setHighLightColor(Color.rgb(244, 117, 117));
            set1.setDrawCircleHole(false);
            //set1.setFillFormatter(new MyFillFormatter(0f));
            //set1.setDrawHorizontalHighlightIndicator(false);
            //set1.setVisible(false);
            //set1.setCircleHoleColor(Color.WHITE);

            // create a dataset and give it a type

            // create a data object with the data sets
            LineData data = new LineData(set1);
            data.setValueTextColor(Color.YELLOW);
            data.setValueTextSize(9f);

            // set data
            chartRR.setData(data);
        }
    }

    private void setSVData(int count, float range) {

        ArrayList<Entry> values1 = new ArrayList<>();

        float sum=0;
        float[] sv=new float[count];
        for (int i = 0; i < count; i++) {
            float val = totaldata_info.getSVList(i);
            sum+=val;
            sv[i]=val;
            values1.add(new Entry(i, val));
        }
        float AVG_SV=sum/count;
        Arrays.sort(sv);
        TextView textView1=(TextView) findViewById(R.id.max_min_sv);
        textView1.setText(String.valueOf(sv[0])+"//"+String.valueOf(sv[count-1]));
        TextView textView=(TextView) findViewById(R.id.avg_sv);
        textView.setText(String.valueOf(AVG_SV));


        LineDataSet set1, set2, set3,set4;

        if (chartSV.getData() != null &&
                chartSV.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) chartSV.getData().getDataSetByIndex(0);

            set1.setValues(values1);
            chartSV.getData().notifyDataChanged();
            chartSV.notifyDataSetChanged();
        } else {
            // create a dataset and give it a type
            set1 = new LineDataSet(values1, "SV ");

            set1.setAxisDependency(YAxis.AxisDependency.LEFT);
            set1.setColor(ColorTemplate.getHoloBlue());
            set1.setCircleColor(Color.RED);
            set1.setColor(Color.RED);
            set1.setLineWidth(2f);
            set1.setCircleRadius(3f);
            set1.setFillAlpha(65);
            set1.setFillColor(ColorTemplate.getHoloBlue());
            set1.setHighLightColor(Color.rgb(244, 117, 117));
            set1.setDrawCircleHole(false);
            //set1.setFillFormatter(new MyFillFormatter(0f));
            //set1.setDrawHorizontalHighlightIndicator(false);
            //set1.setVisible(false);
            //set1.setCircleHoleColor(Color.WHITE);


            // create a data object with the data sets
            LineData data = new LineData(set1);
            data.setValueTextColor(Color.WHITE);
            data.setValueTextSize(9f);

            // set data
            chartSV.setData(data);
        }
    }

    private void setHRVData(int count, float range) {

        ArrayList<Entry> values1 = new ArrayList<>();
        float sum=0;
        float[] hrv=new float[count];
        for (int i = 0; i < count; i++) {
            float val = totaldata_info.getHRVList(i);
            sum+=val;
            hrv[i]=val;
            values1.add(new Entry(i, val));
        }
        float AVG_HRV=sum/count;
        Arrays.sort(hrv);
        TextView textView1=(TextView) findViewById(R.id.max_min_hrv);
        textView1.setText(String.valueOf(hrv[0])+"//"+String.valueOf(hrv[count-1]));
        TextView textView=(TextView) findViewById(R.id.avg_hrv);
        textView.setText(String.valueOf(AVG_HRV));

        LineDataSet set1, set2, set3,set4;

        if (chartHRV.getData() != null &&
                chartHRV.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) chartHRV.getData().getDataSetByIndex(0);

            set1.setValues(values1);

            chartHRV.getData().notifyDataChanged();
            chartHRV.notifyDataSetChanged();
        } else {
            // create a dataset and give it a type
            set1 = new LineDataSet(values1, "HRV ");

            set1.setAxisDependency(YAxis.AxisDependency.LEFT);
            set1.setColor(ColorTemplate.getHoloBlue());
            set1.setCircleColor(Color.GREEN);
            set1.setColor(Color.GREEN);
            set1.setLineWidth(2f);
            set1.setCircleRadius(3f);
            set1.setFillAlpha(65);
            set1.setFillColor(ColorTemplate.getHoloBlue());
            set1.setHighLightColor(Color.rgb(244, 117, 117));
            set1.setDrawCircleHole(false);
            //set1.setFillFormatter(new MyFillFormatter(0f));
            //set1.setDrawHorizontalHighlightIndicator(false);
            //set1.setVisible(false);
            //set1.setCircleHoleColor(Color.WHITE);


            // create a data object with the data sets
            LineData data = new LineData(set1);
            data.setValueTextColor(Color.WHITE);
            data.setValueTextSize(9f);

            // set data
            chartHRV.setData(data);
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
