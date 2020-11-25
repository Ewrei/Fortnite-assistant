package robin.vitalij.fortniteassitant.ui.web

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_web_view.*
import kotlinx.android.synthetic.main.toolbar_center_title.*
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.common.extensions.setToolbarTitle

const val WEB_URL = "web_url"
const val WEB_TITLE = "web_title"

class WebActivity : AppCompatActivity() {

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)
        setToolbar()
        setWebView()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setWebView() {
        webView.settings.javaScriptEnabled = true
        webView.loadUrl((intent?.getStringExtra(WEB_URL)!!))
        webView.webViewClient = WebViewClient()
        webView.webChromeClient = WebChromeClient()
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount <= 1) {
            finish()
        } else {
            super.onBackPressed()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setToolbar() {
        setSupportActionBar(toolbar)
        enableBackButton()
        setToolbarTitle(intent?.getStringExtra(WEB_TITLE) ?: "")
    }

    private fun enableBackButton() {
        supportActionBar?.let { actionBar ->
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setDisplayShowHomeEnabled(true)
        }
    }

    companion object {
        fun newInstance(context: Context?, url: String, title: String) =
            Intent(context, WebActivity::class.java)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                .putExtra(WEB_URL, url)
                .putExtra(WEB_TITLE, title)
    }
}