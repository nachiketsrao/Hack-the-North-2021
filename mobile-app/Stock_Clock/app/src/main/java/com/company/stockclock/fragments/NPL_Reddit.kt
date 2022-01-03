package com.company.stockclock.fragments

import android.graphics.Typeface
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.company.stockclock.R
import com.company.stockclock.requestsJava
import java.util.*


class NPL_Reddit : AppCompatActivity() {

    lateinit var query_box : EditText
    lateinit var ans_box : TextView
    lateinit var stock: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_npl_reddit)

        val extras = intent.extras
        if (extras != null) {
            stock = extras.getString("stock").toString()
            //The key argument here must match that used in the other activity
        }
    }
    fun queryNLP(view: View) {
        query_box = findViewById(R.id.query_box)
        ans_box = findViewById(R.id.answer_box)

        ans_box.setMovementMethod(ScrollingMovementMethod())

        val tv1 = findViewById(R.id.textView7) as TextView
        val tv2 = findViewById(R.id.textView4) as TextView
        tv1.setTypeface(null, Typeface.BOLD)
        tv2.setTypeface(null, Typeface.BOLD)

        // http requests: (trial)
        val keys = ArrayList<String>()
        val values = ArrayList<String>()

        keys.add("stock")
        keys.add("query")
        values.add(stock)
        values.add(query_box.text.toString())

        //Log.d("htn", String.valueOf(position));
        val http_obj = requestsJava()
        http_obj.makeRequest({ result -> //Toast.makeText(context, "hello", Toast.LENGTH_LONG).show();
            ans_box.text = result.getString("answer")

            //Toast.makeText(this, close, Toast.LENGTH_LONG).show();
        }, this, "/query-nlp", keys, values)
    }
}