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

import java.util.List;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager=new LinearLayoutManager ( this );
    private MyAdapter myAdapter;

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

                    // Swipe method go!
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

    private void swipeInRecyclerView() {
        // Swipe for RecyclerView
        ItemTouchHelper.SimpleCallback callback=new ItemTouchHelper.SimpleCallback ( 0, ItemTouchHelper.LEFT ) {
            @Override
            public boolean onMove( RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target ) {
                return false;
            }

            // Что делать при свайпе: добавить в архив, либо удалить item`ы
            @Override
            public void onSwiped( RecyclerView.ViewHolder viewHolder, int direction ) {
                // Возвращает свайп обратно
                myAdapter.notifyItemChanged ( viewHolder.getAdapterPosition () );

            }

            // swipe draw method
            @Override
            public void onChildDraw( Canvas c, RecyclerView recyclerView,
                                     RecyclerView.ViewHolder viewHolder, float dX, float dY,
                                     int actionState, boolean isCurrentlyActive ) {
                new RecyclerViewSwipeDecorator.Builder ( c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive )
                        .addSwipeLeftBackgroundColor ( ContextCompat.getColor ( MainActivity.this, R.color.recycler_view_item_swipe_left_background ) )
                        .addSwipeLeftActionIcon ( R.drawable.ic_like )
                        .setSwipeLeftLabelColor ( Color.WHITE )
                        .create ()
                        .decorate ();

                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                    float width=(float) viewHolder.itemView.getWidth ();
                    float alpha= 30.0f - Math.abs ( dX ) / width;
                    viewHolder.itemView.setAlpha ( alpha );
                    viewHolder.itemView.setTranslationX ( dX );

                } else {
                    super.onChildDraw ( c, recyclerView, viewHolder, dX, dY,
                                        actionState, isCurrentlyActive );
                }
            }
        };
        ItemTouchHelper itemTouchHelper=new ItemTouchHelper ( callback );
        itemTouchHelper.attachToRecyclerView ( recyclerView );
    }

}