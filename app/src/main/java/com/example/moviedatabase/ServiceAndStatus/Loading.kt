package com.example.moviedatabase

enum class Status {
    Loading, Success, Error

}

enum class ErrorType {
    DATA_OFF, NETWORK_ERROR, UNKNOWN_ERROR
}

data class Loading(
        val status: Status,
        val errorType: ErrorType?,
        val message: String?

) {
    companion object {
        fun loading(): Loading {
            return Loading(Status.Loading, null, null)
        }

        fun success(): Loading {
            return Loading(Status.Success, null, null)
        }

        fun error(errorType: ErrorType?, message: String?): Loading {
            return Loading(Status.Error, errorType, message)
        }
    }
}
