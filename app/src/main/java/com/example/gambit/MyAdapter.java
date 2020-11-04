package com.example.gambit;

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

    public MyAdapter( List<ResponseData> responseDataList ) {
        this.responseDataList=responseDataList;
    }


    @NonNull
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder( @NonNull ViewGroup parent, int viewType ) {
        View view =LayoutInflater.from ( parent.getContext () ).inflate ( R.layout.layout_item, parent, false );
        ViewHolder vh = new ViewHolder ( view );
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


        public ViewHolder( @NonNull View itemView ) {
            super ( itemView );
            imageFood = itemView.findViewById ( R.id.imageFood );
            titleFood = itemView.findViewById ( R.id.titleFood );
            btnPrice = itemView.findViewById ( R.id.btn_price );
            btnMinus = itemView.findViewById ( R.id.btnMinus );
            btnPlus = itemView.findViewById ( R.id.btnPlus );
            textSum = itemView.findViewById ( R.id.textSum );
            btnBasket = itemView.findViewById ( R.id.btnBasket );

        }

        public void bind( ResponseData responseData ) {
            // get image from url and set in ImageView
            String imageUrl = responseData.getImage ();
            Picasso.with ( itemView.getContext () )
                    .load ( imageUrl )
                    .into ( imageFood );
            // setting title food
            titleFood.setText ( responseData.getName () );
            // setting price on the button text
            btnPrice.setText ( String.valueOf ( responseData.getPrice () + "₽" ));

        }
    }

}
