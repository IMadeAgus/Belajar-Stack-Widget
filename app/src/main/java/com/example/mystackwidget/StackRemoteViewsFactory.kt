package com.example.mystackwidget

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import androidx.core.os.bundleOf
import java.util.*

/**
 * Created by dicoding on 1/9/2017.
 */

internal class StackRemoteViewsFactory(private val mContext: Context) : RemoteViewsService.RemoteViewsFactory {

    private val mWidgetItems = ArrayList<Bitmap>()

    override fun onCreate() {

    }

    //untuk memuat semua data yang akan digunakan pada widget. Proses load harus kurang dari 20 detik  supaya tidak ANR (apllication not responding)
    override fun onDataSetChanged() {
        //Ini berfungsi untuk melakukan refresh saat terjadi perubahan.
        mWidgetItems.add(BitmapFactory.decodeResource(mContext.resources, R.drawable.darth_vader))
        mWidgetItems.add(BitmapFactory.decodeResource(mContext.resources, R.drawable.star_wars_logo))
        mWidgetItems.add(BitmapFactory.decodeResource(mContext.resources, R.drawable.storm_trooper))
        mWidgetItems.add(BitmapFactory.decodeResource(mContext.resources, R.drawable.starwars))
        mWidgetItems.add(BitmapFactory.decodeResource(mContext.resources, R.drawable.falcon))
    }

    override fun onDestroy() {

    }

    override fun getCount(): Int = mWidgetItems.size

    //Pada metode getViewTypeCount(), kita perlu mengembalikan nilai yang lebih dari 0. Nilai di sini mewakili jumlah layout item yang akan kita gunakan pada widget.
    override fun getViewTypeCount(): Int = 1


    //Pada metode getViewAt kita memasang item yang berisikan ImageView. Kita akan memasang gambar bitmap dengan memanfaatkan remoteviews. Kemudian item tersebut akan ditampilkan oleh widget.
    override fun getViewAt(position: Int): RemoteViews {
        val rv = RemoteViews(mContext.packageName, R.layout.widget_item)
        rv.setImageViewBitmap(R.id.imageView, mWidgetItems[position])

        val extras = bundleOf(
            ImagesBannerWidget.EXTRA_ITEM to position
        )
        val fillInIntent = Intent()
        fillInIntent.putExtras(extras)

        rv.setOnClickFillInIntent(R.id.imageView, fillInIntent)
        return rv
    }

    //Ketika Anda menggunakan Widget untuk menampilkan data dari database, Anda perlu menambahkan kode-kode berikut agar tidak force close dan data akan muncul secara realtime:
    //
    //override fun onDataSetChanged() {
    //    if (cursor != null) {
    //        cursor.close()
    //    }
    //
    //    val identityToken = Binder.clearCallingIdentity()
    //
    //    // querying ke database
    //    cursor = context.getContentResolver().query(CONTENT_URI, null, null, null, null)
    //
    //    Binder.restoreCallingIdentity(identityToken)
    //}

    override fun getLoadingView(): RemoteViews? = null



    override fun getItemId(i: Int): Long = 0

    override fun hasStableIds(): Boolean = false

}