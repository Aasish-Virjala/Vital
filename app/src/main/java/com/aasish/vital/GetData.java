package com.aasish.vital;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.Map;

public class GetData extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private ScrollView scrollView;
    private LinearLayout ll;
    private String data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_data);
        scrollView = (ScrollView)findViewById(R.id.scrollView);
        ll = (LinearLayout)findViewById(R.id.ll);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection(mAuth.getCurrentUser().getUid()).document(AccountActivity.documentName);

        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    //Log.w(TAG, "Listen failed.", e);
                    return;
                }


                if (snapshot != null && snapshot.exists()) {
                    data="";
                    data += "Temperature"+": "+snapshot.getData().get("Temperature")+"\n";
                    data += "Humidity"+": "+snapshot.getData().get("Humidity")+"\n";
                    data += "Moisture"+": "+snapshot.getData().get("Moisture")+"\n";

                    Toast.makeText(GetData.this, data, Toast.LENGTH_SHORT).show();
                    TextView textView = new TextView(getApplicationContext());
                    textView.setText(data);
                    textView.setTextSize(20);
                    ll.removeAllViews();
                    //ll.removeView(textView);
                    ll.addView(textView);
                    Button button = new Button(getApplicationContext());
                    button.setText("Get summary");
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(GetData.this,GraphsActivity.class));
                        }
                    });
                    ll.addView(button);
                } else {

                }
            }
        });

    }
}
