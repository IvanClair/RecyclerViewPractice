package personal.ivan.recyclerviewpractice

import androidx.annotation.IntDef

sealed class VhModel(@ViewType val viewType: Int) {


    companion object {

        @IntDef(
            VIEW_TYPE_HEADER,
            VIEW_TYPE_CHILD
        )
        @Retention(AnnotationRetention.SOURCE)
        annotation class ViewType

        const val VIEW_TYPE_HEADER = 0
        const val VIEW_TYPE_CHILD = 1
    }

    data class VhHeaderModel(
        val headId: Int,
        val text: String,
        var expand: Boolean
    ) : VhModel(viewType = VIEW_TYPE_HEADER) {

        constructor(number: Int) : this(
            headId = number,
            text = "Header $number",
            expand = false
        )
    }

    data class VhChildModel(
        val headId: Int,
        val text: String
    ) : VhModel(viewType = VIEW_TYPE_CHILD) {

        constructor(
            headId: Int,
            number: Int
        ) : this(
            headId = headId,
            text = "child $number"
        )
    }
}