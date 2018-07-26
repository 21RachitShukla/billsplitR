package rs21.billsplitr.Activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.transition.Fade;
import android.transition.Slide;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import rs21.billsplitr.Adapters.BillAdapter;
import rs21.billsplitr.Fragments.BillDesc;
import rs21.billsplitr.Fragments.BillItems;
import rs21.billsplitr.Fragments.BillMembers;
import rs21.billsplitr.MyApplication;
import rs21.billsplitr.POJOs.Bill;
import rs21.billsplitr.POJOs.Item;
import rs21.billsplitr.POJOs.Member;
import rs21.billsplitr.R;

public class BillActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);
        Intent i = getIntent();
        MobileAds.initialize(this, "ca-app-pub-6464793630486269~5561755973");
        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Fade fade = new Fade();
            fade.setDuration(1000);
            getWindow().setEnterTransition(fade);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Slide slide = new Slide();
            slide.setDuration(1000);
            getWindow().setExitTransition(slide);
        }
        ViewPager vp = findViewById(R.id.viewPager);
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(vp);
        vp.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        final int[] ICONS = new int[]{
                R.drawable.ic_desc,
                R.drawable.ic_items,
                R.drawable.ic_mems};

        tabLayout.getTabAt(0).setIcon(ICONS[0]);
        tabLayout.getTabAt(1).setIcon(ICONS[1]);
        tabLayout.getTabAt(2).setIcon(ICONS[2]);
        setTitle(BillAdapter.currentBill.getName());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bill_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_save:
                boolean flag = true;
                int totalpercent = 0;
                double totalPaid = 0;
                double total = 0;
                try {
                    for (int i = 0; i < BillAdapter.currentBill.getItems().size(); i++) {
                        totalpercent = 0;
                        for (Member m : BillAdapter.currentBill.getMembers()) {
                            totalpercent += m.getMemItems().get(i).getPercentage();
                        }
                        if (totalpercent != (int) 100) {
                            flag = false;
                            break;
                        }
                    }
                } catch (Exception e) {
                    Toast.makeText(getBaseContext(), "Error: Please set up member(s) data by clicking VIEW!", Toast.LENGTH_SHORT).show();
                }
                for (Member m : BillAdapter.currentBill.getMembers()) {
                    totalPaid += m.getPaidAmount();
                }
                for (Item i : BillAdapter.currentBill.getItems()) {
                    total += i.getAmount();
                }
                if ((total == totalPaid) && flag) {
                    Toast.makeText(getBaseContext(), "Saved!", Toast.LENGTH_SHORT).show();
                    //Save data of current bill in db.
                    Bill temp = MyApplication.getDb().getDao().billWithId(BillAdapter.currentBill.id);
                    if (temp == null)
                        MyApplication.getDb().getDao().insertBill(BillAdapter.currentBill);
                    else
                        MyApplication.getDb().getDao().updateBill(BillAdapter.currentBill);
                } else if (!flag)
                    Toast.makeText(getBaseContext(), "Error: Member(s) data not correct!", Toast.LENGTH_SHORT).show();
                else if (total != totalPaid)
                    Toast.makeText(getBaseContext(), "Error: Paid Amount does not match with Item(s) Amount!", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return null;
                case 1:
                    return null;
                case 2:
                    return null;
            }
            return "";
        }

        @Override
        public Fragment getItem(int i) {
            switch (i) {
                case 0:
                    return new BillDesc();
                case 1:
                    return new BillItems();
                case 2:
                    return new BillMembers();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 3;
        }
    }
}
