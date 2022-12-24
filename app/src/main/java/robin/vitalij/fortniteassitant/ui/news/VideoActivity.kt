package robin.vitalij.fortniteassitant.ui.news

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.navArgs
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.extractor.ExtractorsFactory
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import kotlinx.android.synthetic.main.activity_video_view.*
import kotlinx.android.synthetic.main.toolbar_center_title.*
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.common.extensions.setToolbarTitle

class VideoActivity : AppCompatActivity() {

    private var simpleExoPlayer: SimpleExoPlayer? = null

    private val args: VideoActivityArgs by navArgs()

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_view)
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
        simpleExoPlayer?.stop()
    }

    private fun setToolbar() {
        setSupportActionBar(toolbar)
        enableBackButton()
        setToolbarTitle(args.argVideoName)
    }

    private fun enableBackButton() {
        supportActionBar?.let { actionBar ->
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setDisplayShowHomeEnabled(true)
        }
    }

    private fun initializePlayer() {
        if (simpleExoPlayer == null) {
            simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(this)
        } else {
            simpleExoPlayer?.release()
            simpleExoPlayer = null
            simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(this)
        }

        try {
            val dataSourceFactory = DefaultHttpDataSourceFactory("exoplayer_video")
            val extractorsFactory: ExtractorsFactory = DefaultExtractorsFactory()
            val mediaSource: MediaSource =
                ExtractorMediaSource(
                    Uri.parse(args.argVideoUrl),
                    dataSourceFactory,
                    extractorsFactory,
                    null,
                    null
                )
            storyDisplayVideo.player = simpleExoPlayer

            simpleExoPlayer?.prepare(mediaSource)

            simpleExoPlayer?.playWhenReady = true
        } catch (e: Exception) {
            //Do nothing
        }
    }

}