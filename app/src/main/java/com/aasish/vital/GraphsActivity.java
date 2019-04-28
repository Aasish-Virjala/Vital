package com.aasish.vital;

import android.accounts.Account;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.List;

public class GraphsActivity extends AppCompatActivity {
    private GraphView mMoisture,mTemperature,mHumidity;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private String idealMoist,idealTemp,idealHum;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphs);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        mMoisture = (GraphView)findViewById(R.id.Moisture);
        mTemperature = (GraphView)findViewById(R.id.Temperature);
        mHumidity = (GraphView)findViewById(R.id.Humidity);
        final LineGraphSeries<DataPoint> moist = new LineGraphSeries<DataPoint>();
        final LineGraphSeries<DataPoint> humid = new LineGraphSeries<DataPoint>();
        final LineGraphSeries<DataPoint> temp = new LineGraphSeries<DataPoint>();
        final DocumentReference docRef = db.collection(mAuth.getCurrentUser().getUid()).document(AccountActivity.documentName);



        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        //Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        List<String> moisture = (List<String>) document.getData().get("MoistArray");
                        List<String> humidity = (List<String>) document.getData().get("HumArray");
                        List<String> temperature = (List<String>) document.getData().get("TempArray");
                        //Log.d(TAG, "Current data: " + snapshot.getData());
                        for(int i = 0;i<moisture.size();i++)
                            moist.appendData(new DataPoint(i, Double.valueOf(moisture.get(i))), true, moisture.size());
                        for(int i = 0;i<humidity.size();i++)
                            humid.appendData(new DataPoint(i, Double.valueOf(humidity.get(i))), true, humidity.size());
                        for(int i = 0;i<temperature.size();i++)
                            temp.appendData(new DataPoint(i, Double.valueOf(temperature.get(i))), true, temperature.size());
                    } else {
                        //Log.d(TAG, "No such document");
                    }
                } else {
                    //Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });





        db.collection("plants").document(AccountActivity.documentName).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        idealMoist = "Between "+document.getData().get("minMoisture")+" and "+document.getData().get("maxMoisture").toString();
                        mMoisture.setTitle("Moisture (Ideal:"+idealMoist+")");
                        mMoisture.addSeries(moist);
                        mMoisture.setTitleTextSize(80);
                        mMoisture.getViewport().setMinX(1);
                        mMoisture.getViewport().setMaxX(100);
                        mMoisture.getViewport().setMinY(0);
                        mMoisture.getViewport().setMaxY(1);
                        mMoisture.getViewport().setYAxisBoundsManual(true);
                        mMoisture.getViewport().setXAxisBoundsManual(true);
                        mMoisture.setTitle("Moisture (Ideal: "+idealMoist+")");
                    } else {

                    }
                } else {

                }
            }
        });





        db.collection("plants").document(AccountActivity.documentName).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        idealTemp = "Between "+document.getData().get("minTemp").toString()+" and "+document.getData().get("maxTemp").toString();
                        // Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        mTemperature.addSeries(temp);
                        mTemperature.setTitleTextSize(80);
                        mTemperature.getViewport().setMinX(1);
                        mTemperature.getViewport().setMaxX(100);
                        mTemperature.getViewport().setMinY(0);
                        mTemperature.getViewport().setMaxY(40);
                        mTemperature.getViewport().setYAxisBoundsManual(true);
                        mTemperature.getViewport().setXAxisBoundsManual(true);
                        mTemperature.setTitle("Temperature (Ideal:"+idealTemp+")");

                    } else {
                    }
                } else {
                }
            }
        });




        db.collection("plants").document(AccountActivity.documentName).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        idealHum = "Between "+document.getData().get("minHumidity").toString()+" and "+document.getData().get("maxHumidity").toString();
                        // Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        mHumidity.addSeries(humid);
                        mHumidity.setTitleTextSize(80);
                        mHumidity.getViewport().setMinX(1);
                        mHumidity.getViewport().setMaxX(100);
                        mHumidity.getViewport().setMinY(0);
                        mHumidity.getViewport().setMaxY(1);
                        mHumidity.getViewport().setYAxisBoundsManual(true);
                        mHumidity.getViewport().setXAxisBoundsManual(true);
                        mHumidity.setTitle("Humidity (Ideal: "+idealHum+")");
                    } else {
                        // Log.d(TAG, "No such document");
                    }
                } else {
                    // Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });





    }
}
