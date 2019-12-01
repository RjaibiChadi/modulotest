package noblur.com.modulotest.controlcomponent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import noblur.com.modulotest.R
import noblur.com.modulotest.homecomponent.HomeFragment
import noblur.com.modulotest.homecomponent.HomeViewModel
import noblur.com.modulotest.utils.obtainViewModel
import noblur.com.modulotest.utils.replaceFragmentInActivity
import noblur.com.modulotest.utils.setupActionBar

class ControlActivity : AppCompatActivity() {

    companion object {

        const val EXTRA_DEVICE_ID = "DEVICE_ID"

    }

    private lateinit var viewModel: ControlViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_control)

        setupActionBar(R.id.toolbar){
            title = "Pilotage"
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        setupViewFragment()
    }

    private fun setupViewFragment() {

        supportFragmentManager.findFragmentById(R.id.contentFrame)?:
        replaceFragmentInActivity(ControlFragment.newInstance(intent.getStringExtra(EXTRA_DEVICE_ID)),R.id.contentFrame)

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    fun obtainViewModel(): ControlViewModel = obtainViewModel(ControlViewModel::class.java)

}
