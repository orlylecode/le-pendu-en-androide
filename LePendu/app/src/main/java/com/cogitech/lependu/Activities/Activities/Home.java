package com.cogitech.lependu.Activities.Activities;

import android.content.Intent;
import android.os.Build;
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
import android.widget.SeekBar;
import android.widget.TextView;

import com.cogitech.lependu.Activities.Models.Terminal;
import com.cogitech.lependu.R;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener  {
    private Terminal terminal = new Terminal("","",0,0,0, 0,"");
    private  double montantVal = 0;
    private  double montantAG = 0;
    private  double margeVal= 0;
    private  double essaiesVal = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        terminal = getIntent().getExtras().getParcelable("terminal");

        final EditText montant = findViewById(R.id.montant);

        final EditText montantAgagner = findViewById(R.id.montantAgagner);

        final Button miser = findViewById(R.id.mise);
        miser.setEnabled(false);

        final TextView textViewMarge = findViewById(R.id.textViewMarge);
        textViewMarge.setVisibility(View.INVISIBLE);

        final SeekBar marge = findViewById(R.id.marge);
        marge.setVisibility(View.INVISIBLE);

        final TextView textViewEssaie = findViewById(R.id.textViewEssay);
        textViewEssaie.setVisibility(View.INVISIBLE);

        final SeekBar essaie =findViewById(R.id.essay);
        essaie.setVisibility(View.INVISIBLE);

        montantAgagner.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(s.toString().length() >1 ){
                    if (montant.getText().toString().length() >1 && Integer.parseInt(montant.getText().toString()) <= Integer.parseInt(s.toString()) ){
                        marge.setVisibility(View.VISIBLE);
                        textViewMarge.setVisibility(View.VISIBLE);
                        textViewEssaie.setVisibility(View.VISIBLE);
                        essaie.setVisibility(View.VISIBLE);
                        miser.setEnabled(true);

                        //proportions(marge,essaie,montant);
                        montantVal = Integer.parseInt(montant.getText().toString());
                        montantAG = Integer.parseInt(s.toString());
                        margeVal= marge.getProgress();
                        essaiesVal = essaie.getProgress();

                        //montantVal = 25 * essaiesVal ;

                        margeVal = 5*(montantAG/montantVal);

                        essaiesVal = (int) (Math.log(margeVal)/Math.log(2));

                        //essaiesVal =(int) margeVal/50;

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            marge.setProgress((int) margeVal, true);
                            textViewMarge.setText("Marge: 0 a "+(int) margeVal);
                        }else {
                            marge.setProgress((int) margeVal);
                            textViewMarge.setText("Marge: 0 a "+(int) margeVal);
                        }

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            essaie.setProgress((int) essaiesVal, true);
                            textViewMarge.setText("Marge: 0 a "+ (int)margeVal);
                        }else {
                            essaie.setProgress((int) essaiesVal);
                            textViewEssaie.setText("Nombre essay: "+ (int)essaiesVal);
                        }
                        // montant.setText(montantVal);

                    }else {
                        marge.setVisibility(View.INVISIBLE);
                        textViewMarge.setVisibility(View.VISIBLE);
                        textViewEssaie.setVisibility(View.VISIBLE);
                        essaie.setVisibility(View.INVISIBLE);
                        miser.setEnabled(false);
                        textViewEssaie.setText("Nombre essay: Error");
                        textViewMarge.setText("Marge: Error");
                    }
                }else {
                    textViewMarge.setVisibility(View.VISIBLE);
                    textViewEssaie.setVisibility(View.VISIBLE);
                    textViewEssaie.setText("Nombre essay: Error");
                    textViewMarge.setText("Marge: Error");
                    marge.setVisibility(View.INVISIBLE);
                    essaie.setVisibility(View.INVISIBLE);
                    miser.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        miser.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                terminal.setEssai((int) essaiesVal);
                terminal.setMarge((int) margeVal);
                terminal.setMontant(montantVal);
                terminal.setMontantAG(montantAG);

                Intent play = new Intent(Home.this, Play.class);
                play.putExtra("terminal" , terminal);
                startActivity(play);
            }
        });

        marge.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser) {
                // TODO Auto-generated method stub
/*              //  proportions(marge,essaie,montant);
                int margeVal= progress;
                int montantVal = 0 ;//Integer.parseInt(s.toString());
                int essaiesVal = 0; //essaie.getProgress();
                essaiesVal =(int) margeVal/50;
                //essaiesVal = (int) montantVal/25;
                montantVal = 25 * essaiesVal ;
                margeVal = 50*essaiesVal ;

  *//*              if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    marge.setProgress(margeVal , true);
                    textViewMarge.setText("Marge: 0 a "+margeVal);
                }else {
                    marge.setProgress(margeVal);
                    textViewMarge.setText("Marge: 0 a "+margeVal);
                }*//*

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    essaie.setProgress(essaiesVal , true);
                    textViewMarge.setText("Marge: 0 a "+margeVal);
                }else {
                    essaie.setProgress(essaiesVal);
                    textViewEssaie.setText("Nombre essay: "+essaiesVal);
                }
                 montant.setText(montantVal);*/
            }
        });

        essaie.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser) {
                // TODO Auto-generated method stub
/*                //proportions(marge,essaie,montant);
                int essaiesVal = progress;
                int montantVal = 0 ;//Integer.parseInt(s.toString());
                int margeVal = 0; //essaie.getProgress();

                margeVal = 50*essaiesVal ;
                //essaiesVal =(int) margeVal/50;
                //essaiesVal = (int) montantVal/25;
                montantVal = 25 * essaiesVal ;

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    marge.setProgress(margeVal , true);
                    textViewMarge.setText("Marge: 0 a "+margeVal);
                }else {
                    marge.setProgress(margeVal);
                    textViewMarge.setText("Marge: 0 a "+margeVal);
                }

               *//* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    essaie.setProgress(essaiesVal , true);
                    textViewMarge.setText("Marge: 0 a "+margeVal);
                }else {
                    essaie.setProgress(essaiesVal);
                    textViewEssaie.setText("Nombre essay: "+essaiesVal);
                }*//*

                montant.setText(montantVal);*/
            }
        });

    }


    public void  proportions(SeekBar marge , SeekBar essaie, EditText montant ){
        int montantVal = Integer.parseInt(montant.getText().toString());
        int margeVal= marge.getProgress();
        int essaiesVal = essaie.getProgress();

        montantVal = 25 * essaiesVal ;
        essaiesVal = (int) montantVal/25;

        margeVal = 50*essaiesVal ;
        essaiesVal =(int) margeVal/50;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                marge.setProgress(margeVal , true);
            }else {
                marge.setProgress(margeVal);
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                essaie.setProgress(essaiesVal , true);
            }else {
                essaie.setProgress(essaiesVal);
            }
            montant.setText(montantVal);
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


}
