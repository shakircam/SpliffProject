package com.shakir.spliff.data.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shakir.spliff.data.repository.UserRepository
import kotlinx.coroutines.launch

class UserViewModel(private val repository: UserRepository): ViewModel() {


    fun createUser(email:String, password:String){
        viewModelScope.launch {
            repository.loginUser(email, password)

        }
    }

    fun registration(email:String, password:String,c_password:String,name:String){
        viewModelScope.launch {
            repository.doRegistration(email, password, c_password, name)

        }
    }

}
