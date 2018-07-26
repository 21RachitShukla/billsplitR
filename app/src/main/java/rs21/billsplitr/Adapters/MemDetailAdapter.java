package rs21.billsplitr.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

import rs21.billsplitr.R;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class MemDetailAdapter extends RecyclerView.Adapter<MemDetailAdapter.ViewHolder> {
    Context ctx;

    public MemDetailAdapter(Context ctx) {
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public MemDetailAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        ViewHolder vh;
        LayoutInflater li = (LayoutInflater) (ctx.getSystemService(LAYOUT_INFLATER_SERVICE));
        v = li.inflate(R.layout.rv_detail, parent, false);
        vh = new ViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final MemDetailAdapter.ViewHolder holder, final int position) {
        holder.item.setText(MemAdapter.currentMember.getMemItems().get(holder.getAdapterPosition()).getName());
        holder.percent.setProgress((int) MemAdapter.currentMember.getMemItems().get(holder.getAdapterPosition()).getPercentage());
        holder.percent.setOnProgressChangeListener(new DiscreteSeekBar.OnProgressChangeListener() {
            @Override
            public void onProgressChanged(DiscreteSeekBar seekBar, int value, boolean fromUser) {
                MemAdapter.currentMember.getMemItems().get(holder.getAdapterPosition()).setPercentage(value);
            }

            @Override
            public void onStartTrackingTouch(DiscreteSeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(DiscreteSeekBar seekBar) {
            }
        });
    }


    @Override
    public int getItemCount() {
        return MemAdapter.currentMember.getMemItems().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView item;
        DiscreteSeekBar percent;

        public ViewHolder(View itemView) {
            super(itemView);
            item = itemView.findViewById(R.id.nameDet);
            percent = itemView.findViewById(R.id.percent);
        }
    }
}