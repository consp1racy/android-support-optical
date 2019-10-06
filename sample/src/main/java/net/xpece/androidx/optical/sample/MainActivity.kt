package net.xpece.androidx.optical.sample

import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.LayerDrawable
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import net.xpece.androidx.optical.getOpticalInsetsCompat

class MainActivity : AppCompatActivity() {

    private val mOnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_unsupported -> {
                    setTitle(R.string.title_unsupported)
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainer, UnsupportedFragment())
                        .commitNow()
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_wrong -> {
                    setTitle(R.string.title_wrong)
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainer, WrongFragment())
                        .commitNow()
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_correct -> {
                    setTitle(R.string.title_fixed)
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainer, FixedFragment())
                        .commitNow()
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        if (savedInstanceState == null) {
            navigation.selectedItemId = R.id.navigation_wrong
        }

        LayerDrawable(arrayOf(ColorDrawable(0))).getOpticalInsetsCompat()
    }
}
