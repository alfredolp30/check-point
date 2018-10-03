package br.com.semanapesada.checkpoint.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import br.com.semanapesada.checkpoint.R
import br.com.semanapesada.checkpoint.database.CheckPoint


class CheckPointViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val txtDatetime : TextView? = view.findViewById(R.id.txtDatetime)
}

class CheckPointAdapter(val checkPoints : MutableList<CheckPoint>) : RecyclerView.Adapter<CheckPointViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CheckPointViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cell_check_point, parent, false)
        return CheckPointViewHolder(view)
    }

    override fun getItemCount(): Int = checkPoints.size

    override fun onBindViewHolder(holder: CheckPointViewHolder, position: Int) {
        val checkPoint = checkPoints[position]
        holder.txtDatetime?.text = checkPoint.uid?.toString() + " >>> " + checkPoint.datetime +
                " >>> Acc: ${checkPoint.accuracy}% >>>" + if (checkPoint.entering) "Entrando" else "Saindo"
    }

}