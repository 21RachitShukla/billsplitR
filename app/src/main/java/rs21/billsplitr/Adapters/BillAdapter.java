package rs21.billsplitr.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;

import rs21.billsplitr.Activities.BillActivity;
import rs21.billsplitr.Activities.MainActivity;
import rs21.billsplitr.MyApplication;
import rs21.billsplitr.POJOs.Bill;
import rs21.billsplitr.R;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class BillAdapter extends RecyclerView.Adapter<BillAdapter.ViewHolder> {
    ArrayList<Bill> bills;
    Context ctx;
    public static Bill currentBill;
    InterstitialAd interstitialAd;

    public BillAdapter(Context ctx, ArrayList<Bill> al) {
        this.ctx = ctx;
        this.bills = al;
    }

    @NonNull
    @Override
    public BillAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        ViewHolder vh;
        LayoutInflater li = (LayoutInflater) (ctx.getSystemService(LAYOUT_INFLATER_SERVICE));
        v = li.inflate(R.layout.bill_item, parent, false);
        vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final BillAdapter.ViewHolder holder, int position) {
        MobileAds.initialize(ctx, "ca-app-pub-6464793630486269~5561755973");
        interstitialAd = new InterstitialAd(ctx);
        interstitialAd.setAdUnitId(ctx.getString(R.string.interstitial));
        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                startGame();
            }
        });
        currentBill = bills.get(holder.getAdapterPosition());
        String name = currentBill.getName();
        holder.item.setText(name);

        final View dialogView = LayoutInflater.from(ctx).inflate(R.layout.confirm_layout, null, true);

        final AlertDialog alertDialog = new AlertDialog.Builder(ctx)
                .setView(dialogView).setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Bill temp = MyApplication.getDb().getDao().billWithId(BillAdapter.currentBill.id);
                        if (temp != null)
                            MyApplication.getDb().getDao().deleteBill(BillAdapter.currentBill);
                        bills.remove(holder.getAdapterPosition());
                        notifyDataSetChanged();
                    }
                }).create();

        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInterstitial();
                currentBill = bills.get(holder.getAdapterPosition());
                Intent i = new Intent(ctx, BillActivity.class);
                ctx.startActivity(i);
            }
        });
        holder.item.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                alertDialog.show();
                showInterstitial();
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return bills.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView item;

        public ViewHolder(View itemView) {
            super(itemView);
            item = itemView.findViewById(R.id.name);
        }
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
