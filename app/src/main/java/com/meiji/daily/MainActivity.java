package com.meiji.daily;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.meiji.daily.ui.ZhuanlanFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private long exitTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        replaceFragment(new ZhuanlanFragment());
        navigationView.setCheckedItem(R.id.nav_product);
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        long currentTime = System.currentTimeMillis();
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else if ((currentTime - exitTime) < 2000) {
            super.onBackPressed();
        } else {
            Snackbar.make(drawerLayout, "再按一次退出程序", Snackbar.LENGTH_SHORT).show();
            exitTime = currentTime;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_copyright) {
            MaterialDialog dialog = new MaterialDialog.Builder(MainActivity.this)
                    .title(R.string.action_copyright)
                    .content(R.string.copyright_content)
                    .neutralText(R.string.got_it)
                    .onNeutral(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            dialog.dismiss();
                        }
                    }).build();

            dialog.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_product) {

            ZhuanlanFragment fragment = ZhuanlanFragment.newInstance();
            fragment.setType(ZhuanlanFragment.TYPE_PRODUCT);
            replaceFragment(fragment);

        } else if (id == R.id.nav_life) {

            ZhuanlanFragment fragment = ZhuanlanFragment.newInstance();
            fragment.setType(ZhuanlanFragment.TYPE_LIFE);
            replaceFragment(fragment);

        } else if (id == R.id.nav_music) {

            ZhuanlanFragment fragment = ZhuanlanFragment.newInstance();
            fragment.setType(ZhuanlanFragment.TYPE_MUSIC);
            replaceFragment(fragment);

        } else if (id == R.id.nav_emotion) {

            ZhuanlanFragment fragment = ZhuanlanFragment.newInstance();
            fragment.setType(ZhuanlanFragment.TYPE_EMOTION);
            replaceFragment(fragment);

        } else if (id == R.id.nav_profession) {

            ZhuanlanFragment fragment = ZhuanlanFragment.newInstance();
            fragment.setType(ZhuanlanFragment.TYPE_FINANCE);
            replaceFragment(fragment);

        } else if (id == R.id.nav_zhihu) {

            ZhuanlanFragment fragment = ZhuanlanFragment.newInstance();
            fragment.setType(ZhuanlanFragment.TYPE_ZHIHU);
            replaceFragment(fragment);

        } else if (id == R.id.nav_user_define) {


        } else if (id == R.id.nav_about) {

        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void replaceFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.content_main, fragment).commit();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}
