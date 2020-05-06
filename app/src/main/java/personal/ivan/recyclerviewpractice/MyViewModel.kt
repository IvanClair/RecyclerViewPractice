package personal.ivan.recyclerviewpractice

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MyViewModel : ViewModel() {

    // Origin Data List
    private val mOriginList =
        mutableListOf<VhModel>().apply {
            repeat(10) { headerId ->

                // header
                add(
                    VhModel(
                        viewType = VhModel.VIEW_TYPE_HEADER,
                        headerId = headerId,
                        text = "Header $headerId",
                        expand = false
                    )
                )

                // child
                repeat(20) { childNumber ->
                    add(
                        VhModel(
                            viewType = VhModel.VIEW_TYPE_CHILD,
                            headerId = headerId,
                            text = "child $childNumber",
                            expand = null
                        )
                    )
                }

                // close
                add(
                    VhModel(
                        viewType = VhModel.VIEW_TYPE_CLOSE,
                        headerId = headerId,
                        text = null,
                        expand = null
                    )
                )
            }
        }

    // Display Data Set
    val displayDataList = MutableLiveData<MutableList<VhModel>>()

    /* ------------------------------ Initial */

    init {
        displayDataList.value =
            mOriginList
                .filter { it.viewType == VhModel.VIEW_TYPE_HEADER }
                .toMutableList()
    }

    /* ------------------------------ UI Event */

    /**
     * Head clicked
     */
    fun headerOnClick(model: VhModel) {
        displayDataList.value?.also { displayList ->
            mOriginList
                .find { it.viewType == VhModel.VIEW_TYPE_HEADER && it.headerId == model.headerId }
                ?.also { headerModel ->
                    val newList =
                        if (headerModel.expand == true) {
                            // collapse
                            displayList
                                .filter {
                                    when (it.viewType) {
                                        VhModel.VIEW_TYPE_HEADER -> true
                                        else -> it.headerId != headerModel.headerId
                                    }
                                }
                                .toMutableList()
                        } else {
                            val filteredOriginList =
                                mOriginList.filter { it.viewType != VhModel.VIEW_TYPE_HEADER && it.headerId == headerModel.headerId }
                            val position =
                                displayList.indexOfFirst { it.viewType == VhModel.VIEW_TYPE_HEADER && it.headerId == headerModel.headerId }
                            displayList.toMutableList()
                                .apply { addAll(position + 1, filteredOriginList) }
                        }

                    displayDataList.value = newList
                    headerModel.expand = !headerModel.expand!!
                }
        }
    }
}