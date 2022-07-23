package com.application.foggy.resuables;

import android.app.AlertDialog;
import android.content.Context;

public class Reusable {

    public static void createErrorDialog(Context context, String title, String msg) {
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(msg)
                .setNegativeButton("ok", (dialogInterface, i) -> dialogInterface.dismiss())
                .create()
                .show();
    }
}
