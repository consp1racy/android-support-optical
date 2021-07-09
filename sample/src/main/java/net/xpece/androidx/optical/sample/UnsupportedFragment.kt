package net.xpece.androidx.optical.sample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class UnsupportedFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_common, container, false) as ViewGroup
        inflater.inflate(R.layout.layout_unsupported, root)
        return root
    }
}
