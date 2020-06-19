package me.creatorguy.androidthread;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static TextView tvMainHighLoadTaskResult;
    private static Button btnMainPerformHighLoadTask;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            //based on high load task result
            tvMainHighLoadTaskResult.setText("Task is complete.");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvMainHighLoadTaskResult = findViewById(R.id.tvMainHighLoadTaskResult);
        btnMainPerformHighLoadTask = findViewById(R.id.btnMainPerformHighLoadTask);
        btnMainPerformHighLoadTask.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnMainPerformHighLoadTask:

                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        // high load task start
                        long futureTime = System.currentTimeMillis() + 10000;
                        while (System.currentTimeMillis() < futureTime) {
                            synchronized (this) {
                                try {
                                    wait(futureTime - System.currentTimeMillis());
                                }catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        // high load task end

                        // you cannot make ui thread level changes from thread. instead use ui thread using handler.
                        handler.sendEmptyMessage(0);
                    }
                };

                Thread thread = new Thread(runnable);
                thread.start();

                break;
            default:
                break;
        }
    }
}