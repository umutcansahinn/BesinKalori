package com.umutcansahin.besinbesin.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.umutcansahin.besinbesin.model.Besin
import com.umutcansahin.besinbesin.servis.BesinAPIServis
import com.umutcansahin.besinbesin.servis.BesinDatabase
import com.umutcansahin.besinbesin.util.OzelSharedPreferences
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class BesinListesiViewModel(application: Application) : BaseViewModel(application) {
    val besinler = MutableLiveData<List<Besin>>()
    val besinHataMesaji = MutableLiveData<Boolean>()
    val besinYukleniyor = MutableLiveData<Boolean>()

    private val besinApiServis = BesinAPIServis()
    private val disposable = CompositeDisposable()
    private val ozelSharedPreferences = OzelSharedPreferences(getApplication())
    private var guncellemeZamani = 10 * 60 * 1000 * 1000 * 1000L

    fun refreshData(){
        val kaydedilmeZamani = ozelSharedPreferences.zamaniAl()
        if(kaydedilmeZamani != null && kaydedilmeZamani != 0L && (System.nanoTime() - kaydedilmeZamani) < guncellemeZamani){
            //verileri Sqlite'dan al
            verileriSQLitetanAl()

        }else{
            //verileri indernetten çek
            verileriInternettenAl()
        }
    }
    fun refreshFromInternet(){
        verileriInternettenAl()
    }

    private fun verileriSQLitetanAl(){
        besinYukleniyor.value = true
        launch {
            val besinListesi = BesinDatabase(getApplication()).besinDao().getAllBesin()
            besinleriGoster(besinListesi)
            Toast.makeText(getApplication(), "Besinleri Roomdan aldık", Toast.LENGTH_LONG).show()
        }
    }

    private fun verileriInternettenAl(){
        besinYukleniyor.value = true

        disposable.add(
            besinApiServis.getData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Besin>>(){
                    override fun onSuccess(t: List<Besin>) {
                        //basarılı olursa
                        sqliteSakla(t)
                        Toast.makeText(getApplication(), "Besinleri Internetten aldık", Toast.LENGTH_LONG).show()
                    }

                    override fun onError(e: Throwable) {
                        //hata alırsak
                        besinHataMesaji.value = true
                        besinYukleniyor.value = false
                        e.printStackTrace() //eger hata alırsak logcat'de göstermeye yarar
                    }
                })
        )
    }
    private fun besinleriGoster(besinlerListesi : List<Besin>){
        besinler.value = besinlerListesi
        besinHataMesaji.value = false
        besinYukleniyor.value = false
    }

    private fun sqliteSakla(besinlerListesi :List<Besin>){

        launch {
            val dao = BesinDatabase(getApplication()).besinDao()
            dao.deleteAllBesin()
            val uuidListesi = dao.insertAll(*besinlerListesi.toTypedArray()) //listeyi tek tek haline  getirir ve veritabanına yüklendi
            var i = 0
            while (i < besinlerListesi.size){
                besinlerListesi[i].uuid = uuidListesi[i].toInt()
                i = i + 1
            }
            besinleriGoster(besinlerListesi)
        }

        ozelSharedPreferences.zamanKaydet(System.nanoTime())
    }
}