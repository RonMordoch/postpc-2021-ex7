package exercises.android.ronm.makemysandwich

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase


const val SP_CUSTOMER_NAME_KEY = "customer_name"
const val SP_ORDER_ID_KEY = "order_id"
const val FIREBASE_DB_NAME = "orders"
const val SP_KEY = "USER_SP"

class UserInfoStore(context: Context) {

    var orderId: String = ""
    var customerName: String = ""
        set(value) {
            field = value
            saveToSP()
        }

    private val db = Firebase.firestore
    private val sp: SharedPreferences = context.getSharedPreferences(SP_KEY, Context.MODE_PRIVATE)
    val orderLiveData: MutableLiveData<Order?> = MutableLiveData()
    private lateinit var liveQuery: ListenerRegistration

    init {
        loadFromSP()
        if (orderId != "") {
            setOrderFirestoreListener()
        }
    }


    private fun setOrderFirestoreListener() {
        // listen and navigate to the relevant fragment
        val docRef = db.collection(FIREBASE_DB_NAME).document(orderId)
        liveQuery = docRef.addSnapshotListener { snapshot, e ->
            if (e != null) { // listen failed
                return@addSnapshotListener
            }
            if (snapshot != null && snapshot.exists()) { // listen succeeded
                try {
                    val order = snapshot.toObject<Order>()
                    orderLiveData.value = order
                } catch (e: Exception) {
                    // couldn't convert item, return until database owner fix the problematic fields (e.g. state that's not in the enum)
                }
            } else { // listener returns null
                return@addSnapshotListener
            }
        }
    }

    private fun removeOrderFirestoreListener() {
        liveQuery.remove()
    }


    private fun loadFromSP() {
        orderId = sp.getString(SP_ORDER_ID_KEY, "").toString()
        customerName = sp.getString(SP_CUSTOMER_NAME_KEY, "").toString()
    }

    private fun saveToSP() {
        val editor = sp.edit()
        editor.putString(SP_ORDER_ID_KEY, orderId)
        editor.putString(SP_CUSTOMER_NAME_KEY, customerName)
        editor.apply()
    }

    fun addOrder(order: Order) {
        if (order.orderId == "") { // invalid call with an invalid order
            return
        }
        // update orderId and save to SP
        orderId = order.orderId
        saveToSP()
        // create document in firestore database, update livedata and set listener
        db.collection(FIREBASE_DB_NAME).document(order.orderId).set(order)
        orderLiveData.value = order
        setOrderFirestoreListener()
    }

    fun updateOrder(order: Order) {
        if (order.orderId == "" || order.orderId != orderId) { // invalid call with an invalid order
            return
        }
        db.collection(FIREBASE_DB_NAME).document(order.orderId).set(order)
        orderLiveData.value = order
        // don't set up another listener here
    }

    fun markOrderDone() {
        if (orderId == "") {
            return
        }
        // else mark order as done, reset the order current order id and remove listener
        val doneOrder = orderLiveData.value
        if (doneOrder != null) {
            orderId = ""
            saveToSP()
            doneOrder.status = Order.Status.DONE
            db.collection(FIREBASE_DB_NAME).document(orderId).set(doneOrder)
            removeOrderFirestoreListener()
        }

    }

    fun deleteOrder() {
        if (orderId != "") { // avoid subsequent calls to delete when no order exists
            // reset the current order id, remove listener and delete from database
            orderId = ""
            saveToSP()
            removeOrderFirestoreListener()
            db.collection(FIREBASE_DB_NAME).document(orderId).delete()

        }
    }
}


