package exercises.android.ronm.makemysandwich

import android.app.Application
import android.content.SharedPreferences
import android.preference.PreferenceManager



class MyApp : Application() {

    lateinit var info : UserInfoStore

    override fun onCreate() {
        super.onCreate()
        info = UserInfoStore(this)
    }


}