package net.xpece.androidx.optical.sample

import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.LayerDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.navigation.NavigationBarView
import net.xpece.androidx.optical.getOpticalInsetsCompat
import net.xpece.androidx.optical.sample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val mOnItemSelectedListener =
        NavigationBarView.OnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_unsupported -> {
                    setTitle(R.string.title_unsupported)
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainer, UnsupportedFragment())
                        .commitNow()
                    return@OnItemSelectedListener true
                }
                R.id.navigation_wrong -> {
                    setTitle(R.string.title_wrong)
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainer, WrongFragment())
                        .commitNow()
                    return@OnItemSelectedListener true
                }
                R.id.navigation_correct -> {
                    setTitle(R.string.title_fixed)
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainer, FixedFragment())
                        .commitNow()
                    return@OnItemSelectedListener true
                }
            }
            false
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.navigation.setOnItemSelectedListener(mOnItemSelectedListener)

        if (savedInstanceState == null) {
            binding.navigation.selectedItemId = R.id.navigation_wrong
        }

        LayerDrawable(arrayOf(ColorDrawable(0))).getOpticalInsetsCompat()
    }
}
