package exercises.android.ronm.makemysandwich

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


const val SP_CUSTOMER_NAME_KEY = "customer_name"
const val SP_ORDER_ID_KEY = "order_id"
const val FIREBASE_DB_NAME = "orders"

class UserInfoStore(context: Context) {

    var orderId: String = ""
    var customerName: String = ""

    private val db = Firebase.firestore
    private val sp: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    init {
        loadFromSP()
    }

    fun getFireStoreDocRef(): DocumentReference {
        return db.collection(FIREBASE_DB_NAME).document(orderId)
    }

    private fun loadFromSP() {
        orderId = sp.getString(SP_ORDER_ID_KEY, "").toString()
        customerName = sp.getString(SP_CUSTOMER_NAME_KEY, "").toString()
    }

    private fun saveToSP() {
        val editor = sp.edit()
        editor.clear()
        editor.putString(SP_ORDER_ID_KEY, orderId)
        editor.putString(SP_CUSTOMER_NAME_KEY, customerName)
        editor.apply()
    }

    fun addOrder(order: Order) {
        if (order.orderId == "") { // invalid call with an invalid order
            return
        }
        orderId = order.orderId
        saveToSP()
        db.collection(FIREBASE_DB_NAME).document(order.orderId).set(order)
    }

    fun deleteOrder() {
        if (orderId != "") { // avoid subsequent calls to delete when no order exists
            db.collection(FIREBASE_DB_NAME).document(orderId).delete()
            orderId = ""
            saveToSP()
        }
    }


}