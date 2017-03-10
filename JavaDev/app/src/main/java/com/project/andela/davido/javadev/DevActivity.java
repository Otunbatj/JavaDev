package com.project.andela.davido.javadev;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class DevActivity extends AppCompatActivity {
    String userName, userImageURL, userhtmlURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dev);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        TextView userNameText = (TextView) findViewById(R.id.user_name);
        TextView userhtmlURLText = (TextView) findViewById(R.id.html_url);
        ImageView userImage = (ImageView) findViewById(R.id.image);

        Intent intent = getIntent();
        userName = intent.getStringExtra("userName");
        userImageURL = intent.getStringExtra("userImageURL");
        userhtmlURL = intent.getStringExtra("userhtmlURL");
        toolbar.setTitle(userName);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        userNameText.setText(userName);
        userhtmlURLText.setText(userhtmlURL);
        Glide.with(this).load(userImageURL)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(userImage);
        userImage.setColorFilter(null);

        userhtmlURLText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse(userhtmlURL));
                startActivity(browserIntent);
            }
        });

        userImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse(userhtmlURL));
                startActivity(browserIntent);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT, "Check out this awesome developer @"
                        + userName + ", " + userhtmlURL + ".");
                startActivity(shareIntent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
