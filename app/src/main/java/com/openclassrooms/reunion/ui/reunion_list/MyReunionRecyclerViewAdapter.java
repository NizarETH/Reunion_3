package com.openclassrooms.reunion.ui.reunion_list;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
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

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyReunionRecyclerViewAdapter extends RecyclerView.Adapter<MyReunionRecyclerViewAdapter.ViewHolder> {

    private List<Reunion> mReunion;
    private View view;


    public MyReunionRecyclerViewAdapter(List<Reunion> items) {
        mReunion = items;
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


      String emails = "";
        for (int i = 0; i < reunion.getMailAddresse().size(); i++) {
            if(i == 0)
                emails = reunion.getMailAddresse().get(0);
            else

            emails = emails + (", "+reunion.getMailAddresse().get(i));
        }
        holder.listParticipant.setText(emails);

        if (position==0) {
           holder.mReunionAvatar.setImageTintList(ColorStateList.valueOf(Color.parseColor("#FFB6C1")));
        }

        holder.mDeleteButton.setOnClickListener(new OnClickListener() {
           @Override
           public void onClick(View v) {
               mReunion.remove(holder.getAbsoluteAdapterPosition());
               notifyItemRemoved(holder.getAbsoluteAdapterPosition());

           }
             }
        );


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


}
