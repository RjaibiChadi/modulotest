package noblur.com.modulotest.profilcomponent

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import noblur.com.modulotest.R
import noblur.com.modulotest.data.entity.Event
import noblur.com.modulotest.homecomponent.HomeFragment
import noblur.com.modulotest.homecomponent.HomeViewModel
import noblur.com.modulotest.profilupdatecomponent.ProfilUpdateActivity
import noblur.com.modulotest.utils.obtainViewModel
import noblur.com.modulotest.utils.replaceFragmentInActivity
import noblur.com.modulotest.utils.setupActionBar

class ProfilActivity : AppCompatActivity() {

    private lateinit var viewModel: ProfilViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profil)

        setupActionBar(R.id.toolbar){
            title = "Profile"
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        setupViewFragment()

        viewModel =obtainViewModel().apply {

            newProfilUpdatePage.observe(this@ProfilActivity, Observer<Event<Unit>> { event ->

                event?.getContentIfNotHandled()?.let {

                    this@ProfilActivity.viewProfilUpdate()
                }
            })
        }
    }

    private fun viewProfilUpdate() {

        val intent = Intent(this, ProfilUpdateActivity::class.java)
        startActivity(intent)
    }

    private fun setupViewFragment() {

        supportFragmentManager.findFragmentById(R.id.contentFrame)?:
        replaceFragmentInActivity(ProfilFragment.newInstance(),R.id.contentFrame)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }


    fun obtainViewModel(): ProfilViewModel = obtainViewModel(ProfilViewModel::class.java)

}
