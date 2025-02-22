package com.example.crypto_project

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.crypto_project.databinding.ActivityMainBinding
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var rvAdapter: RvAdapter
    private lateinit var data: ArrayList<Model>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        data = ArrayList<Model>()
        rvAdapter = RvAdapter(this, data)
        binding.rv.layoutManager = LinearLayoutManager(this)
        binding.rv.adapter = rvAdapter
        apidata
    binding.search.addTextChangedListener(object :TextWatcher{
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }

        override fun afterTextChanged(s: Editable?) {
            val filterData= ArrayList<Model>()
            for (item in data){
            if(item.name.lowercase(Locale.getDefault()).contains(s.toString().lowercase((Locale.getDefault())))){
                filterData.add(item)
                }
                 if (filterData.isEmpty()){
//                  Toast.makeText(this@MainActivity, "No data available", Toast.LENGTH_SHORT).show()

                }else{
                    rvAdapter.changeData(filterData)
                }
            }
        }

    })
    }

    val apidata: Unit
        get() {
            val url = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest"
            val queue = Volley.newRequestQueue(this)
            val jsonObjRequest: JsonObjectRequest =
                object : JsonObjectRequest(Method.GET, url, null, Response.Listener {
                    response ->
                    binding.progressBar.isVisible=false
                    try {
                        val dataArray = response.getJSONArray("data")
                        for (i in 0 until dataArray.length()) {
                            val dataObject = dataArray.getJSONObject(i)
                            val symbol = dataObject.getString("symbol")
                            val name = dataObject.getString("name")
                            val quote = dataObject.getJSONObject("quote")
                            val USD = quote.getJSONObject("USD")
                            val price = USD.getDouble("price")
                            val priced = String.format("$ "+"%.2f",price)
                            data.add(Model(name, symbol, priced.toString()))
                        }
                        rvAdapter.notifyDataSetChanged()
                    } catch (e: Exception) {
                        Toast.makeText(this, "Error", Toast.LENGTH_LONG).show()

                    }

                }, Response.ErrorListener {
                    Toast.makeText(this, "Error 1", Toast.LENGTH_LONG).show()

                }) {
                    override fun getHeaders(): Map<String, String> {
                        val headers = HashMap<String, String>();
                        headers["X-CMC_PRO_API_KEY"] = "38ff8810-58f1-44b9-865c-574d13dcd43f"
                        return headers
                    }
                }
            queue.add(jsonObjRequest)

        }
}