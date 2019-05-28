package com.example.drbed;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Set;

//import android.support.v7.app.ActionBarActivity;


public class ServerSetActivity extends Activity {

    EditText etServerIp;

    public final static String SERVER_IP = "com.example.sensorquery.SERVER_IP";
    public final static String PHONEID = "com.example.sensorquery.PHONEID";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server_set);
        final Intent intent = new Intent(this,StatusCheckActivity.class);
        // putExtra�� ���� ���޵� ���� getIntent�� ���� ����
        Intent ci = getIntent();
        String cServerIp = ci.getStringExtra(SERVER_IP);

        etServerIp = (EditText)findViewById(R.id.editTextServerIP);
        etServerIp.setText(cServerIp);

        //String set_SERVERIP = etServerIp.getText().toString();

        Button btn = (Button)findViewById(R.id.buttonSetOk);
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                // AnotherActivity���� �����͸� �����Ͽ� �ִ� �κ�
                Intent intentData = new Intent();
                intentData.putExtra(SERVER_IP, etServerIp.getText().toString());
                startActivity(intent);
                // ���� Activity�� ����

            }
        });
    }


}
