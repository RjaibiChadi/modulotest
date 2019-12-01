package noblur.com.modulotest.controlcomponent

import android.annotation.TargetApi
import android.opengl.Visibility
import android.os.Build
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.control_fragment.*

import noblur.com.modulotest.R
import noblur.com.modulotest.controlcomponent.ControlFragment.Companion.ARGUMENT_DEVICE_ID
import noblur.com.modulotest.data.repository.Device
import noblur.com.modulotest.homecomponent.HomeActivity
import noblur.com.modulotest.utils.ProductMode
import noblur.com.modulotest.utils.ProductType

class ControlFragment : Fragment() {

    companion object {

        const val ARGUMENT_DEVICE_ID = "DEVICE_ID"


        fun newInstance(deviceId:String) = ControlFragment().apply {

            arguments = Bundle().apply {
                putString(ARGUMENT_DEVICE_ID, deviceId)
            }
        }
    }

    private lateinit var viewModel: ControlViewModel
    private var updateSwtchMode:String?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.control_fragment, container, false)
    }

    @TargetApi(Build.VERSION_CODES.O)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        viewModel = (activity as ControlActivity).obtainViewModel()

        viewModel.run {

            loadUser.observe(this@ControlFragment, Observer { device->

                showDeviceDetails(device)

            })
        }

        seek_bar.setOnSeekBarChangeListener(object :SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {


                txt_value.text = progress.toString()

            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {


            }


        })

        seek_bar_radiator.setOnSeekBarChangeListener(object :SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                txt_value.text = progress.toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })

        switch_mode.setOnCheckedChangeListener { buttonView, isChecked ->

            updateSwtchMode = if (isChecked){
                ProductMode.ON.description
            }else{
                ProductMode.OFF.description

            }

            viewModel.updateSwithMode(updateSwtchMode)
        }


    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun showDeviceDetails(device: Device?) {

        when(device!!.productType){
            ProductType.Light.description->showLightDevice(device)
            ProductType.Heater.description->showRadiatorDevice(device)
            ProductType.RollerShutter.description->showRollerShuterDevice(device)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun showRollerShuterDevice(device: Device) {

        txt_value.text = device.position.toString()
        txt_type_device.text = "Position"

        seek_bar_radiator.visibility = View.GONE
        seek_bar.visibility = View.VISIBLE

        switch_mode.visibility = View.GONE
        txt_mode.visibility = View.GONE
        seek_bar.setProgress(device.position!!,true)
        seek_bar.rotation = 270f
    }

    @TargetApi(Build.VERSION_CODES.O)
    private fun showRadiatorDevice(device: Device) {

        seek_bar_radiator.setProgress(device.temperature!!,true)
        seek_bar_radiator.visibility = View.VISIBLE
        seek_bar.visibility = View.GONE

        txt_value.text = device.temperature.toString()
        txt_type_device.text = "Température"

        switch_mode.visibility = View.VISIBLE
        txt_mode.visibility = View.VISIBLE

        when(device.mode){
            ProductMode.ON.description->switch_mode.isChecked = true
            ProductMode.OFF.description->switch_mode.isChecked = false
        }


    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun showLightDevice(device: Device) {

        txt_value.text = device.intensity.toString()
        txt_type_device.text = "Intensité"
        switch_mode.visibility = View.VISIBLE
        txt_mode.visibility = View.VISIBLE
        seek_bar_radiator.visibility = View.GONE
        seek_bar.visibility = View.VISIBLE
        seek_bar.setProgress(device.intensity!!,true)
        when(device.mode){

            ProductMode.ON.description->switch_mode.isChecked = true
            ProductMode.OFF.description->switch_mode.isChecked = false
        }


    }

    override fun onResume() {
        super.onResume()
        viewModel.start(arguments?.getString(ARGUMENT_DEVICE_ID))
    }

}
