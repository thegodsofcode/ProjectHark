package tes.recognition.voice.finalisinghawk;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


public class Settings_Activity extends ActionBarActivity {

    MainActivity obj;

    public TextView openMusic;
    private TextView edMk;
    private ImageButton don;
    private ImageButton tes;

    private static final int PICKFILE_RESULT_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_);

        obj = new MainActivity();



        openMusic = (TextView)findViewById(R.id.openmus);

        edMk = (TextView)findViewById(R.id.editmk);

        don = (ImageButton)findViewById(R.id.done_click);

        tes = (ImageButton)findViewById(R.id.test_button);



        //Initialising

        edMk.setText(obj.mk);
        openMusic.setText(obj.mu);



        /*
            Open Music
         */

        openMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(),"Enter OpenMusic",Toast.LENGTH_SHORT).show();
                OpenMusic();
            }
        });




        /*

        //EDIT TEXT ONCLICK


        edMk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edMk.setText("");
            }
        });

        /****************************************
         * ****
         */
        /*
                Test Click
         */

        tes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                obj.mk = edMk.getText().toString();

                Intent service = new Intent(getApplicationContext(), Myservice.class);
                startService(service);

            }
        });


        /************/

        /*
            Done Click
         */

        don.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               DoneClicked();
            }


        });


    }
    /*
        End Of OnCreate
         */





    public void DoneClicked() {

        stopService(new Intent(getBaseContext(), Myservice.class));

        obj.org_mk = obj.mk;
        obj.org_mu = obj.mu;

        obj.SaveMKU();

        Intent intent = new Intent(Settings_Activity.this,MainActivity.class);
        startActivity(intent);
        finish();



    }

    public void OpenMusic() {

        //Toast.makeText(this,"Open Music",Toast.LENGTH_LONG).show();
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("audio/*");

        startActivityForResult(intent,PICKFILE_RESULT_CODE);


    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode){
            case PICKFILE_RESULT_CODE:
                if(resultCode==RESULT_OK){
                    obj.mu = data.getData().getPath();
                    String FileName = data.getData().getPath();

                    openMusic.setText(FileName);
                }
                break;

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        obj.LoadSavedMKU();
        Intent intent = new Intent(Settings_Activity.this,MainActivity.class);
        startActivity(intent);
        finish();

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        obj.LoadSavedMKU();

        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings_, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
