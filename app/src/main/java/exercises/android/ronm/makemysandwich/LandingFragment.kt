package exercises.android.ronm.makemysandwich

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import com.google.firebase.firestore.ktx.toObject


class LandingFragment : Fragment() {

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
        // listen once and then navigate to the relevant fragment
        val docRef = appContext.info.getFireStoreDocRef()
        docRef.get().addOnSuccessListener { documentSnapshot ->
            val order = documentSnapshot.toObject<Order>()
            when (order?.status) {
                Order.Status.WAITING -> {
                    navController.navigate(R.id.action_landingFragment_to_editOrderFragment) // here order id is necessarily not "" so we have a created order
                }
                Order.Status.IN_PROGRESS -> {
                    navController.navigate(R.id.action_landingFragment_to_orderInProgressFragment)
                }
                Order.Status.DONE -> {
                    navController.navigate(R.id.action_landingFragment_to_orderReadyFragment)
                }
                else -> { // safety case, navigate to new order fragment
                    navController.navigate(R.id.action_landingFragment_to_newOrderFragment)
                }
            }
        }

    }


}