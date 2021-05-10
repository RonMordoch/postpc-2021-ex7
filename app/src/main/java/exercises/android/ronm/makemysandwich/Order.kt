package exercises.android.ronm.makemysandwich

class Order(val customerName: String,
            var numPickles : Int,
            var hummus : Boolean,
            var tahini: Boolean,
            var customerComment : String) {

    enum class Status {WAITING, IN_PROGRESS, READY, DONE}

    init {
        var status = Status.WAITING
    }
}