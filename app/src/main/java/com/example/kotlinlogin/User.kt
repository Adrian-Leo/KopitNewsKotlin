package com.example.kotlinlogin

class User {
    var name: String? = null
    var email: String? = null
    var age: String? = null

    constructor() {}
    constructor(nameX: String?, emailX: String?, ageX: String?) {
        name = nameX
        email = emailX
        age = ageX
    }
}