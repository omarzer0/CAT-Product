package az.zero.cat_product.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import az.zero.cat_product.core.MyApplication
import az.zero.cat_product.core.ResponseState
import az.zero.cat_product.databinding.ActivityMainBinding
import az.zero.cat_product.data.models.Product
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

        setupViews()
        observeData()
    }

    private fun setupViews() {
        binding.apply {
            rvProducts.adapter = productAdapter
            root.apply {
                setOnRefreshListener {
                    viewModel.onRefresh()
                    isRefreshing = false
                }
            }
        }
    }

    private fun observeData() {
        viewModel.productsLD.observe(this) { productState ->
            val isLoading = productState is ResponseState.Loading
            val isError = productState is ResponseState.Error
            val productsNotNullNorEmpty =
                productState.data != null && productState.data.isNotEmpty()
            val productsNotNullButEmpty =
                productState.data != null && productState.data.isEmpty()

            binding.apply {
                pbLoading.isVisible = isLoading && productsNotNullButEmpty
                cvLoadingLayout.isVisible = isLoading && productsNotNullNorEmpty
                if (productsNotNullNorEmpty) updateAdapterData(productState.data!!)
                if (isError) {
                    Toast.makeText(
                        this@MainActivity,
                        "${productState.message ?: "Unknown error"}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun updateAdapterData(products: List<Product>) {
        productAdapter.submitList(products)
    }
}