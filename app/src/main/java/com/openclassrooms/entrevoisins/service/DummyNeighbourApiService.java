package com.openclassrooms.entrevoisins.service;

import com.openclassrooms.entrevoisins.model.Neighbour;

import java.util.ArrayList;
import java.util.List;

/**
 * Dummy mock for the Api
 */
public class DummyNeighbourApiService implements NeighbourApiService {

    private List<Neighbour> neighbours = DummyNeighbourGenerator.generateNeighbours();

    // >>> PH >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    private List<Neighbour> favorites;
    // //fin

    /**
     * {@inheritDoc}
     */

    /* >>> Initialement dans le code >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    @Override
    public List<Neighbour> getNeighbours() {
        return neighbours;
    }
    */ //fin

    // >>> PH >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> *7
    @Override
    public List<Neighbour> getNeighbours(Boolean flagFavorite) {
        if(flagFavorite == false) {
            return neighbours;
        }
        favorites = new ArrayList<>();
        for(Neighbour neighbour : neighbours) {
            if(neighbour.getStatusFavorite() == true) {
                favorites.add(neighbour);
            }
        }
        return favorites;
    }
    // //fin

    // >>> PH >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> *2
    /**
     * {@inheritDoc}
     * @param id
     */
    @Override
    public Neighbour getNeighbourById(long id) {
        for(Neighbour neighbour : neighbours) {
            if(neighbour.getId() == id) {
                return neighbour;
            }
        }
        return null;
    }
    // //fin

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteNeighbour(Neighbour neighbour) {
            neighbours.remove(neighbour);
    }

    /**
     * {@inheritDoc}
     * @param neighbour
     */
    @Override
    public void createNeighbour(Neighbour neighbour) {
        neighbours.add(neighbour);
    }

    // >>> PH >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> *6
    /**
     * {@inheritDoc}
     * @param neighbour
     */
    @Override
    public void toggleStatusFavorite(Neighbour neighbour) {
            neighbour.setStatusFavorite(!neighbour.getStatusFavorite());
    }
    // //fin

    // >>> PH >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> *15
    /**
     * {@inheritDoc}
     * @param neighbour
     */
    @Override
    public void removeFromFavorites(Neighbour neighbour) {
        neighbour.setStatusFavorite(false);
    }
    // //fin
}
