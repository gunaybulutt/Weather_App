package com.gunay.weather_app.view


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.gunay.weather_app.databinding.FragmentSearchPageBinding


class SearchPage : Fragment() {


    private lateinit var binding: FragmentSearchPageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchPageBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val sharedPref = requireContext().getSharedPreferences("mySharedPreferences", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()


        binding.searchButton.setOnClickListener {

            editor.putString("myKey", binding.citySearchText.text.toString())
            editor.apply()

            val action = SearchPageDirections.actionSearchPageToStatePage()
            Navigation.findNavController(it).navigate(action)
        }
    }

}