package tes.recognition.voice.finalisinghawk;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;


public class MainActivity extends ActionBarActivity {

    private int mBindFlag;
    private Messenger mServiceMessenger;
    public  Myservice myservice;
    private Button btn;
    private Button stng;

    public String mk = "Where Are You";
    public String mu = "/Sdcard/dsd/dwe2w.mp3";

    public String org_mk;
    public String org_mu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
               setContentView(R.layout.activity_main);

        /*
            Calling The Function That Reads The File And
            Load The MK AND MV
         */

        LoadSavedMKU();

        myservice=new Myservice();
        btn=(Button)findViewById(R.id.button);
        stng=(Button)findViewById(R.id.setting_button);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btn.getText() == "OFF") {
                    btn.setText("ON");
                    Intent service = new Intent(getApplicationContext(), Myservice.class);
                    startService(service);
                    mBindFlag = Build.VERSION.SDK_INT < Build.VERSION_CODES.ICE_CREAM_SANDWICH ? 0 : Context.BIND_ABOVE_CLIENT;
                } else {
                    btn.setText("OFF");
                    stopService(new Intent(getBaseContext(), Myservice.class));


                }
            }
        });

        stng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Call Settings Activity;

                stopService(new Intent(getBaseContext(), Myservice.class));

                Intent intent = new Intent(MainActivity.this,Settings_Activity.class);
                startActivity(intent);
                finish();

            }
        });

    }

    public void LoadSavedMKU() {

        //Reads And Copies To

        //mk = org_mk;
        //mu = org_mu;

    }

    public void SaveMKU(){

        //Save To the File - org_mk and org_mv

    }


    @Override
    protected void onStart()
    {
        super.onStart();

        bindService(new Intent(this, Myservice.class), mServiceConnection, mBindFlag);
    }
    @Override
    protected void onStop()
    {
        super.onStop();

        if (mServiceMessenger != null)
        {
            unbindService(mServiceConnection);
            mServiceMessenger = null;
        }
    }

    private final ServiceConnection mServiceConnection = new ServiceConnection()
    {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service)
        {

            mServiceMessenger = new Messenger(service);
            Message msg = new Message();
            msg.what = myservice.MSG_RECOGNIZER_START_LISTENING;

            try
            {
                myservice.mServerMessenger.send(msg);
            }
            catch (RemoteException e)
            {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name)
        {

            mServiceMessenger = null;
        }

    };


    @Override
    protected void onPause() {
        super.onPause();

        finish();
    }
}
