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
import androidx.constraintlayout.widget.Placeholder;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gambit.API.ResponseData;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<ResponseData> responseDataList;
    private SharedPreferences preferencesBasket;
    private SharedPreferences preferencesPlus;
    private SharedPreferences preferencesMinus;
    private SharedPreferences preferencesSum;
    private SharedPreferences preferencesNumberSum;

    public MyAdapter( List<ResponseData> responseDataList ) {
        this.responseDataList=responseDataList;
    }


    @NonNull
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder( @NonNull ViewGroup parent, int viewType ) {
        View view =LayoutInflater.from ( parent.getContext () ).inflate ( R.layout.layout_item, parent, false );
        ViewHolder vh = new ViewHolder ( view );
        if(preferencesBasket == null){
            preferencesBasket= parent.getContext ().getSharedPreferences ( "NICE_BASKET", Context.MODE_PRIVATE );
        }
        if (preferencesPlus == null){
            preferencesPlus = parent.getContext ().getSharedPreferences ( "NICE_PLUS", Context.MODE_PRIVATE );
        }
        if(preferencesMinus == null){
            preferencesMinus = parent.getContext ().getSharedPreferences ( "NICE_MINUS", Context.MODE_PRIVATE );
        }
        if(preferencesSum == null){
            preferencesSum = parent.getContext ().getSharedPreferences ( "NICE_SUM", Context.MODE_PRIVATE );
        }
        if(preferencesNumberSum == null){
            preferencesNumberSum = parent.getContext ().getSharedPreferences ( "NICE_NUMBER_SUM", Context.MODE_PRIVATE );
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
        boolean basket;
        boolean plus;
        boolean minus;
        boolean sum;
        int number = 0;


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
                    if(preferencesBasket.getBoolean ( String.valueOf ( currentResponseData.getId () ), false)){
                        // Button Basket visibility and save data
                        btnBasket.setVisibility ( View.VISIBLE );
                        saveDataBtnBasket ( String.valueOf ( currentResponseData.getId () ), false );

                        // Button Plus visibility and save data
                        btnPlus.setVisibility ( View.VISIBLE );
                        saveDataPlus ( String.valueOf ( currentResponseData.getId () ), false );

                        // Button Minus visibility and save data
                        btnMinus.setVisibility ( View.VISIBLE );
                        saveDataMinus ( String.valueOf ( currentResponseData.getId () ), false );

                        // TextView textSum visibility and save data
                        textSum.setVisibility ( View.VISIBLE );
                        saveDataTextSum ( String.valueOf ( currentResponseData.getId () ), false );
                    }else {
                        // Button Basket visibility and save data
                        btnBasket.setVisibility ( View.GONE );
                        saveDataBtnBasket ( String.valueOf ( currentResponseData.getId () ), true );

                        // Button Plus visibility and save data
                        btnPlus.setVisibility ( View.VISIBLE );
                        saveDataPlus ( String.valueOf ( currentResponseData.getId () ), true );

                        // Button Minus visibility and save data
                        btnMinus.setVisibility ( View.VISIBLE );
                        saveDataMinus ( String.valueOf ( currentResponseData.getId () ), true );

                        // TextView textSum visibility and save data
                        textSum.setVisibility ( View.VISIBLE );
                        saveDataTextSum ( String.valueOf ( currentResponseData.getId () ), true );

                    }
                }
            } );

        }

        public void bind( ResponseData responseData ) {
            currentResponseData = responseData;
            // Показ сохраненных данных на активити
            basket= preferencesBasket.getBoolean ( String.valueOf ( currentResponseData.getId () ), true );
            plus = preferencesPlus.getBoolean ( String.valueOf ( currentResponseData.getId () ), true );
            minus = preferencesMinus.getBoolean ( String.valueOf ( currentResponseData.getId () ), true );
            sum = preferencesSum.getBoolean ( String.valueOf ( currentResponseData.getId () ), false );
            number = preferencesNumberSum.getInt ( String.valueOf ( currentResponseData.getId () ), 0 );
            textSum.setText ( String.valueOf ( number ) );


            // get image from url and set in ImageView
            String imageUrl = responseData.getImage ();
            Picasso.with ( itemView.getContext () )
                    .load ( imageUrl )
                    .into ( imageFood );
            // setting title food
            titleFood.setText ( responseData.getName () );
            // setting price on the button text
            btnPrice.setText ( String.valueOf ( responseData.getPrice () + "₽" ));


            // Кнопка Basket при входе в активити
            if(preferencesBasket.getBoolean ( String.valueOf ( currentResponseData.getId () ), false )){
                btnBasket.setVisibility ( View.GONE );
            }else {
                btnBasket.setVisibility ( View.VISIBLE );
            }

            // Кнопка Plus при входе в активити
            if(preferencesPlus.getBoolean ( String.valueOf ( currentResponseData.getId () ), false )){
                btnPlus.setVisibility ( View.VISIBLE );
            }else {
                btnPlus.setVisibility ( View.GONE );
            }

            // Кнопка Minus при входе в активити
            if(preferencesPlus.getBoolean ( String.valueOf ( currentResponseData.getId () ), false )){
                btnMinus.setVisibility ( View.VISIBLE );
            }else {
                btnMinus.setVisibility ( View.GONE );
            }

            // TextView textSum при входе в активити
            if(preferencesSum.getBoolean ( String.valueOf ( currentResponseData.getId () ), false )){
                textSum.setVisibility ( View.VISIBLE );
            }else {
                textSum.setVisibility ( View.GONE );
            }

            // Вызов клика на кнопку Plus
            clickerPlus ();


        }

        public void saveDataBtnBasket( String id, boolean dataToSave){
            SharedPreferences.Editor editor = preferencesBasket.edit ();
            editor.putBoolean ( id, dataToSave );
            editor.apply ();
        }

        public void saveDataPlus(String id, boolean dataToSave){
            SharedPreferences.Editor editor = preferencesPlus.edit ();
            editor.putBoolean ( id, dataToSave );
            editor.apply ();
        }

        public void saveDataMinus(String id , boolean dataToSave){
            SharedPreferences.Editor editor = preferencesMinus.edit ();
            editor.putBoolean ( id, dataToSave );
            editor.apply ();
        }

        public void saveDataTextSum( String id, boolean dataToSave){
            SharedPreferences.Editor editor = preferencesSum.edit ();
            editor.putBoolean ( id, dataToSave );
            editor.apply ();
        }

        public void saveDataNumberSum(String id, int dataToSave){
            SharedPreferences.Editor editor = preferencesNumberSum.edit ();
            editor.putInt ( id, dataToSave );
            editor.apply ();
        }

        public void clickerPlus(){
            btnPlus.setOnClickListener ( new View.OnClickListener () {
                @Override
                public void onClick( View view ) {
                    number = Integer.parseInt ( textSum.getText ().toString () ) + 1;
                    textSum.setText ( String.valueOf ( number ) );
                    saveDataNumberSum ( String.valueOf ( currentResponseData.getId () ), number );
                }
            } );
        }

        public void clickerMinus(){
            btnMinus.setOnClickListener ( new View.OnClickListener () {
                @Override
                public void onClick( View view ) {

                }
            } );
        }

    }

}
