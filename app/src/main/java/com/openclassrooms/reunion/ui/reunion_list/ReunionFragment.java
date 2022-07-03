package com.openclassrooms.reunion.ui.reunion_list;

import static com.openclassrooms.reunion.ui.reunion_list.Utils.handleDay;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.openclassrooms.reunion.R;
import com.openclassrooms.reunion.di.DI;
import com.openclassrooms.reunion.events.DeleteReunionEvent;
import com.openclassrooms.reunion.model.Reunion;
import com.openclassrooms.reunion.service.ReunionApiService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ReunionFragment extends Fragment {

    private ReunionApiService mApiService;
    private List<Reunion> mReunions;
    private List<Reunion> mReunions1;
    private String timeValue ;
    private RecyclerView mRecyclerView;
    private int lSYear;
    private int lSMonth;
    private int lastSelectedDayOfMonth;
//    private int lastSelectedHour = -1;
//    private int lastSelectedMinute = -1;
    private MyReunionRecyclerViewAdapter mAdapter;
    private View view;


    /**
     * Create and return a new instance
     * @return @{@link ReunionFragment}
     */
    public static ReunionFragment newInstance() {
        ReunionFragment fragment = new ReunionFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApiService = DI.getReunionApiService();



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_reunion_list, container, false);

        Context context = view.getContext();
        mRecyclerView = view.findViewById(R.id.list_reunion);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

        view.findViewById(R.id.buttonFilter).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v1) {

                PopupMenu sortMenu = Utils.showMenu(view.findViewById(R.id.buttonFilter), getActivity());
                sortMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.menu_date: {
                                buttonSelectDate();
                                return true;
                           }

                            case R.id.menu_lieu: {
                               if(view.findViewById(R.id.edit_search).getVisibility() == View.GONE)
                                    view.findViewById(R.id.edit_search).setVisibility(View.VISIBLE);

                                return true;
                            }

                            case R.id.menu_init: {
                                initList();

                                return true;
                            }
                            default:
                                return false;
                        }

                    }
                });
            }
        });
        ((EditText)view.findViewById(R.id.edit_search)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

               if(s.length() > 1)
                {
                    List<Reunion> filteredList = new ArrayList<>();
                    if(mReunions1.size() > 0 && mReunions1.size() < mReunions.size())
                        for (int i = 0; i < mReunions1.size(); i++) {
                            if (mReunions1.get(i).getNameSalleReunion().contains(s))
                                filteredList.add(mReunions1.get(i));
                        }
                    else
                        for (int i = 0; i < mReunions.size(); i++) {
                            if (mReunions.get(i).getNameSalleReunion().contains(s))
                                filteredList.add(mReunions.get(i));
                        }

                        mAdapter = new MyReunionRecyclerViewAdapter(filteredList);
                        mRecyclerView.setAdapter(mAdapter);
                        mReunions1 = filteredList;

                }
               else if( s.length() == 0)
                   initList();

            }
        });

        view.findViewById(R.id.add_reunion).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer id = mReunions.size();
                Intent i = new Intent(getActivity(), AddReunionActivity.class);
                startActivity(i);

            }
        } );
        return view;
    }

    /**
     * Init the List of Reunion
     */
    private void initList() {

        view.findViewById(R.id.edit_search).setVisibility(View.GONE);
        mReunions = mApiService.getReunions();
        mReunions1 = mApiService.getReunions();
        mAdapter = new MyReunionRecyclerViewAdapter(mReunions);
        mRecyclerView.setAdapter(mAdapter);
    }



    @Override
    public void onResume() {
        super.onResume();
        initList();
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    /**
     * Fired if the user clicks on a delete button
     * @param event
     */
    @Subscribe
    public void onDeleteReunion(DeleteReunionEvent event) {
        mApiService.deleteReunion(event.reunion);
        initList();

    }

    // User click on 'Select Date' button.
    public void buttonSelectDate() {

        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);



        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        String dayHandled = handleDay(monthOfYear + 1);
                      String  date = dayOfMonth + "/" +dayHandled + "/" + year;
                        lSYear = year;
                        lSMonth = monthOfYear;
                        lastSelectedDayOfMonth = dayOfMonth;
                        List<Reunion> filteredList = new ArrayList<>();

                        if(mReunions1.size() > 0 && mReunions1.size() < mReunions.size())
                            for (int i = 0; i < mReunions1.size(); i++) {
                                if (mReunions1.get(i).getDateReunion().toLowerCase().contains(date))
                                    filteredList.add(mReunions1.get(i));
                            }
                        else
                            for (int i = 0; i < mReunions.size(); i++) {
                                if (mReunions.get(i).getDateReunion().toLowerCase().contains(date))
                                    filteredList.add(mReunions.get(i));
                            }
                            mAdapter = new MyReunionRecyclerViewAdapter(filteredList);
                            mRecyclerView.setAdapter(mAdapter);
                            mReunions1 = filteredList;


                    }
                }, year, month, day);

        datePickerDialog.show();
    }


    }




