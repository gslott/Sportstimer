package se.gunnarslott.sportstimer;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import static java.lang.System.currentTimeMillis;
import static se.gunnarslott.sportstimer.R.id.BT_away;
import static se.gunnarslott.sportstimer.R.id.BT_away_decrese;
import static se.gunnarslott.sportstimer.R.id.BT_home;
import static se.gunnarslott.sportstimer.R.id.BT_home_decrese;
import static se.gunnarslott.sportstimer.R.id.BT_reset;
import static se.gunnarslott.sportstimer.R.id.BT_start;
import static se.gunnarslott.sportstimer.R.id.BT_stop;

public class MainActivity extends AppCompatActivity {

    final String TAG = "Main";
    int home = 0;
    int away = 0;
    TextView tv_home;
    TextView tv_gone;
    TextView tv_timer;
    long start_time;
    long stop_time;
    long elapsed_time;
    boolean is_running;

    private void Timer(){

        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                Long diff;
                /*int hours = seconds/3600;
                int minutes = (seconds%3600)/60;
                int secs = seconds%60;

                String time = String.format("%d:%02d:%02d", hours, minutes, secs);

                timeView.setText(time);

                if(startRun){
                    seconds++;
                }
*/
                Log.d(TAG, "Timer is running");
                handler.postDelayed(this, 100);

                if (is_running){
                    diff = currentTimeMillis() - start_time;
                    diff = diff/100;
                    diff = elapsed_time + diff;
                    updateTime(diff);
                }
            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tv_gone = findViewById(R.id.TV_gone);
        tv_home = findViewById(R.id.TV_home);
        tv_timer = findViewById(R.id.TV_timer);

        //Start runnable timer
        Timer();

        Button bt_home = findViewById(BT_home);
        bt_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Hemma öka");
                goalHome();

            }
        });

        Button bt_home_decrese = findViewById(BT_home_decrese);
        bt_home_decrese.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Hemma minska");
                goalHomeDecrese();

            }
        });

        Button bt_away = findViewById(BT_away);
        bt_away.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Borta öka");
                goalAway();
            }
        });

        Button bt_away_decrese = findViewById(BT_away_decrese);
        bt_away_decrese.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Borta minska");
                goalAwayDecrese();

            }
        });

        Button bt_start_time = findViewById(BT_start);
        bt_start_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!is_running){
                    is_running = true;
                    Log.d(TAG, "Start timer button");
                    startTime();
                }


            }
        });


        Button bt_stop_time = findViewById(BT_stop);
        bt_stop_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long diff;
                Log.d(TAG, "Stop timer");

                if (is_running) is_running = false;

                stop_time = currentTimeMillis();

                //Calc time since started
                diff = stop_time - start_time;
                diff = diff/100;

                //Adding saved time from previous saved timers
                elapsed_time = elapsed_time + diff;

                Log.d(TAG, Long.toString(elapsed_time));
            }
        });

        Button bt_reset_time = findViewById(BT_reset);
        bt_reset_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!is_running){
                    elapsed_time = 0;
                    updateTime(elapsed_time);
                }
            }
        });

    }

    public void updateTime(long time){

        int hour = (int) time/36000;
        time = time - (hour * 36000);

        int minute = (int) time/600;
        time = time - (minute * 600);

        int sec = (int) time/10;
        time = time - (sec * 10);

        int dec = (int) time;

        String sHour, sMinute, sSec, sDec;

        if (hour < 10){
            sHour = "0" + Integer.toString(hour);
        } else  sHour = Integer.toString(hour);

        if (minute < 10){
            sMinute = "0" + Integer.toString(minute);
        } else  sMinute = Integer.toString(minute);

        if (sec < 10){
            sSec = "0" + Integer.toString(sec);
        } else  sSec = Integer.toString(sec);

        sDec = Integer.toString(dec);

        String temp = sHour +":"+ sMinute +":"+ sSec +":"+ sDec;

        tv_timer.setText(temp);

    }

    public void startTime(){

            Log.d(TAG, "startTime func");
            start_time = currentTimeMillis();
            Log.d(TAG, String.format("%d",start_time));
    }

    public void goalHome(){
        home = home + 1;
        //tv_home.setText(Integer.toString(home));
        tv_home.setText(String.format("%d",home));
    }

    public void goalAway(){
        away = away + 1;
        tv_gone.setText(String.format("%d",away));
    }

    public void goalHomeDecrese(){
        Log.d(TAG, "Home -");
        if (home > 0){
            home = home - 1;
            tv_home.setText(String.format("%d",home));
        }
    }

    public void goalAwayDecrese(){
        Log.d(TAG, "Away -");
        if (away > 0){
        away = away - 1;
        tv_gone.setText(String.format("%d",away));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_homeDecrese:
                goalHomeDecrese();
                return true;
            case R.id.action_awayDecrese:
                goalAwayDecrese();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
