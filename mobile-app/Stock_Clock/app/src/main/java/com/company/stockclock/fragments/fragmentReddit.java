package com.company.stockclock.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.company.stockclock.R;

public class fragmentReddit extends Fragment {

    public static fragmentReddit newInstance()
    {
        return new fragmentReddit();
    }

    private ImageView imageViewItaly;
    private ProgressBar progressBarItaly;
    private EditText editText;
    private TextView textView;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_reddit, container, false);

        String company_name = getArguments().getString("company_name");
        String image_name = getArguments().getString("image_name");
        String stock_name = getArguments().getString("stock_name");

        editText = view.findViewById(R.id.editTextReddit);


        /*
        Picasso.get().load("https://p.kindpng.com/picc/s/208-2084626_flag-hd-png-download.png")
                .into(imageViewItaly, new Callback() {
                    @Override
                    public void onSuccess() {

                        progressBarItaly.setVisibility(View.INVISIBLE);

                    }

                    @Override
                    public void onError(Exception e) {

                        Toast.makeText(getActivity(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                        progressBarItaly.setVisibility(View.INVISIBLE);

                    }
                });
    */
       return view;
    }
}
