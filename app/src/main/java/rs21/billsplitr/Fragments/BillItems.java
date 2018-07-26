package rs21.billsplitr.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import rs21.billsplitr.Adapters.BillAdapter;
import rs21.billsplitr.Adapters.ItemAdapter;
import rs21.billsplitr.POJOs.Item;
import rs21.billsplitr.R;

public class BillItems extends Fragment {
    public static BillItems newInstance() {

        Bundle args = new Bundle();
        BillItems fragment = new BillItems();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_bill_items, container, false);

        final ItemAdapter l = new ItemAdapter(getContext());

        RecyclerView rvList = view.findViewById(R.id.rvItems);
        rvList.setAdapter(l);
        rvList.setLayoutManager(new LinearLayoutManager(getContext()));

        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BillAdapter.currentBill.getItems().add(new Item());
                Snackbar.make(view, "Item Added!", Snackbar.LENGTH_SHORT).show();
                l.notifyItemInserted(BillAdapter.currentBill.getItems().size() - 1);
            }
        });
        return view;
    }
}