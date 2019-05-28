package com.example.drbed;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class DataGuardianRequest extends StringRequest {
    final static private String URL ="http://dbwo4011.cafe24.com/DRbed/Guardian_request.php";
    private Map<String,String> parameters;

    /*
    현재 테이블에 사용자 ID을 안넣어서 메소드에 추가 x
     */
    public DataGuardianRequest(String Protected_ID, String time, Response.Listener<String> listener){
        super(Request.Method.POST, URL, listener, null);
        parameters=new HashMap<>();
        parameters.put("Protected_ID",Protected_ID);
        parameters.put("Time",time);
    }


    public Map<String,String> getParams(){
        return parameters;
    }
}
