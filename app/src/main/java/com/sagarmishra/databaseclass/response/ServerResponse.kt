package com.sagarmishra.databaseclass.response

data class ServerResponse(val success: Boolean? = null, val token: String? = null,val message:String? = null,val userId:String?=null) {
}