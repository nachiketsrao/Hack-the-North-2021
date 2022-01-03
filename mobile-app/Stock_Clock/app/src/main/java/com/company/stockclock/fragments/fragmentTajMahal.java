package com.company.stockclock.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.company.stockclock.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class fragmentTajMahal extends Fragment {

    public static fragmentTajMahal newInstance()
    {
        return new fragmentTajMahal();
    }

    private ImageView imageViewTajMahal;
    private ProgressBar progressBarTajMahal;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

       View view = inflater.inflate(R.layout.fragment_taj_mahal, container, false);

        imageViewTajMahal = view.findViewById(R.id.imageViewTajMahal);
        progressBarTajMahal = view.findViewById(R.id.progressBarTajMahal);

        Picasso.get().load("https://images.indianexpress.com/2019/05/taj-mahal-amp.jpg")
                .into(imageViewTajMahal, new Callback() {
                    @Override
                    public void onSuccess() {

                        progressBarTajMahal.setVisibility(View.INVISIBLE);

                    }

                    @Override
                    public void onError(Exception e) {

                        Toast.makeText(getActivity(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                        progressBarTajMahal.setVisibility(View.INVISIBLE);

                    }
                });

       return view;
    }
}
