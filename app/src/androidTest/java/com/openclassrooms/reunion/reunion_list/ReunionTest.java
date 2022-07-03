package com.openclassrooms.reunion.reunion_list;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;


import static com.openclassrooms.reunion.utils.RecyclerViewItemCountAssertion.withItemCount;


import com.openclassrooms.reunion.ui.reunion_list.ListReunionActivity;


import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.contrib.PickerActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.rule.ActivityTestRule;

import com.openclassrooms.reunion.R;
import com.openclassrooms.reunion.utils.DeleteViewAction;


import static org.hamcrest.Matchers.not;

import android.widget.DatePicker;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;


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
    /**
     * When we delete an item, the item is no more shown
     */
    @Test
    public void testDeleteReunion() {
        // Given : We remove the element at position 2
        onView(ViewMatchers.withId(R.id.list_reunion)).check(withItemCount(ITEMS_COUNT+1));
        // When perform a click on a delete icon
        onView(ViewMatchers.withId(R.id.list_reunion))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, new DeleteViewAction()));
        // Then : the number of element is 6
        onView(ViewMatchers.withId(R.id.list_reunion)).check(withItemCount(ITEMS_COUNT));
    }

    @Test
    public void testAddReunion(){

        onView(ViewMatchers.withId(R.id.list_reunion)).check(withItemCount(ITEMS_COUNT));
        onView(withId(R.id.add_reunion)).perform(click());

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

    @Test
    public void checkTests(){

        onView(withId(R.id.list_reunion)).perform(
                RecyclerViewActions.actionOnItemAtPosition(0,click()));

        Intents.init();

        onView(withId(R.id.titre_Reunion)).check(matches(not(withText(""))));
        Intents.release();



    }


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
     * Test sur le filtre par date
     */
    @Test
    public void mFiltreDate(){


        onView(withId(R.id.buttonFilter)).perform(click());

        onView(withText(R.string.menu_date)).perform(click());

        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(2022, 7, 15));

        onView(withId(android.R.id.button1)).perform(click());
        onView(withId(R.id.list_reunion)).check(matches(isDisplayed()));


    }


    /**
     * Test sur le filtre par Lieu
     */
    @Test
    public void mFiltre_Lieu(){
        onView(withId(R.id.buttonFilter)).perform(click());

        onView(withText(R.string.menu_lieu)).perform(click());

        onView(ViewMatchers.withId(R.id.edit_search))
                .perform(ViewActions.replaceText("Mangallet"),
                        ViewActions.closeSoftKeyboard()).perform(click());
         onView(withId(R.id.list_reunion)).check(matches(isDisplayed()));
    }
}


