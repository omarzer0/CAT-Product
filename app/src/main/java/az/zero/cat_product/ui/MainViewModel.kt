package az.zero.cat_product.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import az.zero.cat_product.core.ResponseState
import az.zero.cat_product.core.networkCall
import az.zero.cat_product.models.ProductResponse
import az.zero.cat_product.repository.Repository
import kotlinx.coroutines.launch

private const val TAG = "MainViewModel"

class MainViewModel(
    private val repository: Repository
) : ViewModel() {

    private val _productsMLD = MutableLiveData<ResponseState<ProductResponse>>()
    val productLD: LiveData<ResponseState<ProductResponse>> = _productsMLD
    
    private fun getAllProducts() {
        viewModelScope.launch {
            networkCall(action = {
                repository.getAllProducts()
            }, onResponse = { responseState ->
                _productsMLD.postValue(responseState)
            })
        }
    }

    init {
        getAllProducts()
    }
}