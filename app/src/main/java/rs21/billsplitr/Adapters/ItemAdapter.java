package rs21.billsplitr.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import rs21.billsplitr.POJOs.Item;
import rs21.billsplitr.R;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {
    Context ctx;

    public ItemAdapter(Context ctx) {
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public ItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        ViewHolder vh;
        LayoutInflater li = (LayoutInflater) (ctx.getSystemService(LAYOUT_INFLATER_SERVICE));
        v = li.inflate(R.layout.rv_item, parent, false);
        vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemAdapter.ViewHolder holder, final int position) {
        final Item currentItem = BillAdapter.currentBill.getItems().get(holder.getAdapterPosition());
        final View dialogView = LayoutInflater.from(ctx).inflate(R.layout.confirm_layout, null, true);
        if (BillAdapter.currentBill.status) {
            holder.name.setText(currentItem.getName());
            holder.quantity.setText("" + currentItem.getQuantity());
            holder.price.setText("" + currentItem.getPrice());
        }
        holder.name.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    currentItem.setName(holder.name.getText().toString());
                }

            }
        });

        holder.quantity.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus && !(holder.quantity.getText().toString()).equals("")) {
                    currentItem.setQuantity(Double.parseDouble(holder.quantity.getText().toString()));
                    currentItem.setAmount(currentItem.getPrice() * currentItem.getQuantity());
                }

            }
        });

        holder.price.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus && !(holder.price.getText().toString()).equals("")) {
                    currentItem.setPrice(Double.parseDouble(holder.price.getText().toString()));
                    currentItem.setAmount(currentItem.getPrice() * currentItem.getQuantity());
                }

            }
        });


        final AlertDialog alertDialog = new AlertDialog.Builder(ctx)
                .setView(dialogView).setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        BillAdapter.currentBill.getItems().remove(holder.getAdapterPosition());
                        notifyDataSetChanged();
                    }
                }).create();

        holder.item.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                alertDialog.show();
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return BillAdapter.currentBill.getItems().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView item;
        EditText name, price, quantity;

        public ViewHolder(View itemView) {
            super(itemView);
            item = itemView.findViewById(R.id.item);
            name = itemView.findViewById(R.id.nameItem);
            price = itemView.findViewById(R.id.price);
            quantity = itemView.findViewById(R.id.quantity);
        }
    }
}
