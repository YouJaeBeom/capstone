package com.example.drbed;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Sleep_stop_Time_Request extends StringRequest {
    final static private String URL ="http://dbwo4011.cafe24.com/DRbed/sleep_stop_time.php";
    private Map<String,String> parameters;

    public Sleep_stop_Time_Request(String ID, String Time,String date1 ,Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        parameters=new HashMap<>();
        parameters.put("ID",ID);
        parameters.put("Time",Time);
        parameters.put("date1",date1);



    }


    public Map<String,String> getParams(){
        return parameters;
    }
}
