package com.example.farrellmochammad.jarkomlan;

import android.media.MediaPlayer;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.w3c.dom.Text;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MonitoringActivity extends AppCompatActivity {

    MqttHelper mqttHelper;

    TextView dataReceivedSound, dataReceivedVibrate, dataStatus;

    Vibrator vibrator;
    MediaPlayer trexsound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitoring);

        dataReceivedSound = (TextView) findViewById(R.id.soundMonitoring);
        dataReceivedVibrate = (TextView) findViewById(R.id.vibrationMonitoring);
        dataStatus = (TextView) findViewById(R.id.status);
        vibrator = (Vibrator)getSystemService(MonitoringActivity.VIBRATOR_SERVICE);
        trexsound = MediaPlayer.create(this,R.raw.trex_roar);
        startMqtt();
    }

    private void startMqtt() {
        mqttHelper = new MqttHelper(getApplicationContext());
        mqttHelper.setCallback(new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean b, String s) {

            }

            @Override
            public void connectionLost(Throwable throwable) {

            }

            @Override
            public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
                Log.w("Debug", mqttMessage.toString());
                String[] message = mqttMessage.toString().split(",");
                Log.w("Message sound : ",message[0]);
                String sound = message[0];
                String vibrate = message[1];
                dataReceivedSound.setText(sound);
                dataReceivedVibrate.setText(vibrate);
                if ((Integer.parseInt(sound)<41 || Integer.parseInt(sound)>42) && Integer.parseInt(vibrate)== 1){
                    vibrator.vibrate(1000);
                    trexsound.start();
                    dataStatus.setText("Bayi sedang menangis");
                }
                else{
                    dataStatus.setText("Bayi dalam kondisi normal");
                }

            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

            }
        });
    }
}
