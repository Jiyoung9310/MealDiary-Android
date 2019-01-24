package com.teamnexters.android.mealdiary.ui.write.photo

import android.content.Context
import android.provider.MediaStore
import java.util.ArrayList

internal class GalleryProviderImpl(
        private val context: Context

) : GalleryProvider {

    override fun photoPathList(): List<Photo> {
        val photoList = ArrayList<Photo>()

        val uri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI

        val projection = arrayOf(MediaStore.MediaColumns.DATA, MediaStore.Images.Media.DATE_ADDED)

        val cursor = context.contentResolver.query(uri, projection, null, null, null)

        val columnIndexData = cursor!!.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA)

        while(cursor.moveToNext()) {
            val photoVO = Photo(cursor.getString(columnIndexData))

            photoList.add(photoVO)
        }

        cursor.close()

        return photoList
    }

}
