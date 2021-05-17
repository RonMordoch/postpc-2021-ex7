package exercises.android.ronm.makemysandwich

class Order(var customerName: String = "",
            var numPickles : Int = 0,
            var hummus : Boolean = false,
            var tahini: Boolean = false,
            var customerComment : String = "") {

    enum class Status {WAITING, IN_PROGRESS, READY, DONE}
    val orderId : String
    var status: Status = Status.WAITING

    init {
        val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
        orderId =  (1..11)
            .map { allowedChars.random() }
            .joinToString("")
    }
}