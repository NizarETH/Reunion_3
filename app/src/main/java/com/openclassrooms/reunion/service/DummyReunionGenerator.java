package com.openclassrooms.reunion.service;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.openclassrooms.reunion.model.Reunion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public abstract class DummyReunionGenerator {

    public static List<Reunion> DUMMY_REUNION = Arrays.asList(
            new Reunion(2, "Réunion B", "15h00", "Du_Mont",
                    Arrays.asList(new String[]{"carole@hotmail.fr", "Premir2@ymail.fr", "aimeminyem67@gmail.com", "Simon@easynet@fr"})),

            new Reunion(3, "Réunion C", "16h00", "Mengalet",
                    Arrays.asList(new String[]{"gousou@hotmail.fr", "minyem@hotmail.fr", "aimeminy@gmail.com"})),

            new Reunion(4, "Réunion D", "17h00", "Saint-Pierre",
                    Arrays.asList(new String[]{"gousou@hotmail.fr", "minyem@hotmail.fr", "aimeminy@gmail.com"})),

            new Reunion(5, "Réunion F", "18h00", "Du_Mont",
                    Arrays.asList(new String[]{"gousou@hotmail.fr", "minyem@hotmail.fr", "aimeminy@gmail.com"})),

            new Reunion(6, "Réunion G", "19h00", "Du_Mont",
                    Arrays.asList(new String[]{"gous@hotmail.fr", "ngui@camtel.fr", "aimeminy@gmail.com"})),

            new Reunion(7, "Réunion H", "20h00", "Saintserin",
                    Arrays.asList(new String[]{"amougou@hotmail.fr", "belinga@camtel.fr", "bot@gmail.com"})),

            new Reunion(1, "Réunion A", "14h00", "Saint_Pierre",
                    Arrays.asList(new String[]{"caroline@hotmail.fr", "remir2@hotmail.fr", "aimeminyem67@gmail.com"})));

    static List<Reunion> generateReunion() {
        return new ArrayList<>(DUMMY_REUNION);
    }

}

