package gunveer.codes.timerwithhandler;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button btnStart, btnReset, btnStop;
    private TextView tvMinutes, tvSeconds;
    private EditText etMinutes, etSeconds;
    public int minutes, seconds;
    public Handler mainHandler = new Handler(Looper.getMainLooper());
    public Thread timeThread;
    public Looper looper;
    public static long totalTime = 0;
    public volatile boolean stop = false;
    public volatile boolean reset = false;
    public volatile boolean stopbtn = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnStart = findViewById(R.id.btnStart);
        btnReset = findViewById(R.id.btnReset);
        btnStop = findViewById(R.id.btnStop);
        tvMinutes = findViewById(R.id.tvMinutes);
        tvSeconds = findViewById(R.id.tvSeconds);
        etMinutes = findViewById(R.id.etMinutes);
        etSeconds = findViewById(R.id.etSeconds);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stop = false;
                reset = false;
                stopbtn = false;
                minutes = Integer.parseInt(etMinutes.getText().toString());
                seconds = Integer.parseInt(etSeconds.getText().toString());
                tvMinutes.setText(String.valueOf(minutes));
                tvSeconds.setText(String.valueOf(seconds));
                totalTime = minutes*60000 + seconds*1000;
                beginTimer(totalTime);
                timeThread.start();
            }

            private void beginTimer(long totalTime) {
                final long[] tt = {totalTime};
                timeThread = new Thread(){
                    @Override
                    public void run() {
                        while(tt[0] !=0){
                            if(!stop){
                                SystemClock.sleep(1000);
                                tt[0] = tt[0] - 1000;
                                mainHandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        tvMinutes.setText(String.valueOf(tt[0]/60000));
                                        tvSeconds.setText(String.valueOf((tt[0]%60000)/1000));
                                    }
                                });
                            }else{
                                if(reset){
                                    mainHandler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            tvMinutes.setText(String.valueOf(minutes));
                                            tvSeconds.setText(String.valueOf(seconds));
                                        }
                                    });
                                }else if(stopbtn){
                                    mainHandler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            tvMinutes.setText("0");
                                            tvSeconds.setText("0");
                                            etMinutes.setText("");
                                            etSeconds.setText("");
                                        }
                                    });
                                }
                                return;
                            }
                        }
                    }
                };
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stop = true;
                reset = true;
                stopbtn = false;
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stop = true;
                stopbtn = true;
                reset = false;
                tvMinutes.setText("0");
                tvSeconds.setText("0");
                etMinutes.setText("");
                etSeconds.setText("");
            }
        });

    }

}