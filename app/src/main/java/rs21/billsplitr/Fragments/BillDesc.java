package rs21.billsplitr.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rengwuxian.materialedittext.MaterialEditText;

import rs21.billsplitr.Adapters.BillAdapter;
import rs21.billsplitr.POJOs.Item;
import rs21.billsplitr.R;

public class BillDesc extends Fragment {
    public static BillDesc newInstance() {

        Bundle args = new Bundle();
        BillDesc fragment = new BillDesc();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bill_desc, container, false);
        final MaterialEditText desc = view.findViewById(R.id.desc);
        TextView totalText = view.findViewById(R.id.billName);
        double total = 0;
        for (Item item : BillAdapter.currentBill.getItems()) {
            total += item.getAmount();
        }
        totalText.setText("Total Amount : " + total);
        if (BillAdapter.currentBill.status) {
            desc.setText(BillAdapter.currentBill.getDesc());
        }
        desc.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    BillAdapter.currentBill.setDesc(desc.getText().toString());
                }

            }
        });

        return view;
    }
}