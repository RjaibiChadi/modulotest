package noblur.com.modulotest.homecomponent

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

import kotlinx.android.synthetic.main.device_item.view.*
import noblur.com.modulotest.R
import noblur.com.modulotest.data.repository.Device

class DeviceAdapter (val devices: MutableList<Device>, val listener: DeviceItemUserActionsListener )
    : RecyclerView.Adapter<DeviceAdapter.ViewHolder>() {


    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        val deviceContainer = itemView.container_device

        val deviceName = itemView.txt_device
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val viewItem = LayoutInflater.from(parent.context).inflate(R.layout.device_item,parent,false)

        return ViewHolder(viewItem)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val device = devices[position]

        with(holder){


            deviceContainer.tag = device
            deviceName.text = device.deviceName
            deviceContainer.setOnClickListener{
                listener.onDeviceClicked(device)
            }
        }




    }
    override fun getItemCount(): Int {
        return devices.size
    }

    fun addItem(device: Device) {
        devices.add(device)
        notifyItemInserted(devices.size)
    }

    fun removeAt(position: Int) {
        devices.removeAt(position)
        notifyItemRemoved(position)
    }
}