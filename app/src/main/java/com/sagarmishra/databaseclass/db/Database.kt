package com.sagarmishra.databaseclass.db

import com.sagarmishra.databaseclass.model.AuthenticateModel

class Database() {
    companion object{
        var loginValue : MutableList<AuthenticateModel> = mutableListOf(AuthenticateModel("admin","admin"))
    }
}