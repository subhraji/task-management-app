package com.example.taskpro.presentation.screens.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskpro.domain.use_case.project.GetProjectUseCase
import com.example.taskpro.domain.use_case.project.SearchProjectUseCase
import com.example.taskpro.presentation.screens.home.state.GetProjectUiState
import com.example.taskpro.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val useCase: GetProjectUseCase,
    private val searchProjectUseCase: SearchProjectUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow<GetProjectUiState>(GetProjectUiState.Idl)
    val uiState = _uiState.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    private var fetchJob: Job? = null

    init {
        getProjects()
        observeSearchQuery()
    }

    fun getProjects(){

        fetchJob?.cancel()

        fetchJob = viewModelScope.launch {
            _uiState.value = GetProjectUiState.Loading
            useCase()
                .collectLatest { res ->
                    _uiState.value = when (res) {
                        is Resource.Error -> GetProjectUiState.ERROR(res.message)
                        is Resource.Loading -> GetProjectUiState.Loading
                        is Resource.Success -> GetProjectUiState.SUCCESS(
                            res.data,
                            "Projects fetched successfully"
                        )
                    }
                }
        }
    }

    fun updateSearchQuery(query: String){
        _searchQuery.value = query
    }

    fun toggleSearch(){
        _isSearching.value = !_isSearching.value
    }

    fun restoreSearchState(){
        _isSearching.value = _searchQuery.value.isNotEmpty()
    }

    private fun searchProjects(query: String){
        fetchJob?.cancel()

        fetchJob = viewModelScope.launch {
            _uiState.value = GetProjectUiState.Loading
            searchProjectUseCase(query = query).collectLatest{ result ->
                _uiState.value = when(result){
                    is Resource.Error -> GetProjectUiState.ERROR(result.message)
                    is Resource.Loading -> GetProjectUiState.Loading
                    is Resource.Success -> GetProjectUiState.SUCCESS(
                        result.data,
                        "Projects fetched successfully"
                    )
                }
            }
        }
    }

    @OptIn(FlowPreview::class)
    private fun observeSearchQuery() {
        viewModelScope.launch {
            _searchQuery
                .drop(1)
                .debounce(300)
                .distinctUntilChanged()
                .collectLatest { query ->
                    if (query.isBlank()) {
                        getProjects()
                    } else {
                        searchProjects(query)
                    }
                }
        }
    }
}