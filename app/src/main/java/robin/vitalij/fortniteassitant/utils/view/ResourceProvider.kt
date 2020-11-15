package robin.vitalij.fortniteassitant.utils.view

import android.content.Context

class ResourceProvider(private val context: Context) {

    fun getString(string: Int) = context.getString(string)

    fun getString(string: Int, text: String) = context.getString(string, text)

    fun getQuantityString(pluralsId: Int, size: Int) =
        context.resources.getQuantityString(pluralsId, size, size)
}