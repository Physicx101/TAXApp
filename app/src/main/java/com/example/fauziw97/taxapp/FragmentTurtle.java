package com.example.fauziw97.taxapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.fauziw97.taxapp.Adapter.SpeciesAdapter;
import com.example.fauziw97.taxapp.Model.SpeciesModel;
import com.example.fauziw97.taxapp.Util.Measure;
import com.example.fauziw97.taxapp.View.GridSpacingItemDecoration;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Fauziw97 on 9/12/17.
 */

public class FragmentTurtle extends Fragment {

    DatabaseReference mRef = FirebaseDatabase.getInstance().getReference();
    private RecyclerView.Adapter mAdapter;
    private List<SpeciesModel> mSpeciesModels;
    private GridSpacingItemDecoration spacingDecoration;

    @BindView(R.id.recycler_view_turtle)
    RecyclerView mRecyclerView;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_turtle, container, false);
        ButterKnife.bind(this, view);

        spacingDecoration = new GridSpacingItemDecoration(2, Measure.pxToDp(16, getContext()), true);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(spacingDecoration);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));


        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        mSpeciesModels = new ArrayList<>();
        retrieveData();


    }

    private void retrieveData() {

        mProgressBar.setVisibility(View.VISIBLE);
        DatabaseReference turtleData = mRef.child("Amfirep");
        Query turtleList = turtleData.orderByChild("Jenis").equalTo("Turtle");
        turtleList.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    SpeciesModel listSpeciesModel = new SpeciesModel(
                            snapshot.child("ImgOverall").getValue(String.class),
                            snapshot.child("NamaSpesies").getValue(String.class),
                            snapshot.child("StatusKonservasi").getValue(String.class));


                    mSpeciesModels.add(listSpeciesModel);

                    mAdapter = new SpeciesAdapter(mSpeciesModels, getActivity());
                    mRecyclerView.setAdapter(mAdapter);
                }
                mProgressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    @OnClick(R.id.fabSpecies)
    public void addTurtle(View view) {
        addSpecies.JenisHewan = "Turtle";
        startActivity(new Intent(getActivity(), addSpecies.class));

    }


}
