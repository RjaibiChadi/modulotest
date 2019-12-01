package noblur.com.modulotest.profilupdatecomponent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import noblur.com.modulotest.R
import noblur.com.modulotest.data.entity.Event
import noblur.com.modulotest.homecomponent.HomeViewModel
import noblur.com.modulotest.profilcomponent.ProfilFragment
import noblur.com.modulotest.profilcomponent.ProfilViewModel
import noblur.com.modulotest.utils.obtainViewModel
import noblur.com.modulotest.utils.replaceFragmentInActivity
import noblur.com.modulotest.utils.setupActionBar

class ProfilUpdateActivity : AppCompatActivity() {

    private lateinit var viewModel: ProfilUpdateViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profil_update)

        setupActionBar(R.id.toolbar){
            title = "Editer profile"
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        setupViewFragment()

        viewModel =obtainViewModel().apply {

            profilUpdated.observe(this@ProfilUpdateActivity, Observer<Event<Unit>> { event ->

                event?.getContentIfNotHandled()?.let {

                    this@ProfilUpdateActivity.profilUpdated()
                }
            })

        }
    }

    private fun profilUpdated() {

        finish()
    }

    private fun setupViewFragment() {

        supportFragmentManager.findFragmentById(R.id.contentFrame)?:
        replaceFragmentInActivity(ProfilUpdateFragment.newInstance(),R.id.contentFrame)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    fun obtainViewModel(): ProfilUpdateViewModel = obtainViewModel(ProfilUpdateViewModel::class.java)

}
