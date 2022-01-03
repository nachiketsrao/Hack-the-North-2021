
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
import com.company.stockclock.requestsJava;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class fragmentQuickStats extends Fragment {

    public static fragmentQuickStats newInstance()
    {
        return new fragmentQuickStats();
    }

    private ImageView imageViewLogo;
    private ProgressBar progressBarLogo;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        //getting required values so that specific stock/company can be searched for
        String image_name = null;
        String company_name = null;
        String stock_name = null;

        if (getArguments() != null) {
            image_name = getArguments().getString("image_name");
            company_name = getArguments().getString("company_name");
            stock_name = getArguments().getString("stock_name");
        }

        //fragment_quick_stats is the fragment now
        //get graph instead over here:
       View view = inflater.inflate(R.layout.fragment_quick_stats, container, false);

       imageViewLogo = view.findViewById(R.id.imageViewLogo);
       progressBarLogo = view.findViewById(R.id.progressBarLogo);

       Toast.makeText(getContext(), image_name, Toast.LENGTH_LONG);

       //graph comes here:
        Picasso.get().load(image_name)
                .into(imageViewLogo, new Callback() {
                    @Override
                    public void onSuccess() {

                        progressBarLogo.setVisibility(View.INVISIBLE);

                    }

                    @Override
                    public void onError(Exception e) {

                        Toast.makeText(getActivity(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                        progressBarLogo.setVisibility(View.INVISIBLE);

                    }
                });



        //here get details from server and display in the textView
        // http requests: (trial)
        ArrayList<String> keys = new ArrayList<>();
        ArrayList<String> values = new ArrayList<>();
        keys.add("stock");
        values.add(stock_name);

        //Toast.makeText(context, model.getCategoryName(), Toast.LENGTH_LONG).show();

        requestsJava http_obj = new requestsJava();
        http_obj.makeRequest(new requestsJava.VolleyCallback() {
            @Override
            public void onSuccess(JSONObject result) throws JSONException {
                //Toast.makeText(context, "hello", Toast.LENGTH_LONG).show();
                String close = result.getString("Close");
                String dividends = result.getString("Dividends");
                String high = result.getString("High");
                String low = result.getString("Low");
                String open = result.getString("Open");
                String stock_splits = result.getString("Stock Splits");


            }
        }, getContext(), "/latest-all", keys, values );

        return view;
    }
}
