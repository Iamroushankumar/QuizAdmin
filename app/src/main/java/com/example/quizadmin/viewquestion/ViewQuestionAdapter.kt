package com.example.quizadmin.viewquestion

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.quizadmin.Quiz
import com.example.quizadmin.R
import kotlinx.android.synthetic.main.question_item.view.*

class ViewQuestionAdapter(val dlist:List<Quiz>,val listener:OnQuestionListner) : RecyclerView.Adapter<ViewQuestionAdapter.ViewHolder>() {
    val list = mutableListOf<Quiz>()


    fun update( dlist: MutableList<Quiz>){
        list.clear()
        list.addAll(dlist)
        notifyDataSetChanged()
    }
    class ViewHolder (val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.question_item,parent,false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val quiz:Quiz=list[position]
        holder.view.tv_question_questionitem.text=quiz.question
        holder.view.tv1_questioniten.text=quiz.option1
        holder.view.tv2_questionitem.text=quiz.option2
        holder.view.tv3_questionitem.text=quiz.option3
        holder.view.tv_correcr_questionitem.text=quiz.correctans.toString()
        holder.view.iv_delete_questionitem.setOnClickListener({
            listener.onQuestionDelete(quiz.id)
        })
        holder.view.iv_edit_questionitem.setOnClickListener({
            listener.onQuestionDelete(quiz.id
            )
        })

    }

    override fun getItemCount(): Int =list.size
}