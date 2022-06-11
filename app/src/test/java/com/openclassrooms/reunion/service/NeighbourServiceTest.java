package com.openclassrooms.reunion.service;

import com.openclassrooms.reunion.di.DI;
import com.openclassrooms.reunion.model.Neighbour;

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
 * Unit test on Neighbour
 */
@RunWith(JUnit4.class)
public class NeighbourServiceTest {

    private NeighbourApiService service;

    @Before
    public void setup() {
        service = DI.getNewInstanceApiService();
    }

    @Test
    public void getNeighboursWithSuccess() {
        List<Neighbour> neighbours = service.getNeighbours();
        List<Neighbour> expectedNeighbours = DummyNeighbourGenerator.DUMMY_NEIGHBOURS;
        assertThat(neighbours, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedNeighbours.toArray()));
    }

    @Test
    public void deleteNeighbourWithSuccess() {
        Neighbour neighbourToDelete = service.getNeighbours().get(0);
        service.deleteNeighbour(neighbourToDelete);
        assertFalse(service.getNeighbours().contains(neighbourToDelete));
    }

    @Test
    public void testButtonBack() {
        // on ne peut tester un click dans un test unitaire


    }

    @Test
    public void testAvatarUser() {
        Neighbour voisin = service.getNeighbours().get(0);
        assertNotNull(voisin.getAvatarUrl());
    }

    @Test
    public void testNameUser() {
        Neighbour voisin = service.getNeighbours().get(0);
        assertNotNull(voisin.getName());
    }

}
