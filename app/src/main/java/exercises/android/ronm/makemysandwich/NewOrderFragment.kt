package exercises.android.ronm.makemysandwich

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton

class NewOrderFragment : Fragment(R.layout.fragment_new_order) {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_new_order, container, false)
    }

// called after onCreateView
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fabCreateOrder : FloatingActionButton = view.findViewById(R.id.fabCreateOrder)
        fabCreateOrder.setOnClickListener {
            view.findNavController().navigate(R.id.action_newOrderFragment_to_editOrderFragment)}
    }
}

//
//example
//override fun onViewCreated(
//    view: View,
//    savedInstanceState: Bundle?
//) {
//    val nextButton = view.findViewById<View>(R.id.button_next)
//    nextButton.setOnClickListener(
//        Navigation.createNavigateOnClickListener(R.id.mainFragmentDestanation, null)
//    )
//}
