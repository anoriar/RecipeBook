package com.example.recipebook.presentation.adapter

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.recipebook.domain.entity.Category


class CategorySpinnerAdapter(
    private val context: Activity,
    private val textViewResourceId: Int
): ArrayAdapter<Category>(context, textViewResourceId) {

    var values: List<Category> = listOf()

    override fun getCount(): Int {
        return values.size
    }

    override fun getItem(position: Int):  Category{
        return values[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getPosition(item: Category?): Int {
        return values.indexOf(item)
    }

    override fun getView(
        position: Int,
        convertView: View?,
        parent: ViewGroup
    ): View {
        val label = super.getView(position, convertView, parent) as TextView
        label.text = values[position].name
        return label
    }

    override fun getDropDownView(
        position: Int,
        convertView: View?,
        parent: ViewGroup
    ): View {
        val label = super.getDropDownView(position, convertView, parent) as TextView
        label.text = values[position].name
        return label
    }
}