package com.company.stockclock.Activities

import android.content.Intent
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.company.stockclock.R
import com.company.stockclock.fragments.NPL_Reddit
import com.company.stockclock.requestsJava
import com.company.stockclock.requestsJava.VolleyCallback
import org.json.JSONException
import org.json.JSONObject
import java.util.ArrayList

class DisplayDetails : AppCompatActivity() {

    lateinit var patientID: String
    lateinit var field_name: TextView
    lateinit var field_age: TextView
    lateinit var field_disease: TextView
    lateinit var field_symptoms: TextView
    lateinit var field_help: TextView
    lateinit var field_attacks: TextView
    lateinit var field_allergies: TextView
    lateinit var stock: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_details2)

        val extras = intent.extras
        if (extras != null) {
            stock = extras.getString("stock").toString()
            //The key argument here must match that used in the other activity
        }

        field_name = findViewById(R.id.field_name)
        field_age = findViewById(R.id.field_age)
        field_disease = findViewById(R.id.field_disease)
        field_symptoms = findViewById(R.id.field_symptoms)
        field_help = findViewById(R.id.field_help)
        field_attacks = findViewById(R.id.field_attacks)
        field_allergies = findViewById(R.id.field_allergies)

        val tv0 = findViewById(R.id.textView6) as TextView
        val tv1 = findViewById(R.id.textView7) as TextView
        val tv2 = findViewById(R.id.textView8) as TextView
        val tv3 = findViewById(R.id.textView9) as TextView
        val tv4 = findViewById(R.id.textView10) as TextView
        val tv5 = findViewById(R.id.textView11) as TextView
        val tv6 = findViewById(R.id.textView13) as TextView

        tv0.setTypeface(null, Typeface.BOLD)
        tv1.setTypeface(null, Typeface.BOLD)
        tv2.setTypeface(null, Typeface.BOLD)
        tv3.setTypeface(null, Typeface.BOLD)
        tv4.setTypeface(null, Typeface.BOLD)
        tv5.setTypeface(null, Typeface.BOLD)
        tv6.setTypeface(null, Typeface.BOLD)


        //here get details from server and display in the textView
        // http requests: (trial)
        val keys = ArrayList<String>()
        val values = ArrayList<String>()

        keys.add("stock")
        values.add(stock)

        //Log.d("htn", String.valueOf(position));
        val http_obj = requestsJava()
        http_obj.makeRequest({ result -> //Toast.makeText(context, "hello", Toast.LENGTH_LONG).show();
            val close = result.getString("Close")
            val dividends = result.getString("Dividends")
            val high = result.getString("High")
            val low = result.getString("Low")
            val open = result.getString("Open")
            val stock_splits = result.getString("Stock Splits")

            field_name.text = close
            field_age.text = dividends
            field_disease.text = high
            field_symptoms.text = low
            field_help.text = open
            field_attacks.text = stock_splits
            field_allergies.text = result.getString("redditView")

            //Toast.makeText(this, close, Toast.LENGTH_LONG).show();
        }, this, "/latest-all", keys, values)
    }

    fun goToNPL(view: View) {
        val intent = Intent(this, NPL_Reddit::class.java).apply {
            putExtra("stock", stock)
        }
        startActivity(intent)
    }
}