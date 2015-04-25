/*
 * Copyright (C) 2015 Willi Ye
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.grarak.kerneladiutor.fragments.kernel;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;

import com.grarak.kerneladiutor.R;
import com.grarak.kerneladiutor.elements.CardViewItem;
import com.grarak.kerneladiutor.elements.SeekBarCardView;
import com.grarak.kerneladiutor.elements.SwitchCardView;
import com.grarak.kerneladiutor.elements.UsageCardView;
import com.grarak.kerneladiutor.fragments.RecyclerViewFragment;
import com.grarak.kerneladiutor.utils.Utils;
import com.grarak.kerneladiutor.utils.kernel.Battery;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by willi on 03.01.15.
 */
public class BatteryFragment extends RecyclerViewFragment implements
        SwitchCardView.DSwitchCard.OnDSwitchCardListener,
        SeekBarCardView.DSeekBarCardView.OnDSeekBarCardListener {

    private UsageCardView.DUsageCard mBatteryLevelCard;
    private CardViewItem.DCardView mBatteryVoltageCard, mBatteryTemperature;

    private SwitchCardView.DSwitchCard mForceFastChargeCard;

    private SeekBarCardView.DSeekBarCardView mBlxCard;

    @Override
    public void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);

        batteryLevelInit();
        batteryVoltageInit();
        batteryTemperatureInit();
        if (Battery.hasForceFastCharge()) forceFastChargeInit();
        if (Battery.hasBlx()) blxInit();

        getActivity().registerReceiver(this.mBatInfoReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
    }

    @Override
    public void postInit(Bundle savedInstanceState) {
        super.postInit(savedInstanceState);
        if (getCount() < 4) showApplyOnBoot(false);
    }

    private void batteryLevelInit() {
        mBatteryLevelCard = new UsageCardView.DUsageCard();
        mBatteryLevelCard.setText(getString(R.string.battery_level));

        addView(mBatteryLevelCard);
    }

    private void batteryVoltageInit() {
        mBatteryVoltageCard = new CardViewItem.DCardView();
        mBatteryVoltageCard.setTitle(getString(R.string.battery_voltage));

        addView(mBatteryVoltageCard);
    }

    private void batteryTemperatureInit() {
        mBatteryTemperature = new CardViewItem.DCardView();
        mBatteryTemperature.setTitle(getString(R.string.battery_temperature));

        addView(mBatteryTemperature);
    }

    private void forceFastChargeInit() {
        mForceFastChargeCard = new SwitchCardView.DSwitchCard();
        mForceFastChargeCard.setTitle(getString(R.string.usb_fast_charge));
        mForceFastChargeCard.setDescription(getString(R.string.usb_fast_charge_summary));
        mForceFastChargeCard.setChecked(Battery.isForceFastChargeActive());
        mForceFastChargeCard.setOnDSwitchCardListener(this);

        addView(mForceFastChargeCard);
    }

    private void blxInit() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 101; i++) list.add(String.valueOf(i));

        mBlxCard = new SeekBarCardView.DSeekBarCardView(list);
        mBlxCard.setTitle(getString(R.string.blx));
        mBlxCard.setDescription(getString(R.string.blx_summary));
        mBlxCard.setProgress(Battery.getCurBlx());
        mBlxCard.setOnDSeekBarCardListener(this);

        addView(mBlxCard);
    }

    private final BroadcastReceiver mBatInfoReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context arg0, Intent intent) {
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
            int voltage = intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE, 0);
            int temperature = intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0);

            if (mBatteryLevelCard != null) mBatteryLevelCard.setProgress(level);
            if (mBatteryVoltageCard != null)
                mBatteryVoltageCard.setDescription(voltage + getString(R.string.mv));
            if (mBatteryTemperature != null) {
                double celsius = (double) temperature / 10;
                double fahrenheit = Utils.celsiusToFahrenheit(celsius);
                mBatteryTemperature.setDescription((celsius + "°C" + " " + fahrenheit + "°F"));
            }
        }
    };

    @Override
    public void onChecked(SwitchCardView.DSwitchCard dSwitchCard, boolean checked) {
        if (dSwitchCard == mForceFastChargeCard)
            Battery.activateForceFastCharge(checked, getActivity());
    }

    @Override
    public void onChanged(SeekBarCardView.DSeekBarCardView dSeekBarCardView, int position) {
    }

    @Override
    public void onStop(SeekBarCardView.DSeekBarCardView dSeekBarCardView, int position) {
        if (dSeekBarCardView == mBlxCard) Battery.setBlx(position, getActivity());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            getActivity().unregisterReceiver(mBatInfoReceiver);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }
}
