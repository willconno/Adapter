package com.willconnelly.adapter

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

/**
 * Created by William Connelly on 3/11/2018.
 *
 * connelly.william@gmail.com
 */
abstract class Adapter(context: Context?): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    /**
     * Public @see LayoutInflater for ViewHolder inflation
     */
    val inflater: LayoutInflater = LayoutInflater.from(context)

    /**
     *
     */
    final override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType < 0){
            return headerViewHolder((viewType * -1) - 1, parent)
        }

        return viewHolderFor(viewType, parent)!!
    }

    /**
     *
     */
    final override fun getItemCount(): Int {
        var result = 0
        for (count in 0 until numberOfSections()){
            result += numberOfRowsIn(count)
        }
        return result + numberOfSections()
    }

    /**
     * Header view holders are negative integers -1 indexed
     */
    final override fun getItemViewType(position: Int): Int {
        if (isSection(position)){
            return (getSectionFrom(position) + 1) * -1
        }

        return getSectionFrom(position)
    }

    /**
     * Calculate ViewHolder type, row and section and pass to public onBindViewHolder methods
     */
    final override fun onBindViewHolder(p0: RecyclerView.ViewHolder, position: Int) {
        if (isSection(position)){
            onBindHeaderViewHolder(p0, getSectionFrom(position))
        } else {
            onBindViewHolder(p0, getSectionFrom(position), getRowFrom(position))
        }
    }

    /**
     * Override to customise default HeaderViewHolder or custom ViewHolder for header in section
     */
    protected open fun onBindHeaderViewHolder(p0: RecyclerView.ViewHolder, section: Int){
        if (p0 is HeaderViewHolder) {
            p0.textView.text = titleForHeaderInSection(section)
        }
    }

    /**
     *
     */
    abstract fun numberOfSections(): Int

    /**
     * This method is taken from onBindViewHolder includes section and row positions
     */
    abstract fun onBindViewHolder(holder: RecyclerView.ViewHolder, section: Int, row: Int)

    /**
     * Required to calculate section and row index
     */
    abstract fun numberOfRowsIn(section: Int): Int

    /**
     * Standard ViewHolder implementation
     */
    abstract fun viewHolderFor(section: Int, parent: ViewGroup) : RecyclerView.ViewHolder?

    /**
     * When using the libraries HeaderViewHolder this method return the title to be displayed
     */
    protected open fun titleForHeaderInSection(section: Int): String {return ""}

    /**
     * Override to customise the header for a specific section.
     */
    protected open fun headerViewHolder(section: Int, parent: ViewGroup) : RecyclerView.ViewHolder {
        return headerViewHolder(parent)
    }

    /**
     * if Adapter.headerViewHolder(Int, ViewGroup) is undefined, this is the default
     * @see Adapter.headerViewHolder(Int, ViewGroup)
     */
    protected open fun headerViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder {
        return HeaderViewHolder(inflater.inflate(R.layout.header_view_holder, parent, false))
    }

    /**
     * @return section number from ViewHolder.adapterPosition
     * @see RecyclerView.ViewHolder.getAdapterPosition
     */
    fun getSectionFrom(position: Int): Int {

        var totalCount = 0

        for (section in 0 until numberOfSections()) {

            totalCount += numberOfRowsIn(section)

            if (position == section || position <= totalCount + section) {
                return section
            }
        }

        return 0
    }

    /**
     * @return row number from ViewHolder.adapterPosition
     */
    fun getRowFrom(position: Int): Int {
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

    /**
     * Determine whether adapterPosition is section
     */
    private fun isSection(position: Int): Boolean {
        if (position == 0) return true

        var counted = 0

        for (section in 0 until numberOfSections()) {

            counted += numberOfRowsIn(section)

            if (position == counted + (section + 1)) return true
        }

        return false
    }

    /**
     * Get item range in section
     * todo: Get section from adapter position
     */
    fun rangeForItemsInSection(section: Int, sectionAdapterPosition: Int): Pair<Int, Int> {
        return Pair(sectionAdapterPosition + 1, numberOfRowsIn(section))
    }

    /**
     * Get index of a section header
     */
    fun indexForHeader(section: Int): Int {

        if (section == 0) return 0

        var index = 0

        for (i in 0 until section){
            index += 1 //count the section header
            index += numberOfRowsIn(i)
        }

        return index
    }
}

class HeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
    val textView: TextView = itemView.findViewById(R.id.textView)
}