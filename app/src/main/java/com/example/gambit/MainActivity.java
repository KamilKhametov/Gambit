package com.example.gambit;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;

import com.example.gambit.API.APIConfig;
import com.example.gambit.API.APIService;
import com.example.gambit.API.APIServiceConstructor;
import com.example.gambit.API.ResponseData;
import com.example.gambit.swipeController.SwipeController;

import java.util.List;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager=new LinearLayoutManager ( this );
    private MyAdapter myAdapter;
    private SwipeController swipeController;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_main );

        // setting title to action bar
        setActionBarTitle ();

        // Call request from API
        APIService apiService=APIServiceConstructor.CreateService ( APIService.class );
        Call<List<ResponseData>> call=apiService.getDataGambit ( APIConfig.PAGE );
        call.enqueue ( new Callback<List<ResponseData>> () {
            @Override
            public void onResponse( Call<List<ResponseData>> call, Response<List<ResponseData>> response ) {
                if (response.isSuccessful ()) {
                    List<ResponseData> responseData=response.body ();

                    recyclerView=findViewById ( R.id.recyclerView );
                    recyclerView.setHasFixedSize ( true );
                    myAdapter=new MyAdapter ( responseData );
                    recyclerView.setLayoutManager ( layoutManager );
                    recyclerView.setAdapter ( myAdapter );

                    swipeInRecyclerView ();

                }
            }

            @Override
            public void onFailure( Call<List<ResponseData>> call, Throwable t ) {

            }
        } );

    }

    private void setActionBarTitle() {
        getSupportActionBar ().setDisplayOptions ( ActionBar.DISPLAY_SHOW_CUSTOM );
        getSupportActionBar ().setCustomView ( R.layout.action_bar_layout );
    }


    private void swipeInRecyclerView(){
        swipeController = new SwipeController ( this, myAdapter );
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper ( swipeController );
        itemTouchHelper.attachToRecyclerView ( recyclerView );
    }

}