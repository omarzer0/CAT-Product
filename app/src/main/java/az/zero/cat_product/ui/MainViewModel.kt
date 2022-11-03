package az.zero.cat_product.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import az.zero.cat_product.core.ResponseState
import az.zero.cat_product.core.networkCall
import az.zero.cat_product.data.models.Product
import az.zero.cat_product.data.models.ProductResponse
import az.zero.cat_product.data.repository.Repository
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

private const val TAG = "MainViewModel"

class MainViewModel(
    private val repository: Repository
) : ViewModel() {

    private val _productsMLD = MutableLiveData<ResponseState<List<Product>>>()
    val productsLD: LiveData<ResponseState<List<Product>>> = _productsMLD

    private fun getAllProducts() {
        viewModelScope.launch {
            repository.getAllProducts().collectLatest {
                _productsMLD.postValue(it)
            }
        }
    }

    fun onRefresh() {
        getAllProducts()
    }

    init {
        getAllProducts()
    }
}