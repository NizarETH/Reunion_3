package com.openclassrooms.reunion.reunion_list;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.Espresso.openContextualActionModeOverflowMenu;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.hasSibling;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;


import static com.openclassrooms.reunion.utils.RecyclerViewItemCountAssertion.withItemCount;

import com.openclassrooms.reunion.model.Reunion;
import com.openclassrooms.reunion.ui.reunion_list.ListReunionActivity;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.rule.ActivityTestRule;

import com.openclassrooms.reunion.R;
import com.openclassrooms.reunion.utils.DeleteViewAction;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.not;
import static java.util.EnumSet.allOf;

import android.support.test.InstrumentationRegistry;

import org.hamcrest.CoreMatchers;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Collection;
import java.util.Collections;
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
        onView(withId(R.id.add_reunion)).perform(click());
        onView(withId(R.id.layout1)).check(matches(isDisplayed()));
        // Comment ==> addButton.setEnabled(true)
        onView(ViewMatchers.withId(R.id.inom_Reunion))
                .perform(ViewActions.replaceText("Reunion A"), ViewActions.closeSoftKeyboard());
        onView(ViewMatchers.withId(R.id.idate_Reunion))
                .perform(ViewActions.replaceText("15/04/2022"), ViewActions.closeSoftKeyboard());
        onView(ViewMatchers.withId(R.id.iheure_Reunion))
                .perform(ViewActions.replaceText("15h45"), ViewActions.closeSoftKeyboard());
        onView(ViewMatchers.withId(R.id.inom_salle_Reunion))
                .perform(ViewActions.replaceText("Mangallet"), ViewActions.closeSoftKeyboard());
        onView(ViewMatchers.withId(R.id.iparticipant_Reunion))
                .perform(ViewActions.replaceText("toto@hotmail.fr,ruth@ymail.com"), ViewActions.closeSoftKeyboard());
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
    public void mFiltreDate(){
        onView(withId(R.id.buttonFilter)).perform(click());

        openActionBarOverflowOrOptionsMenu(
                InstrumentationRegistry.getTargetContext());

        onView(withId(R.id.menu_date)).perform(click());
        onView(ViewMatchers.withId(R.id.menu_date))
                .perform(ViewActions.replaceText("15/0/2022"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.list_reunion)).check(matches(isDisplayed()));
      //  onView(allOf(withText().matches("15/0/2022"), hasSibling(withText("item: 0"))));
    }


    /**
     * Test sur le filtre par Lieu
     */
    @Test
    public void mFiltre_Lieu(){
        onView(withId(R.id.buttonFilter)).perform(click());

        openActionBarOverflowOrOptionsMenu(
                InstrumentationRegistry.getTargetContext());

      //  openContextualActionModeOverflowMenu();
        // Click on the icon.
        onView((withId(R.id.menu_lieu)))
                .perform(click());

        onView(ViewMatchers.withId(R.id.edit_search))
                .perform(ViewActions.replaceText("Mangallet"),
                        ViewActions.closeSoftKeyboard()).perform(click());
        onView(withId(R.id.list_reunion)).check(matches(isDisplayed()));
    }
}


