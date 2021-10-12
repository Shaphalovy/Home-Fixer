package com.example.homefixer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Timeline extends AppCompatActivity {




    private ViewPager viewPager;

    private RecyclerView usersRecyclerView;
    private UsersAdapter usersAdapter;
    private List<UserModel> userModelList;
    DatabaseReference databaseReference;

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);


        usersRecyclerView = findViewById(R.id.usersRecyclerViewId);
        usersRecyclerView.setHasFixedSize(true);
        usersRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //navigationDrawer---------------START------->
        drawerLayout = findViewById(R.id.drawerId);
        navigationView = findViewById(R.id.navigationId);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.nav_open,R.string.nav_close);
        toggle.syncState();
        //navigationDrawer---------------END---------<



        viewPager = findViewById(R.id.viewPagerId);




        FragmentManager fragmentManager = getSupportFragmentManager();
        CustomAdapter adapter = new CustomAdapter(fragmentManager);
        viewPager.setAdapter(adapter);





//        recyclerView--------------------------->

        userModelList = new ArrayList<>();

        databaseReference = FirebaseDatabase.getInstance().getReference("Users");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot dataSnapshot1 : snapshot.getChildren()){
                    UserModel userModel = dataSnapshot1.getValue(UserModel.class);
                    userModelList.add(userModel);
                }
                usersAdapter = new UsersAdapter(Timeline.this,userModelList);
                usersRecyclerView.setAdapter(usersAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), ""+error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }




    private class CustomAdapter  extends FragmentStatePagerAdapter {
        public CustomAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {

            Fragment fragment = null;

            if(position==0){
                fragment = new Professional1();
            }
            else if(position==1){
                fragment = new Professional2();
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {

            if(position ==0){
                return "PROFESSIONAL HUB 1";
            } if(position ==1){
                return "PROFESSIONAL HUB 2";
            }
            return null;
        }
    }






    //OPTION MENU//...................................................

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_layout,menu);
        return super.onCreateOptionsMenu(menu);
    }





    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.settingId){
            Toast.makeText(getApplicationContext(),"Settings Selected",Toast.LENGTH_SHORT).show();
        }

        if(item.getItemId()==R.id.shareId){
            Toast.makeText(this, "sharing Options Loading", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");

            String subject = "Home Fixer Mobile Application";
            String body = "Fix your problem with professional. Find Professionals and get consulted by them and also do not forget to consult others. Download Now from Google Play Store: https://play.google.com/store/apps";

            intent.putExtra(Intent.EXTRA_SUBJECT,subject);
            intent.putExtra(Intent.EXTRA_TEXT,body);

            startActivity(Intent.createChooser(intent,"Share with: "));
        }

        if(item.getItemId()==R.id.feedbackId){
            Toast.makeText(this, "feedback activity loading", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(),feedback.class);
            startActivity(intent);

        }

        if(item.getItemId()==R.id.aboutUsId){
            MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this);
            builder.setTitle("About Us");
            builder.setMessage("We are Akatsuki Android developer.\nContact Us: +880 1787123928 \nMedical Road, UttarKhan, Uttara, Dhaka");
            builder.setCancelable(true);
            builder.show();
        }
        return super.onOptionsItemSelected(item);
    }
}