package com.openclassrooms.reunion.ui.reunion_list;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.FragmentActivity;

import com.openclassrooms.reunion.R;
import com.openclassrooms.reunion.di.DI;
import com.openclassrooms.reunion.service.ReunionApiService;

public class DetailReunionActivity extends FragmentActivity {
    private ReunionApiService mApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_reunion);
        mApiService = DI.getReunionApiService();

        Intent bundle= getIntent();
        int id=bundle.getIntExtra("id",0);
        String nameR=bundle.getStringExtra("rname");
        String libelleR=bundle.getStringExtra("rlibelle");
        String dateR=bundle.getStringExtra("rdate");
        String heureR=bundle.getStringExtra("rheure");
        String nameSalleR=bundle.getStringExtra("raddresse");
        String participantR=bundle.getStringExtra("rparticpant");


        EditText name_Reunion = findViewById(R.id.inom_Reunion);
        EditText libelle_Reunion = findViewById(R.id.iintitule_Reunion);
        EditText date_Reunion = findViewById(R.id.idate_Reunion);
        EditText heure_Reunion = findViewById(R.id.iheure_Reunion);
        EditText name_Salle_Reunion = findViewById(R.id.inom_salle_Reunion);
        EditText participant_Reunion = findViewById(R.id.iparticipant_Reunion);
        Button ret_arr = findViewById(R.id.returnback);

        name_Reunion.setText(nameR);
        libelle_Reunion.setText(libelleR);
        date_Reunion.setText(dateR);
        heure_Reunion.setText(heureR);
        name_Salle_Reunion.setText(nameSalleR);
        participant_Reunion.setText(participantR);

        ret_arr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });





    }

}

