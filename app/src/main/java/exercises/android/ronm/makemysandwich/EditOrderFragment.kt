package exercises.android.ronm.makemysandwich

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton


/**
 * A simple [Fragment] subclass.
 * Use the [EditOrderFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EditOrderFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_order, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fabDeleteOrder : FloatingActionButton = view.findViewById(R.id.fabDeleteOrder)
        fabDeleteOrder.setOnClickListener {
            view.findNavController().navigate(R.id.action_editOrderFragment_to_newOrderFragment2)
        }
    }

}