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
        List<Neighbour> neighbours = service.getNeighbours(false);
        List<Neighbour> expectedNeighbours = DummyNeighbourGenerator.DUMMY_NEIGHBOURS;
        assertThat(neighbours, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedNeighbours.toArray()));
    }

    @Test
    public void deleteNeighbourWithSuccess() {
        Neighbour neighbourToDelete = service.getNeighbours(false).get(0);
        service.deleteNeighbour(neighbourToDelete);
        assertFalse(service.getNeighbours(false).contains(neighbourToDelete));
    }

    // >>> PH >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    @Test
    public void getFavoritesWithSuccess() {
        service.getNeighbours(true).clear();
        service.getNeighbours(false).get(1).setStatusFavorite(true);
        List<Neighbour> favorites = service.getNeighbours(true);
        assertTrue(!(favorites.isEmpty()));
    }

    @Test
    public void getNeighbourDetailsWithSuccess() {
        Neighbour neighbourRef = DummyNeighbourGenerator.DUMMY_NEIGHBOURS.get(2);
        long idRef = 3;
        String nameRef = "Chloé";
        String avatarUrlRef = "https://i.pravatar.cc/150?u=a042581f4e29026704f";
        String addressRef = "Saint-Pierre-du-Mont ; 5km";
        String phoneNumberRef = "+33 6 86 57 90 14";
        String aboutMeRef = "Bonjour !Je souhaiterais faire de la marche nordique. Pas initiée, je recherche une ou plusieurs personnes susceptibles de m'accompagner !J'aime les jeux de cartes tels la belote et le tarot..";
        // String profilUrlRef = "";

        assertEquals(neighbourRef.getId(), idRef);
        assertEquals(neighbourRef.getName(), nameRef);
        assertEquals(neighbourRef.getAvatarUrl(), avatarUrlRef);
        assertEquals(neighbourRef.getAddress(), addressRef);
        assertEquals(neighbourRef.getPhoneNumber(), phoneNumberRef);
        assertEquals(neighbourRef.getAboutMe(), aboutMeRef);
        // assertEquals(neighbourRef.getProfilUrl(), profilUrlRef);
    }

    @Test
    public void getNeighbourByIdWithSuccess() {
        Neighbour neighbourRef = service.getNeighbours(false).get(2);
        long idRef = neighbourRef.getId();
        Neighbour neighbour = service.getNeighbourById(idRef);

        assertEquals(neighbour, neighbourRef);
    }

    @Test
    public void setNeighbourFavoriteWithSuccess() {
        Neighbour neighbourToSetFavorite = DummyNeighbourGenerator.DUMMY_NEIGHBOURS.get(3);
        neighbourToSetFavorite.setStatusFavorite(true);

        assertTrue(service.getNeighbours(true).contains(neighbourToSetFavorite));
    }

    @Test
    public void removeNeighbourFromFavoritesWithSuccess() {
        Neighbour neighbourToRemoveFromFavorites = DummyNeighbourGenerator.DUMMY_NEIGHBOURS.get(3);
        neighbourToRemoveFromFavorites.setStatusFavorite(true);

        service.removeFromFavorites(neighbourToRemoveFromFavorites);

        assertTrue(service.getNeighbours(false).contains(neighbourToRemoveFromFavorites));
        assertFalse(service.getNeighbours(true).contains(neighbourToRemoveFromFavorites));
    }

    @Test
    public void toggleNeighbourFavoriteStatusWithSuccess() {
        Neighbour neighbourToToggle = DummyNeighbourGenerator.DUMMY_NEIGHBOURS.get(4);

        assertTrue(service.getNeighbours(false).contains(neighbourToToggle));
        assertFalse(service.getNeighbours(true).contains(neighbourToToggle));

        service.toggleStatusFavorite(neighbourToToggle);

        assertTrue(service.getNeighbours(false).contains(neighbourToToggle));
        assertTrue(service.getNeighbours(true).contains(neighbourToToggle));

        service.toggleStatusFavorite(neighbourToToggle);

        assertTrue(service.getNeighbours(false).contains(neighbourToToggle));
        assertFalse(service.getNeighbours(true).contains(neighbourToToggle));
    }

    @Test
    public void addNeighbourWithSuccess() {
        int size = service.getNeighbours(false).size();
        Neighbour neighbourToAdd = new Neighbour(13, "Fool", "https://i.pravatar.cc/150?u=a042581f4e29026704v", "Saint-Pierre-du-Mont ; 5km",
                "+33 6 86 57 90 14",  "Bonjour !Je souhaiterais faire de la marche nordique. Pas initiée, je recherche une ou plusieurs personnes susceptibles de m'accompagner !J'aime les jeux de cartes tels la belote et le tarot..");

        service.createNeighbour(neighbourToAdd);

        assertEquals(service.getNeighbours(false).size(), size + 1);
        assertEquals(service.getNeighbours(false).get(size), neighbourToAdd);
        assertFalse(service.getNeighbourById(13).getStatusFavorite());
    }

    // /:fin
}
