package personal.ivan.recyclerviewpractice

import androidx.annotation.IntDef

data class VhModel(
    @ViewType val viewType: Int,
    val headerId: Int,
    val text: String?,
    var expand: Boolean?
) {

    companion object {

        @IntDef(
            VIEW_TYPE_HEADER,
            VIEW_TYPE_CHILD,
            VIEW_TYPE_CLOSE
        )
        @Retention(AnnotationRetention.SOURCE)
        annotation class ViewType

        const val VIEW_TYPE_HEADER = 0
        const val VIEW_TYPE_CHILD = 1
        const val VIEW_TYPE_CLOSE = 2
    }
}