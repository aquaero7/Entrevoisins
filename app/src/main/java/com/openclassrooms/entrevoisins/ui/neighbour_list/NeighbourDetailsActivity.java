package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.openclassrooms.entrevoisins.R;

public class NeighbourDetailsActivity extends AppCompatActivity {

    private FloatingActionButton mBackButton;
    private FloatingActionButton mToggleFavoriteButton;
    private ImageView mAvatar;
    private TextView mNameAvatar;
    private TextView mName;
    private TextView mAddress;
    private TextView mPhoneNumber;
    private TextView mAvatarUrl;
    private TextView mAboutMe;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_neighbour_details);

        mBackButton = findViewById(R.id.details_button_back);
        mToggleFavoriteButton = findViewById(R.id.details_button_favorite_toggle);
        mAvatar = findViewById(R.id.details_avatar);
        mNameAvatar = findViewById(R.id.details_name_avatar);
        mName = findViewById(R.id.details_name);
        mAddress = findViewById(R.id.details_address);
        mPhoneNumber = findViewById(R.id.details_phone_number);
        mAvatarUrl = findViewById(R.id.details_avatar_url);
        mAboutMe = findViewById(R.id.details_about_me);



    }
}