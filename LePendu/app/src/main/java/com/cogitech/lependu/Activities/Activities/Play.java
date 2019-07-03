package com.cogitech.lependu.Activities.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cogitech.lependu.Activities.Models.Terminal;
import com.cogitech.lependu.R;

public class Play extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener {
private Terminal terminal ;
    private boolean fin = false;
    private boolean rejouer = false;

    private  int essaiTotal  = 0;
    private  int essaiCourant = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        terminal = getIntent().getExtras().getParcelable("terminal");
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final int nomberMistere = (int)(Math.random()*terminal.getMarge()+1);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);

       // NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                Play.this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
      //  navigationView.setNavigationItemSelectedListener(Play.this);


        TextView marge = findViewById(R.id.marge);
        marge.setText("marge 0 a:" + terminal.getMarge());

        final TextView essay = findViewById( R.id.essay);

        essaiTotal  = terminal.getEssai();
        essaiCourant = terminal.getEssai();

        final TextView message = findViewById(R.id.message);
        message.setVisibility(View.INVISIBLE);

        final EditText value =  findViewById(R.id.value);

        final Button valid =  findViewById(R.id.mise);
        valid.setEnabled(false);
        final  TextView stat = findViewById(R.id.stat);

        stat.setText(essaiCourant +" / "+essaiCourant);
        essay.setText("essaies restantes : "+essaiCourant+" sur "+essaiTotal);

        value.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                valid.setEnabled(s.toString().length() > 0);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        valid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(Play.this ,""+nomberMistere,Toast.LENGTH_SHORT).show();
                int val = Integer.parseInt(value.getText().toString());
                if (! rejouer){


                if (essaiCourant > 0){
                    if (nomberMistere == val){
                        essaiCourant = 0;
                        message.setVisibility(View.VISIBLE);
                        message.setText("Bravo vous avez trouvee le nombre mistere. vous gagne donc: "+terminal.getMontantAG() +" FCFA");
                        message.setTextColor(Color.GREEN);
                        addButon(val,Color.GREEN);

                        //  message.setTextColor(1);
                        valid.setEnabled(false);
                        value.setEnabled(false);
                        essay.setText("essais restants : "+essaiCourant+" sur "+essaiTotal);
                        stat.setText(essaiCourant+" / "+essaiTotal);
                        fin= true;
                    }else {
                        if (nomberMistere > val){
                            message.setVisibility(View.VISIBLE);
                            message.setText(val +" est plus petit que le nombre mistere");
                            message.setTextColor(Color.WHITE);

                            //message.setTextColor(1);
                            essaiCourant -- ;
                            stat.setText(essaiCourant+" / "+essaiTotal);
                            essay.setText("essais restants : "+essaiCourant+" sur "+essaiTotal);
                            addButon(val,Color.WHITE);
                        }else {
                            message.setVisibility(View.VISIBLE);
                            message.setText(val +" est plus grand que le nombre mistere");
                            message.setTextColor(Color.YELLOW);
                            addButon(val,Color.YELLOW);

                            // message.setTextColor(10);
                            essaiCourant -- ;

                            stat.setText(essaiCourant+" / "+essaiTotal);
                            essay.setText("essais restants : "+essaiCourant+" sur "+essaiTotal);

                        }
                    }
                }else {
                    valid.setEnabled(false);
                    value.setEnabled(false);
                    essay.setText("essais restants : "+essaiCourant+"sur"+essaiTotal);
                    stat.setText(essaiCourant +" / "+essaiTotal);
                    message.setVisibility(View.VISIBLE);
                    message.setText("Nombre d'essais eppuisee vouz avez perdu !!!. le nombre mistere etait : "+nomberMistere );
                    message.setTextColor(Color.RED);
                    fin=true ;
                }

                if (fin){
                    valid.setEnabled(true);
                    value.setEnabled(true);
                    valid.setText("rejouer");
                    rejouer = true;

                }

                }else {
                    Intent home = new Intent(Play.this , Home.class);

                    home.putExtra("terminal", terminal);

                    startActivity(home);
                }
                value.setText("");
            }

        });


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
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
        if (id == R.id.action_settings) {
            Intent home = new Intent(this,  MainActivity.class);
            startActivity(home);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            Intent home = new Intent(this,  Home.class);
            startActivity(home);
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_tools) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public  void  addButon(int val , int color){
        LinearLayout layout = (LinearLayout) findViewById(R.id.ll);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                50,
                100
        );

        params.setMargins(3, 3, 3, 0);

        Button etiquete = new Button(this);
        etiquete.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        etiquete.setText(""+val);
        etiquete.setBackgroundColor(color);
        etiquete.setEnabled(false);
        etiquete.setTextColor(Color.BLACK);
        layout.addView(etiquete , params);
    }


}
