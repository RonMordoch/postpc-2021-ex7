package exercises.android.ronm.makemysandwich

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import exercises.android.ronm.makemysandwich.fragments.EditOrderFragment
import exercises.android.ronm.makemysandwich.fragments.NewOrderFragment
import exercises.android.ronm.makemysandwich.fragments.OrderReadyFragment
import junit.framework.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class NavigationTest {


    @Test
    fun testNavigation_from_newOrderFragment_to_editOrderFragment() {
        // Create a TestNavHostController
        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())
        // reset order id
        ApplicationProvider.getApplicationContext<MakeMySandwichApp>().info.orderId = ""

        // Create a graphical FragmentScenario for the TitleScreen
        val fragmentScenario = launchFragmentInContainer<NewOrderFragment>(themeResId = R.style.Theme_MakeMySandwich)

        fragmentScenario.onFragment { fragment ->
            // Set the graph on the TestNavHostController
            navController.setGraph(R.navigation.navigation_graph)

            // Make the NavController available via the findNavController() APIs
            Navigation.setViewNavController(fragment.requireView(), navController)
            navController.navigate(R.id.newOrderFragment)
        }

        // Verify that performing a click changes the NavController’s state
        onView(withId(R.id.fabCreateOrder)).perform(click())
        assertEquals(navController.currentDestination?.id, R.id.editOrderFragment)
    }


    @Test
    fun testNavigation_from_editOrderFragment_to_NewOrderFragment() {
        // Create a TestNavHostController
        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())
        // reset order id
        ApplicationProvider.getApplicationContext<MakeMySandwichApp>().info.orderId = ""

        // Create a graphical FragmentScenario for the TitleScreen
        val fragmentScenario = launchFragmentInContainer<EditOrderFragment>(themeResId = R.style.Theme_MakeMySandwich)

        fragmentScenario.onFragment { fragment ->
            // Set the graph on the TestNavHostController
            navController.setGraph(R.navigation.navigation_graph)

            // Make the NavController available via the findNavController() APIs
            Navigation.setViewNavController(fragment.requireView(), navController)
            navController.navigate(R.id.editOrderFragment)
        }

        // Verify that performing a click changes the NavController’s state
        onView(withId(R.id.fabDeleteOrder)).perform(click())
        assertEquals(navController.currentDestination?.id, R.id.newOrderFragment)
    }



    @Test
    fun testNavigation_from_orderReadyFragment_to_NewOrderFragment() {
        // Create a TestNavHostController
        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())
        // reset order id
        ApplicationProvider.getApplicationContext<MakeMySandwichApp>().info.orderId = ""

        // Create a graphical FragmentScenario for the TitleScreen
        val fragmentScenario = launchFragmentInContainer<OrderReadyFragment>(themeResId = R.style.Theme_MakeMySandwich)

        fragmentScenario.onFragment { fragment ->
            // Set the graph on the TestNavHostController
            navController.setGraph(R.navigation.navigation_graph)

            // Make the NavController available via the findNavController() APIs
            Navigation.setViewNavController(fragment.requireView(), navController)
            navController.navigate(R.id.orderReadyFragment)
        }

        // Verify that performing a click changes the NavController’s state
        onView(withId(R.id.fabCollectOrder)).perform(click())
        assertEquals(navController.currentDestination?.id, R.id.newOrderFragment)
    }

}