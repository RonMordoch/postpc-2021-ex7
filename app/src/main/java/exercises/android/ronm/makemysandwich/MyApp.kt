package exercises.android.ronm.makemysandwich

import android.app.Application
import android.content.SharedPreferences


class MyApp : Application() {

    public lateinit var order : Order
    private lateinit var sp : SharedPreferences

    override fun onCreate()
    {
        super.onCreate()
    }
}