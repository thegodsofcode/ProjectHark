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
    public  TextView switchtxt;
    private int mBindFlag;
    private Messenger mServiceMessenger;
    public  Myservice myservice;
    private Switch mySwitch;
    private Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
               setContentView(R.layout.activity_main);

        myservice=new Myservice();
        btn=(Button)findViewById(R.id.button);
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

      /*  mySwitch = (Switch) findViewById(R.id.mySwitch);

        mySwitch.setOnCheckedChangeListener( new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked)
                {
                    Intent service = new Intent(getApplicationContext(), Myservice.class);
                    startService(service);
                    mBindFlag = Build.VERSION.SDK_INT < Build.VERSION_CODES.ICE_CREAM_SANDWICH ? 0 : Context.BIND_ABOVE_CLIENT;

                }

                else
                    stopService(new Intent(getBaseContext(), Myservice.class));



            }
        });



*/
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


    // Method to start the service
    public void startService(View view) {
        Intent service = new Intent(this, Myservice.class);
        startService(service);
        mBindFlag = Build.VERSION.SDK_INT < Build.VERSION_CODES.ICE_CREAM_SANDWICH ? 0 : Context.BIND_ABOVE_CLIENT;


    }

    // Method to stop the service
    public void stopService(View view) {
        stopService(new Intent(getBaseContext(), Myservice.class));
    }

}
