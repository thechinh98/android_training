package com.example.myapplication.fragment.fragment1

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.api.ApiService
import com.example.myapplication.model.GitRepoModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FirstFragmentViewModel : ViewModel() {

    private val _listGitRepo: MutableLiveData<MutableList<GitRepoModel>> = MutableLiveData()
    val listGitRepo get() = _listGitRepo
    private val apiCoroutine = ApiService().getApi()
    fun getData() {
        viewModelScope.launch(Dispatchers.IO) {
            apiCoroutine.searchRepo(1, 1)
                .let { gitResponse -> _listGitRepo.postValue(gitResponse.listGitRepo)
                print(gitResponse.listGitRepo)
                }
        }
    }
}