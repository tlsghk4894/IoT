package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.*;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    TextView t1, t2, t_send, t_recv;
    Button bt1, bt2, bt3;
    Socket socket;
    BufferedReader reader;
    PrintWriter writer;
    String ip = "220.68.27.72";
    int port = 10010;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bt1 = (Button) findViewById(R.id.bt1);
        t2 = (TextView) findViewById(R.id.t2);


        bt1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ConnectServer cs = new ConnectServer();
                cs.start();
                try {
                    cs.join();
                    t2.setText("Successfully connected to the Server");
                } catch (Exception e) {
                    t2.setText("Failed to connect to the server");
                    return;
                }
            }
        });
    }
    private class ConnectServer extends Thread{
        public void run(){
            try{
                socket = new Socket(ip, port);
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
            }catch (Exception e){
                Log.e("lecture", e.getMessage());
            }
        }
    }
}
