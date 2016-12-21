 package pl.edu.wel.wat.ktoragodzina;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.prefs.Preferences;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MyActivity";
    private static BroadcastReceiver tickReceiver; //receives one tic per each minute
    int width; //display's width
    int timeOfVibration = 2000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get display's width 
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        width = displaymetrics.widthPixels;

        howLong();
        //receiving tic
        //Create a broadcast receiver to handle change in time
        tickReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().compareTo(Intent.ACTION_TIME_TICK) == 0) {
                    howLong();
                }
            }
        };
        registerReceiver(tickReceiver, new IntentFilter(Intent.ACTION_TIME_TICK));
    }

    void drawLine(View view, int viewId, int minute, boolean isFirstHalf) {
        view = findViewById(viewId); //take ID of line

        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) view.getLayoutParams();
        if (isFirstHalf) {
            params.width = (int) ((width / 2) + (width * ((float) minute / 90)));
        } else {
            params.width = (int) (width * ((float) minute / 90));
        }
        view.setLayoutParams(params); //set concrete length
    }


    void howLong() {
        Calendar now = Calendar.getInstance();
        Log.d(TAG, "<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< " + now.get(Calendar.HOUR_OF_DAY));
        Log.d(TAG, "<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< " + now.get(Calendar.MINUTE));
        Context context = getApplicationContext();

        int nowMinute = now.get(Calendar.MINUTE);
        int nowHour = now.get(Calendar.HOUR_OF_DAY);

        TextView textView = (TextView) findViewById(R.id.zegarPierwszy);
        View view = findViewById(R.id.liniaPierwsza);



        if (nowHour == 8) {
            if (nowMinute < 45) {
                drawLine(view, R.id.liniaPierwsza, (45 - nowMinute), true);
                Log.d(TAG, "To the break left: " + (45 - nowMinute));
                textView.setText((45 - nowMinute) + " min");
            } else if (nowMinute >= 50) {
                drawLine(view, R.id.liniaPierwsza, (35 + (60 - nowMinute)), false);
                Log.d(TAG, "To the end left: " + (35 + (60 - nowMinute)));
                textView.setText((35 + (60 - nowMinute)) + " min");
            } else {
                if (nowMinute == 45)  ((Vibrator)getSystemService(VIBRATOR_SERVICE)).vibrate(timeOfVibration);
                Log.d(TAG, "Break!");
                textView.setText("Break!");
                drawLine(view, R.id.liniaPierwsza, 45, false);
            }
        } else if (nowHour == 9) {
            if (nowMinute < 35) {
                drawLine(view, R.id.liniaPierwsza, (35 - nowMinute), false);
                textView.setText((35 - nowMinute) + " min");
                Log.d(TAG, "To the end left: " + (35 - nowMinute));
            } else if (nowMinute >= 50) {
                textView = (TextView) findViewById(R.id.zegarDrugi);
                textView.setText((35 + (60 - nowMinute)) + " min");
                drawLine(view, R.id.liniaDruga, (35 + (60 - nowMinute)), true);
                Log.d(TAG, "To the break left: " + (35 + (60 - nowMinute)));
            } else {
                if (nowMinute == 35)  ((Vibrator)getSystemService(VIBRATOR_SERVICE)).vibrate(timeOfVibration);
                Log.d(TAG, "Break!");
                textView = (TextView) findViewById(R.id.zegarDrugi);
                textView.setText("Break!");
            }
        } else if (nowHour == 10) {
            textView = (TextView) findViewById(R.id.zegarDrugi);
            if (nowMinute < 35) {
                Log.d(TAG, "Break!");
                textView.setText((35 - nowMinute) + " min");
                drawLine(view, R.id.liniaDruga, (35 - nowMinute), true);
                Log.d(TAG, "To break left: " + (35 - nowMinute));
            } else if (nowMinute >= 40) {
                textView.setText((25 + (60 - nowMinute)) + " min");
                drawLine(view, R.id.liniaDruga, (25 + (60 - nowMinute)), false);
                Log.d(TAG, "To the end left: " + (25 + (60 - nowMinute)));
            } else {
                if (nowMinute == 35)  ((Vibrator)getSystemService(VIBRATOR_SERVICE)).vibrate(timeOfVibration);
                Log.d(TAG, "Break!");
                textView.setText("Break!");
                drawLine(view, R.id.liniaDruga, 45, false);
            }
        } else if (nowHour == 11) {
            if (nowMinute < 25) {
                textView = (TextView) findViewById(R.id.zegarDrugi);
                textView.setText((25 - nowMinute) + " min");
                drawLine(view, R.id.liniaDruga, (25 - nowMinute), false);
                Log.d(TAG, "To the end left: " + (25 - nowMinute));
            } else if (nowMinute >= 40) {
                textView = (TextView) findViewById(R.id.zegarTrzeci);
                textView.setText((25 + (60 - nowMinute)) + " min");
                drawLine(view, R.id.liniaTrzecia, (25 + (60 - nowMinute)), true);
                Log.d(TAG, "To the break left: " + (25 + (60 - nowMinute)));
            } else {
                if (nowMinute == 25)  ((Vibrator)getSystemService(VIBRATOR_SERVICE)).vibrate(timeOfVibration);
                textView = (TextView) findViewById(R.id.zegarTrzeci);
                Log.d(TAG, "Break!");
                textView.setText("Break!");
            }
        } else if (nowHour == 12) {
            textView = (TextView) findViewById(R.id.zegarTrzeci);
            if (nowMinute < 25) {
                Log.d(TAG, "To the break left: " + (25 - nowMinute));
                textView.setText((25 - nowMinute) + " min");
                drawLine(view, R.id.liniaTrzecia, (25 - nowMinute), true);
            } else if (nowMinute >= 30) {
                textView.setText((15 + (60 - nowMinute)) + " min");
                drawLine(view, R.id.liniaTrzecia, (15 + (60 - nowMinute)), false);
                Log.d(TAG, "To the end left: " + (15 + (60 - nowMinute)));
            } else {
                if (nowMinute == 25)  ((Vibrator)getSystemService(VIBRATOR_SERVICE)).vibrate(timeOfVibration);
                Log.d(TAG, "Break!");
                textView.setText("Break!");
                drawLine(view, R.id.liniaTrzecia, 45, false);
            }
        } else if (nowHour == 13) {
            if (nowMinute < 15) {
                textView = (TextView) findViewById(R.id.zegarTrzeci);
                Log.d(TAG, "To the end left: " + (15 - nowMinute));
                textView.setText((15 - nowMinute) + " min");
                drawLine(view, R.id.liniaTrzecia, (15 - nowMinute), false);
            } else if (nowMinute >= 30) {
                textView = (TextView) findViewById(R.id.zegarCzwarty);
                textView.setText((15 + (60 - nowMinute)) + " min");
                drawLine(view, R.id.liniaCzwarta, (15 + (60 - nowMinute)), true);
                Log.d(TAG, "To the break left: " + (15 + (60 - nowMinute)));
            } else {
                if (nowMinute == 15)  ((Vibrator)getSystemService(VIBRATOR_SERVICE)).vibrate(timeOfVibration);
                Log.d(TAG, "Break!");
                textView = (TextView) findViewById(R.id.zegarCzwarty);
                textView.setText("Break!");
            }
        } else if (nowHour == 14) {
            textView = (TextView) findViewById(R.id.zegarCzwarty);
            if (nowMinute < 15) {
                Log.d(TAG, "To the break left: " + (15 - nowMinute));
                textView.setText((15 - nowMinute) + " min");
                drawLine(view, R.id.liniaCzwarta, (15 - nowMinute), true);
            } else if (nowMinute >= 20) {
                textView.setText((5 + (60 - nowMinute)) + " min");
                drawLine(view, R.id.liniaCzwarta, (5 + (60 - nowMinute)), false);
                Log.d(TAG, "To the end left: " + (5 + (60 - nowMinute)));
            } else {
                if (nowMinute == 15)  ((Vibrator)getSystemService(VIBRATOR_SERVICE)).vibrate(timeOfVibration);
                Log.d(TAG, "Break!");
                textView.setText("Break!");
                drawLine(view, R.id.liniaCzwarta, 45, false);
            }
        } else if (nowHour == 15) {
            if (nowMinute < 5) {
                textView = (TextView) findViewById(R.id.zegarCzwarty);
                Log.d(TAG, "To the end left: " + (5 - nowMinute));
                textView.setText((5 - nowMinute) + " min");
                drawLine(view, R.id.liniaCzwarta, (5 - nowMinute), false);
            } else if (nowMinute >= 45) {
                textView = (TextView) findViewById(R.id.zegarPiaty);
                textView.setText((30 + (60 - nowMinute)) + " min");
                drawLine(view, R.id.liniaPiata, (30 + (60 - nowMinute)), true);
                Log.d(TAG, "To the break left: " + (30 + (60 - nowMinute)));
            } else {
                if (nowMinute == 5)  ((Vibrator)getSystemService(VIBRATOR_SERVICE)).vibrate(timeOfVibration);
                Log.d(TAG, "Break!");
                textView = (TextView) findViewById(R.id.zegarPiaty);
                textView.setText("Break!");
            }
        } else if (nowHour == 16) {
            textView = (TextView) findViewById(R.id.zegarPiaty);
            if (nowMinute < 30) {
                Log.d(TAG, "To the break left: " + (30 - nowMinute));
                textView.setText((30 - nowMinute) + " min");
                drawLine(view, R.id.liniaPiata, (30 - nowMinute), true);
            } else if (nowMinute >= 35) {
                textView.setText((20 + (60 - nowMinute)) + " min");
                drawLine(view, R.id.liniaPiata, (20 + (60 - nowMinute)), false);
                Log.d(TAG, "To the end left: " + (20 + (60 - nowMinute)));
            } else {
                if (nowMinute == 30)  ((Vibrator)getSystemService(VIBRATOR_SERVICE)).vibrate(timeOfVibration);
                Log.d(TAG, "Break!");
                textView.setText("Break!");
                drawLine(view, R.id.liniaPiata, 45, false);
            }
        } else if (nowHour == 17) {
            if (nowMinute < 20) {
                textView = (TextView) findViewById(R.id.zegarPiaty);
                Log.d(TAG, "To the end left: " + (20 - nowMinute));
                textView.setText((20 - nowMinute) + " min");
                drawLine(view, R.id.liniaPiata, (20 - nowMinute), false);
            } else if (nowMinute >= 35) {
                textView = (TextView) findViewById(R.id.zegarSzosty);
                textView.setText((20 + (60 - nowMinute)) + " min");
                drawLine(view, R.id.liniaSzosta, (20 + (60 - nowMinute)), true);
                Log.d(TAG, "To the break left: " + (20 + (60 - nowMinute)));
            } else {
                if (nowMinute == 20)  ((Vibrator)getSystemService(VIBRATOR_SERVICE)).vibrate(timeOfVibration);
                Log.d(TAG, "Break!");
                textView = (TextView) findViewById(R.id.zegarSzosty);
                textView.setText("Break!");
            }
        } else if (nowHour == 18) {
            textView = (TextView) findViewById(R.id.zegarSzosty);
            if (nowMinute < 20) {
                Log.d(TAG, "To the break left: " + (20 - nowMinute));
                textView.setText((20 - nowMinute) + " min");
                drawLine(view, R.id.liniaSzosta, (20 - nowMinute), true);
            } else if (nowMinute >= 25) {
                textView.setText((10 + (60 - nowMinute)) + " min");
                drawLine(view, R.id.liniaSzosta, (10 + (60 - nowMinute)), false);
                Log.d(TAG, "To the end left: " + (10 + (60 - nowMinute)));
            } else {
                if (nowMinute == 20)  ((Vibrator)getSystemService(VIBRATOR_SERVICE)).vibrate(timeOfVibration);
                Log.d(TAG, "Break!");
                textView.setText("Break!");
                drawLine(view, R.id.liniaSzosta, 45, false);
            }
        } else if (nowHour == 19) {
            if (nowMinute < 10) {
                textView = (TextView) findViewById(R.id.zegarSzosty);
                Log.d(TAG, "To the end left: " + (10 - nowMinute));
                textView.setText((20 - nowMinute) + " min");
                drawLine(view, R.id.liniaSzosta, (10 - nowMinute), false);
            } else if (nowMinute >= 25) {
                textView = (TextView) findViewById(R.id.zegarSiodmy);
                textView.setText((10 + (60 - nowMinute)) + " min");
                drawLine(view, R.id.liniaSiodma, (10 + (60 - nowMinute)), true);
                Log.d(TAG, "To the break left: " + (10 + (60 - nowMinute)));
            } else {
                if (nowMinute == 10)  ((Vibrator)getSystemService(VIBRATOR_SERVICE)).vibrate(timeOfVibration);
                Log.d(TAG, "Break!");
                textView = (TextView) findViewById(R.id.zegarSiodmy);
                textView.setText("Break!");
            }
        } else if (nowHour == 20) {
            textView = (TextView) findViewById(R.id.zegarSiodmy);
            if (nowMinute < 10) {
                textView.setText((10 - nowMinute) + " min");
                drawLine(view, R.id.liniaSiodma, (10 - nowMinute), true);
                Log.d(TAG, "To the break left: " + (10 - nowMinute));
            } else if (nowMinute >= 15) {
                textView.setText((60 - nowMinute) + " min");
                drawLine(view, R.id.liniaSiodma, (60 - nowMinute), false);
                Log.d(TAG, "To the end left: " + (60 - nowMinute));
            } else {
                if (nowMinute == 10)  ((Vibrator)getSystemService(VIBRATOR_SERVICE)).vibrate(timeOfVibration);
                Log.d(TAG, "Break!");
                textView.setText("Break!");
                drawLine(view, R.id.liniaSiodma, 45, false);
            }
        } else {
            Toast toast = Toast.makeText(context, "Now there is no classes", Toast.LENGTH_LONG);
            toast.show();
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        howLong();
    }

    @Override
    protected void onResume() {
        super.onResume();
        howLong();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        howLong();
    }

    @Override
    protected void onPause() {
        super.onPause();

        //receiving tic
        //Create a broadcast receiver to handle change in time
        tickReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().compareTo(Intent.ACTION_TIME_TICK) == 0) {
                    howLong();
                }
            }
        };
        registerReceiver(tickReceiver, new IntentFilter(Intent.ACTION_TIME_TICK));
    }

    @Override
    public void onStop() {
        super.onStop();

        //receiving tic
        //Create a broadcast receiver to handle change in time
        tickReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().compareTo(Intent.ACTION_TIME_TICK) == 0) {
                    howLong();
                }
            }
        };
        registerReceiver(tickReceiver, new IntentFilter(Intent.ACTION_TIME_TICK));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //unregister broadcast receiver.
        if (tickReceiver != null)
            unregisterReceiver(tickReceiver);
    }
}
