package com.openclassrooms.reunion.service;

import com.openclassrooms.reunion.di.DI;
import com.openclassrooms.reunion.model.Reunion;
import com.openclassrooms.reunion.ui.reunion_list.MyReunionRecyclerViewAdapter;

import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;

/**
 * Unit test on Reunion
 */
@RunWith(JUnit4.class)
public class ReunionServiceTest {

    private ReunionApiService service;


    @Before
    public void setup() {
        service = DI.getNewInstanceApiService();
    }

    @Test
    public void addReunionWithSuccess() {
        List<Reunion> reunions = service.getReunions();
        Reunion reunionToAdd= new Reunion(10,"ReunionAB","15h15",
                "15/07/2022","Mangallet", Collections.singletonList("louis@fr,sfr@ymail"));
        List<Reunion> expectedReunion = DummyReunionGenerator.DUMMY_REUNION;
        assertThat(reunions, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedReunion.toArray()));
        service.createReunion(reunionToAdd);
        Reunion reunion=service.getReunions().get(0);
        assertSame(reunion,reunionToAdd);
    }

    @Test
    public void deleteReunionWithSuccess() {
        Reunion reunionToDelete = service.getReunions().get(0);
        service.deleteReunion(reunionToDelete);
        assertFalse(service.getReunions().contains(reunionToDelete));
    }

    @Test
    public void testFiltreDate() {
        List<Reunion> reunions = service.getReunions();
        String date="15/07/2022";
        buttonSelectDate(reunions);
    }

    public void buttonSelectDate(List<Reunion> reunions) {
        List<Reunion> filteredList = new ArrayList<>();
        List<Reunion> reunions1 = service.getReunions();
        String date="15/07/2022";

        if(reunions1.size() > 0 && reunions1.size() < reunions.size())
            for (int i = 0; i < reunions1.size(); i++) {
                if (reunions1.get(i).getDateReunion().toLowerCase().contains(date))
                    filteredList.add(reunions1.get(i));
            }
        else
            for (int i = 0; i < reunions.size(); i++) {
                if (reunions.get(i).getDateReunion().toLowerCase().contains(date))
                    filteredList.add(reunions.get(i));
            }

        for (Reunion reunion : reunions = filteredList) {
            assertEquals(date,reunion.getDateReunion());
        }

    }

    @Test
    public void testFiltreLieu() {
        List<Reunion> reunions = service.getReunions();
        String lieu="Mangallet";
        buttonSelectLieu(reunions);
        
    }

    private void buttonSelectLieu(List<Reunion> reunions) {
        List<Reunion> filteredList = new ArrayList<>();
        List<Reunion> reunions1 = service.getReunions();
        String lieu="Mangallet";

        if(reunions1.size() > 0 && reunions1.size() < reunions.size())
            for (int i = 0; i < reunions1.size(); i++) {
                if (reunions1.get(i).getDateReunion().toLowerCase().contains(lieu))
                    filteredList.add(reunions1.get(i));
            }
        else
            for (int i = 0; i < reunions.size(); i++) {
                if (reunions.get(i).getDateReunion().toLowerCase().contains(lieu))
                    filteredList.add(reunions.get(i));
            }

        for (Reunion reunion : reunions = filteredList) {
            assertEquals(lieu,reunion.getDateReunion());
        }
    }


}
