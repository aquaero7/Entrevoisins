
package com.openclassrooms.entrevoisins.neighbour_list;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.DummyNeighbourGenerator;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;
import com.openclassrooms.entrevoisins.ui.neighbour_list.ListNeighbourActivity;
import com.openclassrooms.entrevoisins.ui.neighbour_list.NeighbourFragment;
import com.openclassrooms.entrevoisins.utils.DeleteViewAction;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isSelected;
import static android.support.test.espresso.matcher.ViewMatchers.withChild;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.openclassrooms.entrevoisins.utils.RecyclerViewItemCountAssertion.withItemCount;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.hamcrest.core.IsNull.notNullValue;

// >>> PH >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.not;
import static android.support.test.espresso.action.ViewActions.click;

import java.util.List;
// //fin


/**
 * Test class for list of neighbours
 */
@RunWith(AndroidJUnit4.class)
public class NeighboursListTest {

    // This is fixed
    private static int ITEMS_COUNT = 12;

    private ListNeighbourActivity mActivity;

    // >>> PH >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    private NeighbourApiService testApi = DI.getNeighbourApiService();
    // //fin

    @Rule
    public ActivityTestRule<ListNeighbourActivity> mActivityRule =
            new ActivityTestRule(ListNeighbourActivity.class);

    @Before
    public void setUp() {
        mActivity = mActivityRule.getActivity();
        assertThat(mActivity, notNullValue());
    }

    /**
     * We ensure that our recyclerview is displaying at least on item
     */
    @Test
    public void myNeighboursList_shouldNotBeEmpty() {
        // First scroll to the position that needs to be matched and click on it.

        /* >>> Initialement dans le code >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        onView(ViewMatchers.withId(R.id.list_neighbours))
                .check(matches(hasMinimumChildCount(1)));
        */ //fin

        // >>> PH >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

        // Given : We display neighbours list.
        onView(allOf(ViewMatchers.withText(R.string.tab_neighbour_title), ViewMatchers.isDescendantOfA(withId(R.id.tabs))))
                .perform(click());

        // Then : We check that the list is not empty.
        onView(allOf(ViewMatchers.withId(R.id.list_neighbours), ViewMatchers.hasFocus()))
                .check(matches(hasMinimumChildCount(1)));

        // //fin
    }

    /**
     * When we delete an item, the item is no more shown
     */
    @Test
    public void myNeighboursList_deleteAction_shouldRemoveItem() {
        /* >>> Initialement dans le code >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        // Given : We remove the element at position 2
        onView(ViewMatchers.withId(R.id.list_neighbours)).check(withItemCount(ITEMS_COUNT));
        // When perform a click on a delete icon
        onView(ViewMatchers.withId(R.id.list_neighbours))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, new DeleteViewAction()));
        // Then : the number of element is 11
        onView(ViewMatchers.withId(R.id.list_neighbours)).check(withItemCount(ITEMS_COUNT-1));
        */ //fin

        // >>> PH >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

        // Given : We display neighbours list.
        onView(allOf(ViewMatchers.withText(R.string.tab_neighbour_title), ViewMatchers.isDescendantOfA(withId(R.id.tabs))))
                .perform(click());

        // We calculate the number of elements of the neighbours list
        int sizeN = testApi.getNeighbours(false).size();

        // Given : The number of elements is 'size'
        onView(allOf(ViewMatchers.withId(R.id.list_neighbours), ViewMatchers.hasFocus()))
                .check(withItemCount(sizeN));

        // Given : We remove the element at position 2

        // When perform a click on a delete icon
        onView(allOf(ViewMatchers.withId(R.id.list_neighbours), ViewMatchers.hasFocus()))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, new DeleteViewAction()));

        // Then : the number of element of the neighbours list is now 'size'-1
        onView(allOf(ViewMatchers.withId(R.id.list_neighbours), ViewMatchers.hasFocus()))
                .check(withItemCount(sizeN-1));

        // //fin
    }

    // >>> PH >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    /**
     * When we click on an item, the item details view is displayed
     */
    @Test
    public void myNeighboursList_clickAction_shouldDisplayDetailsView() {

        // Given : We display neighbours list.
        onView(allOf(ViewMatchers.withText(R.string.tab_neighbour_title), ViewMatchers.isDescendantOfA(withId(R.id.tabs))))
                .perform(click());

        // We calculate the number of elements of the neighbours list
        int sizeN = testApi.getNeighbours(false).size();

        // Given : We click on element at position 3

        // Neighbours list is displayed
        onView(allOf(ViewMatchers.withId(R.id.list_neighbours), ViewMatchers.hasFocus()))
                .check(withItemCount(sizeN));

        // When perform a click on element at position 3
        onView(allOf(ViewMatchers.withId(R.id.list_neighbours), ViewMatchers.hasFocus()))
                .perform(RecyclerViewActions.actionOnItemAtPosition(2, click()));

        // Then : The details view is displayed
        onView(ViewMatchers.withId(R.id.main_details_neighbour))
                .check(matches(isDisplayed()));

        // We can return to neighbours list by clicking the back button
        onView(ViewMatchers.withId(R.id.details_button_back))
                .perform(click());
    }

    /**
     * When display details view, then textView for neighbour name is correctly filled
     */
    @Test
    public void detailsView_whenDisplayed_shouldShowTextviewForNameCorrectlyFilled() {

        // Given : We display neighbours list.
        onView(allOf(ViewMatchers.withText(R.string.tab_neighbour_title), ViewMatchers.isDescendantOfA(withId(R.id.tabs))))
                .perform(click());

        // We calculate the number of elements of the neighbours list
        int sizeN = testApi.getNeighbours(false).size();

        // Given : We click on element at position 4

        // Neighbours list is displayed
        onView(allOf(ViewMatchers.withId(R.id.list_neighbours), ViewMatchers.hasFocus()))
                .check(withItemCount(sizeN));

        // When perform a click on element at position 4
        onView(allOf(ViewMatchers.withId(R.id.list_neighbours), ViewMatchers.hasFocus()))
                .perform(RecyclerViewActions.actionOnItemAtPosition(3, click()));

        // Then : The textViews for name displays the name of element at position 4
        onView(ViewMatchers.withId(R.id.details_name))
                .check(matches(withText(testApi.getNeighbours(false).get(3).getName())));

        // Then : The textViews for name on avatar displays the name of element at position 4
        onView(ViewMatchers.withId(R.id.details_name_avatar))
                .check(matches(withText(testApi.getNeighbours(false).get(3).getName())));

        // We can return to neighbours list by clicking the back button
        onView(ViewMatchers.withId(R.id.details_button_back))
                .perform(click());
    }

    /**
     * Favorite list displays only favorites neighbours
     */
    @Test
    public void favoritesList_displayAction_shouldShowOnlyFavorites() {
        int sizeN = testApi.getNeighbours(false).size();
        int sizeF = testApi.getNeighbours(true).size();

        // START CONDITIONS CHECKS : *******

        // Given : We display neighbours list.
        onView(allOf(ViewMatchers.withText(R.string.tab_neighbour_title), ViewMatchers.isDescendantOfA(withId(R.id.tabs))))
                .perform(click());

        // We check that neighbours list is displayed
        onView(allOf(ViewMatchers.withId(R.id.list_neighbours), ViewMatchers.hasFocus()))
                .check(withItemCount(sizeN));

        // We check that favorites list is hidden and empty
        onView(allOf(ViewMatchers.withId(R.id.list_neighbours), not(ViewMatchers.hasFocus())))
                .check(withItemCount(sizeF));


        // TEST CONDITIONS SETUP : *******

        // Given : Set favorite a first element in neighbours list.

        // We click the element at position 5 in neighbours list to open details view.
        onView(allOf(ViewMatchers.withId(R.id.list_neighbours), ViewMatchers.hasFocus()))
                .perform(RecyclerViewActions.actionOnItemAtPosition(4, click()));

        // We click favorite toggle.
        onView(ViewMatchers.withId(R.id.details_button_favorite_toggle))
                .perform(click());

        // We return to neighbours list
        pressBack();

        // Given : Set favorite a second element in neighbours list.

        // We click the element at position 7 in neighbours list to open details view.
        onView(allOf(ViewMatchers.withId(R.id.list_neighbours), ViewMatchers.hasFocus()))
                .perform(RecyclerViewActions.actionOnItemAtPosition(6, click()));

        // We click favorite toggle
        onView(ViewMatchers.withId(R.id.details_button_favorite_toggle))
                .perform(click());

        // We return to neighbours list
        pressBack();


        // RESULTS CHECKS : *******

        // Given : Display favorites list and hide neighbours list by clicking on favorites tab
        onView(allOf(ViewMatchers.withText(R.string.tab_favorites_title), ViewMatchers.isDescendantOfA(withId(R.id.tabs))))
                .perform(click());

        // Then : The displayed list (favorites) contents 2 elements more...
        onView(allOf(ViewMatchers.withId(R.id.list_neighbours), ViewMatchers.hasFocus()))
                .check(withItemCount(sizeF+2));

        //  ...and the hidden list (neighbours) still contents the same numbers of elements
        onView(allOf(ViewMatchers.withId(R.id.list_neighbours), not(ViewMatchers.hasFocus())))
                .check(withItemCount(sizeN));

        // When perform a click on element at position 1 in favorites list
        onView(allOf(ViewMatchers.withId(R.id.list_neighbours), ViewMatchers.hasFocus()))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        // Then : The textViews for name displays the name of element at position 5 in neighbours list (first neighbour set favorite)
        onView(ViewMatchers.withId(R.id.details_name))
                .check(matches(withText(testApi.getNeighbours(false).get(4).getName())));

        // We can return to favorites list
        pressBack();

        // When perform a click on element at position 2 in favorites list
        onView(allOf(ViewMatchers.withId(R.id.list_neighbours), ViewMatchers.hasFocus()))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));

        // Then : The textViews for name displays the name of element at position 7 in neighbours list (second neighbour set favorite)
        onView(ViewMatchers.withId(R.id.details_name))
                .check(matches(withText(testApi.getNeighbours(false).get(6).getName())));

        // We can return to favorites list
        pressBack();

    }
    // //fin

}