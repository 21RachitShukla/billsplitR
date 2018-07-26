package rs21.billsplitr.Activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.Fade;

import android.view.View;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;

import rs21.billsplitr.Adapters.BillAdapter;
import rs21.billsplitr.Adapters.MemAdapter;
import rs21.billsplitr.Adapters.MemDetailAdapter;
import rs21.billsplitr.POJOs.Item;
import rs21.billsplitr.R;

public class MemDetailActivity extends AppCompatActivity {
    double total = 0;
    MemDetailAdapter memDetailAdapter;
    RecyclerView rvList;
    TextView pay;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memdetail);
        Intent i = getIntent();
        MobileAds.initialize(this, "ca-app-pub-6464793630486269~5561755973");
        AdView mAdView = findViewById(R.id.adView1);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Fade fade = new Fade();
            fade.setDuration(1000);
            getWindow().setEnterTransition(fade);
        }
        if (!MemAdapter.currentMember.status || MemAdapter.currentMember.getMemItems().size() != BillAdapter.currentBill.getItems().size()) {
            ArrayList<Item> items = new ArrayList<>();
            for (Item item : BillAdapter.currentBill.getItems()) {
                Item temp = new Item(item.getName(), item.getPrice(), item.getQuantity(), item.getAmount(), 100 / BillAdapter.currentBill.getMembers().size());
                items.add(temp);
            }
            MemAdapter.currentMember.setMemItems(items);
            BillAdapter.currentBill.status = true;
            MemAdapter.currentMember.status = true;
        }
        for (Item curr : MemAdapter.currentMember.getMemItems()) {
            total += (curr.getAmount() * curr.getPercentage()) / 100;
        }
        setTitle(MemAdapter.currentMember.getName());
        memDetailAdapter = new MemDetailAdapter(this);
        rvList = findViewById(R.id.rv);
        rvList.setAdapter(memDetailAdapter);
        rvList.setLayoutManager(new LinearLayoutManager(this));
        pay = findViewById(R.id.pay);
        if (total > MemAdapter.currentMember.getPaidAmount()) {
            pay.setText("PAY : " + String.valueOf(Math.abs(MemAdapter.currentMember.getPaidAmount() - total)));
        } else {
            pay.setText("COLLECT : " + String.valueOf(Math.abs(MemAdapter.currentMember.getPaidAmount() - total)));
        }
        memDetailAdapter.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        rvList.setVisibility(View.GONE);
        super.onBackPressed();
    }
}