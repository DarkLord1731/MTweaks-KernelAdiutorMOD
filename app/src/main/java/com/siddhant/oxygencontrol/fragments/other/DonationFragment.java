package com.siddhant.oxygencontrol.fragments.other;

import com.siddhant.oxygencontrol.R;
import com.siddhant.oxygencontrol.fragments.recyclerview.RecyclerViewFragment;
import com.siddhant.oxygencontrol.utils.Utils;
import com.siddhant.oxygencontrol.views.recyclerview.DescriptionView;
import com.siddhant.oxygencontrol.views.recyclerview.ImageView;
import com.siddhant.oxygencontrol.views.recyclerview.RecyclerViewItem;
import com.siddhant.oxygencontrol.views.recyclerview.TitleView;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

/**
 * Created by Morogoku on 05/12/2017.
 */

public class DonationFragment extends RecyclerViewFragment {


    @Override
    protected boolean showViewPager() {
        return false;
    }

    @Override
    protected void addItems(List<RecyclerViewItem> items) {

        TitleView title = new TitleView();
        title.setText(getString(R.string.donation_title));

        items.add(title);


        DescriptionView desc = new DescriptionView();
        desc.setDrawable(getResources().getDrawable(R.drawable.logo));
        desc.setSummary(getString(R.string.donation_summary));
        desc.setOnItemClickListener(item
                -> Utils.launchUrl("https://www.paypal.me/SiddhantNaik", Objects.requireNonNull(getActivity())));

        items.add(desc);


        String leng = Locale.getDefault().getLanguage();

        ImageView img = new ImageView();
        if(leng.contains("es")){
            img.setDrawable(getResources().getDrawable(R.drawable.ic_donar_paypal));
        }else {
            img.setDrawable(getResources().getDrawable(R.drawable.ic_donate_paypal));
        }
        img.setOnItemClickListener(item
                -> Utils.launchUrl("https://www.paypal.me/SiddhantNaik", Objects.requireNonNull(getActivity())));

        items.add(img);

    }
}
