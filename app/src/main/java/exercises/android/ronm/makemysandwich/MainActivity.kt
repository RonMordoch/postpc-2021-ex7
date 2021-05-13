package exercises.android.ronm.makemysandwich

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val appContext = applicationContext as MyApp
        val orderId = appContext.info.orderId
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        val navController = navHostFragment.navController
        if (orderId == ""){return}
//        val order = appContext.info.getOrder(orderId)
        navController.popBackStack(R.id.newOrderFragment, true)
        navController.navigate(R.id.editOrderFragment)

    }
}