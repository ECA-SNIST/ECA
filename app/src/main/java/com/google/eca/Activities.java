package com.google.eca;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.se.omapi.Session;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;

public class Activities extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseAuth auth;
    FirebaseDatabase database=FirebaseDatabase.getInstance();
    private DatabaseReference ref1;
    FirebaseUser user;
    private Context mContext;
    LinearLayout content_activities;


    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activities);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        content_activities=(LinearLayout)findViewById(R.id.content_activities);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(Activities.this);
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);

        // Get the application context
        mContext = getApplicationContext();
        textView=new TextView(mContext);

        auth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();

        //textView=(TextView)findViewById(R.id.test);

        ref1=database.getReference().child("sessions");

        ref1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> items= dataSnapshot.getChildren().iterator();

                Sessions session=new Sessions();

               // ViewGroup parent = (ViewGroup) content_activities.getParent();
                //parent.removeView(content_activities);
                content_activities.removeAllViews();
                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(Activities.this);//continued after while loop

                while(items.hasNext()){
                    DataSnapshot item=items.next();
                    session=item.getValue(Sessions.class);
                    if (TextUtils.isEmpty(session.getTitle()) || TextUtils.isEmpty(session.getDescription()) || TextUtils.isEmpty(session.getStatus()) || TextUtils.isEmpty(session.getStart_date()))
                        ;
                    else {
                        CardView card=new CardView(content_activities.getContext());
                        //Set the CardView layoutParams
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        card.setLayoutParams(params);
                        card.setRadius(9);
                        card.setBackgroundResource(R.drawable.card_style);
                        card.setContentPadding(15, 15, 15, 15);
                        card.setMaxCardElevation(15);
                        card.getPreventCornerOverlap();
                        card.findFocus();
                        card.setPadding(10, 10, 10, 10);
                        card.setCardElevation(9);

   LinearLayout title_layout = new LinearLayout(mContext);
    title_layout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    title_layout.setOrientation(LinearLayout.VERTICAL);

                        TextView tv1 = new TextView(mContext);
                        tv1.setLayoutParams(params);
                        tv1.setText(session.getTitle() + "\n" + session.getDescription() + "\n" + session.getStart_date() + "\n");
                        tv1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
                        tv1.setTextColor(Color.BLACK);
                        tv1.getExtendedPaddingBottom();
                        tv1.setGravity(View.TEXT_ALIGNMENT_CENTER);
                        title_layout.addView(tv1);

    LinearLayout status_layout = new LinearLayout(mContext);
    status_layout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    status_layout.setOrientation(LinearLayout.HORIZONTAL);

                        ImageView imageView = new ImageView(mContext);
                        imageView.setImageResource(R.drawable.red_btn);
                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(15, 15);
                        imageView.setLayoutParams(layoutParams);
                        status_layout.addView(imageView);


                        TextView tv2 = new TextView(mContext);
                        tv2.setLayoutParams(params);
                        tv2.setText(session.getStatus());
                        tv2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
                        tv2.setTextColor(Color.BLACK);
                        tv2.getExtendedPaddingTop();

                        status_layout.addView(tv2);

    card.addView(title_layout);
    card.addView(status_layout);

                        // Finally, add the CardView in root layout
                        if (card.getParent() != null) {
                            ((ViewGroup) card.getParent()).removeView(card); // <- fix
                        }
                        content_activities.addView(card);


                    }

                }

                //notification
                mBuilder.setSmallIcon(R.drawable.red_btn);
                mBuilder.setContentTitle("ECA Session! On "+session.getTitle());
                mBuilder.setContentText("Going to start on "+session.getStart_date());

                Intent notificationIntent = new Intent(Activities.this, Activities.class);
                PendingIntent contentIntent = PendingIntent.getActivity(Activities.this, 0, notificationIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT);
                mBuilder.setContentIntent(contentIntent);

                NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                mNotificationManager.notify(0, mBuilder.build());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            new AlertDialog.Builder(this)
                    .setMessage("Are you sure you want to exit?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Activities.this.finish();
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activities, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_signout) {
            auth.signOut();
            Intent intent = new Intent(Activities.this,Login.class);
            startActivity(intent);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.activities_nav) {
            // Handle the camera action
        } else if (id == R.id.gallery_nav) {

            startActivity(new Intent(Activities.this,Gallery.class));

        } else if (id == R.id.achieve_nav) {

            startActivity(new Intent(Activities.this,Achievements.class));

        } else if (id == R.id.resource_nav) {

            startActivity(new Intent(Activities.this,Resource.class));

        }else if (id == R.id.feed_nav) {

            startActivity(new Intent(Activities.this,Feedback.class));

        } else if (id == R.id.nav_share) {

            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            String shareBodyText = "This is ECA app";
            intent.putExtra(Intent.EXTRA_TEXT, shareBodyText);
            startActivity(Intent.createChooser(intent, "Choose sharing method"));

        } else if (id == R.id.nav_send) {


        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
