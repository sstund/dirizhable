package grib.dirizhabl_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.hardware.SensorEventListener;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.hardware.SensorEvent;

public class MainActivity extends AppCompatActivity {

    private int Count = 0;
    private double magLast = 0;

    private TextView Steps;
    private Button step_button;
    private Button tonull;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Steps = findViewById(R.id.Steps);
        step_button = findViewById(R.id.step_button);
        tonull = findViewById(R.id.tonull);

        SensorManager sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        SensorEventListener stepDetector = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                if(sensorEvent != null) {
                    double dx = sensorEvent.values[0];
                    double dy = sensorEvent.values[1];
                    double dz = sensorEvent.values[2];

                    double mag = Math.sqrt(dx*dx + dy*dy + dz*dz);

                    if(mag-magLast > 4) {
                        Count++;
                        Steps.setText(Integer.toString(Count));
                    }
                    magLast = mag;
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };

        sensorManager.registerListener(stepDetector,sensor,sensorManager.SENSOR_DELAY_NORMAL);


        step_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Count++;
                Steps.setText(Integer.toString(Count));
            }
        });

        tonull.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Count=0;
                Steps.setText(Integer.toString(Count));
            }
        });
    }

}