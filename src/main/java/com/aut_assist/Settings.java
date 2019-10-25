package com.aut_assist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.EditText;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class Settings extends AppCompatActivity {
    EditText editText;
    boolean invalid = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        editText = (EditText)findViewById(R.id.IP);
        Button connect_button = (Button)findViewById(R.id.ip_set);

        connect_button.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                String str = editText.getText().toString();

                    SharedPreferences settings = getSharedPreferences("IP_ADD", 0);
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString("key", str);
                    editor.commit();
                    Toast msg = Toast.makeText(getBaseContext(), str, Toast.LENGTH_LONG);
                    msg.show();

            }
        });
    }
}
