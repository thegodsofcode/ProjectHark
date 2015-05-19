package tes.recognition.voice.finalisinghawk;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class Settings_Activity extends ActionBarActivity {

    MainActivity obj;

    private TextView openMusic;
    private TextView edMk;
    private TextView don;

    private static final int PICKFILE_RESULT_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_);

        obj = new MainActivity();

        openMusic = (TextView)findViewById(R.id.openmus);
        edMk = (TextView)findViewById(R.id.editmk);
        don = (TextView)findViewById(R.id.done_click);

        edMk.setText(obj.mk);


        /*
            Open Music
         */

        openMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenMusic();
            }
        });

        /**************/

        /*
            Edit MK Click
         */
           edMk.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   edMk.setText("");
                   obj.mk = edMk.getText().toString();
                   //openMusic.setText(obj.mk);
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





    private void DoneClicked() {

        obj.org_mk = obj.mk;
        obj.org_mu = obj.mu;

        obj.SaveMKU();

    }

    private void OpenMusic() {

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
                }
                break;

        }
    }

    @Override
    public void onBackPressed() {
        obj.LoadSavedMKU();
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
