package noblur.com.modulotest.homecomponent

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import noblur.com.modulotest.R
import noblur.com.modulotest.controlcomponent.ControlActivity
import noblur.com.modulotest.data.entity.Event
import noblur.com.modulotest.profilcomponent.ProfilActivity
import noblur.com.modulotest.utils.obtainViewModel
import noblur.com.modulotest.utils.replaceFragmentInActivity
import noblur.com.modulotest.utils.setupActionBar

class HomeActivity : AppCompatActivity() {


    private lateinit var viewModel: HomeViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupActionBar(R.id.toolbar){
            title = "Page d'accueil"

        }



        setupViewFragment()

        viewModel =obtainViewModel().apply {

            newProfilPage.observe(this@HomeActivity, Observer<Event<Unit>> { event->

                event?.getContentIfNotHandled()?.let {

                    this@HomeActivity.viewProfil()
                } })

            openControlEvent.observe(this@HomeActivity, Observer<Event<String>> { event->

                event?.getContentIfNotHandled()?.let{deviceId->

                    openControlDetails(deviceId)
                }

            })
        }
    }

    private fun openControlDetails(deviceId: String) {

        val intent = Intent(this, ControlActivity::class.java).apply {
            putExtra(ControlActivity.EXTRA_DEVICE_ID, deviceId)
        }
        startActivity(intent)
    }

    private fun setupViewFragment() {

        supportFragmentManager.findFragmentById(R.id.contentFrame)?:
        replaceFragmentInActivity(HomeFragment.newInstance(),R.id.contentFrame)


    }

    private fun viewProfil() {

        val intent = Intent(this, ProfilActivity::class.java)
        startActivity(intent)

    }

    fun obtainViewModel(): HomeViewModel = obtainViewModel(HomeViewModel::class.java)

}
