
package com.company.stockclock.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.company.stockclock.Activities.ImageNameHelper;
import com.company.stockclock.Activities.StockFileHelper;
import com.company.stockclock.R;
import com.company.stockclock.requestsJava;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FragmentStats extends Fragment {

    TextView showDetails;

    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        return inflater.inflate(R.layout.fragment_quick_stats, parent, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Setup any handles to view objects here
        // EditText etFoo = (EditText) view.findViewById(R.id.etFoo);
        /*
        String company_name = getArguments().getString("company_name");
        String image_name = getArguments().getString("image_name");

         */

        int position = getArguments().getInt("position");

        ArrayList <String> imageNames = new ArrayList<>();
        imageNames = ImageNameHelper.readData(getContext());

        showDetails = view.findViewById(R.id.textViewQuickStats);

        showDetails.setText("Text will be displayed here");

        //here get details from server and display in the textView
        // http requests: (trial)
        ArrayList<String> keys = new ArrayList<>();
        ArrayList<String> values = new ArrayList<>();
        ArrayList<String> stockNames = new ArrayList<>();

        stockNames = StockFileHelper.readData(getContext());

        keys.add("stock");
        values.add(stockNames.get(position));

        Toast.makeText(view.getContext(), "Hi", Toast.LENGTH_LONG).show();
        Toast.makeText(getContext(), String.valueOf(position), Toast.LENGTH_LONG).show();
        //Log.d("htn", String.valueOf(position));

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


    }



}