package com.openclassrooms.reunion.ui.reunion_list;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.tabs.TabLayout;
import com.openclassrooms.MyApplication;
import com.openclassrooms.reunion.R;
import butterknife.BindView;

public class ListReunionActivity extends AppCompatActivity  {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_reunion);
        getSupportFragmentManager().beginTransaction().replace(R.id.viewFragments, new ReunionFragment()).commit();


    }


}
