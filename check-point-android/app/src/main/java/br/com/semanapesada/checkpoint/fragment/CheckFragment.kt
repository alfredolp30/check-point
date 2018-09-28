package br.com.semanapesada.checkpoint.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.semanapesada.checkpoint.R

/**
 * Created by Alfredo L. Porfirio on 28/09/18.
 * Copyright Universo Online 2018. All rights reserved.
 */
class CheckFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_check, null)
    }
}