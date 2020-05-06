package personal.ivan.recyclerviewpractice

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MyViewModel : ViewModel() {

    // Origin Data List
    private val mOriginList =
        mutableListOf<VhModel>().apply {
            repeat(10) { headerNumber ->
                add(VhModel.VhHeaderModel(number = headerNumber))
                repeat(3) { childNumber ->
                    add(VhModel.VhChildModel(headId = headerNumber, number = childNumber))
                }
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
     * Head
     */
    fun headerOnClick(model: VhModel.VhHeaderModel) {
        val display = displayDataList.value
        if (display != null) {
            if (model.expand) {
                val newList =
                    display
                        .filter {
                            when (it.viewType) {
                                VhModel.VIEW_TYPE_HEADER -> true
                                else -> (it as VhModel.VhChildModel).headId != model.headId
                            }
                        }
                        .toMutableList()
                displayDataList.value = newList

            } else {
                val newList =
                    mOriginList.filter { it.viewType == VhModel.VIEW_TYPE_CHILD && (it as VhModel.VhChildModel).headId == model.headId }
                val position =
                    display.indexOfFirst { it.viewType == VhModel.VIEW_TYPE_HEADER && (it as VhModel.VhHeaderModel).headId == model.headId }
                val clone = display.toMutableList()
                clone.addAll(position + 1, newList)
                displayDataList.value = clone
            }

            model.expand = !model.expand
        }
    }
}