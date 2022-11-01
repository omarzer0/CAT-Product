package az.zero.cat_product.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import az.zero.cat_product.core.MyApplication
import az.zero.cat_product.core.ResponseState
import az.zero.cat_product.databinding.ActivityMainBinding
import az.zero.cat_product.ui.adapters.ProductAdapter

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private val productAdapter = ProductAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val repository = (application as MyApplication).appContainer.repository
        viewModel =
            ViewModelProvider(this, MainViewModelFactory(repository))[MainViewModel::class.java]

        setUpRv()
        observeData()
    }

    private fun setUpRv() {
        binding.rvProducts.adapter = productAdapter
    }

    private fun observeData() {
        viewModel.productLD.observe(this) { productState ->
            binding.pbLoading.isVisible = productState is ResponseState.Loading

            when (productState) {
                is ResponseState.Error -> {
                    Toast.makeText(
                        this,
                        "${productState.message ?: "Unknown error"}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is ResponseState.Loading -> {}
                is ResponseState.Success -> {
                    // submit to the adapter
                    val products = productState.data?.products ?: return@observe
                    productAdapter.submitList(products)
                }
            }
        }
    }
}