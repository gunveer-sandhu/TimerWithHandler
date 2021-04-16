package gunveer.codes.timerwithhandler;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button btnStart, btnReset;
    private TextView tvMinutes, tvSeconds;
    private EditText etMinutes, etSeconds;
    public int minutes, seconds;
    public Handler mainHandler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnStart = findViewById(R.id.btnStart);
        btnReset = findViewById(R.id.btnReset);
        tvMinutes = findViewById(R.id.tvMinutes);
        tvSeconds = findViewById(R.id.tvSeconds);
        etMinutes = findViewById(R.id.etMinutes);
        etSeconds = findViewById(R.id.etSeconds);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                minutes = Integer.parseInt(etMinutes.getText().toString());
                seconds = Integer.parseInt(etSeconds.getText().toString());
                tvMinutes.setText(String.valueOf(minutes));
                tvSeconds.setText(String.valueOf(seconds));

            }
        });

    }

}