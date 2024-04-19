package ba.etf.rma23.projekat

import android.content.pm.ActivityInfo
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.PositionAssertions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.intent.matcher.IntentMatchers.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.core.AllOf.allOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class OwnEspressoTests {

    @get:Rule
    var homeRule: ActivityScenarioRule<HomeActivity> = ActivityScenarioRule(HomeActivity::class.java)

    //testiranje details dugmeta
    @Test
    fun testniScenario1(){

        //klikom na details nista se nece desiti
        onView(withId(R.id.gameDetailsFragment)).perform(click())
        onView(withId(R.id.search_button)).check(matches(isDisplayed()))

        //odlazak na igricu
        var game = GameData.getAll().get(6)
        onView(withId(R.id.game_list)).perform(RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(allOf(
            hasDescendant(withText(game.title)),
            hasDescendant(withText(game.rating.toString())),
            hasDescendant(withText(game.releaseDate)),
            hasDescendant(withText(game.platform))
        ),click()))
        onView(withId(R.id.game_details_fragment_parent)).check(matches(isDisplayed()))

        //povratak na home
        onView(withId(R.id.homeFragment)).perform(click())

        //treba odvesti na "Destiny 2"
        onView(withId(R.id.gameDetailsFragment)).perform(click())
        onView(withId(R.id.item_title_textview)).check(matches(withText("Destiny 2")))
    }

    //testirat cemo klik na drugu igricu i da li se otvara druga igrica, te da je uredu povratak na home
    @Test
    fun testniScenario2(){
        //provjera da li se radi o drugoj igrici, klik na igru
        var game = GameData.getAll().get(1)
        onView(withId(R.id.game_list)).perform(RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(allOf(
            hasDescendant(withText(game.title)),
            hasDescendant(withText(game.rating.toString())),
            hasDescendant(withText(game.releaseDate)),
            hasDescendant(withText(game.platform))
        ),click()))

        //provjera da li se otvorila dobra igrica
        onView(withId(R.id.esrb_rating_textview)).check(matches(withSubstring("and battle hordes of monsters")))

        //povratak na home
        onView(withId(R.id.homeFragment)).perform(click())
        onView(withId(R.id.search_query_edittext)).check(matches(isDisplayed()))
    }


    //testiranje landscape, da li otvara prvu igricu i funckionise li navigacija izmedju fragmenata
    @Test
    fun testniScenario3(){
        
        //u landscape
        homeRule.scenario.onActivity { activity ->
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        }
        
        // provjera da li s otvorila prva igrica
        onView(withId(R.id.item_title_textview)).check(matches(withText("Grand Theft Auto V")))
        
        //povratak na home preko back button i provjera
        pressBack()
        onView(withId(R.id.home_fragment_parent)).check(matches(isDisplayed()))

        //odlazak na drugu igricu i provjera
        var game = GameData.getAll().get(1)
        onView(withId(R.id.game_list)).perform(RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(allOf(
            hasDescendant(withText(game.title)),
            hasDescendant(withText(game.rating.toString())),
            hasDescendant(withText(game.releaseDate)),
            hasDescendant(withText(game.platform))
        ),click()))
        onView(withId(R.id.game_details_fragment_parent)).check(matches(isDisplayed()))
    }

    //kombinacija pejzaskog i portreskog nacina
    @Test
    fun testniScenario4(){
        //provjera da li se radi o trecu igrici, klik na igru
        var game = GameData.getAll().get(2)
        onView(withId(R.id.game_list)).perform(RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(allOf(
            hasDescendant(withText(game.title)),
            hasDescendant(withText(game.rating.toString())),
            hasDescendant(withText(game.releaseDate)),
            hasDescendant(withText(game.platform))
        ),click()))

        //provjera da li se otvorila dobra igrica
        onView(withId(R.id.description_textview)).check(matches(withSubstring("in which players utilize")))

        //povratak na home
        onView(withId(R.id.bottom_nav)).perform(click())

        //u landscape
        homeRule.scenario.onActivity { activity ->
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        }

        // provjera da li s otvorila prva igrica
        onView(withId(R.id.item_title_textview)).check(matches(withText("Grand Theft Auto V")))

        //povratak na home preko back button i provjera
        pressBack()
        onView(withId(R.id.home_fragment_parent)).check(matches(isDisplayed()))

        //prelazak u portret
        homeRule.scenario.onActivity { activity ->
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }
        val lista = GameData.getAll()
        for(game in lista)
            onView(withId(R.id.game_list)).perform(
                RecyclerViewActions.scrollTo<RecyclerView.ViewHolder>(
                    hasDescendant(withText(game.title))
                )
            )
    }

    //testiranje rasporeda u GameDetails
    @Test
    fun testiranjeRasporedaGameDetails(){
        //na prvu igricu
        var game = GameData.getAll().get(0)
        onView(withId(R.id.game_list)).perform(RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(allOf(
            hasDescendant(withText(game.title)),
            hasDescendant(withText(game.rating.toString())),
            hasDescendant(withText(game.releaseDate)),
            hasDescendant(withText(game.platform))
        ),click()))

        //provjera rasporeda
        onView(withId(R.id.item_title_textview)).check(isCompletelyAbove(withId(R.id.release_date_textview)))
        onView(withId(R.id.cover_imageview)).check(
            isCompletelyLeftOf(withId(R.id.release_date_textview)))
        onView(withId(R.id.cover_imageview)).check(
            isCompletelyBelow(withId(R.id.item_title_textview)))
        onView(withId(R.id.developer_textview)).check(isCompletelyRightOf(withId(R.id.cover_imageview)))
        onView(withId(R.id.developer_textview)).check(isCompletelyBelow(withId(R.id.release_date_textview)))
        onView(withId(R.id.publisher_textview)).check(isCompletelyBelow(withId(R.id.developer_textview)))
        onView(withId(R.id.publisher_textview)).check(isCompletelyRightOf(withId(R.id.cover_imageview)))
        onView(withId(R.id.platform_textview)).check(isCompletelyBelow(withId(R.id.publisher_textview)))
        onView(withId(R.id.platform_textview)).check(isCompletelyBelow(withId(R.id.cover_imageview)))
        onView(withId(R.id.genre_textview)).check(isCompletelyBelow(withId(R.id.platform_textview)))
        onView(withId(R.id.description_textview)).check(isCompletelyBelow(withId(R.id.genre_textview)))
        onView(withId(R.id.esrb_rating_textview)).check(isCompletelyBelow(withId(R.id.description_textview)))
        onView(withId(R.id.user_impression)).check(isCompletelyBelow(withId(R.id.esrb_rating_textview)))
    }

}