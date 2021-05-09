package com.anteeone.coverit.ui.utils.extensions

import android.widget.ImageView
import com.squareup.picasso.Picasso

fun ImageView.loadImage(uri: String){
    Picasso.get().load(uri).into(this)
}
