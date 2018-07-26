package rs21.billsplitr.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetView;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;

import rs21.billsplitr.Adapters.BillAdapter;
import rs21.billsplitr.MyApplication;
import rs21.billsplitr.POJOs.Bill;
import rs21.billsplitr.POJOs.Item;
import rs21.billsplitr.POJOs.Member;
import rs21.billsplitr.R;

public class MainActivity extends AppCompatActivity {
    public static ArrayList<Bill> bills = new ArrayList<>();
    BillAdapter billAdapter;
    RecyclerView rvList;
    InterstitialAd interstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MobileAds.initialize(this, "ca-app-pub-6464793630486269~5561755973");
        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId(getString(R.string.interstitial));
        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                startGame();
            }
        });
        rvList = findViewById(R.id.rv);
        rvList.setLayoutManager(new LinearLayoutManager(this));
        if (MyApplication.getDb().getDao().getAllBills().size() != 0) {
            bills = (ArrayList<Bill>) MyApplication.getDb().getDao().getAllBills();
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Slide slide = new Slide();
            slide.setDuration(1000);
            getWindow().setExitTransition(slide);
        }
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        billAdapter = new BillAdapter(this, bills);
        rvList.setAdapter(billAdapter);

        final View dialogView = LayoutInflater.from(this).inflate(R.layout.bill_name, null, true);
        final AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setView(dialogView)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        EditText name = dialogView.findViewById(R.id.name);
                        if (name.getText().toString().length() < 3) {
                            Snackbar.make(findViewById(R.id.main1), "Warning: Title too short!", Snackbar.LENGTH_SHORT).show();
                        } else if (name.getText().toString().length() > 15) {
                            Snackbar.make(findViewById(R.id.main1), "Warning: Title too long!", Snackbar.LENGTH_SHORT).show();
                        }
                        ((AlertDialog) dialogInterface).getButton(i).setVisibility(View.VISIBLE);
                        Item item = new Item();
                        Member member = new Member();
                        ArrayList<Item> items = new ArrayList<>();
                        items.add(item);
                        ArrayList<Member> members = new ArrayList<>();
                        members.add(member);
                        Bill temp = new Bill(name.getText().toString(), "", items, members);
                        bills.add(temp);
                        if (name.getText().toString().length() >= 3 && name.getText().toString().length() <= 15)
                            Snackbar.make(findViewById(R.id.main1), "Bill Added!", Snackbar.LENGTH_SHORT).show();
                        MyApplication.getDb().getDao().insertBill(temp);
                        billAdapter.notifyItemInserted(bills.size() - 1);
                    }
                })
                .create();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.show();
                showInterstitial();
            }
        });

        TapTargetView.showFor(this,
                TapTarget.forView(findViewById(R.id.fab), "Add Bills!", "Click to add new bills...")
                        .outerCircleColor(R.color.colorPrimaryDark1)
                        .outerCircleAlpha(0.96f)
                        .targetCircleColor(R.color.colorAccent1)
                        .titleTextSize(20)
                        .titleTextColor(R.color.white)
                        .descriptionTextSize(10)
                        .descriptionTextColor(R.color.white)
                        .textColor(R.color.white)
                        .dimColor(R.color.black)
                        .drawShadow(true)
                        .cancelable(true)
                        .tintTarget(true)
                        .transparentTarget(true)
                        .icon(getResources().getDrawable(R.drawable.ic_add))
                        .targetRadius(60),
                new TapTargetView.Listener() {
                    @Override
                    public void onTargetClick(TapTargetView view) {
                        super.onTargetClick(view);
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_help:
                Intent i = new Intent(this, HelpActivity.class);
                startActivity(i);
                break;
            case R.id.action_about:
                Intent intent = new Intent(this, AboutActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void startGame() {
        if (!interstitialAd.isLoading() && !interstitialAd.isLoaded()) {
            AdRequest adRequest = new AdRequest.Builder().build();
            interstitialAd.loadAd(adRequest);
        }
    }

    private void showInterstitial() {
        if (interstitialAd != null && interstitialAd.isLoaded()) {
            interstitialAd.show();
        } else {
            if (!interstitialAd.isLoading() && !interstitialAd.isLoaded()) {
                AdRequest adRequest = new AdRequest.Builder().build();
                interstitialAd.loadAd(adRequest);
            }
        }
    }
}

