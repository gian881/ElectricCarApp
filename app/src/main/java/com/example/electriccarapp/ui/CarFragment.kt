package com.example.electriccarapp.ui

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.electriccarapp.ui.adapter.CarAdapter
import com.example.electriccarapp.R
import com.example.electriccarapp.domain.Car
import org.json.JSONArray
import org.json.JSONTokener
import java.net.HttpURLConnection
import java.net.URL

class CarFragment : Fragment() {
    lateinit var listaCarros: RecyclerView
    lateinit var progressBar: ProgressBar
    lateinit var noConnectionImage: ImageView
    lateinit var noConnectionText: TextView

    var carsArray: ArrayList<Car> = ArrayList()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.car_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView(view)
        setupList()
        if (checkForInternet(context)) {
            runTask()
        } else {
            emptyState()
        }
    }

    override fun onResume() {
        super.onResume()
        if (checkForInternet(context)) {
            runTask()
        } else {
            emptyState()
        }
    }

    private fun emptyState() {
        progressBar.isVisible = false
        listaCarros.isVisible = false
        noConnectionImage.isVisible = true
        noConnectionText.isVisible = true
    }

    private fun runTask() {
        val baseUrl = "https://gian881.github.io/data/cars.json"
        getCarInformations().execute(baseUrl)
    }

    private fun setupView(view: View) {
        view.apply {
            listaCarros = findViewById(R.id.rv_car_list)
            progressBar = findViewById(R.id.pb_loader)
            noConnectionImage = findViewById(R.id.iv_no_connection)
            noConnectionText = findViewById(R.id.tv_no_connection)
        }
    }

    private fun setupList() {
        val adapter = CarAdapter(carsArray)
        listaCarros.adapter = adapter
        listaCarros.isVisible = true
    }

    private fun checkForInternet(context: Context?): Boolean {
        val conectivityManager =
            context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = conectivityManager.activeNetwork ?: return false
            val activeNetwork = conectivityManager.getNetworkCapabilities(network) ?: return false
            return when {
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            @Suppress("Deprecation")
            val networkInfo = conectivityManager.activeNetworkInfo ?: return false
            @Suppress("Deprecation")
            return networkInfo.isConnected
        }
        return true
    }

    inner class getCarInformations : AsyncTask<String, String, String>() {

        @Deprecated("Deprecated in Java")
        override fun onPreExecute() {
            super.onPreExecute()
            progressBar.isVisible = true
        }

        override fun doInBackground(vararg url: String?): String {
            var urlConnection: HttpURLConnection? = null
            try {
                val urlBase = URL(url[0])
                urlConnection = urlBase.openConnection() as HttpURLConnection
                urlConnection.connectTimeout = 60000
                urlConnection.readTimeout = 60000
                urlConnection.setRequestProperty(
                    "Accept",
                    "application/json"
                )

                val responseCode = urlConnection.responseCode

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    val response = urlConnection.inputStream.bufferedReader().use { it.readText() }
                    publishProgress(response)
                } else {
                    Log.e("Erro", "Serviço indisponível no momento")
                }


            } catch (e: Exception) {
                Log.e("Erro", "Erro ao realizar processamento")
            } finally {
                urlConnection?.disconnect()
            }
            return ""
        }

        @Deprecated("Deprecated in Java")
        override fun onProgressUpdate(vararg values: String?) {
            try {
                val jsonArray = JSONTokener(values[0]).nextValue() as JSONArray
                for (i in 0 until jsonArray.length()) {
                    val id = jsonArray.getJSONObject(i).getInt("id")
                    val name = jsonArray.getJSONObject(i).getString("name")
                    val price = jsonArray.getJSONObject(i).getDouble("price").toFloat()
                    val battery = jsonArray.getJSONObject(i).getDouble("battery").toFloat()
                    val potency = jsonArray.getJSONObject(i).getInt("potency")
                    val rechargeTime = jsonArray.getJSONObject(i).getInt("rechargeTime")
                    val photoUrl = jsonArray.getJSONObject(i).getString("photoUrl")


                    val car = Car(
                        id,
                        name,
                        price,
                        battery,
                        potency,
                        rechargeTime,
                        photoUrl
                    )

                    carsArray.add(car)
                }
                progressBar.isVisible = false
                noConnectionImage.isVisible = false
                noConnectionText.isVisible = false
                setupList()
            } catch (e: Exception) {
                Log.e("Erro", e.toString())
            }
        }
    }
}