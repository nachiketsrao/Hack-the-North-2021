package com.company.stockclock;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.company.stockclock.Activities.ImageNameHelper;
import com.company.stockclock.Activities.MainActivity;
import com.company.stockclock.Activities.NameFileHelper;
import com.company.stockclock.Activities.StockFileHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AddCompanyActivity extends AppCompatActivity {

    Button buttonAdd;
    EditText newCompany;
    EditText newStock;

    public ArrayList<String> companyNameList = new ArrayList<>();

    public ArrayList<String> imageNames = new ArrayList<>();

    public ArrayList<String> stockNamesList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_company2);

        buttonAdd = findViewById(R.id.buttonAddCompany);
        newCompany = findViewById(R.id.editTextName);
        newStock = findViewById(R.id.editTextStockName);
        // new stock can be used to get stock price from server

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // http requests: (trial)
                ArrayList<String> keys = new ArrayList<>();
                ArrayList<String> values = new ArrayList<>();
                keys.add("stock");
                values.add(newStock.getText().toString());

                requestsJava http_obj = new requestsJava();
                http_obj.makeRequest(new requestsJava.VolleyCallback() {
                    @Override
                    public void onSuccess(JSONObject result) throws JSONException {
                        //Toast.makeText(getApplicationContext(), "hello", Toast.LENGTH_LONG).show();
                        if (result.getString("stock-status").equals("0")) {
                            Toast.makeText(getApplicationContext(), "This stock does not exist", Toast.LENGTH_LONG).show();
                        }
                        else
                            {
                            imageNames = ImageNameHelper.readData(getApplicationContext());
                            imageNames.add("https://logo.clearbit.com/"+newCompany.getText().toString().toLowerCase()+".com");
                            ImageNameHelper.writeData(imageNames, getApplicationContext());

                            companyNameList = NameFileHelper.readData(getApplicationContext()); //this will read data and send it to the arrayList if there is an data
                            companyNameList.add(newCompany.getText().toString());
                            NameFileHelper.writeData(companyNameList, getApplicationContext());

                            stockNamesList = StockFileHelper.readData(getApplicationContext());
                            stockNamesList.add(newStock.getText().toString());
                            StockFileHelper.writeData(stockNamesList, getApplicationContext());

                            Log.d("list", imageNames.toString());
                            Log.d("list 2", companyNameList.toString());

                            Intent i = new Intent(AddCompanyActivity.this, MainActivity.class);
                            startActivity(i);

                            finish();
                        }
                    }
                }, getApplicationContext(), "/add-stock", keys, values );
            }
        });

    }
}