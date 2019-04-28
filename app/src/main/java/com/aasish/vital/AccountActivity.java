package com.aasish.vital;

import android.content.Intent;
import android.nfc.Tag;
import android.renderscript.Sampler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class AccountActivity extends AppCompatActivity  {
    public static String documentName;
    private FirebaseAuth mAuth;
    private Button mLogoutButton, mSearch, mCreateNewList;
    private FirebaseFirestore db;
    static EditText ListName;
    private String mEmailField2 = MainActivity.mEmailField2;
    private EditText mEmailField = MainActivity.mEmailField;
    private String ValuesFromMain = MainActivity.Values, TestValue;
    private Map Hello;
    private String[] KeyValues;
    private Integer Values;
    static String[] keys;
    private Toolbar toolbar;

    private ScrollView scrollView;
    private LinearLayout ll;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        scrollView = (ScrollView)findViewById(R.id.scrollView);
        ll = (LinearLayout)findViewById(R.id.ll);
        db.collection(mAuth.getCurrentUser().getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (final QueryDocumentSnapshot document : task.getResult()) {
                                Button button = new Button(getApplicationContext());
                                button.setText(document.getId());
                                button.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        documentName = document.getId();
                                        startActivity(new Intent(AccountActivity.this,GetData.class));
                                    }
                                });
                                ll.addView(button);


                                //Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                        } else {
                            //Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

//

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int res_id = item.getItemId();
        if(res_id==R.id.action_settings) {
            startActivity(new Intent(AccountActivity.this,SettingsActivity.class));
        }
        else if (res_id==R.id.logout) {
            mAuth.signOut();
            startActivity(new Intent(AccountActivity.this,MainActivity.class));
        }
        return true;
    }
}