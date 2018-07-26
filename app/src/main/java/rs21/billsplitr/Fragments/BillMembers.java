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

import java.util.ArrayList;

import rs21.billsplitr.Adapters.BillAdapter;
import rs21.billsplitr.Adapters.MemAdapter;
import rs21.billsplitr.MyApplication;
import rs21.billsplitr.POJOs.Bill;
import rs21.billsplitr.POJOs.Member;
import rs21.billsplitr.R;

public class BillMembers extends Fragment {
    public static BillMembers newInstance() {

        Bundle args = new Bundle();
        BillMembers fragment = new BillMembers();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_bill_members, container, false);
        final MemAdapter l = new MemAdapter(getContext());

        RecyclerView rvList = view.findViewById(R.id.rvMems);
        rvList.setAdapter(l);
        rvList.setLayoutManager(new LinearLayoutManager(getContext()));

        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BillAdapter.currentBill.getMembers().add(new Member());
                Snackbar.make(view, "Member Added!", Snackbar.LENGTH_SHORT).show();
                l.notifyItemInserted(BillAdapter.currentBill.getMembers().size() - 1);
            }
        });
        return view;
    }
}