package com.teamnexters.android.mealdiary.ui.dialog

import android.content.Context
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.teamnexters.android.mealdiary.R
import com.teamnexters.android.mealdiary.util.SortTypeUtil

object DialogFactory {
    fun diarySortDlg(context: Context) : Toast?{
        val resource = context.resources
        val sortString = resource.getStringArray(R.array.array_sort)
        val ab = AlertDialog.Builder(context)
        var toast: Toast? = null
        ab.setTitle(resource.getString(R.string.sort_title))
        ab.setSingleChoiceItems(sortString, 0) { dialog, which ->
            //선택 시 동작
        }.setPositiveButton(resource.getString(R.string.dialog_ok)) { dialog, which ->
            //확인 클릭 시 동작
            toast = when(which) {
                SortTypeUtil.SortType.LATEST.type -> Toast.makeText(context, SortTypeUtil().getSortString(which), Toast.LENGTH_SHORT)
                SortTypeUtil.SortType.SCORE.type -> Toast.makeText(context, SortTypeUtil().getSortString(which), Toast.LENGTH_SHORT)
                SortTypeUtil.SortType.DISTANCE.type -> Toast.makeText(context, SortTypeUtil().getSortString(which), Toast.LENGTH_SHORT)
                else -> Toast.makeText(context, "정렬타입 못가져옴", Toast.LENGTH_SHORT)
            }
        }.setNegativeButton(resource.getString(R.string.dialog_cancel)) { dialog, which ->
            //취소 클릭 시 동작
        }
        ab.show()
        return toast
    }
}