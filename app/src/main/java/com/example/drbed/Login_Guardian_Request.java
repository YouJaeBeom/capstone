package com.example.drbed;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class Login_Guardian_Request extends StringRequest {
    final static private String URL ="http://dbwo4011.cafe24.com/DRbed/Login_Guardian.php";
    private Map<String,String> parameters;

    public Login_Guardian_Request(String ID, String PW, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        parameters=new HashMap<>();
        parameters.put("ID",ID);
        parameters.put("PW",PW);

        Log.e(this.getClass().getName(),"ID!"+ID);
        Log.e(this.getClass().getName(),"PW!"+PW);

    }


    public Map<String,String> getParams(){
        return parameters;
    }
}
