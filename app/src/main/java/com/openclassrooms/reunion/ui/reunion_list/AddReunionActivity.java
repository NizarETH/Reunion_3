package com.openclassrooms.reunion.ui.reunion_list;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.openclassrooms.reunion.R;
import com.openclassrooms.reunion.di.DI;
import com.openclassrooms.reunion.model.Reunion;
import com.openclassrooms.reunion.service.ReunionApiService;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddReunionActivity<nameParticipantInput> extends AppCompatActivity {



    TextInputEditText nameInput;
    TextInputEditText libelleInput;
    EditText dateInput;
    Button button_Date;
    TextInputEditText nomSalleInput;
    TextInputEditText nameParticipantInput;
    CheckBox checBoxDate;
    Button button_Heure;
    EditText heureInput;
    CheckBox checBoxheure;
    CheckBox  checBoxHeurePlus;

    private int lSYear;
    private int lSMonth;
    private int lastSelectedDayOfMonth;
    private int lastSelectedHour = -1;
    private int lastSelectedMinute = -1;

    public MaterialButton addButton;
    private ReunionApiService mApiService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reunion);
        mApiService = DI.getReunionApiService();

        nameInput =  (TextInputEditText)findViewById(R.id.inom_Reunion);
        libelleInput =(TextInputEditText)findViewById(R.id.iintitule_Reunion);
        button_Date= (Button) findViewById(R.id.date_Reunion);
        button_Heure= (Button) findViewById(R.id.heure_Reunion);
        checBoxDate= (CheckBox) findViewById(R.id.checkBox_isSpinnerMode);
        dateInput = (EditText)findViewById(R.id.idate_Reunion);
        heureInput = (EditText)findViewById(R.id.iheure_Reunion);
        checBoxheure= (CheckBox) findViewById(R.id.checkBoxH_isSpinnerMode);
        checBoxHeurePlus= (CheckBox) findViewById(R.id.checkBox_is24HView);
        nomSalleInput = (TextInputEditText)findViewById(R.id.inom_salle_Reunion);
        nameParticipantInput = (TextInputEditText)findViewById(R.id.iparticipant_Reunion);
        addButton = findViewById(R.id.create);

        this.button_Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonSelectDate();
            }
        });

        // Get Current Date
        final Calendar c = Calendar.getInstance();
        this.lSYear = c.get(Calendar.YEAR);
        this.lSMonth = c.get(Calendar.MONTH);
        this.lastSelectedDayOfMonth = c.get(Calendar.DAY_OF_MONTH);

        this.button_Heure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonSelectTime();
            }
        });

        init();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home : {
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void init() {

        nameInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void afterTextChanged(Editable s) {
                if(s.length()>0)
                addButton.setEnabled(true);
            }
        });

    addButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            String nameR =   nameInput.getText().toString();
            String libelleR = libelleInput.getText().toString();
            String heureR = heureInput.getText().toString();
            String dateR = dateInput.getText().toString();
            String nomSalleR = nomSalleInput.getText().toString();

            List <String> participantR = new ArrayList<>();
            String values = nameParticipantInput.getText().toString();
            String[] emails = values.split(",");
            for (int i = 0; i <emails.length ; i++) {
                participantR.add(emails[i]);
            }

            Reunion mReunion= new Reunion(System.currentTimeMillis(),
                    nameR,
                    libelleR,
                    heureR,
                    dateR,
                    nomSalleR,
                    participantR);
            
                    mApiService.createReunion(mReunion);


            finish();
        }
    });
    }
// User click on 'Select Date' button.
    private void buttonSelectDate() {
        final boolean isSpinnerMode = this.checBoxDate.isChecked();

        // Date Select Listener.
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year,
                                  int monthOfYear, int dayOfMonth) {

                dateInput.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                lSYear = year;
                lSMonth = monthOfYear;
                lastSelectedDayOfMonth = dayOfMonth;
            }
        };

        DatePickerDialog datePickerDialog = null;

        if (isSpinnerMode) {
            // Create DatePickerDialog:
            datePickerDialog = new DatePickerDialog(this,
                    android.R.style.Theme_Holo_Light_Dialog_NoActionBar,
                    dateSetListener, lSYear, lSMonth, lastSelectedDayOfMonth);
        }
        else {
            datePickerDialog = new DatePickerDialog(this,
                    dateSetListener, lSYear, lSMonth, lastSelectedDayOfMonth);
        }

        // Show
        datePickerDialog.show();
    }
    /**
     * Generate a random image. Useful to mock image picker
     * @return String
     */
    String randomImage() {
        return "https://i.pravatar.cc/150?u="+ System.currentTimeMillis();
    }

    private void buttonSelectTime()  {
        if(this.lastSelectedHour == -1)  {
            // Get Current Time
            final Calendar c = Calendar.getInstance();
            this.lastSelectedHour = c.get(Calendar.HOUR_OF_DAY);
            this.lastSelectedMinute = c.get(Calendar.MINUTE);
        }
        final boolean is24HView = this.checBoxHeurePlus.isChecked();
        final boolean isSpinnerMode = this.checBoxheure.isChecked();

        // Time Set Listener.
        TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {

            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                heureInput.setText(hourOfDay + "h" + minute );
                lastSelectedHour = hourOfDay;
                lastSelectedMinute = minute;
            }
        };

        // Create TimePickerDialog:
        TimePickerDialog timePickerDialog = null;

        // TimePicker in Spinner Mode:
        if(isSpinnerMode)  {
            timePickerDialog = new TimePickerDialog(this,
                    android.R.style.Theme_Holo_Light_Dialog_NoActionBar,
                    timeSetListener, lastSelectedHour, lastSelectedMinute, is24HView);
        }
        // TimePicker in Clock Mode (Default):
        else  {
            timePickerDialog = new TimePickerDialog(this,
                    timeSetListener, lastSelectedHour, lastSelectedMinute, is24HView);
        }

        // Show
        timePickerDialog.show();
    }



    /**
     * Used to navigate to this activity
     * @param activity
     */
    public static void navigate(FragmentActivity activity) {
        Intent intent = new Intent(activity, AddReunionActivity.class);
        ActivityCompat.startActivity(activity, intent, null);
    }
}
