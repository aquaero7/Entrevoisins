package com.openclassrooms.entrevoisins.service;

import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;

import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Unit test on Neighbour service
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

        // We get the list of neighbours
        List<Neighbour> neighbours = service.getNeighbours(false);

        // We get the DUMMY_NEIGHBOURS list as reference
        List<Neighbour> expectedNeighbours = DummyNeighbourGenerator.DUMMY_NEIGHBOURS;

        // Then : Both lists are the same
        assertThat(neighbours, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedNeighbours.toArray()));
    }

    @Test
    public void deleteNeighbourWithSuccess() {

        // With element at position 1 in neighbours list
        Neighbour neighbourToDelete = service.getNeighbours(false).get(0);

        // We delete this element
        service.deleteNeighbour(neighbourToDelete);

        // Then : neighbours list no longer contains this element
        assertFalse(service.getNeighbours(false).contains(neighbourToDelete));
    }

    // >>> PH >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    @Test
    public void getFavoritesWithSuccess() {

        // We make sure that favorites list is empty by clearing it
        service.getNeighbours(true).clear();

        // We check that the size of favorites list is 0
        assertEquals(service.getNeighbours(true).size(), 0);

        // We set favorite the elements at position 2 and 6 in neighbours list
        service.getNeighbours(false).get(1).setStatusFavorite(true);
        service.getNeighbours(false).get(5).setStatusFavorite(true);

        // Then we check that the size of favorites list is 2
        assertEquals(service.getNeighbours(true).size(), 2);


        // Then we ckeck that the elements at position 1 and 2 in favorites list are favorite
        assertTrue(service.getNeighbours(true).get(0).getStatusFavorite());
        assertTrue(service.getNeighbours(true).get(1).getStatusFavorite());

    }

    @Test
    public void getNeighbourDetailsWithSuccess() {

        // We get as reference the element at position 3 in DUMMY_NEIGHBOURS list
        Neighbour neighbourRef = DummyNeighbourGenerator.DUMMY_NEIGHBOURS.get(2);

        // We set as reference each information of this element, picked up from DummyNeighbourGenerator class
        long idRef = 3;
        String nameRef = "Chloé";
        String avatarUrlRef = "https://i.pravatar.cc/150?u=a042581f4e29026704f";
        String addressRef = "Saint-Pierre-du-Mont ; 5km";
        String profilUrlRef = "www.facebook.fr/chloe";
        String phoneNumberRef = "+33 6 86 57 90 14";
        String aboutMeRef = "Bonjour !Je souhaiterais faire de la marche nordique. Pas initiée, je recherche une ou plusieurs personnes susceptibles de m'accompagner !J'aime les jeux de cartes tels la belote et le tarot..";

        // Then : All information details got from referenced neighbour are similar to referenced information details
        assertEquals(neighbourRef.getId(), idRef);
        assertEquals(neighbourRef.getName(), nameRef);
        assertEquals(neighbourRef.getAvatarUrl(), avatarUrlRef);
        assertEquals(neighbourRef.getAddress(), addressRef);
        assertEquals(neighbourRef.getProfilUrl(), profilUrlRef);
        assertEquals(neighbourRef.getPhoneNumber(), phoneNumberRef);
        assertEquals(neighbourRef.getAboutMe(), aboutMeRef);
    }

    @Test
    public void getNeighbourByIdWithSuccess() {

        // We get as reference the element at position 3 in neighbours list
        Neighbour neighbourRef = service.getNeighbours(false).get(2);

        // We set as reference the Id of this element
        long idRef = neighbourRef.getId();

        // We get an element by the referenced id
        Neighbour neighbour = service.getNeighbourById(idRef);

        // Then : Both elements are the same
        assertEquals(neighbour, neighbourRef);
    }

    @Test
    public void setNeighbourFavoriteWithSuccess() {

        // We set favorite the element at position 4 in DUMMY_NEIGHBOURS list
        Neighbour neighbourToSetFavorite = DummyNeighbourGenerator.DUMMY_NEIGHBOURS.get(3);
        neighbourToSetFavorite.setStatusFavorite(true);

        // Then : Favorites list contains this element
        assertTrue(service.getNeighbours(true).contains(neighbourToSetFavorite));
    }

    @Test
    public void removeNeighbourFromFavoritesWithSuccess() {

        // We set favorite the element at position 4 in DUMMY_NEIGHBOURS list
        Neighbour neighbourToRemoveFromFavorites = DummyNeighbourGenerator.DUMMY_NEIGHBOURS.get(3);
        neighbourToRemoveFromFavorites.setStatusFavorite(true);

        // We remove this element from favorites list
        service.removeFromFavorites(neighbourToRemoveFromFavorites);

        // Then : Neighbours list still contains this element but favorites list does no longer
        assertTrue(service.getNeighbours(false).contains(neighbourToRemoveFromFavorites));
        assertFalse(service.getNeighbours(true).contains(neighbourToRemoveFromFavorites));
    }

    @Test
    public void toggleNeighbourFavoriteStatusWithSuccess() {

        // We get the element at position 5 in DUMMY_NEIGHBOURS list
        Neighbour neighbourToToggle = DummyNeighbourGenerator.DUMMY_NEIGHBOURS.get(4);

        // We check that neighbours list contains this element and favorites list does not
        assertTrue(service.getNeighbours(false).contains(neighbourToToggle));
        assertFalse(service.getNeighbours(true).contains(neighbourToToggle));

        // We switch the favorite status of this element
        service.toggleStatusFavorite(neighbourToToggle);

        // Then :  Neighbours list contains this element and favorites list too
        assertTrue(service.getNeighbours(false).contains(neighbourToToggle));
        assertTrue(service.getNeighbours(true).contains(neighbourToToggle));

        // We switch again the favorite status of this element
        service.toggleStatusFavorite(neighbourToToggle);

        // Then : Neighbours list contains this element but favorites list does not any longer
        assertTrue(service.getNeighbours(false).contains(neighbourToToggle));
        assertFalse(service.getNeighbours(true).contains(neighbourToToggle));
    }

    @Test
    public void addNeighbourWithSuccess() {

        // Given : Neighbours list size
        int size = service.getNeighbours(false).size();

        // When we add an element to the neighbours list
        Neighbour neighbourToAdd = new Neighbour(13, "Fool", "https://i.pravatar.cc/150?u=a042581f4e29026704v", "Saint-Pierre-du-Mont ; 5km", "www.facebook.fr/fool",
                "+33 6 86 57 90 14",  "Bonjour !Je souhaiterais faire de la marche nordique. Pas initiée, je recherche une ou plusieurs personnes susceptibles de m'accompagner !J'aime les jeux de cartes tels la belote et le tarot..");

        service.createNeighbour(neighbourToAdd);

        // Then : The neighbours list contains 1 more item
        assertEquals(service.getNeighbours(false).size(), size + 1);

        // Then : The last element of neighbour list is the one that was added
        assertEquals(service.getNeighbours(false).get(size), neighbourToAdd);

        // This element's status is not favorite by default
        assertFalse(service.getNeighbourById(13).getStatusFavorite());
    }

    // /:fin
}
