package com.siddhant.oxygencontrol.utils;

import android.content.Context;

import com.github.javiersantos.appupdater.AppUpdater;
import com.github.javiersantos.appupdater.enums.Display;
import com.github.javiersantos.appupdater.enums.UpdateFrom;
import com.siddhant.oxygencontrol.R;

import com.siddhant.oxygencontrol.utils.root.RootUtils;

/**
 * Created by Morogoku on 12/01/2018.
 */

public class AppUpdaterTask {

    private static final String DEVICE = RootUtils.runCommand("getprop ro.build.product");
    
    public static void appCheckNotification(Context context){
        if (Prefs.getBoolean("show_update_notif", true, context)) {
            new AppUpdater(context)
                    .setDisplay(Display.NOTIFICATION)
                    .setUpdateFrom(UpdateFrom.GITHUB)
                    .setIcon(R.drawable.logo)
                    .setGitHubUserAndRepo("DarkLord1731", DEVICE)
                    .start();
        }
    }

    public static void appCheckDialog(Context context){
        if (Prefs.getBoolean("show_update_notif", true, context)) {
            new AppUpdater(context)
                    .setDisplay(Display.DIALOG)
                    .setUpdateFrom(UpdateFrom.GITHUB)
                    .setGitHubUserAndRepo("DarkLord1731", DEVICE)
                    .start();
        }
    }

    public static void appCheckDialogAllways(Context context){
            new AppUpdater(context)
                    .setDisplay(Display.DIALOG)
                    .setUpdateFrom(UpdateFrom.GITHUB)
                    .setGitHubUserAndRepo("DarkLord1731", DEVICE)
                    .start();
    }
}
