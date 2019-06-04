package com.example.drbed;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity<lntent> extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ///////////////////////////////////
        ////////////* 회원등록*////////////
        ///////////////////////////////////


        Button mRegister_btn = (Button) findViewById(R.id.Register_btn);
        mRegister_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptRegister();
            }
        });

        ///////////////////////////////////
        ////////////* 뒤로가기*////////////
        ///////////////////////////////////
        Button mBack_btn = (Button) findViewById(R.id.Back_btn);
        mBack_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attempBack();
            }
        });
    }



    private void attemptRegister() {
        ///////////////////////////////////
        ////////////* 회원등록*////////////
        ///////////////////////////////////





            //간변인으로 선택후 등록한다면

            final EditText ed_ID = (EditText) findViewById(R.id.ID);
            final EditText ed_PW = (EditText) findViewById(R.id.PW);
            final EditText ed_Name = (EditText) findViewById(R.id.Name);
            final EditText ed_Phone = (EditText) findViewById(R.id.Phone);
            final EditText ed_Age = (EditText) findViewById(R.id.Age);
            String ID =ed_ID.getText().toString();
            String PW =ed_PW.getText().toString();
            String Name =ed_Name.getText().toString();
            String Phone =ed_Phone.getText().toString();
            String Age =ed_Age.getText().toString();


            Response.Listener<String> responseListener =new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        boolean success = jsonResponse.getBoolean("success");
                        Log.e(this.getClass().getName(),"회원등록시!"+success);
                        Log.e(this.getClass().getName(),"success!"+success);
                        if(success) /*회원가입성공*/
                        {
                            AlertDialog.Builder builder=new AlertDialog.Builder(RegisterActivity.this);
                            builder.setMessage("회원등록 성공")
                                    .setPositiveButton("확인",null)
                                    .create()
                                    .show();
                            Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
                            RegisterActivity.this.startActivity(intent);
                        }
                        else /*회원가입실패*/
                        {
                            AlertDialog.Builder builder=new AlertDialog.Builder(RegisterActivity.this);
                            builder.setMessage("회원등록 실패")
                                    .setNegativeButton("확인",null)
                                    .create()
                                    .show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };
            RegisterRequest registerRequest=new RegisterRequest(ID, PW, Name, Phone,Age,responseListener);
            RequestQueue queue= Volley.newRequestQueue(RegisterActivity.this);
            queue.add(registerRequest);




    }

    private void attempBack() {
        ///////////////////////////////////
        ////////////* 뒤로가기*////////////
        ///////////////////////////////////
        Intent intent =new Intent(this,LoginActivity.class);
        startActivity(intent);

    }
}
