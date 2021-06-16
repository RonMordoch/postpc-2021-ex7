package exercises.android.ronm.makemysandwich.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import exercises.android.ronm.makemysandwich.MakeMySandwichApp
import exercises.android.ronm.makemysandwich.data.Order
import exercises.android.ronm.makemysandwich.R


class OrderInProgressFragment : Fragment(R.layout.fragment_order_in_progress) {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val appContext = (activity?.applicationContext as MakeMySandwichApp)
        val orderObserver = Observer<Order?> { order ->
            if (order?.status == Order.Status.READY) { // order status moved to READY, enable collection
                view.findNavController()
                    .navigate(R.id.action_orderInProgressFragment_to_orderReadyFragment)
            }
        }
        appContext.info.orderLiveData.observe(viewLifecycleOwner, orderObserver)

    }


}