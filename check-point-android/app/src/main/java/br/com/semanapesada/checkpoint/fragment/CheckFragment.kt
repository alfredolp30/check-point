package br.com.semanapesada.checkpoint.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.semanapesada.checkpoint.CheckApp
import br.com.semanapesada.checkpoint.R
import br.com.semanapesada.checkpoint.adapter.CheckPointAdapter
import kotlinx.android.synthetic.main.fragment_check.*

/**
 * Created by Alfredo L. Porfirio on 28/09/18.
 * Copyright Universo Online 2018. All rights reserved.
 */
class CheckFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_check, null)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        rcCheckPoint.layoutManager = LinearLayoutManager(context)
        rcCheckPoint.adapter = CheckPointAdapter(CheckApp.db.pointDao().getList())
    }
}