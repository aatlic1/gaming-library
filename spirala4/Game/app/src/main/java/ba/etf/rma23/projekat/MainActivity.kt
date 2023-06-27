package ba.etf.rma23.projekat

import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            val bundle = Bundle()
            bundle.putString("game_title", "Grand Theft Auto V")

            val details = GameDetailsFragment()
            details.arguments = bundle

            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.nav_host_fragment, details)
            transaction.addToBackStack(null)
            transaction.commit()
        }
        else {
            val navHostFragment =
                supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
            val navController = navHostFragment.navController
            val navView: BottomNavigationView = findViewById(R.id.bottom_nav)
            navView.setupWithNavController(navController)


            navView.setOnItemSelectedListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.gameDetailsFragment -> {
                        if(lastGame != null) {
                            val bundle = Bundle()
                            lastGame?.id?.let { bundle.putInt("game_ID", it) }
                            navController.navigate(R.id.gameDetailsFragment, bundle)
                            true
                        }
                        else false
                    }
                    R.id.homeFragment -> {
                        navController.navigate(R.id.homeFragment)
                        true
                    }
                    R.id.favoritesGamesFragment -> {
                        navController.navigate(R.id.favoritesGamesFragment)
                        true
                    }
                    R.id.accountFragment -> {
                        navController.navigate(R.id.accountFragment)
                        true
                    }
                    else -> false
                }
            }
        }
    }
}
