package com.example.gambit.swipeController;

import android.content.Context;
import android.graphics.Canvas;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gambit.R;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

import static androidx.recyclerview.widget.ItemTouchHelper.LEFT;

public class SwipeController extends ItemTouchHelper.Callback {

    private Context context;
    private RecyclerView.Adapter adapter;

    public SwipeController( Context context, RecyclerView.Adapter adapter ) {
        this.context=context;
        this.adapter=adapter;
    }

    @Override
    public int getMovementFlags( @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder ) {
        return makeMovementFlags ( 0, LEFT );
    }

    @Override
    public boolean onMove( @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target ) {
        return false;
    }

    @Override
    public void onSwiped( @NonNull RecyclerView.ViewHolder viewHolder, int direction ) {
        adapter.notifyItemChanged ( viewHolder.getAdapterPosition () );
    }

    @Override
    public void onChildDraw( Canvas c, RecyclerView recyclerView,
                             RecyclerView.ViewHolder viewHolder, float dX, float dY,
                             int actionState, boolean isCurrentlyActive ) {
        new RecyclerViewSwipeDecorator.Builder ( c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive )
                .addSwipeLeftBackgroundColor ( ContextCompat.getColor ( context, R.color.recycler_view_item_swipe_left_background ) )
                .addSwipeLeftActionIcon ( R.drawable.ic_like )
                .create ()
                .decorate ();

        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            float width=(float) viewHolder.itemView.getWidth ();
            float alpha=30.0f - Math.abs ( dX ) / width;
            viewHolder.itemView.setAlpha ( alpha );
            viewHolder.itemView.setTranslationX ( dX );

        } else {
            super.onChildDraw ( c, recyclerView, viewHolder, dX, dY,
                                actionState, isCurrentlyActive );
        }
    }
}
