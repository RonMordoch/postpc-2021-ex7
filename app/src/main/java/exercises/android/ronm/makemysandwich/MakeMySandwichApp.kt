package exercises.android.ronm.makemysandwich

import android.app.Application
import com.google.firebase.FirebaseApp
import exercises.android.ronm.makemysandwich.data.UserInfoStore


class MakeMySandwichApp : Application() {

    lateinit var info: UserInfoStore

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this) // to enable unit testing with robo-electric, app works without this line
        info = UserInfoStore(this)
    }


}