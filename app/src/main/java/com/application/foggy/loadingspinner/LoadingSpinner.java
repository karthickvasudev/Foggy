package com.application.foggy.loadingspinner;

import android.app.ProgressDialog;
import android.content.Context;

public class LoadingSpinner {
    private static ProgressDialog progressDialog;

    public static void show(Context context) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please wait...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
    }

    public static void dismiss(){
        progressDialog.dismiss();
    }

    public static void dismissIf(){
        if(progressDialog !=null  && progressDialog.isShowing()){
            progressDialog.dismiss();
        }
    }
}
