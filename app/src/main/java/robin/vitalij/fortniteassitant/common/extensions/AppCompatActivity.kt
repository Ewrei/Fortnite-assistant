package robin.vitalij.fortniteassitant.common.extensions

import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.transaction

fun AppCompatActivity.replaceFragment(@IdRes where: Int, fragment: Fragment, tag: String) {
    supportFragmentManager
        .transaction(now = false, allowStateLoss = false) {
            replace(where, fragment, tag)
        }
}

fun AppCompatActivity.setToolbarTitle(title: String) {
    supportActionBar?.let {
        it.title = title
    }
}