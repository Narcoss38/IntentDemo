package fr.rtgrenoble.crouzetl.intentdemo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "log";
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 0;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        editText = (EditText) findViewById(R.id.editText);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    public void OnClickRAZ(View v) {
        editText.setText("");
    }

    public void OnClickGoogle(View v) throws UnsupportedEncodingException {
        String question = editText.getText().toString();
        String url = "https://www.google.fr/";
        if (!question.isEmpty()) {
            question = URLEncoder.encode(question, "UTF-8");
            url += "search?q=" + question;
        }
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    public void OnClickMaps(View v) throws UnsupportedEncodingException {
        String question = editText.getText().toString();
        String geo = "geo:";
        if (!question.isEmpty()) {
            question = URLEncoder.encode(question, "UTF-8");
            geo += "0,0?q=" + question;
        } else geo += "45.19960,5.77688";
        Uri uri = Uri.parse(geo);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
        startActivity(intent);
    }

    public void OnClickWifi(View v) throws Exception {
        try {
            Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
            startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(this, "Pas de module Wifi", Toast.LENGTH_SHORT).show();
        }
    }

    public void OnClickSysteme(View v) {
        Intent intent = new Intent(Settings.ACTION_DEVICE_INFO_SETTINGS);
        startActivity(intent);
    }

    public void OnClickAppel(View v) throws UnsupportedEncodingException {
        String num = editText.getText().toString();
        String uri = "tel:";
        if (!num.isEmpty()) {
            num = URLEncoder.encode(num, "UTF-8");
            uri += num;
        }else uri = "tel:666";
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.CALL_PHONE},
                MY_PERMISSIONS_REQUEST_CALL_PHONE);
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse(uri));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        startActivity(intent);
    }

    public void OnClickQRcode(View v){
        Intent intent = new Intent("com.google.zxing.client.android.SCAN");
        intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
        startActivity(intent);
    }
}
