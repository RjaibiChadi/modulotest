package noblur.com.modulotest.profilupdatecomponent

import android.app.DatePickerDialog
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.profil_fragment.*
import kotlinx.android.synthetic.main.profil_update_fragment.*

import noblur.com.modulotest.R
import noblur.com.modulotest.data.repository.User
import noblur.com.modulotest.profilcomponent.ProfilActivity
import noblur.com.modulotest.utils.Util
import java.util.*

class ProfilUpdateFragment : Fragment(), View.OnTouchListener {


    companion object {
        fun newInstance() = ProfilUpdateFragment()
    }

    private lateinit var viewModel: ProfilUpdateViewModel
    var monthSelected: String? = null
    var daySelected:String? = null
    var yearSelected:String? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.profil_update_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setHasOptionsMenu(true)

        viewModel = (activity as ProfilUpdateActivity).obtainViewModel()

        viewModel.run {

            loadUser.observe(this@ProfilUpdateFragment, Observer { user->

                showUser(user)
            })
        }

        edt_birthday.setOnTouchListener(this)

        btn_update_profil.setOnClickListener {


            viewModel.updateProfil(
                edt_last_name.text.toString(),
                edt_first_name.text.toString(),
                edt_birthday.text.toString(),
                edt_street_code.text.toString(),
                edt_street.text.toString(),
                edt_city.text.toString(),
                edt_postal_code.text.toString(),
                edt_country.text.toString()
            )

        }
    }

    private fun showUser(user: User?) {

        if (user!=null){
            edt_last_name.setText(user.lastName)
            edt_first_name.setText(user.firstName)
            edt_birthday.setText(Util.changeFormatDate(user.birthDate))
            edt_street_code.setText(user.address.streetCode)
            edt_street.setText(user.address.street)
            edt_city.setText(user.address.city)
            edt_postal_code.setText(user.address.postalCode.toString())
            edt_country.setText(user.address.country)

        }
    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {

        if (v == edt_birthday){
            if (event!!.action == MotionEvent.ACTION_UP) {

                val now = Calendar.getInstance()
                val c = Calendar.getInstance()
                val year = c.get(Calendar.YEAR)
                val month = c.get(Calendar.MONTH)
                val day = c.get(Calendar.DAY_OF_MONTH)

                val dpd = DatePickerDialog(activity, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->

                    monthSelected = if (monthOfYear + 1 < 10) "0" + (monthOfYear + 1) else (monthOfYear + 1).toString()

                    daySelected = if (dayOfMonth + 0 < 10) "0" + (dayOfMonth + 0) else (dayOfMonth + 0).toString()

                    yearSelected = (year).toString()


                    edt_birthday.setText(daySelected + "/" + monthSelected + "/" + year)
                }, year, month, day)

                dpd.show()

            }
        }

        return false
    }

    override fun onResume() {
        super.onResume()

        viewModel.start()
    }


}
