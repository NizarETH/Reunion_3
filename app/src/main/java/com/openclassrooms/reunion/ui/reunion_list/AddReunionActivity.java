package com.openclassrooms.reunion.ui.reunion_list;

import static com.openclassrooms.reunion.ui.reunion_list.Utils.handleDay;

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
import android.widget.TextView;
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
    TextView dateInput;
    TextView button_Date;
    TextInputEditText nomSalleInput;
    TextInputEditText nameParticipantInput;

    TextView button_Heure;
    TextView heureInput;


    private int lSYear;
    private int lSMonth;
    private int lastSelectedDayOfMonth;
    private int lastSelectedHour = -1;
    private int lastSelectedMinute = -1;

    public boolean nameInputadd;
    public boolean nomSalleInputadd;
    public boolean nameParticipantInputadd ;
    public boolean dateInputadd ;
    public boolean heureInputadd ;

    public MaterialButton addButton;
    private ReunionApiService mApiService;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reunion);
        mApiService = DI.getReunionApiService();

        nameInput =  (TextInputEditText)findViewById(R.id.inom_Reunion);
        button_Date= (TextView) findViewById(R.id.date_Reunion);
        button_Heure= (TextView) findViewById(R.id.heure_Reunion);
        dateInput = (TextView)findViewById(R.id.idate_Reunion);
        heureInput = (TextView)findViewById(R.id.iheure_Reunion);
        nomSalleInput = (TextInputEditText)findViewById(R.id.inom_salle_Reunion);
        nameParticipantInput = (TextInputEditText)findViewById(R.id.iparticipant_Reunion);
        addButton = findViewById(R.id.create);

        this.dateInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateInput.setEnabled(true);
                buttonSelectDate();
            }

        });

        // Get Current Date
        final Calendar c = Calendar.getInstance();
        this.lSYear = c.get(Calendar.YEAR);
        this.lSMonth = c.get(Calendar.MONTH);
        this.lastSelectedDayOfMonth = c.get(Calendar.DAY_OF_MONTH);

        this.heureInput.setOnClickListener(new View.OnClickListener() {
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
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0)
                    nameInputadd = true;
                else
                    nameInputadd = false;
                    enableButton();
            }
        });

        nomSalleInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                     nomSalleInputadd = true;
                }
                else
                nomSalleInputadd = false;
                enableButton();
            }
        });

        nameParticipantInput.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0)
                    nameParticipantInputadd = true;
               else
                    nameParticipantInputadd = false;
                    enableButton();
            }
        });

        dateInput.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {

                if (s.length() > 0)
                    dateInputadd = true;
                else
                    dateInputadd = false;
                    enableButton();
            }
        });

        heureInput.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    heureInputadd = true;
                }
                else
                    heureInputadd = false;
                 enableButton();

            }
            });

        // Condition pour ajouter la reunion


    addButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {



                String nameR = nameInput.getText().toString();
                String heureR = heureInput.getText().toString();
          //      String heureR = lastSelectedHour + "h" + lastSelectedMinute;
                String dateR = dateInput.getText().toString();
           //     String dateR = lastSelectedDayOfMonth + "/" + lSMonth + "/" + lSYear;
                String nomSalleR = nomSalleInput.getText().toString();

                List<String> participantR = new ArrayList<>();
                String values = nameParticipantInput.getText().toString();
                String[] emails = values.split(",");
                for (int i = 0; i < emails.length; i++) {
                    participantR.add(emails[i]);
                }

                Reunion mReunion = new Reunion(System.currentTimeMillis(),
                        nameR,
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


        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        dateInput.setText(handleDay(dayOfMonth) + "/" + handleDay((monthOfYear + 1)) + "/" + year);

                        lSYear = year;
                        lSMonth = monthOfYear;
                        lastSelectedDayOfMonth = dayOfMonth;
                    }
                }, lSYear, lSMonth, lastSelectedDayOfMonth);

        datePickerDialog.show();
    }


   private void buttonSelectTime()  {
        if(this.lastSelectedHour == -1)  {
            // Get Current Time
            final Calendar c = Calendar.getInstance();
            this.lastSelectedHour = c.get(Calendar.HOUR_OF_DAY);
            this.lastSelectedMinute = c.get(Calendar.MINUTE);
        }



       TimePickerDialog  timePickerDialog = new TimePickerDialog(this,
               (view, hourOfDay, minute) -> {
                   heureInput.setText(handleDay(hourOfDay) + "h" + handleDay(minute) );
                   lastSelectedHour = hourOfDay;
                   lastSelectedMinute = minute;
               }, lastSelectedHour, lastSelectedMinute, true);
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

    private void enableButton() {
        if ((nomSalleInputadd) && (nameInputadd) && (heureInputadd) && (dateInputadd) && (nameParticipantInputadd)) {
            addButton.setEnabled(true);
        }
        else
           addButton.setEnabled(false);
    }
}
