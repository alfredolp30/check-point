package br.com.semanapesada.checkpoint.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import br.com.semanapesada.checkpoint.R
import br.com.semanapesada.checkpoint.database.CheckPoint


class CheckPointViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val txtDatetime = view.findViewById<TextView>(R.id.txtDatetime)
}

class CheckPointAdapter(private val checkPoints : List<CheckPoint>) : RecyclerView.Adapter<CheckPointViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CheckPointViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cell_check_point, parent, false)
        return CheckPointViewHolder(view)
    }

    override fun getItemCount(): Int = checkPoints.size

    override fun onBindViewHolder(holder: CheckPointViewHolder, position: Int) {
        holder.txtDatetime.text = checkPoints[position].uid?.toString() + " >>> " + checkPoints[position].datetime
    }

}