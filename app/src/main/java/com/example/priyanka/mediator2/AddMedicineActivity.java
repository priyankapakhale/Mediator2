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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.priyanka.mediator2.Helpers.CircleTransform;
import com.example.priyanka.mediator2.Network.ApiClient;
import com.example.priyanka.mediator2.Network.ApiInterface;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.zxing.client.android.Intents;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by priyanka on 1/24/18.
 */

public class AddMedicineActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    EditText TFname, TFbarcode, TFuse, TFexpiryDate, TFendDate;
    private String name, barcode, use, expirydate, end_date;
    boolean isEverydayMedicine, setReminder;
    private int dosage;
    boolean morning, noon, night;
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
        setContentView(R.layout.activity_add_medicine);
        mAuth = FirebaseAuth.getInstance();
        userid = getIntent().getIntExtra("userid", 0);
        email_id = mAuth.getCurrentUser().getEmail();


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


        dosage = 0;
        TFname = findViewById(R.id.medicine_name);
        TFbarcode = findViewById(R.id.barcode);
        TFuse = findViewById(R.id.use);
        TFexpiryDate = findViewById(R.id.expiry_date);
        TFendDate = findViewById(R.id.end_date);
        ToggleButton toggle = (ToggleButton) findViewById(R.id.S_add_reminders);
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            public void onCheckedChanged (CompoundButton buttonView,boolean isChecked){
                if (isChecked) {
                    // The toggle is enabled
                    setReminder = true;
                } else {
                    // The toggle is disabled
                    setReminder = false;
                }
            }
        });

        String barcode = getIntent().getStringExtra("barcode");
        if(!barcode.equalsIgnoreCase("N/A"))
        {
            TFbarcode.setText(barcode);
        }


        String medicine_name = getIntent().getStringExtra("medicine_name");
        Log.d("name here",medicine_name);
        if(!medicine_name.equalsIgnoreCase("N/A"))
        {
            TFname.setText(medicine_name);
            TFuse.setText(getIntent().getStringExtra("use"));
        }
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
                        mDrawerLayout.closeDrawers();
                        return true;
                    case R.id.nav_activity_pickup_requests:
                        showRequests();
                        break;
                    case R.id.nav_activity_donate:
                        donate();
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

    public void home()
    {
        Intent intent = new Intent(AddMedicineActivity.this, HomeActivity.class);
        startActivity(intent);
    }

    public void showRequests()
    {
        Intent intent = new Intent(AddMedicineActivity.this, PickupRequestsActivity.class);
        startActivity(intent);
    }

    public void donate()
    {
        Intent intent = new Intent(AddMedicineActivity.this, DonateActivity.class);
        startActivity(intent);
    }

    public void settings()
    {
        Intent intent = new Intent(AddMedicineActivity.this, SettingsActivity.class);
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


    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.B_radio_yes:
                if (checked)
                    //Every day medicine
                    isEverydayMedicine = true;
                break;
            case R.id.B_radio_no:
                if (checked)
                    //Temporary medicine
                    isEverydayMedicine = false;
                break;
        }
    }


    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch (view.getId()) {
            case R.id.CB_morning:
                if (checked) {
                    dosage += 1;
                    morning = true;
                } else {
                    morning = false;
                    dosage -= 1;
                }
                break;
            case R.id.CB_noon:
                if (checked) {
                    dosage += 1;
                    noon = true;
                } else {
                    noon = false;
                    dosage -= 1;
                }
                break;
            case R.id.CB_night:
                if (checked) {
                    dosage += 1;
                    night = true;
                } else {
                    night = false;
                    dosage -= 1;
                }
                break;

        }
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.B_scan_barcode:
                scan_barcode();
                break;
            case R.id.B_add:
                add();
                break;
        }
    }

    public void scan_barcode() {
        Intent intent = new Intent(AddMedicineActivity.this, ScannerActivity.class);
        startActivity(intent);
    }





    public void add() {
        Log.d("set reminder",setReminder+"");
        name = TFname.getText().toString();
        barcode = TFbarcode.getText().toString();
        expirydate = TFexpiryDate.getText().toString();
        use = TFuse.getText().toString();
        if (!isEverydayMedicine) {
            end_date = TFendDate.getText().toString();
        } else {
            end_date = "2050-01-01";
        }

        Log.d("here morning",morning+"");
        if (setReminder) {
            addMedicine(name, barcode, expirydate, use, dosage, end_date);
            Intent intent = new Intent(AddMedicineActivity.this, AddRemindersActivity.class);
            intent.putExtra("userid", userid);
            intent.putExtra("name", name);
            intent.putExtra("barcode", barcode);
            intent.putExtra("expirydate", expirydate);
            intent.putExtra("dosage", dosage);
            intent.putExtra("end_date", end_date);
            intent.putExtra("morning", morning);
            intent.putExtra("noon", noon);
            intent.putExtra("night", night);

            startActivity(intent);

        }
        else
        {
            addMedicine(name, barcode, expirydate, use, dosage, end_date);
            Intent intent = new Intent(AddMedicineActivity.this, HomeActivity.class);
            startActivity(intent);
        }

    }


    public void addMedicine(String name, String barcode, String expirydate, String use, int dosage, String end_date)
    {
        ApiInterface apiService = ApiClient.getStringClient().create(ApiInterface.class);
        Call<ResponseBody> call = apiService.addMedicine(email_id,name,barcode,expirydate,use,dosage,end_date);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Toast.makeText(getApplicationContext(),"Medicine added successfully",Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });



    }


}
