package com.aasish.vital;

import android.renderscript.Script;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class AddPlant extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private EditText mPlantName, mMoisture;
    private Button mDone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_plant);
        mAuth = FirebaseAuth.getInstance();
        mPlantName = (EditText)findViewById(R.id.plantName);
        mMoisture = (EditText)findViewById(R.id.moisture);
        mDone = (Button)findViewById(R.id.done);
        db = FirebaseFirestore.getInstance();
        mDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(mPlantName.getText().toString())||TextUtils.isEmpty(mMoisture.getText().toString())) {
                    Toast.makeText(AddPlant.this, "One or more fields are empty", Toast.LENGTH_SHORT).show();
                }
                else {
                    Map<String,Object> plant= new HashMap<>();
                    plant.put("Moisture",mMoisture.getText().toString());
                    db.collection(mAuth.getCurrentUser().getUid()).document(mPlantName.getText().toString()).set(plant);
                }
            }
        });

    }
}
