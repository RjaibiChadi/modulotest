package noblur.com.modulotest.homecomponent

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.SimpleAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.home_fragment.*
import kotlinx.android.synthetic.main.home_fragment.view.*

import noblur.com.modulotest.R
import noblur.com.modulotest.data.repository.Device

class HomeFragment : Fragment(), DeviceItemUserActionsListener {


    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var viewModel: HomeViewModel
    private lateinit var deviceAdapter: DeviceAdapter
    private lateinit var _devices: MutableList<Device>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setHasOptionsMenu(true)


        viewModel = (activity as HomeActivity).obtainViewModel()

        _devices = mutableListOf()
        deviceAdapter = DeviceAdapter(_devices,this)

        /**
         * initialise recycle view
         */
        recyclerView.apply {

            addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
            layoutManager = LinearLayoutManager(activity)
            adapter = deviceAdapter

        }

        val swipeHandler = object : SwipeToDeleteCallback(context!!) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val adapter = recyclerView.adapter as DeviceAdapter
                adapter.removeAt(viewHolder.adapterPosition)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(recyclerView)

        viewModel.run {

            devices.observe(this@HomeFragment, Observer { devices->

                showDevices(devices)

            })

        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_profil -> {
                viewModel?.viewProfil()
                true
            }
            else -> false
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.profil_menu, menu)
    }

    private fun showDevices(devices: List<Device>?) {
        _devices.clear()
        _devices.addAll(devices!!)
        deviceAdapter.notifyDataSetChanged()

    }

    override fun onDeviceClicked(device: Device) {

        viewModel.showControl(device.id)
    }

    override fun onResume() {
        super.onResume()

        viewModel.start()
    }

}
