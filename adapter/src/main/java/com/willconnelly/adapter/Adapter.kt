package com.willconnelly.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

/**
 * Created by William Connelly on 3/11/2018.
 *
 *ClientVault Pty Ltd.
 *william@clientvault.com
 */
abstract class Adapter(context: Context): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val _inflater: LayoutInflater = LayoutInflater.from(context)

    val HEADER = -1
    val CUSTOM = -2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == HEADER){
            return HeaderViewHolder(_inflater.inflate(R.layout.header_view_holder, parent, false))
        }

        val viewViewHolder = viewHolderFor(viewType, parent)
        return viewViewHolder!!
    }

    override fun getItemCount(): Int {
        var result = 0
        for (count in 0 until numberOfSections()){
            result += numberOfRowsIn(count)
        }
        return result + numberOfSections()
    }

    override fun getItemViewType(position: Int): Int {
        if (isSection(position)){
            return HEADER
        }

        return getSectionFrom(position)
    }

    override fun onBindViewHolder(p0: RecyclerView.ViewHolder, position: Int) {
        if (p0 is HeaderViewHolder){
            p0.textView.text = titleForHeaderInSection(getSectionFrom(position))
        } else {
            onBindViewHolder(p0, getSectionFrom(position), getRowFrom(position))
        }
    }

    /**
     * Override and cast to custom viewHolder
     */
    abstract fun numberOfSections(): Int
    abstract fun onBindViewHolder(holder: RecyclerView.ViewHolder, section: Int, row: Int)
    abstract fun numberOfRowsIn(section: Int): Int

//    abstract fun viewForHeaderIn(section: Int) : RecyclerView.ViewHolder? //todo
    protected open fun titleForHeaderInSection(section: Int): String {return ""}

    abstract fun viewHolderFor(section: Int, parent: ViewGroup) : RecyclerView.ViewHolder?

//    private fun getItemViewTypes(): List<RecyclerView.ViewHolder?> {
//        val result = ArrayList<RecyclerView.ViewHolder?>()
//        for (i in 0..numberOfSections()) {
//            result.add(viewHolderFor(i))
//        }
//        return result
//    }

    private fun getSectionFrom(position: Int): Int {

        var totalCount = 0

        for (section in 0 until numberOfSections()) {

            totalCount += numberOfRowsIn(section)

            if (position == section || position <= totalCount + section) {
                return section
            }

        }

        return 0
    }

    open fun getRowFrom(position: Int): Int {
        var totalCount = 0

        for (section in 0..getSectionFrom(position)) {

            for (index in 0 until numberOfRowsIn(section)){

                totalCount++

                if (position == index || position == (totalCount + section)){
                    return index
                }
            }

        }

        return 0
    }

    private fun isSection(position: Int): Boolean {
        if (position == 0) return true

        var counted = 0

        for (section in 0 until numberOfSections()) {

            counted += numberOfRowsIn(section)

            if (position == counted + (section + 1)) return true
        }

        return false
    }

}

class HeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
    val textView: TextView = itemView.findViewById(R.id.textView)
}