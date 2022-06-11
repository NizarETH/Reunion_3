package com.openclassrooms.reunion.service;

import com.openclassrooms.reunion.model.Reunion;


import java.util.List;

/**
 * Dummy mock for the Api
 */
public class DummyReunionApiService implements  ReunionApiService {

    private List<Reunion> reunions = DummyReunionGenerator.generateReunion();


    /**
     * {@inheritDoc}
     */
    @Override
    public List<Reunion> getReunions() {
        return reunions;
    }


    /**
     * {@inheritDoc}
     * @param reunion
     */
    @Override
    public void deleteReunion(Reunion reunion) {
        reunions.remove(reunion);
    }

    /**
     * {@inheritDoc}
     * @param reunion
     */
    @Override
    public void createReunion(Reunion reunion) {
        reunions.add(0,reunion);
    }
}
