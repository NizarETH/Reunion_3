package com.openclassrooms.reunion.service;

import com.openclassrooms.reunion.model.Reunion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public abstract class DummyReunionGenerator {

    public static List<Reunion> DUMMY_REUNION = Arrays.asList(
            new Reunion( 1,"Reunion A","15h00","15/07/2022", "Mangallet",
                    Collections.singletonList("remir@hotmail.fr,tera@ymail.fr")),
            new Reunion( 2,"Reunion B","16h00","15/07/2022", "Petit_Louis",
                    Collections.singletonList("rmir@hotmail.fr,tera@ymail.fr,mongars@fab.dr")),
            new Reunion( 3,"Reunion C","17h00","15/07/2022", "Petit_Louis",
                    Collections.singletonList("toto@hotmail.fr,ferrara@ymail.com,tarani@fb.fr")),
            new Reunion( 4,"Reunion D","10h00","16/07/2022", "Mangallet",
                    Collections.singletonList("rmir@hotmail.fr,remir@hotmail.fr")),
            new Reunion( 5,"Reunion E","11h00","16/07/2022", "Crypte",
                    Collections.singletonList("rmir@hotmail.fr,tera@ymail.fr,mongars@fab.dr")),
            new Reunion( 6,"Reunion F","14h00","16/07/2022", "Petit_Louis",
                    Collections.singletonList("rmir2@hotmail.fr,tarato@gmail.fr,monfils@gmail.fr")),
            new Reunion( 7,"Reunion G","9h00","17/07/2022", "Crypte",
                    Collections.singletonList("remir@hotmail.fr,tarato@gmail.fr"))

    );

    static List<Reunion> generateReunion() {
        return new ArrayList<>(DUMMY_REUNION);
    }

}

