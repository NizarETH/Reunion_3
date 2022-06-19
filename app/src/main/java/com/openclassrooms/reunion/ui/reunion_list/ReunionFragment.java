package com.openclassrooms.reunion.ui.reunion_list;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

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
import java.util.Collections;
import java.util.List;
import java.util.logging.Filter;


public class ReunionFragment extends Fragment {

    private ReunionApiService mApiService;
    private List<Reunion> mReunions;
    private List<Reunion> mReunionsFull;
    private RecyclerView mRecyclerView;
    Reunion reunion;
    MyReunionRecyclerViewAdapter mAdapter;

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
                            case R.id.menu_dateAsc: {
                                Collections.sort(mReunions, Reunion.ReunionDateAscComparator);

                               mAdapter.notifyDataSetChanged();
                                return true;
                            }

                            case R.id.menu_lieu: {
                                Collections.sort(mReunions, Reunion.ReunionSalleComparator);
                                //Toast.makeText(this,"Sort nameSalleReunion ascending",Toast.LENGTH_SHORT).show();
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
/*
    public void filter(String text) {
        items.clear();
        if(text.isEmpty()){
            items.addAll(itemsCopy);
        } else{
            text = text.toLowerCase();
            for(mReunions item: itemsCopy){
                if(item.name.toLowerCase().contains(text) || item.phone.toLowerCase().contains(text)){
                    items.add(item);
                }
            }
        }
        notifyDataSetChanged();
    }

searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {
            adapter.filter(query);
            return true;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
           mAdapter.filter(newText);
            return true;
        }
    });

    @Override
    public Filter getFilter() {
        return new Filter() {
            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mReunionsFull = (List<Reunion>) results.values;
                mAdapter.notifyDataSetChanged();
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<Reunion> filteredResults = null;
                if (constraint.length() == 0) {
                    filteredResults = mReunions;
                } else {
                    filteredResults = getFilteredResults(constraint.toString().toLowerCase());
                }

                FilterResults results = new FilterResults();
                results.values = filteredResults;

                return results;
            }
        };
 } */

    protected List<Reunion> getFilteredResults(String constraint) {
        List<Reunion> results = new ArrayList<>();

        for (Reunion item : mReunions) {
            if (item.getNameReunion().toLowerCase().contains(constraint)) {
                results.add(item);
            }
        }
        return results;
    }
}
//}
