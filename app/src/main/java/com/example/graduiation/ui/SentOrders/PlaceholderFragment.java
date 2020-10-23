package com.example.graduiation.ui.SentOrders;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.graduiation.R;
import com.example.graduiation.ui.Adapters.CurrentOrdersRecyclerAddapter;
import com.example.graduiation.ui.LegacyData.OrderModel;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;


/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {


    private static final String ARG_SECTION_NUMBER = "section_number";

    private SentOrdersViewModel pageViewModel;
    private static final String TAG = "PlaceholderFragment";
    private RecyclerView currentOrdersRecyclerView;
    private CurrentOrdersRecyclerAddapter adapter;


    public static PlaceholderFragment newInstance(int index, String hey) {

        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        bundle.putString("s", hey);

        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_sent_orders_place_holder, container, false);
        currentOrdersRecyclerView = root.findViewById(R.id.current_orders_recycler_view);
        pageViewModel = ViewModelProviders.of(this).get(SentOrdersViewModel.class);
        pageViewModel.getMutableLiveDataForOrdeRModelList(FirebaseAuth.getInstance().getUid()).observe(this, new Observer<ArrayList<OrderModel>>() {
            @Override
            public void onChanged(ArrayList<OrderModel> orderModels) {
                adapter = new CurrentOrdersRecyclerAddapter(orderModels);
                currentOrdersRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                currentOrdersRecyclerView.setAdapter(adapter);

            }
        });


        return root;
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e(TAG, "onStop: ");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy: ");
    }
}