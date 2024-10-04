package robin.vitalij.fortniteassitant.ui.news

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import robin.vitalij.fortniteassitant.common.extensions.setToolbarTitle
import robin.vitalij.fortniteassitant.databinding.ActivityVideoViewBinding


const val VIDEO_URL = "video_url"
const val VIDEO_TITLE = "video_title"

class VideoActivity : AppCompatActivity() {

    private var simpleExoPlayer: ExoPlayer? = null

    private val binding by viewBinding(ActivityVideoViewBinding::bind)

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setToolbar()
        initializePlayer()
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

    override fun onPause() {
        super.onPause()
        simpleExoPlayer?.pause()
    }

    override fun onDestroy() {
        super.onDestroy()
        simpleExoPlayer?.release()
        simpleExoPlayer = null
    }

    private fun setToolbar() {
        setSupportActionBar(binding.toolbarInclude.toolbar)
        enableBackButton()
        setToolbarTitle(intent?.getStringExtra(VIDEO_TITLE) ?: "")
    }

    private fun enableBackButton() {
        supportActionBar?.let { actionBar ->
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setDisplayShowHomeEnabled(true)
        }
    }

    private fun initializePlayer() {
        if (simpleExoPlayer == null) {
            simpleExoPlayer = SimpleExoPlayer.Builder(this).build()
        }

        try {
            val dataSourceFactory = DefaultHttpDataSource.Factory()
            val mediaSource: MediaSource = ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(MediaItem.fromUri(Uri.parse(intent?.getStringExtra(VIDEO_URL))))

            binding.storyDisplayVideo.player = simpleExoPlayer

            simpleExoPlayer?.setMediaSource(mediaSource)
            simpleExoPlayer?.prepare()
            simpleExoPlayer?.playWhenReady = true
        } catch (e: Exception) {
            // Handle exception if necessary
        }
    }

    companion object {
        fun newInstance(context: Context?, url: String, title: String) =
            Intent(context, VideoActivity::class.java)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                .putExtra(VIDEO_URL, url)
                .putExtra(VIDEO_TITLE, title)
    }
}