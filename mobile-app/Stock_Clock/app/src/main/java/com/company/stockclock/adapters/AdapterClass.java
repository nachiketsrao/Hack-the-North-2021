package com.company.stockclock.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.company.stockclock.Activities.DisplayDetails;
import com.company.stockclock.ModelClass;
import com.company.stockclock.R;
import com.company.stockclock.requestsJava;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AdapterClass extends RecyclerView.Adapter<AdapterClass.CardViewHolder>{

    private ArrayList<ModelClass> modelList;
    private Context context;

    public AdapterClass(ArrayList<ModelClass> modelList, Context context) {
        this.modelList = modelList;
        this.context = context;
    }

    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //we mention which card holder design we wanna use, here

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_design, parent, false);

        return new CardViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull AdapterClass.CardViewHolder holder, int position) {

        //display data here
        ModelClass model = modelList.get(position);
        holder.textViewSplash.setText(model.getCategoryName());

        //here below the image is displayed:
        /*
        holder.imageViewSplash.setImageResource(context.getResources()
                .getIdentifier(model.getImageName(), "", context.getPackageName()));

         */

        // http requests: (trial)
        ArrayList<String> keys = new ArrayList<>();
        ArrayList<String> values = new ArrayList<>();
        keys.add("stock");
        values.add(model.getStockName());

        //Toast.makeText(context, model.getCategoryName(), Toast.LENGTH_LONG).show();

        requestsJava http_obj = new requestsJava();
        http_obj.makeRequest(new requestsJava.VolleyCallback() {
            @Override
            public void onSuccess(JSONObject result) throws JSONException {
                //Toast.makeText(context, "hello", Toast.LENGTH_LONG).show();
                holder.prevClose.setText(result.getString("prev-close"));
            }
        }, context, "/latest-close", keys, values );

        Picasso.get().load(model.getImageName()).into(holder.imageViewSplash);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, DisplayDetails.class);
                intent.putExtra("stock", model.getStockName());
                context.startActivity(intent);

                //MainActivity mainActivity = new MainActivity();
                /*

                ArrayList<String> companyNameList;
                ArrayList<String> imageNames;
                ArrayList<String> stockNames;

                companyNameList = NameFileHelper.readData(context);
                imageNames = ImageNameHelper.readData(context);
                stockNames = StockFileHelper.readData(context);


                intent.putExtra("company_name", companyNameList.get(position)); // "name" is the keyword
                intent.putExtra("image_name", imageNames.get(position));
                intent.putExtra("stock_name", stockNames.get(position));
                context.startActivity(intent);
                */

                /*
                if (position == 0)
                {
                    Intent intent = new Intent(context, GeneralScreen2Activity.class);
                    context.startActivity(intent);
                }
                if (position == 1)
                {
                    Intent intent = new Intent(context, LeadersActivity.class);
                    context.startActivity(intent);
                }
                if (position == 2)
                {
                    Intent intent = new Intent(context, MuseumsActivity.class);
                    context.startActivity(intent);
                }
                if (position == 3)
                {
                    Intent intent = new Intent(context, WondersActivity.class);
                    context.startActivity(intent);
                }
                */
            }
        });

    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class CardViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageViewSplash;
        private TextView textViewSplash;
        private CardView cardView;
        private TextView prevClose;

        public CardViewHolder(@NonNull View itemView) {
            super(itemView);

            imageViewSplash = itemView.findViewById(R.id.imageViewSplash);
            textViewSplash = itemView.findViewById(R.id.textViewSplash);
            cardView = itemView.findViewById(R.id.cardView);
            prevClose = itemView.findViewById(R.id.textViewPrevClose);

        }


    }

}
