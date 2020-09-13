package com.alvayonara.myreactivesearch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ed_place.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                lifecycleScope.launch {
                    viewModel.queryChannel.send(p0.toString())
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })

        viewModel.searchResult.observe(this, Observer { placesItem ->
            val placesName = arrayListOf<String?>()
            placesItem.map {
                placesName.add(it.placeName)
            }
            val adapter =
                ArrayAdapter(this@MainActivity, android.R.layout.select_dialog_item, placesName)
            adapter.notifyDataSetChanged()
            ed_place.setAdapter(adapter)
        })
    }
}