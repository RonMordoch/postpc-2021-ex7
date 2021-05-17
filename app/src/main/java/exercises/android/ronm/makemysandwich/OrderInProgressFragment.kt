package exercises.android.ronm.makemysandwich

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.google.firebase.firestore.ktx.toObject


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
        val docRef =
            appContext.info.db.collection(FIREBASE_DB_NAME).document(appContext.info.orderId)
        docRef.addSnapshotListener { snapshot, e ->
            if (e != null) { // listen failed
                return@addSnapshotListener
            }
            if (snapshot != null && snapshot.exists()) { // listen succeeded
                val order = snapshot.toObject<Order>()
                if (order?.status == Order.Status.DONE) { // order status moved to IN_PROGRESS, cant make changes anymore
                    view.findNavController()
                        .navigate(R.id.action_orderInProgressFragment_to_orderReadyFragment)
                }
            } else { // listener returns null
                return@addSnapshotListener
            }
        }
    }


}