package noblur.com.modulotest.profilcomponent

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.profil_fragment.*

import noblur.com.modulotest.data.repository.User
import noblur.com.modulotest.homecomponent.HomeActivity
import noblur.com.modulotest.utils.Util.Companion.changeFormatDate
import java.sql.Date
import java.sql.Timestamp
import java.util.*
import java.text.SimpleDateFormat


class ProfilFragment : Fragment() {

    companion object {
        fun newInstance() = ProfilFragment()
    }

    private lateinit var viewModel: ProfilViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(noblur.com.modulotest.R.layout.profil_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        setHasOptionsMenu(true)

        viewModel = (activity as ProfilActivity).obtainViewModel()

        viewModel.run {

            loadUser.observe(this@ProfilFragment, Observer { user->

                showUser(user)
            })
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            noblur.com.modulotest.R.id.menu_profil_update -> {
                viewModel?.updateProfil()
                true
            }
            else -> false
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(noblur.com.modulotest.R.menu.profil_update_menu, menu)
    }

    private fun showUser(user: User?) {

        txt_last_name.text = user!!.lastName
        txt_first_name.text = user!!.firstName

        txt_birthday.text = changeFormatDate(user.birthDate)
        txt_address.text = "${user.address.streetCode} ${user.address.street} ${user.address.city}\n${user.address.postalCode} ${user.address.country}"
    }



    override fun onResume() {
        super.onResume()

        viewModel.start()
    }

}
