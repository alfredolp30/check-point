package br.com.semanapesada.checkpoint.fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.semanapesada.checkpoint.AppCheck
import br.com.semanapesada.checkpoint.R
import br.com.semanapesada.checkpoint.adapter.CheckPointAdapter
import br.com.semanapesada.checkpoint.event.MessageEvent
import kotlinx.android.synthetic.main.fragment_check.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * Created by Alfredo L. Porfirio on 28/09/18.
 * Copyright Universo Online 2018. All rights reserved.
 */
class CheckFragment : Fragment() {
    private val adapter = CheckPointAdapter(AppCheck.db.pointDao().getList().toMutableList())

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_check, null)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        rcCheckPoint.layoutManager = LinearLayoutManager(context)
        rcCheckPoint.adapter = adapter
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        EventBus.getDefault().register(this)
    }

    override fun onDetach() {
        super.onDetach()

        EventBus.getDefault().unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(newCheckPoint: MessageEvent.NewCheckPoint) {
        adapter.checkPoints.add(newCheckPoint.checkPoint)
        adapter.notifyDataSetChanged()
    }
}