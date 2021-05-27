package exercises.android.ronm.makemysandwich

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.slider.Slider

class NewOrderFragment : Fragment(R.layout.fragment_new_order) {

    private lateinit var editTextCustomerName: EditText
    private lateinit var textViewCustomerName: TextView
    private lateinit var fabStartEditName: FloatingActionButton
    private lateinit var fabFinishEditName: FloatingActionButton
    private lateinit var checkBoxHummus: CheckBox
    private lateinit var checkBoxTahini: CheckBox
    private lateinit var textViewPickles: TextView
    private lateinit var sliderPickles: Slider
    private lateinit var editTextCustomerComment: EditText
    private lateinit var fabCreateOrder: FloatingActionButton
    private lateinit var appContext: MyApp


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_new_order, container, false)
    }

    // called after onCreateView
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // find all views
        editTextCustomerName = view.findViewById(R.id.editTextCustomerName)
        textViewCustomerName = view.findViewById(R.id.textViewCustomerName)
        fabStartEditName = view.findViewById(R.id.fabStartEditName)
        fabFinishEditName = view.findViewById(R.id.fabFinishEditName)
        checkBoxHummus = view.findViewById(R.id.checkBoxHummus)
        checkBoxTahini = view.findViewById(R.id.checkBoxTahini)
        textViewPickles = view.findViewById(R.id.textViewPickles)
        sliderPickles = view.findViewById(R.id.sliderPickles)
        editTextCustomerComment = view.findViewById(R.id.editTextCustomerComment)
        fabCreateOrder = view.findViewById(R.id.fabCreateOrder)
        // get app context
        appContext = activity?.applicationContext as MyApp

        // disable all order views views
        disableOrderViews()
        // load name into customer-name text view and edit text
        textViewCustomerName.text = appContext.info.customerName
        editTextCustomerName.setText(appContext.info.customerName)
        initCustomerName()


        fabStartEditName.setOnClickListener {
            // disable order views when editing name in order to force user to order only when having a valid name
            startEditName()
        }

        fabFinishEditName.setOnClickListener {
            // get the text from edit-text, if it is empty force user to enter a valid name
            finishEditName()
        }


        fabCreateOrder.setOnClickListener {
            createOrder()
            view.findNavController().navigate(R.id.action_newOrderFragment_to_editOrderFragment)
        }

    }

    /** Called once when the fragment's layout is created */
    private fun initCustomerName() {
        val name = appContext.info.customerName
        if (name == "") {
            fabStartEditName.visibility = View.INVISIBLE
            fabFinishEditName.visibility = View.VISIBLE
            editTextCustomerName.visibility = View.VISIBLE
            textViewCustomerName.visibility = View.INVISIBLE
        } else {
            fabStartEditName.visibility = View.VISIBLE
            fabFinishEditName.visibility = View.INVISIBLE
            editTextCustomerName.visibility = View.INVISIBLE
            textViewCustomerName.visibility = View.VISIBLE
            textViewCustomerName.text = getString(R.string.customer_name_welcome_msg, name)
            enableOrderViews()
        }
    }


    private fun startEditName() {
        disableOrderViews()
        // animate the edit button out and the finish button in
        fadeOutAnimation(fabStartEditName)
        fadeInAnimation(fabFinishEditName)
        // enable editing
        textViewCustomerName.visibility = View.INVISIBLE
        editTextCustomerName.visibility = View.VISIBLE
        // pop up the keyboard
        showSoftKeyboard(editTextCustomerName)
    }

    private fun finishEditName() {
        val nameTyped = editTextCustomerName.text.toString()
        if (nameTyped == "") {
            editTextCustomerName.error = getString(R.string.customer_name_error_msg)
            return
        }
        // animate the finish button out and the edit button in
        fadeOutAnimation(fabFinishEditName)
        fadeInAnimation(fabStartEditName)
        // disable editing
        editTextCustomerName.visibility = View.INVISIBLE
        textViewCustomerName.visibility = View.VISIBLE
        // update text-view with name and store the name in application-class
        textViewCustomerName.text = getString(R.string.customer_name_welcome_msg, nameTyped)
        appContext.info.customerName = nameTyped
        // hide the keyboard after editing
        hideSoftKeyboard(editTextCustomerName)
        // enable order-views after user has set a valid name
        enableOrderViews()
    }


    private fun createOrder() {
        val customerName: String = editTextCustomerName.text.toString()
        val numPickles: Int = sliderPickles.value.toInt()
        val hummus: Boolean = checkBoxHummus.isChecked
        val tahini: Boolean = checkBoxTahini.isChecked
        val customerComment: String = editTextCustomerComment.text.toString()
        val order = Order(customerName, numPickles, hummus, tahini, customerComment)
        // saves the created order id as well
        appContext.info.addOrder(order)
    }

    private fun disableOrderViews() {
        checkBoxHummus.visibility = View.INVISIBLE
        checkBoxTahini.visibility = View.INVISIBLE
        textViewPickles.visibility = View.INVISIBLE
        sliderPickles.visibility = View.INVISIBLE
        editTextCustomerComment.visibility = View.INVISIBLE
        fabCreateOrder.visibility = View.INVISIBLE
    }

    private fun enableOrderViews() {
        checkBoxHummus.visibility = View.VISIBLE
        checkBoxTahini.visibility = View.VISIBLE
        textViewPickles.visibility = View.VISIBLE
        sliderPickles.visibility = View.VISIBLE
        editTextCustomerComment.visibility = View.VISIBLE
        fabCreateOrder.visibility = View.VISIBLE
    }

    private fun showSoftKeyboard(view: View) {
        if (view.requestFocus()) {
            val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
        }
    }

    private fun hideSoftKeyboard(view: View) {
        val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm!!.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun fadeInAnimation(view: View) {
        view.visibility = View.VISIBLE
        view.alpha = 0f
        view.animate()
            .alpha(1f)
            .setDuration(200L)
            .start()
    }

    private fun fadeOutAnimation(view: View) {
        view.animate()
            .alpha(0f)
            .setStartDelay(100L)
            .setDuration(200L)
            .withEndAction { view.visibility = View.INVISIBLE }.start()
    }


}
