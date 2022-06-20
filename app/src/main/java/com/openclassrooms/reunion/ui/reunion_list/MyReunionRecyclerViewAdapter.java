package com.openclassrooms.reunion.ui.reunion_list;

import static com.openclassrooms.reunion.ui.reunion_list.Utils.getRandomColor;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.openclassrooms.reunion.R;
import com.openclassrooms.reunion.events.DeleteReunionEvent;
import com.openclassrooms.reunion.model.Reunion;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyReunionRecyclerViewAdapter extends RecyclerView.Adapter<MyReunionRecyclerViewAdapter.ViewHolder> implements Filterable {

    private List<Reunion> mReunion;
    private List<Reunion> FullList;
    private View view;


    public MyReunionRecyclerViewAdapter(List<Reunion> items) {
        this. mReunion = items;

        FullList = new ArrayList<>(items);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_reunion, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Reunion reunion = mReunion.get(position);
        holder.mReunionName.setText(reunion.getNameReunion());
        holder.mReunionHeure.setText(reunion.getHeureReunion());
        holder.mReunionSalle.setText(reunion.getNameSalleReunion());
        holder.mReunionAvatar.setImageTintList(ColorStateList.valueOf(getRandomColor()));


      String emails = "";
        for (int i = 0; i < reunion.getMailAddresse().size(); i++) {
            if(i == 0)
                emails = reunion.getMailAddresse().get(0);
            else

            emails = emails + (", "+reunion.getMailAddresse().get(i));
        }
        holder.listParticipant.setText(emails);

        holder.mainContent.setOnClickListener(new View.OnClickListener() {
            String emails = "";
            @Override
            public void onClick(View view) {
                Intent i = new Intent(holder.mainContent.getContext(), DetailReunionActivity.class);
                i.putExtra("id", reunion.getId());
                i.putExtra("rname", reunion.getNameReunion());
                i.putExtra("rdate", reunion.getDateReunion());
                i.putExtra("rheure", reunion.getHeureReunion());
                i.putExtra("raddresse", reunion.getNameSalleReunion());

                for (int j = 0; j < reunion.getMailAddresse().size(); j++) {
                    if(j == 0)
                        emails = reunion.getMailAddresse().get(0);
                    else
                        emails = emails + (", "+reunion.getMailAddresse().get(j));
                }

                i.putExtra("rparticpant",emails);



                holder.mainContent.getContext().startActivity(i);
            }
        });

        holder.mDeleteButton.setOnClickListener(new OnClickListener() {

            @Override
           public void onClick(View v) {
                    mReunion.remove(holder.getAbsoluteAdapterPosition());
                    notifyItemRemoved(holder.getAbsoluteAdapterPosition());
           }

             });

    }


    @Override
    public int getItemCount() {
        return mReunion.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_list_avatar)
        public ImageView mReunionAvatar;

        @BindView(R.id.item_list_nameR)
        public TextView mReunionName;

        @BindView(R.id.item_list_heureR)
        public TextView mReunionHeure;

        @BindView(R.id.item_list_salleR)
        public TextView mReunionSalle;

        @BindView(R.id.item_list_delete_button)
        public ImageButton mDeleteButton;

        @BindView(R.id.main_content)
        public LinearLayout mainContent;

        @BindView(R.id.item_list_participant)
        public TextView listParticipant;


        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    @Override
    public Filter getFilter() {
        return Searched_Filter;
    }
    private Filter Searched_Filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<Reunion> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(FullList);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Reunion item : FullList) {
                    if (item.getNameReunion().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mReunion.clear();
            mReunion.addAll((ArrayList) results.values);
            notifyDataSetChanged();
        }
    };

}
