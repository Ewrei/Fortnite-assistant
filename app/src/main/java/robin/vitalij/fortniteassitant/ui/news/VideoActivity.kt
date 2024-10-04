package robin.vitalij.fortniteassitant.ui.news

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.common.extensions.setToolbarTitle
import robin.vitalij.fortniteassitant.databinding.ActivityVideoViewBinding

class VideoActivity : AppCompatActivity(R.layout.activity_video_view) {

    private var simpleExoPlayer: ExoPlayer? = null

    private val binding by viewBinding(ActivityVideoViewBinding::bind)

    private val args: VideoActivityArgs by navArgs()

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
        setToolbarTitle(intent?.getStringExtra(args.argVideoName) ?: "")
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
                .createMediaSource(MediaItem.fromUri(Uri.parse(args.argVideoUrl)))

            binding.storyDisplayVideo.player = simpleExoPlayer

            simpleExoPlayer?.setMediaSource(mediaSource)
            simpleExoPlayer?.prepare()
            simpleExoPlayer?.playWhenReady = true
        } catch (e: Exception) {
            // Handle exception if necessary
        }
    }
}