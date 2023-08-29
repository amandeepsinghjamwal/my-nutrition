package com.example.mynutrition

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mynutrition.data.OverlayType
import com.example.mynutrition.databinding.ActivityMainBinding
import com.example.mynutrition.viewmodel.MainViewModel
import com.example.mynutrition.viewmodel.MainViewModelFactory
import java.util.Locale


// get free API key at https://calorieninjas.com/api and put it down below to work

class MainActivity : AppCompatActivity() {
    private lateinit var adapter: ItemListAdapter
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val factory = MainViewModelFactory((applicationContext as AppClass).provideRepo())
        viewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]

        viewModel.dataLoading.observe(this) {
            loadingState(it)
        }

        viewModel.isError.observe(this) {
            errorState(it)
        }

        adapter = ItemListAdapter(this) { its ->
            val name = its.name.replaceFirstChar {
                if (it.isLowerCase()) {
                    it.titlecase(Locale.getDefault())
                } else {
                    it.toString()
                }
            }
            val sugar = its.sugar_g
            val fiber = its.fiber_g
            val servingSize = its.serving_size_g
            val sodium = its.sodium_mg
            val potassium = its.potassium_mg
            val saturatedFat = its.fat_saturated_g
            val totalFat = its.fat_total_g
            val calories = its.calories
            val cholesterol = its.cholesterol_mg
            val protein = its.protein_g
            val carbs = its.carbohydrates_total_g

            gotoDetails(
                name,
                sugar,
                fiber,
                servingSize,
                sodium,
                potassium,
                saturatedFat,
                totalFat,
                calories,
                cholesterol,
                protein,
                carbs
            )
        }

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        viewModel.responseData.observe(this) {
            if (it.isEmpty()) {
                changeOverlay(OverlayType.NO_DATA)
                adapter.submitList(it)
            } else {
                changeOverlay(OverlayType.DATA)
                binding.recyclerView.visibility = View.VISIBLE
                adapter.submitList(it)
            }
        }
        binding.searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextSubmit(p0: String?): Boolean {
                changeOverlay(OverlayType.DATA)
                viewModel.callApi(p0)
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
        cholesterol: Int,
        protein: Double,
        carbs: Double
    ) {
        val intent = Intent(this, NutritionDetailsActivity::class.java).apply {
            putExtra("name", name)
            putExtra("sugar", sugar)
            putExtra("fiber", fiber)
            putExtra("servingSize", servingSize)
            putExtra("sodium", sodium)
            putExtra("potassium", potassium)
            putExtra("saturatedFat", saturatedFat)
            putExtra("totalFat", totalFat)
            putExtra("calories", calories)
            putExtra("cholesterol", cholesterol)
            putExtra("protein", protein)
            putExtra("carbs", carbs)
        }
        startActivity(intent)
    }

    @SuppressLint("SetTextI18n")
    private fun errorState(isError: Boolean) {
        if (isError) {
            binding.notFoundText.text = "Oops! Something went wrong"
            binding.imageView.visibility = View.GONE
            binding.textToHide.visibility = View.GONE
            binding.notFound.visibility = View.VISIBLE
            binding.notFoundText.visibility = View.VISIBLE
            binding.recyclerView.visibility = View.GONE
            binding.shimmer.visibility = View.GONE
        } else {
            binding.notFoundText.text = "Oops! No item found"
        }
    }

    private fun loadingState(isLoading: Boolean) {
        if (isLoading) {
            binding.imageView.visibility = View.GONE
            binding.textToHide.visibility = View.GONE
            binding.notFound.visibility = View.GONE
            binding.notFoundText.visibility = View.GONE
            binding.recyclerView.visibility = View.GONE
            binding.shimmer.visibility = View.VISIBLE
        } else {
            binding.shimmer.visibility = View.GONE
        }
    }

    private fun changeOverlay(overlay: OverlayType) {
        if (overlay == OverlayType.DATA) {
            binding.imageView.visibility = View.GONE
            binding.textToHide.visibility = View.GONE
            binding.notFound.visibility = View.GONE
            binding.notFoundText.visibility = View.GONE
            binding.recyclerView.visibility = View.GONE
        } else {
            binding.notFound.visibility = View.VISIBLE
            binding.notFoundText.visibility = View.VISIBLE
        }
    }
}