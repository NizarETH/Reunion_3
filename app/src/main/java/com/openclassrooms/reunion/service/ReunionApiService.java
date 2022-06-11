package com.openclassrooms.reunion.service;

import com.openclassrooms.reunion.model.Reunion;

import java.util.List;


/**
 * Reunion API client
 */
public interface ReunionApiService {

    /**
     * Get all my Reunion
     * @return {@link List}
     */
    List<Reunion> getReunions();


    /**
     * Deletes a reunion
     * @param reunion
     */
    void deleteReunion(Reunion reunion);

    /**
     * Create a reunion
     * @param reunion
     */
    void createReunion(Reunion reunion);
}
