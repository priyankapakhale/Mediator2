package com.example.priyanka.mediator2;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.priyanka.mediator2.Fragments.MedicineRecordFragment;
import com.example.priyanka.mediator2.Helpers.CircleTransform;
import com.example.priyanka.mediator2.Models.PickupRequestResponse;
import com.example.priyanka.mediator2.Models.User;
import com.example.priyanka.mediator2.Models.UserResponse;
import com.example.priyanka.mediator2.Network.ApiClient;
import com.example.priyanka.mediator2.Network.ApiInterface;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import butterknife.ButterKnife;
import butterknife.InjectView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by priyanka on 1/24/18.
 */

public class HomeActivity extends AppCompatActivity {
    private static final String TAG = "HomeActivity";
    private FirebaseAuth mAuth;
    private ViewPager viewPager;

    private User user;
    TextView TVname, TVemail,TVcontactno;
    String name,email;

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private Toolbar mToolbar;
    private NavigationView navigationView;
    private View navHeader;
    private ImageView imgNavHeaderBg, imgProfile;


    private static final String urlNavHeaderBg = "https://api.androidhive.info/images/nav-menu-header-bg.jpg";
    private static final String urlProfileImg = "https://pbs.twimg.com/profile_images/3766735565/6e0118f54f242cf5978fa990c9c838df.jpeg";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mAuth = FirebaseAuth.getInstance();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        String email = currentUser.getEmail();
        Log.d("current user",currentUser.toString());
        Log.d("Email",email);
        getUser(email);

        //TVname = (TextView)findViewById(R.id.user_name);
        //TVemail = (TextView)findViewById(R.id.email);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);




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
                        mDrawerLayout.closeDrawers();
                        return true;
                    case R.id.nav_activity_add_medicine:
                        addMedicine();
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


        @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if(mToggle.onOptionsItemSelected(item))
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        Bundle bundle = new Bundle();
        Fragment mrFragment = new MedicineRecordFragment();
        adapter.addFragment(mrFragment, "Current");
        viewPager.setAdapter(adapter);

    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }



    public void onClick(View v)
    {
        switch(v.getId())
        {
            case R.id.B_add_medicine: addMedicine(); break;
            case R.id.B_donate: donate(); break;
            case R.id.B_show_requests: showRequests(); break;
        }
    }

    public void settings()
    {
        Intent intent = new Intent(HomeActivity.this, SettingsActivity.class);
        startActivity(intent);
    }

    public void showRequests()
    {
        Intent intent = new Intent(HomeActivity.this, PickupRequestsActivity.class);
        startActivity(intent);
    }

    public void donate()
    {
        Intent intent = new Intent(HomeActivity.this, DonateActivity.class);
        intent.putExtra("user_id",1);
        startActivity(intent);

    }

    public void addMedicine()
    {
        Intent intent = new Intent(HomeActivity.this, AddMedicineActivity.class);
        intent.putExtra("userid",user.getId());
        intent.putExtra("medicine_name","N/A");
        intent.putExtra("barcode","");
        startActivity(intent);

    }

    public void getUser(String email)
    {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<UserResponse> call = apiService.getUser(email);
        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                Log.d("response",response.message());
                user = response.body().getUser();
                int id = response.body().getId();
                user.setId(id);
                Log.d("Id fetched", ""+id);
                TVname.setText(user.getName());
                TVemail.setText(user.getEmailId());

            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Log.d("Failure",t.getMessage());
            }
        });
    }

}
