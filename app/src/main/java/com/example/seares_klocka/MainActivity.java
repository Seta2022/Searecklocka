package com.example.seares_klocka;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import org.apache.commons.net.ntp.NTPUDPClient;
import org.apache.commons.net.ntp.TimeInfo;

import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    // Skapar en instans av NTPUDPClient för att hämta tid från NTP-servern
    private final NTPUDPClient ntpseareClient = new NTPUDPClient();
    private final String NTP_SERVER = "time.google.com";
    private long ntpTimeMillsecond;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Kontrollerar om enheten har nätverksanslutning och hämtar tid från NTP-servern om det finns en anslutning
        if (isNetworkConnected()) {
            fetchNTPTime();
        }
        // Skapar en timer för att uppdatera tidsvisningen varje sekund
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(() -> updateDisplay());
            }
        }, 0, 1000);
    }
    // Funktion för att hämta tid från NTP-servern
    private void fetchNTPTime() {
        new Thread(() -> {
            try {
                InetAddress inetAddress = InetAddress.getByName(NTP_SERVER);
                TimeInfo timeInfo = ntpseareClient.getTime(inetAddress);
                ntpTimeMillsecond = timeInfo.getMessage().getTransmitTimeStamp().getTime();
            } catch (Exception e) {
                e.printStackTrace();
                ntpTimeMillsecond = 0;
            }
        }).start();
    }

    private void updateDisplay() {
        TextView timeTextView = findViewById(R.id.timeTextView);
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());

        Date currentTime = (ntpTimeMillsecond != 0) ? new Date(ntpTimeMillsecond) : new Date();
        if (ntpTimeMillsecond != 0) {
            ntpTimeMillsecond += 1000;
        }

        timeTextView.setText(format.format(currentTime));
        timeTextView.setBackgroundResource(isNetworkConnected() ? R.drawable.round_background : R.drawable.round_background2);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerNetworkReceiver();
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(networkReceiver);
    }
    // Funktion för att registrera en mottagare för nätverksanslutningsändringar
    private void registerNetworkReceiver() {
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkReceiver, filter);
    }

    private final BroadcastReceiver networkReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (isNetworkConnected()) {
                fetchNTPTime();
            } else {
                ntpTimeMillsecond = 0;
            }
        }
    };
    // Funktion för att kontrollera om enheten är ansluten till nätverket
    private boolean isNetworkConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager != null && connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }
}
