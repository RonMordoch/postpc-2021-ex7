package exercises.android.ronm.makemysandwich

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.findNavController


class OrderInProgressFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_order_in_progress, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val appContext = (activity?.applicationContext as MyApp)
        val orderObserver = Observer<Order?> { order ->
            if (order?.status == Order.Status.READY) { // order status moved to READY, enable collection
                view.findNavController()
                    .navigate(R.id.action_orderInProgressFragment_to_orderReadyFragment)
            }
        }
        appContext.info.orderLiveData.observe(viewLifecycleOwner, orderObserver)

    }


}