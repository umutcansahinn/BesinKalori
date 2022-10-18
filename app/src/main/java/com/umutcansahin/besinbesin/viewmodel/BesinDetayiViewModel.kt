package com.umutcansahin.besinbesin.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.umutcansahin.besinbesin.model.Besin
import com.umutcansahin.besinbesin.servis.BesinDatabase
import kotlinx.coroutines.launch

class BesinDetayiViewModel(application: Application) : BaseViewModel(application) {

    val besinLiveData = MutableLiveData<Besin>()

    fun roomVerisiniAl(uuid : Int){
        launch {
            val dao = BesinDatabase(getApplication()).besinDao()
            val besin = dao.getBesin(uuid)
            besinLiveData.value = besin
        }

    }
}