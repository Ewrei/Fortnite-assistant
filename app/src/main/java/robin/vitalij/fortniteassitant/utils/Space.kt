package robin.vitalij.fortniteassitant.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class Space(var space: Int, var spanCount: Int = 0) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {

        super.getItemOffsets(outRect, view, parent, state)
        if (parent.getChildLayoutPosition(view) <= spanCount) {
            outRect.top = space
        }
        outRect.bottom = space
    }
}