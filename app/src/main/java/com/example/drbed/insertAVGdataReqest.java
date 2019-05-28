package com.example.drbed;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class insertAVGdataReqest extends StringRequest {
    final  static private String URL ="http://dbwo4011.cafe24.com/DRbed/insertAVGdata.php";
    private Map<String,String> parameters;

    /*
    현재 테이블에 사용자 ID을 안넣어서 메소드에 추가 x
     */
    public insertAVGdataReqest(String ID,String AVG_HR,String AVG_RR,String AVG_SV,String AVG_HRV, Response.Listener<String> listener){
        super(Request.Method.POST, URL, listener, null);
        parameters=new HashMap<>();
        parameters.put("ID",ID);
        parameters.put("AVG_HR",AVG_HR);
        parameters.put("AVG_RR",AVG_RR);
        parameters.put("AVG_SV",AVG_SV);
        parameters.put("AVG_HRV",AVG_HRV);
    }


    public Map<String,String> getParams(){
        return parameters;
    }
}
