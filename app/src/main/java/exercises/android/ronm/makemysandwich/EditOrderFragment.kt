package exercises.android.ronm.makemysandwich

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.navigation.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.slider.Slider
import com.google.firebase.firestore.ktx.toObject


class EditOrderFragment : Fragment() {

    private lateinit var textViewCustomerMsg: TextView
    private lateinit var progressBarLoadingData: ProgressBar
    private lateinit var checkBoxEditHummus: CheckBox
    private lateinit var checkBoxEditTahini: CheckBox
    private lateinit var textViewEditPickles: TextView
    private lateinit var sliderEditPickles: Slider
    private lateinit var editTextEditCustomerComment: EditText
    private lateinit var fabDeleteOrder: FloatingActionButton
    private lateinit var btnSaveOrderEdit: Button
    private var order: Order? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_edit_order, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // find all views
        textViewCustomerMsg = view.findViewById(R.id.textViewCustomerMsg)
        progressBarLoadingData = view.findViewById(R.id.progressBarLoadingDataEdit)
        checkBoxEditHummus = view.findViewById(R.id.checkBoxEditHummus)
        checkBoxEditTahini = view.findViewById(R.id.checkBoxEditTahini)
        textViewEditPickles = view.findViewById(R.id.textViewEditPickles)
        sliderEditPickles = view.findViewById(R.id.sliderEditPickles)
        editTextEditCustomerComment = view.findViewById(R.id.editTextEditCustomerComment)
        btnSaveOrderEdit = view.findViewById(R.id.buttonSaveOrderEdit)
        fabDeleteOrder = view.findViewById(R.id.fabDeleteOrder)

        // set the loading data message and progress bar
        textViewCustomerMsg.visibility = View.VISIBLE
        disableOrderEdit()
        // fetch the order data
        val appContext = (activity?.applicationContext as MyApp)
        val docRef = appContext.info.db.collection("orders").document(appContext.info.orderId)
        docRef.addSnapshotListener { snapshot, e ->
            if (e != null) { // listen failed
                return@addSnapshotListener
            }
            if (snapshot != null && snapshot.exists()) { // listen succeeded
                order = snapshot.toObject<Order>()
                // check for both IN_PROGRESS, DONE because order might be ready immediately
                if (order?.status == Order.Status.IN_PROGRESS || order?.status == Order.Status.DONE) {
                    // cant make changes anymore, navigate forward
                    view.findNavController()
                        .navigate(R.id.action_editOrderFragment_to_orderInProgressFragment)
                }
                // else we finished loading data, enable edit
                enableOrderEdit()
                loadOrderToViews()

            } else { // listener returns null
                return@addSnapshotListener
            }
        }

        btnSaveOrderEdit.setOnClickListener {
            order?.hummus = checkBoxEditHummus.isChecked
            order?.tahini = checkBoxEditTahini.isChecked
            order?.numPickles = sliderEditPickles.value.toInt()
            order?.customerComment = editTextEditCustomerComment.text.toString()
            order?.let { it1 -> appContext.info.addOrder(it1) }
        }

        fabDeleteOrder.setOnClickListener {
            appContext.info.deleteOrder()
            view.findNavController().navigate(R.id.action_editOrderFragment_to_newOrderFragment2)
        }
    }

    private fun disableOrderEdit() {
        textViewCustomerMsg.text = "Please wait while we load your order.."
        progressBarLoadingData.visibility = View.VISIBLE
        checkBoxEditHummus.visibility = View.INVISIBLE
        checkBoxEditTahini.visibility = View.INVISIBLE
        textViewEditPickles.visibility = View.INVISIBLE
        sliderEditPickles.visibility = View.INVISIBLE
        editTextEditCustomerComment.visibility = View.INVISIBLE
        btnSaveOrderEdit.visibility = View.INVISIBLE
        fabDeleteOrder.visibility = View.INVISIBLE
    }


    private fun enableOrderEdit() {
        textViewCustomerMsg.text = "Feel free to edit your order before we start making it!"
        progressBarLoadingData.visibility = View.INVISIBLE
        checkBoxEditHummus.visibility = View.VISIBLE
        checkBoxEditTahini.visibility = View.VISIBLE
        textViewEditPickles.visibility = View.VISIBLE
        sliderEditPickles.visibility = View.VISIBLE
        editTextEditCustomerComment.visibility = View.VISIBLE
        btnSaveOrderEdit.visibility = View.VISIBLE
        fabDeleteOrder.visibility = View.VISIBLE
    }

    private fun loadOrderToViews() {
        checkBoxEditHummus.isChecked = order?.hummus ?: false
        checkBoxEditTahini.isChecked = order?.tahini ?: false
        sliderEditPickles.value = (order?.numPickles ?: 0).toFloat()
        editTextEditCustomerComment.setText(order?.customerComment ?: "")
    }


}