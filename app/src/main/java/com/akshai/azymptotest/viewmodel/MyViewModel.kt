package com.akshai.azymptotest.viewmodel

import android.database.Cursor
import android.os.Environment
import android.provider.MediaStore
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akshai.azymptotest.model.SongModel
import com.akshai.azymptotest.repo.MyRepo
import com.akshai.azymptotest.repo.domain.CategoryDomain
import com.akshai.azymptotest.repo.entity.ItemsItem
import com.akshai.azymptotest.repo.entity.MockApiEntity
import com.akshai.azymptotest.repo.mapper.CategoryMapper
import com.akshai.azymptotest.utils.DataHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.internal.notify
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MyViewModel @Inject constructor(
    private val myRepo: MyRepo,
) : ViewModel() {


    private val _mockLiveData = MutableLiveData<DataHandler<MockApiEntity>>()
    val mockLiveData: LiveData<DataHandler<MockApiEntity>> = _mockLiveData

    val audioListLiveData: LiveData<ArrayList<String>> = MutableLiveData()


    init {
        //  getMockApi()
    }

    private fun getMockApi() {
        viewModelScope.launch {
            val response = myRepo.getMockApi()
            _mockLiveData.postValue(handleResponse(response))
        }
    }

    private fun handleResponse(response: Response<MockApiEntity>): DataHandler<MockApiEntity> {
        if (response.isSuccessful) {
            response.body()?.let {

                val list: List<ItemsItem>? = it.items
                val mapper = CategoryMapper()
                val categoryList = list?.let { it1 -> mapper.fromEntityList(it1) }
                categoryList?.let { it1 -> createTree(it1) }

                return DataHandler.SUCCESS(it)
            }
        }
        return DataHandler.ERROR(message = "Data not Found")
    }

    private fun createTree(list: List<CategoryDomain>) {

        val mapTmp: LinkedHashMap<String, CategoryDomain> = LinkedHashMap()

        // Save all nodes to a map
        for (current in list) {
            current.orderLevel = current.title
            mapTmp[current.id.toString()] = current
        }

        //loop and assign parent/child relationships
        for (current in list) {
            val parentId: String = current.parentId.toString()
            val parent: CategoryDomain? = mapTmp[parentId]
            if (parent != null) {
                current.parent = parent
                current.orderLevel = "${parent.orderLevel}/${current.orderLevel}"
                parent.addChild(current)
                mapTmp[parentId] = parent
                mapTmp[current.id.toString()] = current
            }
        }

        mapTmp.forEach { (key, value) ->
            println("Console ->${value.orderLevel}")
        }

    }

}


