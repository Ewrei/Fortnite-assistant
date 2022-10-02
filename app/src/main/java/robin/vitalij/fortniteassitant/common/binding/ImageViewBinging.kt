package robin.vitalij.fortniteassitant.common.binding

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.TypedValue
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import robin.vitalij.fortniteassitant.R
import robin.vitalij.fortniteassitant.db.entity.Images
import robin.vitalij.fortniteassitant.model.enums.RarityType

object ImageViewBinging {

    @JvmStatic
    @BindingAdapter("rarity")
    fun ImageView.loadBackgroundRarity(rarity: String?) {
        background =
            ContextCompat.getDrawable(context, RarityType.getRarityType(rarity).getBackgroundRes())
    }

    @JvmStatic
    @BindingAdapter("drawableRes")
    fun ImageView.loadDrawableRes(drawableRes: Int) {
        Glide.with(context)
            .load(drawableRes)
            .apply(
                RequestOptions()
                    .placeholder(getCircularProgressDrawable(context))
                    .error(getErrorDrawable())
            )
            .into(this)
    }

    @JvmStatic
    @BindingAdapter("drawable")
    fun ImageView.loadDrawable(drawableRes: Drawable) {
        Glide.with(context)
            .load(drawableRes)
            .apply(
                RequestOptions()
                    .placeholder(getCircularProgressDrawable(context))
                    .error(getErrorDrawable())
            )
            .into(this)
    }


    @JvmStatic
    @BindingAdapter("images")
    fun ImageView.loadImages(images: Images?) {
        Glide.with(context)
            .load(images?.featured ?: images?.icon)
            .apply(
                RequestOptions()
                    .placeholder(getCircularProgressDrawable(context))
                    .error(getErrorDrawable())
            )
            .into(this)
    }


    @JvmStatic
    @BindingAdapter("imageUrl")
    fun ImageView.loadImage(imageUrl: String?) {
        Glide.with(context)
            .load(imageUrl)
            .apply(
                RequestOptions()
                    .placeholder(getCircularProgressDrawable(context))
                    .error(getErrorDrawable())
            )
            .into(this)
    }

    @JvmStatic
    @BindingAdapter("profileImageUrl")
    fun ImageView.loadProfileImage(imageUrl: String?) {
        Glide.with(context)
            .load(if (imageUrl.isNullOrEmpty()) R.drawable.ic_cop else imageUrl)
            .apply(RequestOptions.circleCropTransform())
            .placeholder(getCircularProgressDrawable(context))
            .error(getErrorDrawable())
            .into(this)
    }

    private fun getCircularProgressDrawable(context: Context) =
        CircularProgressDrawable(context).apply {
            strokeWidth = getFloat(R.dimen.circular_stroke_width, context)
            centerRadius = getFloat(R.dimen.circular_center_radius, context)
            start()
        }

    private fun getFloat(res: Int, context: Context): Float {
        val outValue = TypedValue()
        context.resources.getValue(res, outValue, true)
        return outValue.float
    }

    private fun getErrorDrawable() = R.drawable.img_error_profile
}