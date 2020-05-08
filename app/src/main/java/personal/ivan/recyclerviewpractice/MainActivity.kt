package personal.ivan.recyclerviewpractice

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import personal.ivan.recyclerviewpractice.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    // view model
    private val mViewModel by viewModels<MyViewModel>()

    // data binding
    private val mBinding: ActivityMainBinding by lazy {
        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
    }

    /* ------------------------------ Life Cycle */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding.recyclerView.apply {
            adapter = MyAdapter(mViewModel = mViewModel)

            ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.UP or ItemTouchHelper.DOWN,
                0
            ) {

                override fun canDropOver(
                    recyclerView: RecyclerView,
                    current: ViewHolder,
                    target: ViewHolder
                ): Boolean {
                    return current.itemViewType == target.itemViewType
                }

                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: ViewHolder,
                    viewHolder1: ViewHolder
                ): Boolean {
                    val fromPos = viewHolder.adapterPosition
                    val toPos = viewHolder1.adapterPosition
                    if (fromPos != toPos) {
                        (recyclerView.adapter as? MyAdapter)?.move(fromPos, toPos)
                    }
                    return fromPos != toPos
                }

                override fun onSwiped(viewHolder: ViewHolder, direction: Int) {
                }
            }).attachToRecyclerView(this)
        }
        observeLiveData()
    }

    /* ------------------------------ Observe LiveData */

    private fun observeLiveData() {
        mViewModel.apply {

            displayDataList.observe(
                this@MainActivity,
                Observer { (mBinding.recyclerView.adapter as? MyAdapter)?.submitList(it) })
        }
    }
}
