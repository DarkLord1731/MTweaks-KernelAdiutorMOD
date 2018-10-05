package com.github.javiersantos.appupdater;

import android.app.Activity;

import android.net.Uri;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;

import com.github.javiersantos.appupdater.enums.UpdateFrom;

import com.siddhant.oxygencontrol.utils.root.RootUtils;

import java.net.URL;

/**
 * Click listener for the "Update" button of the update dialog. <br/>
 * Extend this class to add custom actions to the button on top of the default functionality.
 */
public class UpdateClickListener implements DialogInterface.OnClickListener {

    private final Context context;
    private final UpdateFrom updateFrom;
    private final URL apk;
    private final String DEVICE = RootUtils.runCommand("getprop ro.oxygen.device");
    private final String url = "https://sourceforge.net/projects/oxygen-kernel/files/" + DEVICE;
    
    public UpdateClickListener(final Context context, final UpdateFrom updateFrom, final URL apk) {
        this.context = context;
        this.updateFrom = updateFrom;
        this.apk = apk;
    }

    @Override
    public void onClick(final DialogInterface dialog, final int which) {
        Uri webpage = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        }
    }
}
