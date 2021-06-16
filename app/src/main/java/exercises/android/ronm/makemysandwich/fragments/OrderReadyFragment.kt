package exercises.android.ronm.makemysandwich.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.view.animation.AnimationUtils
import androidx.navigation.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import exercises.android.ronm.makemysandwich.MakeMySandwichApp
import exercises.android.ronm.makemysandwich.R


class OrderReadyFragment : Fragment(R.layout.fragment_order_ready) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // find the collect order button, upon click delete the order from db and move to the new order fragment
        val fabCollectOrder: FloatingActionButton = view.findViewById(R.id.fabCollectOrder)
        fabCollectOrder.setOnClickListener {
            val appContext = (activity?.applicationContext as MakeMySandwichApp)
            appContext.info.markOrderDone()
            view.findNavController().navigate(R.id.action_orderReadyFragment_to_newOrderFragment)
        }
        // vibrate button once to encourage clicking
        fabCollectOrder.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.vibrate))
    }


}