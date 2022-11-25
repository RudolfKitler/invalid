package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartModel
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartType
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartView
import com.github.aachartmodel.aainfographics.aachartcreator.AASeriesElement
import kotlin.math.sqrt

data class Data(var date: String = "",
                var time: String = "",
                var temperature: Double = 0.0,
                var humidity: Double = 0.0)

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //TODO: Нужно узнать, в каком формате получается время и дата

        //Считаем, что мы данные получаем тут
        val dataList = ArrayList<Data>()
        for (i in 0 .. 30) {
            dataList.add(Data(temperature = i.toDouble(), humidity = sqrt(i.toDouble()), date = "date_${i / 4}", time = "time_$i"))
        }

        //TODO: Функции фильтрации по дате и времени над dataList
        //Первый вариант фильтрации по дате - неоптимальный - приведён ниже.
        //Создаём реестр дат, затем сравниваем каждый элемент даты входных данных
        //с каждым элементом реестра дат.
        //Второй вариант - переводим в дни наименьшую и наибольшую требуемые даты,
        //и затем переводим каждый элемент даты входных данных в дни и сравниваем.

        //-----------------------Фильтр №1-------------------------
        /*
        val removalList = ArrayList<Data>()
        for (data in dataList) {
            if (data.date != "date_2") {
                removalList.add(data)
            }
        }
        dataList.removeAll(removalList.toSet())
        */

        //-----------------------Фильтр №2-------------------------

        val lowReq = 2
        val highReq = 4

        val filterData = ArrayList<String>()
        for (index in lowReq .. highReq) {
            filterData.add("date_$index")
        }

        val removalList = ArrayList<Data>()
        for (data in dataList) {
            if (!filterData.contains(data.date)) {
                removalList.add(data)
            }
        }
        dataList.removeAll(removalList.toSet())

        //---------------------------------------------------------

        //Работаем с температурой
        val temperatureData = ArrayList<Double>()
        for (index in 0 until dataList.size) {
            temperatureData.add(dataList[index].temperature)
        }

        //Работаем с влажностью
        val humidityData = ArrayList<Double>()
        for (index in 0 until dataList.size) {
            humidityData.add(dataList[index].humidity)
        }

        val xAxisText = ArrayList<String>()
        for (index in 0 until dataList.size) {
            xAxisText.add("${dataList[index].date} ${dataList[index].time}")
        }

        val aaChartView = findViewById<AAChartView>(R.id.aa_chart_view)
        val aaChartModel : AAChartModel = AAChartModel()
            .chartType(AAChartType.Area)
            .dataLabelsEnabled(true)
            .title("Влажность")
            //.subtitle("subtitle")
            .backgroundColor("#ffffff")
            .series(arrayOf(
                AASeriesElement()
                    .name("Влажность")
                    .data(humidityData.toArray())
                    .color("#44BBCC"),
                ),
            ).categories(xAxisText.toArray(arrayOf(String())))

        val aaChartView2 = findViewById<AAChartView>(R.id.aa_chart_view2)
        val aaChartModel2 : AAChartModel = AAChartModel()
            .chartType(AAChartType.Area)
            .dataLabelsEnabled(true)
            .title("Температура")
            //.subtitle("subtitle")
            .backgroundColor("#ffffff")
            .series(arrayOf(
                AASeriesElement()
                    .name("Температура")
                    .data(temperatureData.toArray())
                    .color("#DDBB22"),
            ),
            ).categories(xAxisText.toArray(arrayOf(String())))

        aaChartView.aa_drawChartWithChartModel(aaChartModel)
        aaChartView2.aa_drawChartWithChartModel(aaChartModel2)

    }
}