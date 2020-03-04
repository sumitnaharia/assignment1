package com.example.myapplication.binder


import androidx.databinding.BindingAdapter

import android.text.TextUtils
import android.widget.ImageView
import com.example.myapplication.R
import com.squareup.picasso.Picasso


object ImageBinding {

    /**
     * @param imageView   imageview instance
     * @param imageType   0=>banner, 1=>profile picture,2=> certified image
     * @param imageUrl    url of image
     * @param placeHolder placeholder drawable id
     */
    @BindingAdapter(
        value = ["app:imageLink", "app:placeHolder", "app:imageType"],
        requireAll = true
    )
    @JvmStatic
    fun setImage(imageView: ImageView, imageUrl: String, placeHolder: Int, imageType: Int) {
        var imageUrl = imageUrl

        if (TextUtils.isEmpty(imageUrl)) {
            imageUrl = "abc"
            Picasso.with(imageView.context).load(imageUrl).error(placeHolder).into(imageView)
        } else if (imageType == 0) {
            Picasso.with(imageView.context).load(imageUrl).error(placeHolder).into(imageView)
        } else if (imageType == 1) {
            Picasso.with(imageView.context).load(imageUrl).error(placeHolder)
                .resizeDimen(R.dimen.dimen_profile_icon, R.dimen.dimen_profile_icon).into(imageView)
        } else
            Picasso.with(imageView.context).load(imageUrl).error(placeHolder).into(imageView)
    }
}
