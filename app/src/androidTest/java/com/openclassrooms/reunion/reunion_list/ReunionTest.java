package com.openclassrooms.reunion.reunion_list;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;


import static com.openclassrooms.reunion.utils.RecyclerViewItemCountAssertion.withItemCount;

import com.openclassrooms.reunion.ui.reunion_list.ListReunionActivity;

import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.rule.ActivityTestRule;

import com.openclassrooms.reunion.R;
import com.openclassrooms.reunion.utils.DeleteViewAction;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.not;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Collection;
import java.util.Date;


@RunWith(JUnit4.class)
public class ReunionTest {
    /**
     * test instrumentalisé sur le
     * projet
     */
    private static int ITEMS_COUNT = 7;
    private ListReunionActivity mActivity;

    @Rule
    public ActivityTestRule<ListReunionActivity> mActivityRule =
            new ActivityTestRule(ListReunionActivity.class);

    @Before
    public void setUp() {
        //Intents.init();
        mActivity = mActivityRule.getActivity();
        ViewMatchers.assertThat(mActivity, Matchers.notNullValue());
    }

    /**
     * We ensure that our recyclerview is displaying at least on item
     */
    @Test
    public void mReunionList_shouldNotBeEmpty() {
        // First scroll to the position that needs to be matched and click on it.
        onView(ViewMatchers.withId(R.id.list_reunion))
                .check(matches(hasMinimumChildCount(0)));
    }

    @Test
    public void checkTests(){

        //test vérifiant que lorsqu’on clique sur un élément de la liste, l’écran de
        //détails est bien lancé ;
        Intents.init();
        onView(withId(R.id.list_reunion)).perform(
                RecyclerViewActions.actionOnItemAtPosition(0,click()));

        onView(withId(R.id.layout1)).check(matches(isDisplayed()));

         //test vérifiant qu’au démarrage de ce nouvel écran, le TextView indiquant
        //le nom de l’utilisateur en question est bien rempli ;
        onView(withId(R.id.titre_Reunion)).check(matches(not(withText(""))));


    }

    @Test
    public void checkAjoutReunion(){
        // test vérifiant que lorsque l'on clique sur le bouton save,
        // la liste pricipale s'affiche correctement avec un élément en plus

        onView(ViewMatchers.withId(R.id.list_reunion)).check(withItemCount(ITEMS_COUNT));

        onView(withId(R.id.list_reunion)).perform(
                RecyclerViewActions.actionOnItemAtPosition(0,click()));
        onView(withId(R.id.layout1)).check(matches(isDisplayed()));
        onView(withId(R.id.create)).perform(click());
        onView(withId(R.id.list_reunion)).check(matches(isDisplayed()));
        onView(ViewMatchers.withId(R.id.list_reunion)).check(withItemCount(ITEMS_COUNT+1));
    };

    /**
     * We ensure that our recyclerview is displaying at least on item
     */
    @Test
    public void myReunionList_shouldNotBeEmpty() {
        // First scroll to the position that needs to be matched and click on it.
        onView(ViewMatchers.withId(R.id.list_reunion))
                .check(matches(hasMinimumChildCount(1)));
    }
    /**
     * When we delete an item, the item is no more shown
     */
    @Test
    public void myReunionList_deleteAction_shouldRemoveItem() {
        // Given : We remove the element at position 2
        onView(ViewMatchers.withId(R.id.list_reunion)).check(withItemCount(ITEMS_COUNT));
        // When perform a click on a delete icon
        onView(ViewMatchers.withId(R.id.list_reunion))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, new DeleteViewAction()));
        // Then : the number of element is 6
        onView(ViewMatchers.withId(R.id.list_reunion)).check(withItemCount(ITEMS_COUNT-1));
    }

    /**
     * Test sur le filtre trie ascendant
     */
    @Test
    public void mTriDateAsc(){
        onView(withId(R.id.buttonFilter)).perform(click());
        onView(withId(R.id.menu_dateAsc)).perform(click());
   //     onView(ViewMatchers.withId(R.id.list_reunion)).
   //             check(ViewMatchers.<String>containsInAnyOrder((Collection<Matcher<? super String>>) withId(R.id.item_list_heureR)));
    }

    /**
     * Test sur le filtre trie descendant
     */
    @Test
    public void mTriDateDes(){
        onView(withId(R.id.buttonFilter)).perform(click());
        onView(withId(R.id.menu_dateDes)).perform(click());
    //    onView(ViewMatchers.withId(R.id.list_reunion)).
    //            check(ViewMatchers.<String>containsInAnyOrder((Collection<Matcher<? super String>>) withId(R.id.item_list_heureR)));
    }

    /**
     * Test sur le filtre trie descendant
     */
    @Test
    public void mTriLieu(){
        onView(withId(R.id.buttonFilter)).perform(click());
        onView(withId(R.id.menu_lieu)).perform(click());
  //      onView(ViewMatchers.withId(R.id.list_reunion)).
 //               check(ViewMatchers.<String>containsInAnyOrder((Collection<Matcher<? super String>>) withId(R.id.item_list_salleR)));
    }
}


