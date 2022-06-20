package com.openclassrooms.reunion.ui.reunion_list;

import static androidx.core.graphics.TypefaceCompatApi21Impl.init;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;

import androidx.appcompat.widget.PopupMenu;
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
import java.util.Collections;
import java.util.List;
import java.util.logging.Filter;
//import android.widget.Filter.FilterResults;

public class ReunionFragment extends Fragment {

    private ReunionApiService mApiService;
    private List<Reunion> mReunions;
    private List<Reunion> mReunionsFull;
    private RecyclerView mRecyclerView;
    Reunion reunion;
    MyReunionRecyclerViewAdapter mAdapter;


    private int lSYear;
    private int lSMonth;
    private int lastSelectedDayOfMonth;

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


        // Get Current Date
        final Calendar c = Calendar.getInstance();
        this.lSYear = c.get(Calendar.YEAR);
        this.lSMonth = c.get(Calendar.MONTH);
        this.lastSelectedDayOfMonth = c.get(Calendar.DAY_OF_MONTH);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_reunion_list, container, false);

        Context context = view.getContext();
        mRecyclerView = view.findViewById(R.id.list_reunion);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

        view.findViewById(R.id.buttonFilter).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu sortMenu = Utils.showMenu(view.findViewById(R.id.buttonFilter), getActivity());
                sortMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.menu_date: {

                                R.id.menu_date.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        buttonSelectDate();
                                    }
                                });

                            String s =lastSelectedDayOfMonth+" - "+lSMonth+ " - " +lSYear;
                            mAdapter.getFilter().filter(s);
                            mAdapter.notifyDataSetChanged();
                            return true;
                           };

                            case R.id.menu_lieu: {
                                    R.id.menu_lieu.addTextChangedListener(new TextWatcher() {

                                    @Override
                                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                                    }

                                    @Override
                                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                                    }

                                    @Override
                                    public void afterTextChanged(Editable s) {
                                        mAdapter.getFilter().filter(s);
                                    }
                                    });
                               mAdapter.notifyDataSetChanged();
                                return true;
                            }

                            case R.id.menu_init: {
                                initList();
                                mAdapter.notifyDataSetChanged();
                                return true;
                            }
                            default:
                                return false;
                        }

                    }
                });
            }
        });

        view.findViewById(R.id.add_reunion).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer id = mReunions.size();
                Intent i = new Intent(getActivity(), AddReunionActivity.class);
                i.putExtra("id", id++);
                startActivity(i);


            }
        } );
        return view;
    }

    /**
     * Init the List of Reunion
     */
    private void initList() {
        mReunions = mApiService.getReunions();
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

    private Filter fRecords;

    //return the filter class object
    @Override
    public Filter getFilter() {
        if(fRecords == null) fRecords = (Filter) new RecordFilter();
        return fRecords;
    }

    //filter class
 /  private class RecordFilter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            FilterResults results = new FilterResults();

            //Implement filter logic
            // if edittext is null return the actual list
            if (constraint == null || constraint.length() == 0) {
                //No need for filter
                results.values= mReunionsFull;
                results.count = mReunionsFull.size();

            } else {
                //Need Filter
                // it matches the text  entered in the edittext and set the data in adapter list
                ArrayList<Reunion> mReunionsFull = new ArrayList<Reunion>();

                for (Reunion s : mReunionsFull) {
                    if (s.getNameSalleReunion().trim().contains(constraint.toString().toUpperCase().trim())) {
                        mReunionsFull.add(s);
                    }
                }
                results.values = mReunionsFull;
                results.count = mReunionsFull.size();
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint,FilterResults results) {

            //it set the data from filter to adapter list and refresh the recyclerview adapter
            mReunionsFull = (ArrayList<Reunion>) results.values;
            mAdapter.notifyDataSetChanged();
        }
    }

    // User click on 'Select Date' button.
    private void buttonSelectDate() {


        // Date Select Listener.
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year,
                                  int monthOfYear, int dayOfMonth) {

                dateInput.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                lSYear = year;
                lSMonth = monthOfYear+1;
                lastSelectedDayOfMonth = dayOfMonth;
            }
        };

        DatePickerDialog datePickerDialog = null;

        datePickerDialog = new DatePickerDialog(this,
                dateSetListener, lSYear, lSMonth, lastSelectedDayOfMonth);

        datePickerDialog.show();
    }
}
//}
//}
