package com.example.netmoboptimisation;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DataServices extends AppCompatActivity {
    Button btnStart;
    TextView txtOperateur;
    TextView txtReseau;
    TextView txtPuissance;
    TelephonyManager Tm;
    PhoneStateListener myPhoneStateListener;


    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_services);


        // Detection réseau utilisé,force du signiale,nom d'operateur

        btnStart = (Button) findViewById(R.id.btnStart);
        //creation des TextView
        txtOperateur = (TextView) findViewById(R.id.txtOperateur);
        txtReseau = (TextView) findViewById(R.id.txtReseau);
        txtPuissance = (TextView) findViewById(R.id.txtPuissance);

        //Creer  methode pour bouton
        btnStart.setOnClickListener(btnStartInfo);

    }     //Bouton start Info

    private View.OnClickListener btnStartInfo = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Handler mHandler = new Handler();
            mHandler.postDelayed(mRunnable, 1000);
        }
    };


    private final Runnable mRunnable = new Runnable() {
        public void run() {
            //récupération de l'opérateur
            Tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            String temp = Tm.getNetworkOperatorName();
            txtOperateur.setText("Operateur réseau : " + temp);

            //récupération du type de réseau
            String NetTypeStr = null;
            if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            switch (Tm.getNetworkType()) {
                case 0:
                    NetTypeStr = getString(R.string.unknown);
                    break;
                case 1:
                    NetTypeStr = "GPRS";
                    break;
                case 2:
                    NetTypeStr = "EDGE";
                    break;
                case 3:
                    NetTypeStr = "UMTS";
                    break;
                case 4:
                    NetTypeStr = "CDMA";
                    break;
                case 5:
                    NetTypeStr = "EVDO_0";
                    break;
                case 6:
                    NetTypeStr = "EVDO_A";
                    break;
                case 7:
                    NetTypeStr = "1xRTT";
                    break;
                case 8:
                    NetTypeStr = "HSDPA";
                    break;
                case 9:
                    NetTypeStr = "HSUPA";
                    break;
                case 10:
                    NetTypeStr = "HSPA";
                    break;
                case 11:
                    NetTypeStr = "iDen";
                    break;
                case 12:
                    NetTypeStr = "EVDO_B";
                    break;
                case 13:
                    NetTypeStr = "LTE";
                    break;
                case 14:
                    NetTypeStr = "eHRPD";
                    break;
                case 15:
                    NetTypeStr = "HSPA+";
                    break;
            }
            txtReseau.setText("Reseau Utilisé : "+ NetTypeStr );

            Tm.listen(myPhoneStateListener, PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);
            final String finalNetTypeStr = NetTypeStr;
            myPhoneStateListener = new PhoneStateListener() {
                public void onSignalStrengthsChanged(SignalStrength signalStrength){
                    int SignalStrength = signalStrength.getGsmSignalStrength();
                    SignalStrength = (2 * SignalStrength) - 113; // -> dBm
                    if(finalNetTypeStr.equals("LTE")){

                        txtPuissance.setText(" RSRQ = " + (SignalStrength - 20) +" Dbm ");
                    }
                    else {
                        txtPuissance.setText(" RSSI : "+ SignalStrength+" Dbm ");
                    }
                }
            };
            Handler mHandler = new Handler();
            mHandler.postDelayed(mRunnable, 1000);
        }
    };


    //




}
