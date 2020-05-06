package personal.ivan.recyclerviewpractice

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
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
        mBinding.recyclerView.adapter = MyAdapter(mViewModel = mViewModel)
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
