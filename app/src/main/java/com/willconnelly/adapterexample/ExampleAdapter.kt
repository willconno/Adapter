package com.willconnelly.adapterexample

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.willconnelly.adapter.Adapter

/**
 * Created by William Connelly on 3/11/2018.
 *
 *ClientVault Pty Ltd.
 *william@clientvault.com
 */
class ExampleAdapter(context: Context): Adapter(context){

    val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun viewHolderFor(section: Int, parent: ViewGroup): RecyclerView.ViewHolder? {
        val holder = ViewHolder(inflater.inflate(R.layout.view_holder, parent, false))

        when (section){
            0->holder.background.setBackgroundColor(parent.context.resources.getColor(R.color.grey_500))
            1->holder.background.setBackgroundColor(parent.context.resources.getColor(R.color.amber_500))
            2->holder.background.setBackgroundColor(parent.context.resources.getColor(R.color.red_500))
        }

        return holder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, section: Int, row: Int) {
        (holder as? ViewHolder)?.let{
            it.textView.text = "Section: $section  -  Position $row"
        }
    }

    override fun numberOfRowsIn(section: Int): Int {

        when (section){
            0->return 5
            1->return 10
            2->return 15
        }
        return 0
    }

    override fun numberOfSections(): Int {
        return 3
    }

    override fun titleForHeaderInSection(section: Int): String {

        when (section){
            0->return "Section 1"
            1->return "Section 2"
            2->return "Section 3"
        }

        return super.titleForHeaderInSection(section)
    }
}

class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
    val background: ConstraintLayout = view.findViewById(R.id.background)
    val textView: TextView = view.findViewById(R.id.textView)
}
