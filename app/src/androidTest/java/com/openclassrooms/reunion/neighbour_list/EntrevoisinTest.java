package com.openclassrooms.reunion.neighbour_list;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;


import com.openclassrooms.reunion.ui.reunion_list.ListReunionActivity;

import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.rule.ActivityTestRule;

import com.openclassrooms.reunion.R;

import static org.hamcrest.Matchers.not;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;



@RunWith(JUnit4.class)
public class EntrevoisinTest {
    /**
     * test instrumentalisé sur le
     * projet
     */
    private static int ITEMS_COUNT = 12;
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

    @Test
    public void checkTests(){

        //test vérifiant que lorsqu’on clique sur un élément de la liste, l’écran de
        //détails est bien lancé ;
        Intents.init();
        onView(withId(R.id.list_neighbours)).perform(
                RecyclerViewActions.actionOnItemAtPosition(0,click()));

        onView(withId(R.id.container_view)).check(matches(isDisplayed()));

         //test vérifiant qu’au démarrage de ce nouvel écran, le TextView indiquant
        //le nom de l’utilisateur en question est bien rempli ;
        onView(withId(R.id.nameLyt)).check(matches(not(withText(""))));


    }

    @Test
    public void checkButtonRetour(){
        // test vérifiant que lorsque l'on clique sur la flèche,
        // la liste s'affiche correctement

        onView(withId(R.id.list_neighbours)).perform(
                RecyclerViewActions.actionOnItemAtPosition(0,click()));
        onView(withId(R.id.layout1)).check(matches(isDisplayed()));
        onView(withId(R.id.ReturnButton)).perform(click());
        onView(withId(R.id.list_neighbours)).check(matches(isDisplayed()));
    };

    @Test
    public void checkButtonEtoile(){
        // test vérifiant que lorsque l'on clique sur le bouton étoile,
        // la liste favorie s'affiche
        onView(withId(R.id.list_neighbours)).perform(
                RecyclerViewActions.actionOnItemAtPosition(0,click()));
        onView(withId(R.id.button_etoile)).perform(click());


        onView(withId(R.id.list_neighbours_favoris)).check(matches(isDisplayed()));
    };
    @Test
    public void checkAvatar(){
        // test vérifiant que lorsque l'on clique sur le bouton étoile,
        // la liste principale par défault s'affiche correctement

        onView(withId(R.id.list_neighbours)).perform(
                RecyclerViewActions.actionOnItemAtPosition(0,click()));
        onView(withId(R.id.layout1)).check(matches(isDisplayed()));
        onView(withId(R.id.kb9)).check(matches(isDisplayed()));
    };

    @Test
    public void checkName(){
        // test vérifiant que lorsque l'on clique sur le bouton étoile,
        // la liste principale par défault s'affiche correctement

        onView(withId(R.id.list_neighbours)).perform(
                RecyclerViewActions.actionOnItemAtPosition(0,click()));
        onView(withId(R.id.layout1)).check(matches(isDisplayed()));
        onView(withId(R.id.nameLyt)).check(matches(CoreMatchers.not(withText(""))));

    };

};
