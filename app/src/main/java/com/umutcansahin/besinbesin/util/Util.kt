package com.umutcansahin.besinbesin.util

import android.content.Context
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.umutcansahin.besinbesin.R

fun ImageView.gorselIndir(url : String? , placeHolder : CircularProgressDrawable){

    val option = RequestOptions().placeholder(placeHolder).error(R.mipmap.ic_launcher_round)

    Glide.with(context).setDefaultRequestOptions(option).load(url).into(this)
}

fun placeHolderYap(context : Context) : CircularProgressDrawable{
    return CircularProgressDrawable(context).apply {
        strokeWidth = 8f
        centerRadius = 40f
        start()
    }
}
@BindingAdapter("android:downloadImage")
fun downloadImage(view: ImageView , url: String?){
    view.gorselIndir(url, placeHolderYap(view.context))
}