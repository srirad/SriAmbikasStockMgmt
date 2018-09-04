package com.example.sriambikasstockmgmt;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginPage extends Activity {

    Button loginbtn;
    EditText text;
    String pwd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        text = (EditText) findViewById(R.id.passwordtxt);
        loginbtn = (Button) findViewById(R.id.loginbtn);

        final Intent intent = new Intent(this, MainActivity.class);

        loginbtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                pwd = text.getText().toString();

                Bundle extras = new Bundle();
                extras.putString("PWD",pwd);
                extras.putString("WAREHOUSE","1");
                intent.putExtras(extras);

                intent.putExtra("Extras", extras);
                startActivity(intent);
            }
        });

    }
}

