package com.umutcansahin.besinbesin.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

//cons. icindeki degerlerin null olmasısın sebebi internetten gelecek verilerin bos olması durumunda hata almamak
@Entity
data class Besin(

    @ColumnInfo(name = "isim")  //sql tablosunun icindeki kolon icin isim verildi.
    @SerializedName("isim")   // internetten alacağımız tablo isim eslestirme yapabilmek icin
    val besinIsmi : String?,

    @ColumnInfo(name = "kalori")
    @SerializedName("kalori")
    val besinKalori : String?,

    @ColumnInfo(name = "karbonhidrat")
    @SerializedName("karbonhidrat")
    val besinKarbonhidrat : String?,

    @ColumnInfo(name = "protein")
    @SerializedName("protein")
    val besinProtein : String?,

    @ColumnInfo(name = "yag")
    @SerializedName("yag")
    val besinYag : String?,

    @ColumnInfo(name = "gorsel")
    @SerializedName("gorsel")
    val besinGorsel : String?

    ) {
    @PrimaryKey(autoGenerate = true) //id otomatik versin diye 'true' dedik ve bir degiskene atadık.
    var uuid : Int = 0

}