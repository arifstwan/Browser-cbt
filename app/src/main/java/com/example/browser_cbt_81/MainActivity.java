package com.example.browser_cbt_81;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.content.DialogInterface;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private WebView myWebView;
    public String baseURL ="http://192.168.5.15";
    private boolean status;
    private String currentUrl;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myWebView = (WebView) findViewById(R.id.myWebview);
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setUserAgentString("cbt-user-galak");
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);
        myWebView.setWebViewClient(new WebViewClient());
        myWebView.loadUrl(baseURL);
        mHandler = new Handler();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        currentUrl = myWebView.getUrl();
        status = currentUrl.contains("/mod/quiz");
        //if (!hasFocus && status==true ) {
        if (!hasFocus ) {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                   // if (!hasFocus&& status==true) {
                    if (!hasFocus) {
                        Toast.makeText(MainActivity.this, "Anda membuka aplikasi lain!", Toast.LENGTH_LONG).show();
                        myWebView.loadUrl(baseURL+"/mod/quiz/lock.php");
                    }
                }
            }, 100); }
        if (hasFocus) {
           // Toast.makeText(MainActivity.this, "Anda Kembali", Toast.LENGTH_LONG).show();
            mHandler.removeCallbacksAndMessages(null);
        }
            // The activity has lost focus.
        }


    @Override
    public void onBackPressed() {
        if (myWebView.canGoBack()) {
            myWebView.goBack();
        }
         else{
                // super.onBackPressed();
                new AlertDialog.Builder(this)
                        .setIcon(R.mipmap.ic_launcher)
                        .setTitle(R.string.app_name)
                        .setMessage("Apakah kamu yakin keluar?")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                finish();
                            }
                        })
                        .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                dialogInterface.cancel();
                            }
                        })
                        .show();
            }
        }

}