package exercises.android.ronm.makemysandwich


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.navigation.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.ktx.toObject

class OrderReadyFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_order_ready, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // find the collect order button, upon click delete the order from db and move to the new order fragment
        val fabCollectOrder: FloatingActionButton = view.findViewById(R.id.fabCollectOrder)
        fabCollectOrder.setOnClickListener {
            val appContext = (activity?.applicationContext as MyApp)
            // load the order from database, edit it when it finished and update the status by overriding
            val docRef = appContext.info.getFireStoreDocRef()
            docRef.get().addOnSuccessListener { documentSnapshot ->
                val order = documentSnapshot.toObject<Order>()
                order?.status = Order.Status.DONE;
                appContext.info.markOrderDone(order)
                view.findNavController().navigate(R.id.action_orderReadyFragment_to_newOrderFragment)
            }
            // vibrate button once to encourage clicking
            fabCollectOrder.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.vibrate))

        }

    }
}