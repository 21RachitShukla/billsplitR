package rs21.billsplitr.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import rs21.billsplitr.Activities.MemDetailActivity;
import rs21.billsplitr.POJOs.Member;
import rs21.billsplitr.R;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class MemAdapter extends RecyclerView.Adapter<MemAdapter.ViewHolder> {
    public static Member currentMember;
    Context ctx;

    public MemAdapter(Context ctx) {
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public MemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        ViewHolder vh;
        LayoutInflater li = (LayoutInflater) (ctx.getSystemService(LAYOUT_INFLATER_SERVICE));
        v = li.inflate(R.layout.rv_mem, parent, false);
        vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final MemAdapter.ViewHolder holder, final int position) {
        currentMember = BillAdapter.currentBill.getMembers().get(holder.getAdapterPosition());
        final View dialogView = LayoutInflater.from(ctx).inflate(R.layout.confirm_layout, null, true);
        if (BillAdapter.currentBill.status) {
            holder.name.setText(currentMember.getName());
            holder.paidAmount.setText("" + currentMember.getPaidAmount());
        }
        holder.name.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    currentMember.setName(holder.name.getText().toString());
                }

            }
        });

        holder.paidAmount.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus && !(holder.paidAmount.getText().toString()).equals("")) {
                    currentMember.setPaidAmount(Double.parseDouble(holder.paidAmount.getText().toString()));
                }

            }
        });

        final AlertDialog alertDialog = new AlertDialog.Builder(ctx)
                .setView(dialogView).setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        BillAdapter.currentBill.getMembers().remove(holder.getAdapterPosition());
                        notifyDataSetChanged();
                    }
                }).create();
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //currentMember = BillAdapter.currentBill.getMembers().get(holder.getAdapterPosition());
                Intent i = new Intent(ctx, MemDetailActivity.class);
                ctx.startActivity(i);
            }
        });
        holder.item.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //currentMember = BillAdapter.currentBill.getMembers().get(holder.getAdapterPosition());
                alertDialog.show();
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return BillAdapter.currentBill.getMembers().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView item;
        EditText name, paidAmount;
        Button view;

        public ViewHolder(View itemView) {
            super(itemView);
            item = itemView.findViewById(R.id.item);
            name = itemView.findViewById(R.id.nameMem);
            paidAmount = itemView.findViewById(R.id.paidAmount);
            view = itemView.findViewById(R.id.view);
        }
    }
}