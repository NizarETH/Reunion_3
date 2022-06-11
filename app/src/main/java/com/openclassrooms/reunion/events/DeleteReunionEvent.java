package com.openclassrooms.reunion.events;

import com.openclassrooms.reunion.model.Reunion;

/**
 * Event fired when a user deletes a Neighbour
 */
public class DeleteReunionEvent {

    /**
     * Neighbour to delete
     */
    public Reunion reunion;

    /**
     * Constructor.
     * @param reunion
     */
    public DeleteReunionEvent(Reunion reunion) {
        this.reunion = reunion;
    }
}
