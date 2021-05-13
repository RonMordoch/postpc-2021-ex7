package exercises.android.ronm.makemysandwich

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import androidx.navigation.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.slider.Slider


/**
 * A simple [Fragment] subclass.
 * Use the [EditOrderFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EditOrderFragment : Fragment() {

    private lateinit var textViewCustomerMsg : TextView
    private lateinit var checkBoxEditHummus: CheckBox
    private lateinit var checkBoxEditTahini : CheckBox
    private lateinit var textViewEditPickles : TextView
    private lateinit var sliderEditPickles : Slider
    private lateinit var editTextEditCustomerComment : EditText
    private lateinit var fabDeleteOrder : FloatingActionButton


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_order, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // find all views
        textViewCustomerMsg = view.findViewById(R.id.textViewCustomerMsg)
        checkBoxEditHummus = view.findViewById(R.id.checkBoxEditHummus)
        checkBoxEditTahini = view.findViewById(R.id.checkBoxEditTahini)
        textViewEditPickles = view.findViewById(R.id.textViewEditPickles)
        sliderEditPickles = view.findViewById(R.id.sliderEditPickles)
        editTextEditCustomerComment = view.findViewById(R.id.editTextEditCustomerComment)
        fabDeleteOrder = view.findViewById(R.id.fabDeleteOrder)

        val appContext = (activity?.applicationContext as MyApp)
        textViewCustomerMsg.text = appContext.info.orderId





        fabDeleteOrder.setOnClickListener {
            appContext.info.deleteOrder()
            view.findNavController().navigate(R.id.action_editOrderFragment_to_newOrderFragment2)
        }
    }

    private fun initViews(){

    }

}