package exercises.android.ronm.makemysandwich

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


const val SP_CUSTOMER_NAME_KEY = "customer_name"
const val SP_ORDER_ID_KEY = "order_id"

class UserInfoStore(context: Context) {

    var orderId: String = ""
    var customerName: String = ""

    private val sp: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    init {
        loadFromSP()
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
        val db = Firebase.firestore
        orderId = order.orderId
        saveToSP()
        db.collection("orders").document(order.orderId).set(order)
    }

    fun deleteOrder(){
        val db = Firebase.firestore
        db.collection("orders").document(orderId).delete()
        orderId = ""
        saveToSP()
    }

//    fun getOrder(orderId: String) {
//        val db = Firebase.firestore
//        if (orderId != "") {
//            val orderRef = db.collection("orders").get().addOnCompleteListener { task: Task<QuerySnapshot> ->
//                    val result = task.result
//                    if (task.isSuccessful && result != null) {
//                        for (document in result) {
//                            val documentId = document.id
//                            if (documentId == orderId) {
//                                val order = document.toObject(Order::class.java)
//                                ;
//                            }
//                        }
//                    }
//                }
//        }

    }