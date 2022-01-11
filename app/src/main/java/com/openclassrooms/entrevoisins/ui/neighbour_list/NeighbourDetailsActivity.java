package com.openclassrooms.entrevoisins.ui.neighbour_list;


import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;


public class NeighbourDetailsActivity extends AppCompatActivity {

    private FloatingActionButton mBackButton;
    private FloatingActionButton mToggleFavoriteButton;
    private FloatingActionButton mToggleFavoriteButtonF;
    private ImageView mAvatarUrl;
    private TextView mNameAvatar;
    private TextView mName;
    private TextView mAddress;
    private TextView mPhoneNumber;
    private TextView mLink;
    private TextView mAboutMe;

    // >>> Option1 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    public static final String NEIGHBOUR_ID = "NEIGHBOUR_ID";
    // //fin

    // >>> Option2&3 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    public static final String NEIGHBOUR_POS = "NEIGHBOUR_POS";
    public static final String NEIGHBOUR_LIST = "NEIGHBOUR_LIST";
    public static final String BUNDLE = "BUNDLE";
    public static final String LIST_TO_PASS = "LIST_TO_PASS";
    // //fin

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_neighbour_details);

        mBackButton = findViewById(R.id.details_button_back);
        mToggleFavoriteButton = findViewById(R.id.details_button_favorite_toggle);
        mAvatarUrl = findViewById(R.id.details_url_avatar);
        mNameAvatar = findViewById(R.id.details_name_avatar);
        mName = findViewById(R.id.details_name);
        mAddress = findViewById(R.id.details_address);
        mPhoneNumber = findViewById(R.id.details_phone_number);
        mLink = findViewById(R.id.details_link);
        mAboutMe = findViewById(R.id.details_about_me);

        // >>> Option1 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        Intent detailsActivity = getIntent();

        long neighbourId = detailsActivity.getLongExtra(NEIGHBOUR_ID, -1);
        Log.d(this.getClass().getName(), "onClick: Valeur ID passée : " + neighbourId);

        NeighbourApiService neighbourApi = DI.getNeighbourApiService();
        Neighbour neighbour = neighbourApi.getNeighbourById(neighbourId);

        String neighbourAvatarUrl = neighbour.getAvatarUrl();
        String neighbourName = neighbour.getName();
        String neighbourAddress = neighbour.getAddress();
        String neighbourPhoneNumber = neighbour.getPhoneNumber();
        String neighbourAboutMe = neighbour.getAboutMe();
        // //fin

        /* >>> Option2 Liste KO >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        Bundle extras = getIntent().getExtras();

        int neighbourPos = extras.getInt(NEIGHBOUR_POS, -1);
        Log.d(this.getClass().getName(), "onClick: Valeur POS passée : " + neighbourPos);

        List<Neighbour> mNeighbours = extras.getParcelable(NEIGHBOUR_LIST);
            // FATAL EXCEPTION : ArrayList cannot be cast to android.os.Parcelable
        Neighbour neighbour = mNeighbours.get((int) neighbourPos);

        String neighbourAvatarUrl = neighbour.getAvatarUrl();
        String neighbourName = neighbour.getName();
        String neighbourAddress = neighbour.getAddress();
        String neighbourPhoneNumber = neighbour.getPhoneNumber();
        String neighbourAboutMe = neighbour.getAboutMe();

        Log.d(this.getClass().getName(), "onClick: NOM reconstitué : " + neighbourName);
        */ //fin

        /* >>> Option3 Bundle KO >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        Bundle extras = getIntent().getExtras();

        int neighbourPos = extras.getInt(NEIGHBOUR_POS, -1);
        Log.d(this.getClass().getName(), "onClick: Valeur POS passée : " + neighbourPos);

        Bundle listToPass = extras.getBundle(BUNDLE);
        List<Neighbour> mNeighbours = listToPass.getParcelable(LIST_TO_PASS);
            // FATAL EXCEPTION : ArrayList cannot be cast to android.os.Parcelable
        Neighbour neighbour = mNeighbours.get((int) neighbourPos);

        String neighbourAvatarUrl = neighbour.getAvatarUrl();
        String neighbourName = neighbour.getName();
        String neighbourAddress = neighbour.getAddress();
        String neighbourPhoneNumber = neighbour.getPhoneNumber();
        String neighbourAboutMe = neighbour.getAboutMe();

        Log.d(this.getClass().getName(), "onClick: NOM reconstitué : " + neighbourName);
        */ //fin


        Glide.with(mAvatarUrl.getContext())
                .load(neighbourAvatarUrl)
                //.apply(RequestOptions.circleCropTransform())
                .into(mAvatarUrl);
        mNameAvatar.setText(neighbourName);
        mName.setText(neighbourName);
        mAddress.setText(neighbourAddress);
        mPhoneNumber.setText(neighbourPhoneNumber);
        mLink.setText("www.facebook.fr/" + neighbourName.toLowerCase());
        mAboutMe.setText(neighbourAboutMe);

        checkFavoriteStatus(neighbour);

        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mToggleFavoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // neighbour.setStatusFavorite(!neighbour.getStatusFavorite());
                neighbourApi.toggleStatusFavorite(neighbour);
                checkFavoriteStatus(neighbour);
                toastStatusChange(neighbour);
                Log.d(this.getClass().getName(), "onClick toggleFavoriteButton : Nouvelle valeur = " + neighbour.getStatusFavorite());
            }
        });

    }

    public void checkFavoriteStatus(Neighbour neighbour) {
        if (neighbour.getStatusFavorite() == false) {
            mToggleFavoriteButton.setImageResource(R.drawable.baseline_grade_24_grey);
        } else {
            mToggleFavoriteButton.setImageResource(R.drawable.baseline_grade_24_gold);
        }
    };

    public void toastStatusChange(Neighbour neighbour) {
        if (neighbour.getStatusFavorite() == false) {
            Toast.makeText(this, "Removed from Favorites !", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Added to Favorites !", Toast.LENGTH_SHORT).show();
        }

    }

}