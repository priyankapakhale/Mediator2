package com.example.priyanka.mediator2;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.priyanka.mediator2.Helpers.CircleTransform;
import com.example.priyanka.mediator2.Network.ApiClient;
import com.example.priyanka.mediator2.Network.ApiInterface;
import com.google.firebase.auth.FirebaseAuth;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by priyanka on 1/28/18.
 */

public class DonateActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private EditText TFmedicine_name, TFbarcode, TFexpiry_date, TFquantity;
    private String medicine_name,barcode,expiry_date;
    private int quantity;

    private int userid;
    private String email_id;

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private Toolbar mToolbar;
    private NavigationView navigationView;
    private View navHeader;
    private ImageView imgNavHeaderBg, imgProfile;
    private TextView TVname, TVemail;

    private static final String urlNavHeaderBg = "https://api.androidhive.info/images/nav-menu-header-bg.jpg";
    private static final String urlProfileImg = "https://lh3.googleusercontent.com/eCtE_G34M9ygdkmOpYvCag1vBARCmZwnVS6rS5t4JLzJ6QgQSBquM0nuTsCpLhYbKljoyS-txg";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate);
        mAuth = FirebaseAuth.getInstance();
        email_id = mAuth.getCurrentUser().getEmail();
        Log.d("email",email_id);
        TFmedicine_name = findViewById(R.id.medicine_name);
        TFbarcode = findViewById(R.id.barcode);
        TFexpiry_date = findViewById(R.id.expiry_date);
        TFquantity = findViewById(R.id.quantity);

        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout,R.string.openDrawer,R.string.closeDrawer );
        mToolbar = (Toolbar)findViewById(R.id.nav_action_bar);
        setSupportActionBar(mToolbar);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        // Navigation view header
        navHeader = navigationView.getHeaderView(0);
        TVname = (TextView) navHeader.findViewById(R.id.user_name);
        TVemail = (TextView) navHeader.findViewById(R.id.email);
        imgNavHeaderBg = (ImageView) navHeader.findViewById(R.id.img_header_bg);
        imgProfile = (ImageView) navHeader.findViewById(R.id.img_profile);
        // load nav menu header data
        loadNavHeader();
        // initializing navigation menu
        setUpNavigationView();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }

    /***
     * Load navigation menu header information
     * like background image, profile image
     * name, website, notifications action view (dot)
     */
    private void loadNavHeader() {
        // name, website
        TVname.setText("Prajakta Pakhale");
        TVemail.setText("prajakta.pakhale13@gmail.com");

        // loading header background image
        Glide.with(this).load(urlNavHeaderBg)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgNavHeaderBg);

        // Loading profile image
        Glide.with(this).load(urlProfileImg)
                .crossFade()
                .thumbnail(0.5f)
                .bitmapTransform(new CircleTransform(this))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgProfile);


    }

    private void setUpNavigationView() {
        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()) {
                    //Replacing the main content with ContentFragment Which is our Inbox View;
                    case R.id.nav_activity_home:
                        home();
                        return true;
                    case R.id.nav_activity_add_medicine:
                        addMedicine();
                        return true;
                    case R.id.nav_activity_pickup_requests:
                        showRequests();
                        break;
                    case R.id.nav_activity_donate:
                        mDrawerLayout.closeDrawers();
                        break;
                    case R.id.nav_settings:
                        settings();
                        break;
                    case R.id.nav_logout:
                        break;

                }

                //Checking if the item is in checked state or not, if not make it in checked state
                if (menuItem.isChecked()) {
                    menuItem.setChecked(false);
                } else {
                    menuItem.setChecked(true);
                }
                menuItem.setChecked(true);


                return true;
            }
        });
    }

    public void addMedicine()
    {
        Intent intent = new Intent(DonateActivity.this, AddMedicineActivity.class);
        intent.putExtra("userid",0);
        intent.putExtra("medicine_name","N/A");
        intent.putExtra("barcode","");
        startActivity(intent);


    }

    public void settings()
    {
        Intent intent = new Intent(DonateActivity.this, SettingsActivity.class);
        startActivity(intent);
    }

    public void home()
    {
        Intent intent = new Intent(DonateActivity.this, HomeActivity.class);
        startActivity(intent);
    }

    public void showRequests()
    {
        Intent intent = new Intent(DonateActivity.this, PickupRequestsActivity.class);
        startActivity(intent);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if(mToggle.onOptionsItemSelected(item))
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void onClick(View v)
    {
        switch(v.getId())
        {
            case R.id.B_request_pickup: requestPickup(); break;
        }

    }

    public void requestPickup()
    {
        Log.d("requestpickup", "I am here");
        medicine_name = TFmedicine_name.getText().toString();
        expiry_date = TFexpiry_date.getText().toString();
        barcode = TFbarcode.getText().toString();
        quantity = Integer.parseInt(TFquantity.getText().toString());


        ApiInterface apiService = ApiClient.getStringClient().create(ApiInterface.class);
        Call<ResponseBody> call = apiService.addPickupRequest(email_id,medicine_name,barcode,expiry_date,quantity);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Toast.makeText(getApplicationContext(),"Medicine added successfully",Toast.LENGTH_SHORT).show();
                //Intent intent = new Intent(DonateActivity.this, HomeActivity.class);
                //startActivity(intent);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {


            }
        });

    }

}
