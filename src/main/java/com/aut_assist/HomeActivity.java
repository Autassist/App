package com.aut_assist;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import java.net.URI;
import java.net.URISyntaxException;

public class HomeActivity extends ActivityViews implements View.OnClickListener {

    private MediaPlayer mediaPlayer;
    private Switch statusOne;
    private Switch statusTwo;
    private Switch statusThree;
    private Switch statusFour;
    private Button button3;
    private Button button4;
    private WebSocketClient mWebSocketClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFullScreen();
        setContentView(R.layout.activity_home);
        references();
        //connectWebSocket();
        button3 = (Button) findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openInfo();
            }
        });
        button4 = (Button) findViewById(R.id.button4);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSettings();
            }
        });

        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                //Toast.makeText(MainActivity.this, spinner.getSelectedItem().toString(), Toast.LENGTH_LONG).show();
                sendMessage(spinner.getSelectedItem().toString());
                Toast msg = Toast.makeText(getBaseContext(),spinner.getSelectedItem().toString(),Toast.LENGTH_LONG);
                msg.show();
                //SharedPreferences myPrefs = getSharedPreferences("IP_ADD", 0);  //Activity1.class
                //String ifsound = myPrefs.getString("key","");
                //Toast msg2 = Toast.makeText(getBaseContext(),ifsound,Toast.LENGTH_LONG);
                //msg2.show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });

    }

    public void openInfo() {
        Intent intent = new Intent(this, info.class);
        startActivity(intent);
    }
    public void openSettings() {
        Intent intent = new Intent(this, Settings.class);
        startActivity(intent);
        connectWebSocket();
    }


    @Override

    // This part of the code plays the media
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.playOne:
                if (mediaPlayer.isPlaying()){
                    mediaPlayer.stop();
                    mediaPlayer.release();
                }

                mediaPlayer=MediaPlayer.create(this,R.raw.clapping);
                mediaPlayer.start();
                changeSwitch(true,statusOne);
                changeSwitch(false,statusTwo);
                changeSwitch(false,statusThree);
                changeSwitch(false,statusFour);

                break;
            case R.id.playTwo:
                if (mediaPlayer.isPlaying()){
                    mediaPlayer.stop();
                    mediaPlayer.release();
                }

                mediaPlayer=MediaPlayer.create(this,R.raw.police_siren);
                mediaPlayer.start();
                changeSwitch(true,statusTwo);
                changeSwitch(false,statusOne);
                changeSwitch(false,statusThree);
                changeSwitch(false,statusFour);
                break;
            case R.id.playThree:
                if (mediaPlayer.isPlaying()){
                    mediaPlayer.stop();
                    mediaPlayer.release();
                }

                mediaPlayer=MediaPlayer.create(this,R.raw.car_horn);
                mediaPlayer.start();
                changeSwitch(true,statusThree);
                changeSwitch(false,statusOne);
                changeSwitch(false,statusTwo);
                changeSwitch(false,statusFour);
                break;
            case R.id.playFour:
                if (mediaPlayer.isPlaying()){
                    mediaPlayer.stop();
                    mediaPlayer.release();
                }

                mediaPlayer=MediaPlayer.create(this,R.raw.subway);
                mediaPlayer.start();
                changeSwitch(true,statusFour);
                changeSwitch(false,statusOne);
                changeSwitch(false,statusThree);
                changeSwitch(false,statusTwo);
                break;



        }


    }

    private void references(){
        spinner=findViewById(R.id.spinner);
        statusOne=findViewById(R.id.statusOne);
        statusTwo=findViewById(R.id.statusTwo);
        statusThree=findViewById(R.id.statusThree);
        statusFour=findViewById(R.id.statusFour);
        mediaPlayer=new MediaPlayer();


        //Spinner
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, getValuesList());
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setSelection(0);

    }

    private ArrayList<String> getValuesList() {

        ArrayList<String> list = new ArrayList<String>();

        for (int i=50;i<=200;i++) list.add(String.valueOf(i));

        return list;
    }

    private void changeSwitch(boolean status,Switch onOff ){

        if (status){

            onOff.setChecked(status);
            onOff.setText("On");

        }
        else {
            onOff.setChecked(status);
            onOff.setText("Off");
        }

    }

 ////////////////////////
 private void connectWebSocket() {
     URI uri;
     SharedPreferences myPrefs = getSharedPreferences("IP_ADD", 0);  //Activity1.class
     String ifsound = myPrefs.getString("key","");
     Toast msg2 = Toast.makeText(getBaseContext(),ifsound,Toast.LENGTH_LONG);
     msg2.show();
     try {
         uri = new URI("ws://"+ifsound+":8080");
     } catch (URISyntaxException e) {
         e.printStackTrace();
         return;
     }

     mWebSocketClient = new WebSocketClient(uri) {
         @Override
         public void onOpen(ServerHandshake serverHandshake) {
             //Log.i("Websocket", "Opened");
             mWebSocketClient.send("Hello");
         }

         @Override
         public void onMessage(String s) {
             final String message = s;
             runOnUiThread(new Runnable() {
                 @Override
                 public void run() {
                     Toast msg2 = Toast.makeText(getBaseContext(),message,Toast.LENGTH_LONG);
                     msg2.show();
                 }
             });
         }

         @Override
         public void onClose(int i, String s, boolean b) {
             //Log.i("Websocket", "Closed " + s);
         }

         @Override
         public void onError(Exception e) {
             //Log.i("Websocket", "Error " + e.getMessage());
         }
     };
     mWebSocketClient.connect();
 }
    public void sendMessage(String db_value) {
       // if(db_value!=null){
        //mWebSocketClient.send(db_value.toString());}

    //}
        }
}
