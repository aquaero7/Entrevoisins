package com.openclassrooms.entrevoisins.events;

import com.openclassrooms.entrevoisins.model.Neighbour;

/**
 * Event fired when a user deletes a Neighbour
 */
public class RemoveNeighbourFromFavoritesEvent {

    /**
     * Neighbour to remove from favorites
     */
    public Neighbour neighbour;

    /**
     * Constructor.
     * @param neighbour
     */
    public RemoveNeighbourFromFavoritesEvent(Neighbour neighbour) {
        this.neighbour = neighbour;
    }
}
