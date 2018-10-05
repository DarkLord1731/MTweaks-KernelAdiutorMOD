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

    private static final String DEVICE = RootUtils.runCommand("getprop ro.oxygen.device");
    private static String URL = "https://raw.githubusercontent.com/DarkLord1731/OxygenKernel-Changelog/master/";
    
    public static void appCheckNotification(Context context){
        URL = URL.concat(DEVICE).concat(".json");
        
        if (Prefs.getBoolean("show_update_notif", true, context)) {
            new AppUpdater(context)
                    .setDisplay(Display.NOTIFICATION)
                    .setUpdateFrom(UpdateFrom.JSON)
                    .setIcon(R.drawable.logo)
                    .setUpdateJSON(URL)
                    .start();
        }
    }

    public static void appCheckDialog(Context context){
        URL = URL.concat(DEVICE).concat(".json");
        
        if (Prefs.getBoolean("show_update_notif", true, context)) {
            new AppUpdater(context)
                    .setDisplay(Display.DIALOG)
                    .setUpdateFrom(UpdateFrom.JSON)
                    .setUpdateJSON(URL)
                    .start();
        }
    }

    public static void appCheckDialogAllways(Context context){
        URL = URL.concat(DEVICE).concat(".json");
        
            new AppUpdater(context)
                    .setDisplay(Display.DIALOG)
                    .setUpdateFrom(UpdateFrom.JSON)
                    .setUpdateJSON(URL)
                    .start();
    }
}
