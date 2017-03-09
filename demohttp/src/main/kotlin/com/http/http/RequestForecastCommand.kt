package com.http.http

import com.http.bean.ForecastDataMapper
import com.http.bean.domain.ForecastList
import com.http.imp.Command

/**
 * Created by ZeroAries on 2016/2/19.
 */
class RequestForecastCommand(private val zipCode: String) :
        Command<ForecastList> {
    override fun execute(): ForecastList {
        val forecastRequest = ForecastRequest(zipCode)
        return ForecastDataMapper().convertFromDataModel(
                forecastRequest.execute())
    }
}