package com.cogitech.lependu.Activities.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.Toast;

import com.cogitech.lependu.Activities.Models.Terminal;
import com.cogitech.lependu.Activities.asynctask.GetTerminalAT;
import com.cogitech.lependu.R;

public class MainActivity extends AppCompatActivity implements GetTerminalAT.GetTerminalATResult {
    public String  DATEBASE="LePendu";
    public String  passwordString=null;
    public String  phoneString=null;
    public Terminal terminal = new Terminal("","",0 , 0,0,0,"");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Connected();

        phoneString=get("phone");
        passwordString=get("password");

        if (phoneString != null && passwordString!=null){
            Toast.makeText(MainActivity.this ," phone= "+get("phone") +"pass="+get(" password" ),Toast.LENGTH_SHORT).show();

            terminal.setPhone(phoneString);
            terminal.setPassword(passwordString);

            Intent home = new Intent(MainActivity.this, Home.class);

            home.putExtra("terminal", terminal);

            startActivity(home);
            finish();
        }



        final ProgressBar bar = findViewById(R.id.progressBar);
        bar.setVisibility(View.INVISIBLE);

        final Button connexion = findViewById(R.id.connexion);
        connexion.setEnabled(false);

        final EditText password = findViewById(R.id.password);
        password.setEnabled(false);

        final EditText phone = findViewById(R.id.phone);


        phone.addTextChangedListener(new TextWatcher() {


            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Connected();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Connected();

                if (s.toString().length()>0){
                  password.setEnabled(true);
                }else {
                    password.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                Connected();
            }
        });

        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (s.toString().length() >0 ){
                        connexion.setEnabled(true);
                }else {
                        connexion.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        connexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                phoneString = phone.getText().toString();
                passwordString = password.getText().toString();

                Toast.makeText(MainActivity.this ,phoneString +" se connecte",Toast.LENGTH_SHORT).show();
                final ProgressDialog dialog = ProgressDialog.show(MainActivity.this, "", "Loading. Please wait...", true);
                //dialog.dismiss();
                Thread t = new Thread(new Runnable(){
                    public void run() {
                        save("password", passwordString);
                        save("phone" , phoneString);
                        new GetTerminalAT(MainActivity.this , terminal).execute();
                        while (terminal.getPhone().isEmpty()){

                            try{
                                Thread.sleep(5000);
                                terminal.setPhone("123");
                            } catch (InterruptedException e) {
                                // no-op
                            }
                        }
                        if (terminal.getPhone().isEmpty()){
                            dialog.dismiss();
                            //terminal.setPhone(phoneString);
                            //terminal.setPassword(passwordString);
                            Intent home = new Intent(MainActivity.this, Home.class);

                            home.putExtra("terminal", terminal);
                            startActivity(home);
                            finish();
                        }
                    }
                });
                t.start();
            }
        });


/*        new Thread(new Runnable() {
            public void run() {
                while (progressBarStatus < 100) {
                    progressBarStatus = downloadFile();

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    progressBarbHandler.post(new Runnable() {
                        public void run() {
                            progressBar.setProgress(progressBarStatus);
                        }
                    });
                }

                if (progressBarStatus >= 100) {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    progressBar.dismiss();
                }
            }
        }).start();

        */
    }

    protected void Connected() {
        final Switch connect = findViewById(R.id.switch1);
        connect.setEnabled(false);
        connect.setChecked(false);
        //////////////////////////////Check connectivity //////////////////
        ConnectivityManager cm = (ConnectivityManager) MainActivity.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

        if (isConnected) {
            connect.setChecked(true);
        }
    }

    public  void save(String attribut , String value){
        SharedPreferences settings = getApplicationContext().getSharedPreferences(DATEBASE , MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(attribut, value);
        editor.apply();
    }

    public String get(String attribut){
        SharedPreferences settings = getApplicationContext().getSharedPreferences(DATEBASE , MODE_PRIVATE);
      return settings.getString(attribut,"");
    }


    @Override
    public void terminalCharges(Terminal terminal) {
         this.terminal = terminal ;
    }

    @Override
    public void getTerminalResultErreur(Exception exception) {
        Toast.makeText(this,exception.getMessage() ,Toast.LENGTH_SHORT).show();
    }

    public void progress(boolean b){
        final ProgressDialog dialog = ProgressDialog.show(MainActivity.this, "", "Connexion. Please wait...", true);
        if (b){

        }else {
            dialog.dismiss();
        }
    }
}
