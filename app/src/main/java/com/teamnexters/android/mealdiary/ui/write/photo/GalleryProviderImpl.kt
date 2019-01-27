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

        val sortOrderDESC = MediaStore.Images.Media._ID + " COLLATE LOCALIZED DESC"

        val cursor = context.contentResolver.query(uri, projection, null, null, sortOrderDESC)

        val columnIndexData = cursor!!.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA)

        while(cursor.moveToNext()) {
            val photo = Photo(cursor.getString(columnIndexData))

            photoList.add(photo)
        }

        cursor.close()

        return photoList
    }

}
