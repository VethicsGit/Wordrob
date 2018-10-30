package com.pro.wardrobe.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pro.wardrobe.Adapter.DashboardListAdapter;
import com.pro.wardrobe.ApiHelper.APIClient;
import com.pro.wardrobe.ApiHelper.APIInterface;
import com.pro.wardrobe.ApiResponse.UpdateDeviceTokenResponse.UpdateDeviceTokenResponse;
import com.pro.wardrobe.Extra.Dashboard_list_model;
import com.pro.wardrobe.Extra.SlideHolder;
import com.pro.wardrobe.Fragment.Favorite;
import com.pro.wardrobe.Fragment.Fragment_Designers;
import com.pro.wardrobe.Fragment.Fragment_Home;
import com.pro.wardrobe.Fragment.Fragment_Notification;
import com.pro.wardrobe.Fragment.Fragment_OfferZone;
import com.pro.wardrobe.Fragment.Fragment_category;
import com.pro.wardrobe.Fragment.Fragment_settings;
import com.pro.wardrobe.R;
import com.volcaniccoder.bottomify.BottomifyNavigationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Dashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    ListView dashboard_nav_listview;
    TextView logout;
    TextView title;
    ImageView dashboard_search;

    ImageView dashboard_toolbar_back,bottomnav_toolbar_back;
    RelativeLayout sidebar_toolbar;
    RelativeLayout bottomnav_toolbar;


    LinearLayout bottom_nav_categories,bottom_nav_designer,bottom_nav_home,bottom_nav_favorites,bottom_nav_notification;
    TextView bottom_nav_cat_text,bottom_nav_des_text,bottom_nav_fav_text,bottom_nav_not_text;
    ImageView bottom_nav_cat_img,bottom_nav_des_img,bottom_nav_hom_img,bottom_nav_fav_img,bottom_nav_not_img;

    public boolean isItemFragmentOnTop=false;
    BottomifyNavigationView bottomify_nav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        title = toolbar.findViewById(R.id.title);
        Typeface facebold = Typeface.createFromAsset(getAssets(),
                "Philosopher_Bold.ttf");
        title.setTypeface(facebold);

        final SlideHolder drawer = findViewById(R.id.drawer_layout);

        bottom_nav_categories=findViewById(R.id.bottom_nav_categories);
        bottom_nav_designer=findViewById(R.id.bottom_nav_designer);
        bottom_nav_home=findViewById(R.id.bottom_nav_home);
        bottom_nav_favorites=findViewById(R.id.bottom_nav_favorites);
        bottom_nav_notification=findViewById(R.id.bottom_nav_notification);

        bottom_nav_cat_img=findViewById(R.id.bottom_nav_cat_img);
        bottom_nav_des_img=findViewById(R.id.bottom_nav_des_img);
        bottom_nav_hom_img=findViewById(R.id.bottom_nav_hom_img);
        bottom_nav_fav_img=findViewById(R.id.bottom_nav_fav_img);
        bottom_nav_not_img=findViewById(R.id.bottom_nav_not_img);


                 bottom_nav_cat_text=findViewById(R.id.bottom_nav_cat_text);
                 bottom_nav_des_text=findViewById(R.id.bottom_nav_des_text);

                 bottom_nav_fav_text=findViewById(R.id.bottom_nav_fav_text);
                 bottom_nav_not_text=findViewById(R.id.bottom_nav_not_text);



        drawer.setAllowInterceptTouch(false);
        bottomnav_toolbar=toolbar.findViewById(R.id.bottomnav_toolbar);
        sidebar_toolbar=toolbar.findViewById(R.id.sidebar_toolbar);
        dashboard_toolbar_back=toolbar.findViewById(R.id.dashboard_toolbar_back);
        dashboard_right_appbar=toolbar.findViewById(R.id.dashboard_right_appbar);
        bottomnav_toolbar_back=toolbar.findViewById(R.id.bottomnav_toolbar_back);


        bottomify_nav=findViewById(R.id.bottomify_nav);
        dashboard_toolbar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        bottomnav_toolbar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


       /* ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawer.isDrawerVisible(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                } else {
                    drawer.openDrawer(GravityCompat.START);
                }
            }
        });

        toggle.setDrawerIndicatorEnabled(false);
        Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.mipmap.menu_toggle, getTheme());
        toggle.setHomeAsUpIndicator(drawable);*/

        ImageView dashboard_toggle = toolbar.findViewById(R.id.dashboard_toggle);
        dashboard_toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.toggle();
            }
        });
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);


        navigationView.setNavigationItemSelectedListener(this);

        ImageView mybag=toolbar.findViewById(R.id.dashboard_Mybag);
        dashboard_search=toolbar.findViewById(R.id.dashboard_search);

        dashboard_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        mybag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentbag = new Intent(getApplicationContext(), Activty_MyBag.class);
                startActivity(intentbag);
            }
        });

        dashboard_nav_listview = navigationView.findViewById(R.id.dashboard_nav_listview);
        TextView textView=navigationView.getHeaderView(0).findViewById(R.id.textView);

        Typeface face = Typeface.createFromAsset(getAssets(),
                "Philosopher_Bold.ttf");
        textView.setTypeface(face);


        logout = navigationView.findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                SharedPreferences preferences = getSharedPreferences("LoginStatus", MODE_PRIVATE);
                APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
                Call<UpdateDeviceTokenResponse> call1 = apiInterface.Logout(preferences.getString("user_id", ""), preferences.getString("firebase_id",""),  preferences.getString("token", ""));
                call1.enqueue(new Callback<UpdateDeviceTokenResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<UpdateDeviceTokenResponse> call, @NonNull Response<UpdateDeviceTokenResponse> response) {
                        UpdateDeviceTokenResponse changePassResponse = response.body();
                        List<com.pro.wardrobe.ApiResponse.UpdateDeviceTokenResponse.Response> responseList = changePassResponse.getResponse();
                        com.pro.wardrobe.ApiResponse.UpdateDeviceTokenResponse.Response res = responseList.get(0);
                        if (res.getStatus().equals("true")) {

                            SharedPreferences preferences = getSharedPreferences("LoginStatus", MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.clear().apply();

                            Intent intent = new Intent(getApplicationContext(), Login.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        } else {
                            Toast.makeText(Dashboard.this, res.getResponseMsg(), Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onFailure(@NonNull Call<UpdateDeviceTokenResponse> call, @NonNull Throwable t) {

                    }
                });




            }
        });

        /*dashboard_nav_listview.addFooterView(LayoutInflater.from(getApplicationContext()).inflate(R.layout.dashboard_list_footer,null));*/
        List<Dashboard_list_model> modelList = new ArrayList<>();
        modelList.add(new Dashboard_list_model(getResources().getDrawable(R.mipmap.home_brown), "HOME"));
        modelList.add(new Dashboard_list_model(getResources().getDrawable(R.mipmap.people), "PROFILE"));
        modelList.add(new Dashboard_list_model(getResources().getDrawable(R.mipmap.order_history), "ORDER HISTORY"));
        modelList.add(new Dashboard_list_model(getResources().getDrawable(R.mipmap.my_bag), "My bag"));
        modelList.add(new Dashboard_list_model(getResources().getDrawable(R.mipmap.heart_brown), "Favorite"));
        modelList.add(new Dashboard_list_model(getResources().getDrawable(R.mipmap.offer_zone), "Offer Zone"));
        modelList.add(new Dashboard_list_model(getResources().getDrawable(R.mipmap.settings), "Settings"));
        modelList.add(new Dashboard_list_model(getResources().getDrawable(R.mipmap.mail_brown), "Contact us"));

        DashboardListAdapter adapter = new DashboardListAdapter(modelList, getApplicationContext());
        dashboard_nav_listview.setAdapter(adapter);

//        getSupportFragmentManager().beginTransaction().replace(R.id.viewpager, new Fragment_Home(title,dashboard_search)).commit();
        getSupportFragmentManager().beginTransaction().replace(R.id.viewpager, new Fragment_Designers(1,title)).commit();

        dashboard_nav_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                switch (i) {
                    case 0:
                        title.setText("The Wardrobe");
//                        ft.replace(R.id.viewpager, new Fragment_Home(title,dashboard_search)).commit();
                        bottomify_nav.setActiveNavigationIndex(2);
//                        bottomify_nav.getChildAt(2).setSelected(true);
//                        ft.replace(R.id.viewpager,new Fragment_Designers(1)).commit();
                        break;
                    case 1:
//                        title.setText("Profile");
//                        ft.addToBackStack("Dashboard");
//                        ft.replace(R.id.viewpager, new Profile()).commit();
                        Intent intent=new Intent(getApplicationContext(),Profile_.class);
                        startActivity(intent);
                        break;
                    case 2:
//                        title.setText("Order History");
//                        ft.addToBackStack("Dashboard");
//                        ft.replace(R.id.viewpager, new OrderHistory()).commit();

                        Intent intent4=new Intent(getApplicationContext(),OrderHistory.class);
                        startActivity(intent4);
                        break;
                    case 3:
                        Intent intentbag = new Intent(getApplicationContext(), Activty_MyBag.class);
                        startActivity(intentbag);
                        break;

                    case 4:
                        title.setText("Favorite");
                        ft.addToBackStack("Dashboard");
                        bottomify_nav.setActiveNavigationIndex(3);
//                        bottomify_nav.getChildAt(3).setSelected(true);
//                        ft.replace(R.id.viewpager, new Favorite()).commit();
                        break;
                    case 5:
//                        title.setText("Offers");
//                        ft.addToBackStack("Dashboard");
//                        ft.replace(R.id.viewpager, new Fragment_OfferZone()).commit();
//                        break;

                        Intent intent3=new Intent(getApplicationContext(),OfferZone.class);
                        startActivity(intent3);
                        break;
                    case 6:
//                        title.setText("Settings");
//                        ft.addToBackStack("Dashboard");
//                        ft.replace(R.id.viewpager, new Fragment_settings(title)).commit();

                        Intent intent2=new Intent(getApplicationContext(),Setting.class);
                        startActivity(intent2);
                        break;
                    case 7:
//                        title.setText("Contact Us");
//                        ft.addToBackStack("Dashboard");
//                        ft.replace(R.id.viewpager, new ContactUs()).commit();

                        Intent intent1 = new Intent(getApplicationContext(),ContactUs_.class);
                        startActivity(intent1);
                        break;
                }

//                drawer.closeDrawer(GravityCompat.START);
                drawer.toggle();

            }
        });

        final ImageView dashboard_arc=findViewById(R.id.dashboard_arc);
        final LinearLayout dashboard_arc_layout=findViewById(R.id.dashboard_arc_layout);




        dashboard_arc_layout.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Resources res = getResources();
        final int newColor = res.getColor(R.color.colorseleced);
        dashboard_arc.setColorFilter(newColor, PorterDuff.Mode.SRC_ATOP);

        bottomify_nav.getChildAt(0).performClick();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        title.setText("Designers");
        ft.addToBackStack("Dashboard");
        ft.replace(R.id.viewpager, new Fragment_Home()).commit();
    }
});


        bottom_nav_cat_img.setImageDrawable(getResources().getDrawable(R.drawable.categories));
        bottom_nav_des_img.setImageDrawable(getResources().getDrawable(R.drawable.designers));
        bottom_nav_hom_img.setImageDrawable(getResources().getDrawable(R.drawable.home_gold));
        bottom_nav_not_img.setImageDrawable(getResources().getDrawable(R.drawable.notification));
        bottom_nav_fav_img.setImageDrawable(getResources().getDrawable(R.drawable.favourite));


        bottom_nav_cat_text.setTextColor(Color.parseColor("#383051"));
        bottom_nav_des_text.setTextColor(Color.parseColor("#383051"));
        bottom_nav_fav_text.setTextColor(Color.parseColor("#383051"));
        bottom_nav_not_text.setTextColor(Color.parseColor("#383051"));

        bottom_nav_categories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                Typeface custom = Typeface.createFromAsset(getAssets(),"Roboto_Medium.ttf");
                title.setTypeface(custom);
                title.setText("Category");
                    ft.addToBackStack("Dashboard");
                ft.replace(R.id.viewpager, new Fragment_category()).commit();

                bottom_nav_cat_img.setImageDrawable(getResources().getDrawable(R.drawable.categories_gold));
                bottom_nav_des_img.setImageDrawable(getResources().getDrawable(R.drawable.designers));
                bottom_nav_hom_img.setImageDrawable(getResources().getDrawable(R.drawable.home));
                bottom_nav_not_img.setImageDrawable(getResources().getDrawable(R.drawable.notification));
                bottom_nav_fav_img.setImageDrawable(getResources().getDrawable(R.drawable.favourite));

                bottom_nav_cat_text.setTextColor(Color.parseColor("#cfaa42"));
                bottom_nav_des_text.setTextColor(Color.parseColor("#383051"));
                bottom_nav_fav_text.setTextColor(Color.parseColor("#383051"));
                bottom_nav_not_text.setTextColor(Color.parseColor("#383051"));
            }
        });
        bottom_nav_designer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                Typeface custom1 = Typeface.createFromAsset(getAssets(),"Roboto_Medium.ttf");
                title.setTypeface(custom1);

                title.setText("Designers");
                    ft.addToBackStack("Dashboard");
                ft.replace(R.id.viewpager, new Fragment_Designers(0,title)).commit();

                bottom_nav_cat_img.setImageDrawable(getResources().getDrawable(R.drawable.categories));
                bottom_nav_des_img.setImageDrawable(getResources().getDrawable(R.drawable.designers_gold));
                bottom_nav_hom_img.setImageDrawable(getResources().getDrawable(R.drawable.home));
                bottom_nav_not_img.setImageDrawable(getResources().getDrawable(R.drawable.notification));
                bottom_nav_fav_img.setImageDrawable(getResources().getDrawable(R.drawable.favourite));

                bottom_nav_cat_text.setTextColor(Color.parseColor("#383051"));
                bottom_nav_des_text.setTextColor(Color.parseColor("#cfaa42"));
                bottom_nav_fav_text.setTextColor(Color.parseColor("#383051"));
                bottom_nav_not_text.setTextColor(Color.parseColor("#383051"));
            }
        });
        bottom_nav_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                Typeface custom5 =Typeface.createFromAsset(getAssets(),"Philosopher_Regular.ttf");
                title.setTypeface(custom5);
                title.setText("The Wardbrobe");
                    ft.addToBackStack("Dashboard");
//                    ft.replace(R.id.viewpager, new Fragment_Home(title,dashboard_search)).commit();
                ft.replace(R.id.viewpager, new Fragment_Designers(1,title)).commit();

                bottom_nav_cat_img.setImageDrawable(getResources().getDrawable(R.drawable.categories));
                bottom_nav_des_img.setImageDrawable(getResources().getDrawable(R.drawable.designers));
                bottom_nav_hom_img.setImageDrawable(getResources().getDrawable(R.drawable.home_gold));
                bottom_nav_not_img.setImageDrawable(getResources().getDrawable(R.drawable.notification));
                bottom_nav_fav_img.setImageDrawable(getResources().getDrawable(R.drawable.favourite));


                bottom_nav_cat_text.setTextColor(Color.parseColor("#383051"));
                bottom_nav_des_text.setTextColor(Color.parseColor("#383051"));
                bottom_nav_fav_text.setTextColor(Color.parseColor("#383051"));
                bottom_nav_not_text.setTextColor(Color.parseColor("#383051"));
            }
        });
        bottom_nav_favorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                Typeface custom2 = Typeface.createFromAsset(getAssets(),"Roboto_Medium.ttf");
                title.setTypeface(custom2);

                title.setText("Favorite");
                    ft.addToBackStack("Dashboard");
                ft.replace(R.id.viewpager, new Favorite()).commit();

                bottom_nav_cat_img.setImageDrawable(getResources().getDrawable(R.drawable.categories));
                bottom_nav_des_img.setImageDrawable(getResources().getDrawable(R.drawable.designers));
                bottom_nav_hom_img.setImageDrawable(getResources().getDrawable(R.drawable.home));
                bottom_nav_not_img.setImageDrawable(getResources().getDrawable(R.drawable.notification));
                bottom_nav_fav_img.setImageDrawable(getResources().getDrawable(R.drawable.favourite_gold));

                bottom_nav_cat_text.setTextColor(Color.parseColor("#383051"));
                bottom_nav_des_text.setTextColor(Color.parseColor("#383051"));
                bottom_nav_fav_text.setTextColor(Color.parseColor("#cfaa42"));
                bottom_nav_not_text.setTextColor(Color.parseColor("#383051"));


            }
        });

        bottom_nav_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                Typeface custom3 = Typeface.createFromAsset(getAssets(),"Roboto_Medium.ttf");
                title.setTypeface(custom3);

                title.setText("Notifications");
                ft.addToBackStack("Dashboard");
                ft.replace(R.id.viewpager, new Fragment_Notification()).commit();

                bottom_nav_cat_img.setImageDrawable(getResources().getDrawable(R.drawable.categories));
                bottom_nav_des_img.setImageDrawable(getResources().getDrawable(R.drawable.designers));
                bottom_nav_hom_img.setImageDrawable(getResources().getDrawable(R.drawable.home));
                bottom_nav_not_img.setImageDrawable(getResources().getDrawable(R.drawable.notification_gold));
                bottom_nav_fav_img.setImageDrawable(getResources().getDrawable(R.drawable.favourite));

                bottom_nav_cat_text.setTextColor(Color.parseColor("#383051"));
                bottom_nav_des_text.setTextColor(Color.parseColor("#383051"));
                bottom_nav_fav_text.setTextColor(Color.parseColor("#383051"));
                bottom_nav_not_text.setTextColor(Color.parseColor("#cfaa42"));


            }
        });

      /*  bottomify_nav.setOnNavigationItemChangedListener(new OnNavigationItemChangeListener() {
            @Override
            public void onNavigationItemChanged(BottomifyNavigationView.NavigationItem navigationItem) {
                dashboard_arc.setColorFilter(null);
                if (navigationItem.component1()==0){
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    Typeface custom = Typeface.createFromAsset(getAssets(),"Roboto_Regular.ttf");
                    title.setTypeface(custom);
                    title.setText("Categ ory");
//                    ft.addToBackStack("Dashboard");
                    ft.replace(R.id.viewpager, new Fragment_category()).commit();
                }if (navigationItem.component1()==1){
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    Typeface custom1 = Typeface.createFromAsset(getAssets(),"Roboto_Regular.ttf");
                    title.setTypeface(custom1);

                    title.setText("Designers");
//                    ft.addToBackStack("Dashboard");
                    ft.replace(R.id.viewpager, new Fragment_Designers(0,title)).commit();
                }if (navigationItem.component1()==2){
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    Typeface custom5 =Typeface.createFromAsset(getAssets(),"Philosopher_Regular.ttf");
                    title.setTypeface(custom5);
                    title.setText("The Wardbrobe");
//                    ft.addToBackStack("Dashboard");
//                    ft.replace(R.id.viewpager, new Fragment_Home(title,dashboard_search)).commit();
                    ft.replace(R.id.viewpager, new Fragment_Designers(1,title)).commit();
                }if (navigationItem.component1()==3){
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    Typeface custom2 = Typeface.createFromAsset(getAssets(),"Roboto_Regular.ttf");
                    title.setTypeface(custom2);

                    title.setText("Favorite");
//                    ft.addToBackStack("Dashboard");
                    ft.replace(R.id.viewpager, new Favorite()).commit();
                }if (navigationItem.component1()==4){
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    Typeface custom3 = Typeface.createFromAsset(getAssets(),"Roboto_Regular.ttf");
                    title.setTypeface(custom3);

                    title.setText("Notifications");
//                    ft.addToBackStack("Dashboard");
                    ft.replace(R.id.viewpager, new Fragment_Notification()).commit();
                }

            }
        });*/



       /* bottom_nav_categories = findViewById(R.id.bottom_nav_categories);
        bottom_nav_categories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                title.setText("Category");
                ft.addToBackStack("Dashboard");
                ft.replace(R.id.viewpager, new Fragment_category()).commit();
            }
        });
        bottom_nav_designer = findViewById(R.id.bottom_nav_designer);
        final ImageView bottomnav_favorite_img = findViewById(R.id.bottomnav_favorite_img);
        bottom_nav_designer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                title.setText("Designers");
                ft.addToBackStack("Dashboard");
                ft.replace(R.id.viewpager, new Fragment_Designers()).commit();
                bottomnav_favorite_img.setColorFilter(Color.GREEN);
            }
        });
        bottom_nav_favorites = findViewById(R.id.bottom_nav_favorites);
        bottom_nav_favorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentfav = new Intent(getApplicationContext(), Favorite.class);
                startActivity(intentfav);
            }
        });*/




    }

    @Override
    public void onBackPressed() {
       /* DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }*/

//       super.onBackPressed();
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.dashboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
 /*       if (id == R.id.action_settings) {
            return true;
        }
*/

        if (id == R.id.action_bag) {
            Intent intentbag = new Intent(getApplicationContext(), Activty_MyBag.class);
            startActivity(intentbag);
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    LinearLayout dashboard_right_appbar;
    public void toggle(int i){
       /* if (isItemFragmentOnTop){
            getFragmentManager().popBackStack();
            isItemFragmentOnTop = false;
        } else {*/
           if (i==1){
               sidebar_toolbar.setVisibility(View.GONE);
               bottomnav_toolbar.setVisibility(View.VISIBLE);
               dashboard_toolbar_back.setVisibility(View.GONE);
               dashboard_right_appbar.setVisibility(View.GONE);
               dashboard_search.setVisibility(View.VISIBLE);
               bottomify_nav.setVisibility(View.VISIBLE);
           }else if (i==0){
               bottomnav_toolbar.setVisibility(View.GONE);
               sidebar_toolbar.setVisibility(View.VISIBLE);
               dashboard_toolbar_back.setVisibility(View.GONE);
               dashboard_right_appbar.setVisibility(View.VISIBLE);
               dashboard_search.setVisibility(View.VISIBLE);
               bottomify_nav.setVisibility(View.VISIBLE);
           }else if (i==2){
               bottomnav_toolbar.setVisibility(View.GONE);
               sidebar_toolbar.setVisibility(View.VISIBLE);
               dashboard_toolbar_back.setVisibility(View.VISIBLE);
               dashboard_right_appbar.setVisibility(View.GONE);
               dashboard_search.setVisibility(View.VISIBLE);
               bottomify_nav.setVisibility(View.VISIBLE);
           }else if (i==3){
               sidebar_toolbar.setVisibility(View.VISIBLE);
               bottomnav_toolbar.setVisibility(View.GONE);
               dashboard_toolbar_back.setVisibility(View.GONE);
               dashboard_right_appbar.setVisibility(View.VISIBLE);
               dashboard_search.setVisibility(View.GONE);
               bottomify_nav.setVisibility(View.VISIBLE);
           }else if (i==5){
               bottomify_nav.setVisibility(View.GONE);
           }
//        }
    }

    public void setFrament(int position){
        if (position==0){
        }if (position==1){

        }if (position==2){
        }if (position==3){
        }if (position==4){

        }
    }

}
