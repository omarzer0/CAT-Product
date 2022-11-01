package az.zero.cat_product.core

import kotlinx.coroutines.delay
import retrofit2.Response


suspend fun <S> networkCall(
    action: suspend () -> Response<S>,
    onResponse: (ResponseState<S>) -> Unit
) {
    onResponse(ResponseState.Loading())
    try {
        delay(2000L)
        val response = action()
        if (response.isSuccessful) {
            val body = response.body()
            if (body != null) {
                onResponse(ResponseState.Success(data = body))
            } else {
                onResponse(ResponseState.Error(message = "Null body"))
            }
        } else {
            onResponse(ResponseState.Error(message = "code: ${response.code()}"))
        }
    } catch (e: Exception) {
        onResponse(ResponseState.Error(message = "error: ${e.localizedMessage}"))
    }

}
