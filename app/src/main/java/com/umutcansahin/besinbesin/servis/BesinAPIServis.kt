package com.umutcansahin.besinbesin.servis

import com.umutcansahin.besinbesin.model.Besin
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class BesinAPIServis {

    //GET , POST

    //https://raw.githubusercontent.com/atilsamancioglu/BTK20-JSONVeriSeti/master/besinler.json

    //BASE_URL https://raw.githubusercontent.com/
    // atilsamancioglu/BTK20-JSONVeriSeti/master/besinler.json

    private val BASE_URL = "https://raw.githubusercontent.com/"
    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create()) //gson dönüstürme
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) //rxjava kullanılacağı belirtmek
        .build() //insa eder
        .create(BesinAPI :: class.java) //hangi sınıftan olusturulacağı yazılır

    fun getData() : Single<List<Besin>>{
        return api.getBesin()
    }
}
