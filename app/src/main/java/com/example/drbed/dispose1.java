package com.example.drbed;

import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

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
import java.util.Calendar;
import java.util.Date;

public class dispose1 extends AppCompatActivity {
    static SleepStep_1 sleepStep_1=new SleepStep_1();

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
}
