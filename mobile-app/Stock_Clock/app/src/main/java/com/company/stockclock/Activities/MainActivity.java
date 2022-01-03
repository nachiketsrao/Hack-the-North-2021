package com.company.stockclock.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.company.stockclock.AddCompanyActivity;
import com.company.stockclock.adapters.AdapterClass;
import com.company.stockclock.ModelClass;
import com.company.stockclock.R;
import com.company.stockclock.requestsJava;

import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<ModelClass> arrayList;
    private AdapterClass adapter;
    Button addCompany;

    public ArrayList<String> companyNameList = new ArrayList<>(); //this list will store the names of all the companies the user has invested in
    //store the list of all companies locally as of now.
    //make a feature to add company names and add a card in the recycler view section.
    //run a loop or something over an array of modelclass so that code needn't be written for each company

    public ArrayList<String> imageNames = new ArrayList<>(); //this stores the names/links of all the image names.

    public ArrayList<String> stockNames = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("htn", "----->"+"hello!");

        /*
        // http requests: (trial)
        ArrayList<String> keys = new ArrayList<>();
        ArrayList<String> values = new ArrayList<>();
        keys.add("aaa");
        keys.add("bbb");
        values.add("111");
        values.add("222");


        requestsJava http_obj = new requestsJava();
        http_obj.makeRequest(new requestsJava.VolleyCallback() {
            @Override
            public void onSuccess(JSONObject result) {
                Toast.makeText(MainActivity.this, result.toString(), Toast.LENGTH_LONG).show();
            }
        }, getApplicationContext(), "/argument-test", keys, values );

         */

        recyclerView = findViewById(R.id.recylerView);

        //defining addCompany button:
        addCompany = findViewById(R.id.buttonAdd);

        //  recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        // recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        arrayList = new ArrayList<>();

        stockNames = StockFileHelper.readData(this);
        StockFileHelper.writeData(stockNames, getApplicationContext());

        // image names list stuff starts ---->

        imageNames = ImageNameHelper.readData(this);
        ImageNameHelper.writeData(imageNames, getApplicationContext());

        //adding names to the array with company names:


        // company names list stuff starts ---->

        companyNameList = NameFileHelper.readData(this); //this will read data and send it to the arrayList if there is an data
        NameFileHelper.writeData(companyNameList, getApplicationContext());
        //Toast.makeText(this, "Test", Toast.LENGTH_SHORT).show();
        //trying a for loop to reduce number of lines to be written for the modelclass array:
        for (int i = 0; i<imageNames.size(); i++){

            arrayList.add(new ModelClass(imageNames.get(i), companyNameList.get(i), null, stockNames.get(i) ));
            //TODO
            // the third argument in the above thing must be changed after integration with server

        }

        /*
        //creating objects of the model class:
        ModelClass modelClass1 = new ModelClass("apple", "Apple");
        ModelClass modelClass2 = new ModelClass("microsoft", "Microsoft");
        ModelClass modelClass3 = new ModelClass("tesla", "Tesla");
        ModelClass modelClass4 = new ModelClass("amazon", "Amazon");

        arrayList.add(modelClass1);
        arrayList.add(modelClass2);
        arrayList.add(modelClass3);
        arrayList.add(modelClass4);
        */

        adapter = new AdapterClass(arrayList, this);
        recyclerView.setAdapter(adapter);

        addCompany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MainActivity.this, AddCompanyActivity.class);
                startActivity(i);
                finish();

            }
        });



    }
}