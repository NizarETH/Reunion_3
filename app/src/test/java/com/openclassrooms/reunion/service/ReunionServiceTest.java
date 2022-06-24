package com.openclassrooms.reunion.service;

import com.openclassrooms.reunion.di.DI;
import com.openclassrooms.reunion.model.Reunion;

import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;


import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
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
    public void getNeighboursWithSuccess() {
        List<Reunion> reunions = service.getReunions();
        List<Reunion> expectedNeighbours = DummyReunionGenerator.DUMMY_REUNION;
        assertThat(reunions, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedNeighbours.toArray()));
    }

    @Test
    public void deleteNeighbourWithSuccess() {
        Reunion reunionToDelete = service.getReunions().get(0);
        service.deleteReunion(reunionToDelete);
        assertFalse(service.getReunions().contains(reunionToDelete));
    }

    @Test
    public void testTriAscDate() {
        Reunion reunion = service.getReunions().get(0);
        assertNotNull(reunion.getNameReunion());
    }

    @Test
    public void testTriDesDate() {
        Reunion reunion = service.getReunions().get(0);
        assertNotNull(reunion.getHeureReunion());
    }

    @Test
    public void testNameSalleReunion() {
            Reunion reunion = service.getReunions().get(0);
            assertNotNull(reunion.getNameSalleReunion());
    }

    @Test
    public void testTriLieu() {
        Reunion reunion = service.getReunions().get(0);
        assertNotNull(reunion.getMailAddresse());
    }




}
