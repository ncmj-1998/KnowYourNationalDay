package sg.edu.rp.webservices.knowyournationalday;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.optionmenus, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Tally against the respective action item clicked
        //  and implement the appropriate action

        if (item.getItemId() == R.id.send) {
            String[] send = new String[]{"Email", "SMS"};
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Select the way to enrich your friend?")
// Set the list of items easily by just supplying an
                    //  array of the items
                    .setItems(send, new DialogInterface.OnClickListener() {
                        // The parameter "which" is the item index
                        // clicked, starting from 0
                        public void onClick(DialogInterface dialog, int which) {
                            if (which == 0) {
                                Log.v("Method of enriching", "Email");
                                // The action you want this intent to do;
                                // ACTION_SEND is used to indicate sending text
                                Intent email = new Intent(Intent.ACTION_SEND);
                                // Put essentials like email address, subject & body text
                                email.putExtra(Intent.EXTRA_EMAIL,
                                        new String[]{"jason_lim@rp.edu.sg", "16022671@myrp.edu.sg"});
                                email.putExtra(Intent.EXTRA_SUBJECT,
                                        "Know your facts about Singapore!");
                                email.putExtra(Intent.EXTRA_TEXT,
                                        "Singapore is baik");
                                // This MIME type indicates email
                                email.setType("message/rfc822");
                                // createChooser shows user a list of app that can handle
                                // this MIME type, which is, email
                                startActivity(Intent.createChooser(email,
                                        "Choose an Email client :"));

                            } else if (which == 1) {
                                Log.v("Method of enriching", "SMS");
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", "", null)));


                            }
                        }
                    });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();


        } else if (item.getItemId() == R.id.quiz) {

            final LayoutInflater inflater = (LayoutInflater)
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final LinearLayout quizView =
                    (LinearLayout) inflater.inflate(R.layout.quiz, null);


            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Please Login")
                    .setView(quizView)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            RadioGroup rg1 = quizView.findViewById(R.id.rg1);
                            RadioGroup rg2 = quizView.findViewById(R.id.rg2);
                            RadioGroup rg3 = quizView.findViewById(R.id.rg3);
                            int selectedButtonId1 = rg1.getCheckedRadioButtonId();
                            int selectedButtonId2 = rg2.getCheckedRadioButtonId();
                            int selectedButtonId3 = rg3.getCheckedRadioButtonId();
                            RadioButton rb1 = (RadioButton)quizView.findViewById(selectedButtonId1);
                            RadioButton rb2 = (RadioButton)quizView.findViewById(selectedButtonId2);
                            RadioButton rb3 = (RadioButton)quizView.findViewById(selectedButtonId3);
                            int score = 0;
                            if (rb1.getText().toString().equalsIgnoreCase("No")){
                                score = score +1;
                            }
                            if (rb2.getText().toString().equalsIgnoreCase("Yes")){
                                score = score +1;
                            }
                            if (rb3.getText().toString().equalsIgnoreCase("Yes")){
                                score = score +1;
                            }

                            Snackbar sb = Snackbar.make( lv, "Your score is "+score+"/3.", Snackbar.LENGTH_SHORT);
                            sb.show();

                            Toast.makeText(MainActivity.this, "Your score is "+score+"/3.", Toast.LENGTH_SHORT).show();
                        }

                    })
                    .setNegativeButton("Dont know lah", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });

            AlertDialog alertDialog = builder.create();
            alertDialog.show();


        } else if (item.getItemId() == R.id.quit) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Quit?")
                    // Set text for the positive button and the corresponding
                    //  OnClickListener when it is clicked
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            finish();
                        }
                    })
                    // Set text for the negative button and the corresponding
                    //  OnClickListener when it is clicked
                    .setNegativeButton("Not Really", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                        }
                    });
            // Create the AlertDialog object and return it
            AlertDialog alertDialog = builder.create();
            alertDialog.show();

        } else if (item.getItemId() == R.id.logout) {
            SharedPreferences.Editor editor = prefs.edit();
            editor.clear();
            editor.commit();
            Intent i = new Intent(getBaseContext(),MainActivity.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }

    ListView lv;
    ArrayList<String> alFacts;
    ArrayAdapter<String> aa;
    SharedPreferences prefs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String code = prefs.getString("code", "");
        if (code.equals("")) {
            LayoutInflater inflater = (LayoutInflater)
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            LinearLayout passPhrase =
                    (LinearLayout) inflater.inflate(R.layout.passphrase, null);
                       final EditText etPassphrase = (EditText) passPhrase
                    .findViewById(R.id.editText);

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Please Login")
                    .setView(passPhrase)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {

                            if (etPassphrase.getText().toString().equals("738964")) {
                                Toast.makeText(MainActivity.this, "Successfully Logged in", Toast.LENGTH_LONG).show();
                                SharedPreferences.Editor editor = prefs.edit();
                                editor.putString("code", "738964");
                                editor.commit();

                            } else {
                                Toast.makeText(MainActivity.this, "Invalid access code, try again", Toast.LENGTH_LONG).show();
                                finish();
                            }
                        }

                    })
                    .setNegativeButton("No Access Code", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(MainActivity.this, "You have no access to the page", Toast.LENGTH_LONG).show();
                            finish();
                        }
                    });

            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        } else {


        }
        lv = findViewById(R.id.lv);
        alFacts = new ArrayList<>();
        alFacts.add("Singapore National Day is 9th");
        alFacts.add("Singapore is 53year old");
        alFacts.add("Theme is we are Singapore");
        aa = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, alFacts);
        lv.setAdapter(aa);
    }
}
