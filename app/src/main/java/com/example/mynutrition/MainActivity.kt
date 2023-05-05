package com.example.mynutrition

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mynutrition.api.ApplicationApi
import com.example.mynutrition.api.models.ApiResponse
import com.example.mynutrition.api.models.Item
import com.example.mynutrition.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Locale

private const val KEY="GM3Y7RThzpEkmiJIKyiTfw==PkKN4em3byvm10xD"
// get free API key at https://calorieninjas.com/api and put it down below to work
//private const val KEY="Add your api key here"
class MainActivity : AppCompatActivity() {
    var responseList= mutableListOf<Item>()
    private lateinit var adapter: ItemListAdapter
    private lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        adapter=ItemListAdapter(this){ its ->
            val name=its.name.replaceFirstChar {
                if (it.isLowerCase()){
                    it.titlecase(Locale.getDefault())
                }
                else{
                    it.toString()
                }
            }
            val sugar=its.sugar_g
            val fiber=its.fiber_g
            val servingSize=its.serving_size_g
            val sodium=its.sodium_mg
            val potassium=its.potassium_mg
            val saturatedFat=its.fat_saturated_g
            val totalFat=its.fat_total_g
            val calories=its.calories
            val cholestrol=its.cholesterol_mg
            val protein = its.protein_g
            val carbs=its.carbohydrates_total_g

            gotoDetails(name,sugar,fiber,servingSize,sodium,potassium,saturatedFat,totalFat,calories,cholestrol,protein,carbs)
        }
        binding.recyclerView.adapter=adapter
        binding.recyclerView.layoutManager=LinearLayoutManager(this)
        binding.searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextChange(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextSubmit(p0: String?): Boolean {
                responseList.clear()
                adapter.notifyDataSetChanged()
                callApi(p0)
                return false
            }
        })
    }

    private fun gotoDetails(
        name: String,
        sugar: Double,
        fiber: Double,
        servingSize: Double,
        sodium: Int,
        potassium: Int,
        saturatedFat: Double,
        totalFat: Double,
        calories: Double,
        cholestrol: Int,
        protein: Double,
        carbs: Double
    ) {
        val intent=Intent(this,NutritionDetailsActivity::class.java).apply {
            putExtra("name",name)
            putExtra("sugar",sugar)
            putExtra("fiber",fiber)
            putExtra("servingSize",servingSize)
            putExtra("sodium",sodium)
            putExtra("potassium",potassium)
            putExtra("saturatedFat",saturatedFat)
            putExtra("totalFat",totalFat)
            putExtra("calories",calories)
            putExtra("cholestrol",cholestrol)
            putExtra("protein",protein)
            putExtra("carbs",carbs)
        }
        startActivity(intent)

    }

    private fun callApi(p0: String?) {

        binding.shimmer.visibility=View.VISIBLE
        hideOverlays(1)
        val callApi=ApplicationApi.retrofitService.getData(p0!!,KEY)
        CoroutineScope(Dispatchers.IO).launch {
            callApi.enqueue(object : Callback<ApiResponse>{
                override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                    if(response.code()==200){
                        binding.shimmer.visibility=View.GONE
                        responseList= response.body()!!.items as MutableList<Item>
                        adapter.submitList(responseList)
                        if(responseList.isEmpty()){
                            hideOverlays(0)
                        }

                    }
                    Log.e("data",response.code().toString())
                }

                override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                    Log.e("failure","failure")
                }

            })
        }
    }

    private fun hideOverlays(i: Int) {
        if(i==1){
            binding.imageView.visibility=View.GONE
            binding.textToHide.visibility=View.GONE
            binding.notFound.visibility=View.GONE
            binding.notFoundText.visibility=View.GONE
        }
        else{
            binding.notFound.visibility=View.VISIBLE
            binding.notFoundText.visibility=View.VISIBLE
        }
    }
}