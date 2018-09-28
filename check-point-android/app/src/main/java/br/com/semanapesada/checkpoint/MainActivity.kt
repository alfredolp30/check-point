package br.com.semanapesada.checkpoint

import android.os.Bundle
import android.os.PersistableBundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import br.com.semanapesada.checkpoint.fragment.CheckFragment
import br.com.semanapesada.checkpoint.fragment.ConfigFragment
import br.com.semanapesada.checkpoint.fragment.LocationFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                // message.setText(R.string.title_home)
                supportFragmentManager.beginTransaction().replace(R.id.containerFragment, ConfigFragment()).commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                // message.setText(R.string.title_dashboard)
                supportFragmentManager.beginTransaction().replace(R.id.containerFragment, CheckFragment()).commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                // message.setText(R.string.title_notifications)
                supportFragmentManager.beginTransaction().replace(R.id.containerFragment, LocationFragment()).commit()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        supportFragmentManager.beginTransaction().add(R.id.containerFragment, ConfigFragment()).commit()
    }
}
