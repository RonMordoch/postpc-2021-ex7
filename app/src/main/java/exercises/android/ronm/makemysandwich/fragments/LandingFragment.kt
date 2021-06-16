package exercises.android.ronm.makemysandwich.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import exercises.android.ronm.makemysandwich.MakeMySandwichApp
import exercises.android.ronm.makemysandwich.data.Order
import exercises.android.ronm.makemysandwich.R


class LandingFragment : Fragment(R.layout.fragment_landing) {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val appContext = activity?.applicationContext as MakeMySandwichApp
        val navHostFragment =
            activity?.supportFragmentManager?.findFragmentById(R.id.navHostFragment) as NavHostFragment
        val navController = navHostFragment.navController
        // no order yet, navigate to new order fragment
        if (appContext.info.orderId == "") {
            navController.navigate(R.id.action_landingFragment_to_newOrderFragment)
            return
        }

        val orderObserver = Observer<Order?> { order ->
            when (order?.status) {
                Order.Status.WAITING -> {
                    navController.navigate(R.id.action_landingFragment_to_editOrderFragment) // here order id is necessarily not "" so we have a created order
                }
                Order.Status.IN_PROGRESS -> {
                    navController.navigate(R.id.action_landingFragment_to_orderInProgressFragment)
                }
                Order.Status.READY -> {
                    navController.navigate(R.id.action_landingFragment_to_orderReadyFragment)
                }
                else -> { // either DONE on unexpected enum value, navigate to new order fragment
                    navController.navigate(R.id.action_landingFragment_to_newOrderFragment)
                }
            }
        }
        appContext.info.orderLiveData.observe(viewLifecycleOwner, orderObserver)


    }


}