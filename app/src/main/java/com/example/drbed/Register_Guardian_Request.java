package com.example.drbed;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class Register_Guardian_Request extends StringRequest {

    final static private String URL ="http://dbwo4011.cafe24.com/DRbed/Register_Guardian.php";
    private Map<String,String> parameters;

    public  Register_Guardian_Request(String ID, String PW, String Name, String Phone,String Protected_ID, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        parameters=new HashMap<>();
        parameters.put("ID",ID);
        parameters.put("PW",PW);
        parameters.put("Name",Name);
        parameters.put("Phone",Phone);
        parameters.put("Protected_ID",Protected_ID);
        Log.e(this.getClass().getName(),"회원등록!");
        Log.e(this.getClass().getName(),"ID!"+ID);
        Log.e(this.getClass().getName(),"PW!"+PW);
        Log.e(this.getClass().getName(),"Name!"+Name);
        Log.e(this.getClass().getName(),"Phone!"+Phone);
        Log.e(this.getClass().getName(),"Protected_ID!"+Protected_ID);
    }


    public Map<String,String> getParams(){
        return parameters;
    }
}
