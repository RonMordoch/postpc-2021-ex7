package exercises.android.ronm.makemysandwich

import android.app.Application


class MyApp : Application() {

    lateinit var info: UserInfoStore

    override fun onCreate() {
        super.onCreate()
        info = UserInfoStore(this)
    }


}