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
 * connelly.william@gmail.com
 */
class ExampleAdapter(context: Context): Adapter(context){

    override fun viewHolderFor(section: Int, parent: ViewGroup): RecyclerView.ViewHolder? {
        val holder = ViewHolder(inflater.inflate(R.layout.view_holder, parent, false))

        when (section){
            0->holder.background.setBackgroundColor(parent.context.resources.getColor(R.color.green_500))
            1->holder.background.setBackgroundColor(parent.context.resources.getColor(R.color.amber_500))
            2->holder.background.setBackgroundColor(parent.context.resources.getColor(R.color.red_500))
        }

        return holder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, section: Int, row: Int) {
        (holder as? ViewHolder)?.let{
            it.textView.text = "Section: $section  -  Position: $row"
        }
    }

    override fun numberOfRowsIn(section: Int): Int {
        return section + 1
    }

    override fun numberOfSections(): Int {
        return 1000
    }

    override fun titleForHeaderInSection(section: Int): String {
        return "Section ${section + 1}"
    }

    override fun headerViewHolder(section: Int, parent: ViewGroup): RecyclerView.ViewHolder {

        when (section){
            0->return HEADER1(inflater.inflate(R.layout.header_view_holder_1, parent, false))
            1->return HEADER2(inflater.inflate(R.layout.header_view_holder_2, parent, false))
            2->return HEADER3(inflater.inflate(R.layout.header_view_holder_3, parent, false))
        }

        return super.headerViewHolder(section, parent)
    }

    override fun onBindHeaderViewHolder(p0: RecyclerView.ViewHolder, section: Int) {
        super.onBindHeaderViewHolder(p0, section)

        if (p0 is HEADER1){
            p0.background.setBackgroundColor(p0.itemView.context.resources.getColor(R.color.green_300))
        }
        if (p0 is HEADER2){
            p0.background.setBackgroundColor(p0.itemView.context.resources.getColor(R.color.amber_300))
        }
        if (p0 is HEADER3){
            p0.background.setBackgroundColor(p0.itemView.context.resources.getColor(R.color.red_300))
        }

    }

}

class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
    val background: ConstraintLayout = view.findViewById(R.id.background)
    val textView: TextView = view.findViewById(R.id.textView)
}

class HEADER1(view: View): RecyclerView.ViewHolder(view) {
    val background: ConstraintLayout = view.findViewById(R.id.background)
    val textView: TextView = view.findViewById(R.id.textView)
}

class HEADER2(view: View): RecyclerView.ViewHolder(view) {
    val background: ConstraintLayout = view.findViewById(R.id.background)
    val textView: TextView = view.findViewById(R.id.textView)
}
class HEADER3(view: View): RecyclerView.ViewHolder(view) {
    val background: ConstraintLayout = view.findViewById(R.id.background)
    val textView: TextView = view.findViewById(R.id.textView)
}
