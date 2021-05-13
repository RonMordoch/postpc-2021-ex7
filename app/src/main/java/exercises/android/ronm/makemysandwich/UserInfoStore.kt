package exercises.android.ronm.makemysandwich

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

const val SP_CUSTOMER_NAME_KEY = "customer_name"
const val SP_CUSTOMER_ORDER_ID_KEY = "order_id"

class UserInfoStore(context: Context) {

    var orderId : String = ""
        set(value) {
            field = value
            saveToSP()
        }
    var customerName: String = ""
        set(value) {
            field = value
            saveToSP()
        }
    private val sp: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    init {
        loadFromSP()
    }

    private fun loadFromSP() {
        customerName = sp.getString(SP_CUSTOMER_NAME_KEY, "").toString()
        orderId = sp.getString(SP_CUSTOMER_ORDER_ID_KEY, "").toString()
    }

    private fun saveToSP() {
        val editor = sp.edit()
        editor.putString(SP_CUSTOMER_NAME_KEY, customerName)
        editor.putString(SP_CUSTOMER_ORDER_ID_KEY, orderId)
        editor.apply()
    }

    fun addOrder(order: Order){
        val db = Firebase.firestore
        db.collection("orders").add(order)
    }

}