package com.http.activity

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.http.R
import com.http.adapter.DividerItemDecoration
import com.http.adapter.RecyclerAdapter
import com.http.bean.ForecastResult
import com.http.http.RequestForecastCommand
import kotlinx.android.synthetic.main.activity_kotlin.*
import org.jetbrains.anko.async
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread
import java.net.URL
import java.util.*

/**
 * Created by ZeroAries on 2016/2/17.
 */
class KotlinActivity : Activity() {

    private val items = arrayListOf(
            "A", "B", "C", "D",
            "E", "F", "G", "H",
            "I", "J", "K", "L",
            "M", "N", "O", "P",
            "Q", "R", "S", "T",
            "U", "V", "W", "X",
            "Y", "Z"
    )

    var adapter: RecyclerAdapter? = null
    var mContext: Context? = null

    inline fun <T, R : Any> Iterable<T>.mapNotNull1(transform: (T) -> R?): List<R> {
        return mapNotNullTo1(ArrayList<R>(), transform)
    }

    inline fun <T, R : Any, C : MutableCollection<in R>> Iterable<T>.mapNotNullTo1(destination: C, transform: (T) -> R?): C {
        forEach { transform(it)?.let { destination.add(it) } }
        return destination
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin)
        mContext = this
        message.text = "hellow kotlin"
        message.setOnClickListener {
            //            adapter?.removeData(1)
        }

        recycler_view.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recycler_view.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST))
        //        recycler_view.layoutManager = GridLayoutManager(this, 4)
        //        recycler_view.addItemDecoration(DividerGridItemDecoration(this))

        //        SlideInOutLeftItemAnimator, SlideInOutRightItemAnimator, SlideInOutTopItemAnimator, SlideInOutBottomItemAnimator
        recycler_view.itemAnimator = DefaultItemAnimator()

        async() {
            val result = RequestForecastCommand("94043").execute()
            uiThread {
                recycler_view.adapter = RecyclerAdapter(result) {
                    toast(it.date)
                }
            }
        }
    }

    fun gsonToBean(): ForecastResult {
        val type1 = object : TypeToken<ForecastResult>() {}.type
        val type2 = ForecastResult::class.java
        val forecastJsonStr = URL("").readText()
        return Gson().fromJson(forecastJsonStr, ForecastResult::class.java)
    }


}