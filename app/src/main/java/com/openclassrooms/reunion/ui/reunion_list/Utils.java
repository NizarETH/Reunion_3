package com.openclassrooms.reunion.ui.reunion_list;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;

import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuPopupHelper;
import androidx.appcompat.widget.PopupMenu;

import com.openclassrooms.reunion.R;

import java.util.Random;

public class Utils {

    public static int getRandomColor(){
        Random rnd = new Random();
        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
    }
    // pour les méthodes static
    // filtre, tri ...

    @SuppressLint("RestrictedApi")
    public static PopupMenu showMenu(View v, Context context) {
        PopupMenu popup = new PopupMenu(context, v);

        // This activity implements OnMenuItemClickListener
        popup.inflate(R.menu.sort_menu);
        MenuPopupHelper menuHelper = new MenuPopupHelper(context, (MenuBuilder) popup.getMenu(), v);
        menuHelper.setForceShowIcon(true);
        menuHelper.setGravity(Gravity.END);
        menuHelper.show();
        return popup;
    }

}
