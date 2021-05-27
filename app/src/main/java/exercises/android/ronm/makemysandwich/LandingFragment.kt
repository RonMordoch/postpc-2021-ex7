package exercises.android.ronm.makemysandwich

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.ktx.toObject


class LandingFragment : Fragment() {

    private lateinit var liveQuery: ListenerRegistration

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_landing, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val appContext = activity?.applicationContext as MyApp
        val navHostFragment =
            activity?.supportFragmentManager?.findFragmentById(R.id.navHostFragment) as NavHostFragment
        val navController = navHostFragment.navController
        // no order yet, navigate to new order fragment
        if (appContext.info.orderId == "") {
            navController.navigate(R.id.action_landingFragment_to_newOrderFragment)
            return
        }

        // listen and navigate to the relevant fragment
        val docRef = appContext.info.getFireStoreDocRef()
        liveQuery = docRef.addSnapshotListener { snapshot, e ->
            if (e != null) { // listen failed
                return@addSnapshotListener
            }
            if (snapshot != null && snapshot.exists()) { // listen succeeded
                try {
                    val order = snapshot.toObject<Order>()
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
                } catch (e: Exception) {
                    // couldn't convert item, return until database owner fix
                }
            } else { // listener returns null
                return@addSnapshotListener
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        liveQuery.remove() // remove the snapshot listener when fragment is destroyed upon navigation
    }


}