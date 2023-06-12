package com.andresen.library_repositories.helper.network

interface ConnectionService {
    fun isConnectedToInternet(): Boolean
}