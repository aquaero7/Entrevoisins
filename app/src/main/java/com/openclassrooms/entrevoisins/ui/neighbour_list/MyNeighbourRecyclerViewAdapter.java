package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.events.DeleteNeighbourEvent;
import com.openclassrooms.entrevoisins.events.RemoveNeighbourFromFavoritesEvent;
import com.openclassrooms.entrevoisins.model.Neighbour;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MyNeighbourRecyclerViewAdapter extends RecyclerView.Adapter<MyNeighbourRecyclerViewAdapter.ViewHolder> {

    private final List<Neighbour> mNeighbours;

    /* >>> Initialement dans le code >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    public MyNeighbourRecyclerViewAdapter(List<Neighbour> items) {
        mNeighbours = items;
    }
    */ //fin

    // >>> PH >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    private int mTab;
    public MyNeighbourRecyclerViewAdapter(List<Neighbour> items, int tab) {
        mNeighbours = items;
        mTab = tab;
    }
    // //fin


    // >>> Option1 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    public static final String NEIGHBOUR_ID = "NEIGHBOUR_ID";
    // //fin

    /* >>> Option2&3 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    public static final String NEIGHBOUR_POS = "NEIGHBOUR_POS";
    public static final String NEIGHBOUR_LIST = "NEIGHBOUR_LIST";
    public static final String BUNDLE = "BUNDLE";
    public static final String LIST_TO_PASS = "LIST_TO_PASS";
    */ //fin

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_neighbour, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Neighbour neighbour = mNeighbours.get(position);
        holder.mNeighbourName.setText(neighbour.getName());
        Glide.with(holder.mNeighbourAvatar.getContext())
                .load(neighbour.getAvatarUrl())
                .apply(RequestOptions.circleCropTransform())
                .into(holder.mNeighbourAvatar);
        // >>> PH >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        if(mTab == 1) {
            holder.mDeleteButton.setImageResource(R.drawable.baseline_grade_24_gold);
        }
        // //fin

        /* >>> Initialement dans le code >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        holder.mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new DeleteNeighbourEvent(neighbour));
            }
        });
        */ //fin

        // >>> PH >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        holder.mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mTab == 0) {
                    EventBus.getDefault().post(new DeleteNeighbourEvent(neighbour));
                } else {
                    EventBus.getDefault().post(new RemoveNeighbourFromFavoritesEvent(neighbour));
                }
            }
        });
        // //fin

        // >>> PH >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        holder.mNeighbourFragment.setOnClickListener(new View.OnClickListener() {
            // 'mNeighbourFragment' peut etre remplacé par l'objet 'itemView'
            @Override
            public void onClick(View v) {
                Log.d(v.getClass().getName(), "onClick: Clic on neighbour " + neighbour.getName() + " id = " + neighbour.getId());
                Log.d(v.getClass().getName(), "onClick: Clic on neighbour at position " + holder.getAdapterPosition());
                Log.d(v.getContext().getClass().toString(), "onClick: Clic on neighbour " + neighbour.getName());

                Intent detailsActivity = new Intent(v.getContext(), NeighbourDetailsActivity.class);

                // >>> Option1 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
                detailsActivity.putExtra(NEIGHBOUR_ID, neighbour.getId());
                // //fin

                /* >>> Option2 Liste KO >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
                detailsActivity.putExtra(NEIGHBOUR_POS, holder.getAdapterPosition());
                detailsActivity.putExtra(NEIGHBOUR_LIST, (Parcelable) mNeighbours);
                    // FATAL EXCEPTION : ArrayList cannot be cast to android.os.Parcelable
                */ //fin

                /* >>> Option3 Bundle KO >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
                detailsActivity.putExtra(NEIGHBOUR_POS, holder.getAdapterPosition());
                Bundle listToPass = new Bundle();
                listToPass.putParcelable(LIST_TO_PASS, (Parcelable) mNeighbours);
                // FATAL EXCEPTION : ArrayList cannot be cast to android.os.Parcelable
                detailsActivity.putExtra(BUNDLE, listToPass);
                */ //fin

                v.getContext().startActivity(detailsActivity);

            }
        });
        // //fin
    }

    @Override
    public int getItemCount() {
        return mNeighbours.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_list_avatar)
        public ImageView mNeighbourAvatar;
        @BindView(R.id.item_list_name)
        public TextView mNeighbourName;
        @BindView(R.id.item_list_delete_button)
        public ImageButton mDeleteButton;

        // >>> PH >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        @BindView(R.id.main_fragment_neighbour)
        public ConstraintLayout mNeighbourFragment;
        // //fin


        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


}

