package personal.ivan.recyclerviewpractice

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class MyAdapter(private val mViewModel: MyViewModel) :
    ListAdapter<VhModel, MyAdapter.MyViewHolder>(DiffCallback()) {

    /* ------------------------------ Public Function */

    fun move(
        from: Int,
        to: Int
    ) {
        mViewModel.displayDataList.value?.also {
            val newList = it.toMutableList()
            Collections.swap(newList, from, to)
            mViewModel.displayDataList.value = newList
        }
    }

    /* ------------------------------ Override */

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) =
        MyViewHolder(
            mBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                when (viewType) {
                    VhModel.VIEW_TYPE_HEADER -> R.layout.vh_header
                    VhModel.VIEW_TYPE_CHILD -> R.layout.vh_child
                    else -> R.layout.vh_close
                },
                parent,
                false
            )
        )

    override fun onBindViewHolder(
        holder: MyViewHolder,
        position: Int
    ) {
        holder.bind(model = getItem(position), viewModel = mViewModel)
    }

    override fun getItemViewType(position: Int): Int =
        getItem(position).viewType

    /* ------------------------------ Diff */

    class DiffCallback : DiffUtil.ItemCallback<VhModel>() {

        override fun areItemsTheSame(
            oldItem: VhModel,
            newItem: VhModel
        ) = oldItem == newItem

        override fun areContentsTheSame(
            oldItem: VhModel,
            newItem: VhModel
        ) = oldItem.hashCode() == newItem.hashCode()
    }

    /* ------------------------------ View Holder */

    class MyViewHolder(private val mBinding: ViewDataBinding) :
        RecyclerView.ViewHolder(mBinding.root) {

        fun bind(
            model: VhModel,
            viewModel: MyViewModel
        ) {
            mBinding.apply {
                setVariable(BR.model, model)
                setVariable(BR.viewModel, viewModel)
                executePendingBindings()
            }
        }
    }
}