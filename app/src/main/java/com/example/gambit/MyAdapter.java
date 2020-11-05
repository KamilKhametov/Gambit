package com.example.gambit;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gambit.API.ResponseData;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<ResponseData> responseDataList;
    private SharedPreferences preferences;
    private SharedPreferences preferencesPlus;

    public MyAdapter( List<ResponseData> responseDataList ) {
        this.responseDataList=responseDataList;
    }


    @NonNull
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder( @NonNull ViewGroup parent, int viewType ) {
        View view =LayoutInflater.from ( parent.getContext () ).inflate ( R.layout.layout_item, parent, false );
        ViewHolder vh = new ViewHolder ( view );
        if(preferences == null){
            preferences = parent.getContext ().getSharedPreferences ( "NICE", Context.MODE_PRIVATE );
        }
        if (preferencesPlus == null){
            preferencesPlus = parent.getContext ().getSharedPreferences ( "NICE_PLUS", Context.MODE_PRIVATE );
        }
        return vh;
    }


    @Override
    public void onBindViewHolder( @NonNull ViewHolder holder, int position ) {
        holder.bind( responseDataList.get ( position ));
    }

    @Override
    public int getItemCount() {
        return responseDataList.size ();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageFood;
        private TextView titleFood;
        private Button btnPrice;
        private Button btnPlus;
        private Button btnMinus;
        private TextView textSum;
        private Button btnBasket;
        private ResponseData currentResponseData;
        boolean s;
        boolean plus;


        public ViewHolder( @NonNull View itemView ) {
            super ( itemView );
            imageFood = itemView.findViewById ( R.id.imageFood );
            titleFood = itemView.findViewById ( R.id.titleFood );
            btnPrice = itemView.findViewById ( R.id.btn_price );
            btnMinus = itemView.findViewById ( R.id.btnMinus );
            btnPlus = itemView.findViewById ( R.id.btnPlus );
            textSum = itemView.findViewById ( R.id.textSum );
            btnBasket = itemView.findViewById ( R.id.btnBasket );

            btnBasket.setOnClickListener ( new View.OnClickListener () {
                @Override
                public void onClick( View view ) {
                    if(preferences.getBoolean ( String.valueOf ( currentResponseData.getId () ), false)){
                        btnBasket.setVisibility ( View.VISIBLE );
                        saveDataBtnBasket ( String.valueOf ( currentResponseData.getId () ), false );
                        btnPlus.setVisibility ( View.VISIBLE );
                        saveDataPlus ( String.valueOf ( currentResponseData.getId () ), false );
                    }else {
                        btnBasket.setVisibility ( View.GONE );
                        saveDataBtnBasket ( String.valueOf ( currentResponseData.getId () ), true );
                        btnPlus.setVisibility ( View.VISIBLE );
                        saveDataPlus ( String.valueOf ( currentResponseData.getId () ), true );


                    }
                }
            } );

        }

        public void bind( ResponseData responseData ) {
            currentResponseData = responseData;
            s = preferences.getBoolean ( String.valueOf ( currentResponseData.getId () ), true );
            plus = preferencesPlus.getBoolean ( String.valueOf ( currentResponseData.getId () ), true );
            // get image from url and set in ImageView
            String imageUrl = responseData.getImage ();
            Picasso.with ( itemView.getContext () )
                    .load ( imageUrl )
                    .into ( imageFood );
            // setting title food
            titleFood.setText ( responseData.getName () );
            // setting price on the button text
            btnPrice.setText ( String.valueOf ( responseData.getPrice () + "₽" ));


            // Кнопка Basket при входе в приложение
            if(preferences.getBoolean ( String.valueOf ( currentResponseData.getId () ), false )){
                btnBasket.setVisibility ( View.GONE );
            }else {
                btnBasket.setVisibility ( View.VISIBLE );
            }

            // Кнопка Plus при входе в приложение
            if(preferencesPlus.getBoolean ( String.valueOf ( currentResponseData.getId () ), false )){
                btnPlus.setVisibility ( View.VISIBLE );
            }else {
                btnPlus.setVisibility ( View.GONE );
            }


        }

        public void saveDataBtnBasket( String id, boolean dataToSave){
            SharedPreferences.Editor editor = preferences.edit ();
            editor.putBoolean ( id, dataToSave );
            editor.apply ();
        }

        public void saveDataPlus(String id, boolean dataToSave){
            SharedPreferences.Editor editor = preferencesPlus.edit ();
            editor.putBoolean ( id, dataToSave );
            editor.apply ();
        }
    }

}
