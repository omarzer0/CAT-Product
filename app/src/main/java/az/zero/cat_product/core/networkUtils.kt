package az.zero.cat_product.core

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
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

inline fun <ResultType, RequestType> networkBoundResource(
    crossinline query: () -> Flow<ResultType>,
    crossinline fetch: suspend () -> RequestType,
    crossinline saveFetchResult: suspend (RequestType) -> Unit,
    crossinline shouldFetch: (ResultType) -> Boolean = { true }
) = flow<ResponseState<ResultType>> {
    emit(ResponseState.Loading(null))
    val data = query().first()

    val flow = if (shouldFetch(data)) {
        emit(ResponseState.Loading(data))
        try {
            saveFetchResult(fetch())
            query().map { ResponseState.Success(it) }
        } catch (throwable: Throwable) {
            query().map { ResponseState.Error(it, throwable.localizedMessage) }
        }
    } else {
        query().map { ResponseState.Success(it) }
    }
    emitAll(flow)
}
