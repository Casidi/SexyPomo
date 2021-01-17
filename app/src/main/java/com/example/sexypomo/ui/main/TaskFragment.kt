package com.example.sexypomo.ui.main

import android.content.Context
import android.content.DialogInterface
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.ShapeDrawable
import com.google.android.material.floatingactionbutton.FloatingActionButton
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.example.sexypomo.R
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.add_task_dialog.*

class TaskFragment : Fragment() {
    val memberList: MutableList<Member> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_task, container, false)

        val recyclerView = root.findViewById(R.id.recyclerView) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)

        memberList.add(Member(R.drawable.bike, "Exercise"))
        memberList.add(Member(R.drawable.pencil, "Work"))
        recyclerView.adapter = this.context?.let { MemberAdapter(it, memberList) }

        val addTaskButton = root.findViewById(R.id.fabAddTask) as FloatingActionButton
        addTaskButton.setOnClickListener {
            val builder = context?.let { it1 -> AlertDialog.Builder(it1) }

            val inflater = requireActivity().layoutInflater;
            val dialogView = inflater.inflate(R.layout.add_task_dialog, null)
            builder?.apply {
                setView(dialogView)
                setTitle("New task")
                setPositiveButton("Add", DialogInterface.OnClickListener { dialog, which ->
                    val editText = dialogView.findViewById<EditText>(R.id.newTaskName)
                    if(editText.text.isEmpty()) {
                        val toast = Toast(context)
                        toast.setText("Task name shouldn't be empty.")
                        toast.duration = Toast.LENGTH_SHORT
                        toast.show()
                    } else {
                        memberList.add(Member(R.drawable.pencil, editText.text.toString()))
                        //recyclerView.adapter?.notifyItemInserted(memberList.size - 1)
                    }
                })
            }
            val dialog: AlertDialog? = builder?.create()
            dialog?.show()
        }
        return root
    }
}

private class MemberAdapter internal constructor(
    context: Context,
    memberList: List<Member>
) :
    RecyclerView.Adapter<MemberAdapter.ViewHolder>() {
    private val context: Context
    private val memberList: List<Member>
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view: View =
            LayoutInflater.from(context).inflate(R.layout.sample_task_item_card_view, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val member: Member = memberList[position]
        holder.imageId.setImageResource(member.image)

        //set task icon background color
        if(holder.imageId.background is ShapeDrawable) {
            val shapeDrawable = holder.imageId.background as ShapeDrawable
            shapeDrawable.paint.color = 0xffdd0000.toInt()
        } else if (holder.imageId.background is ColorDrawable) {
            val colorDrawable = holder.imageId.background as ColorDrawable
            colorDrawable.color = 0xffdd0000.toInt()
        } else if(holder.imageId.background is GradientDrawable) {
            val gradientDrawable = holder.imageId.background as GradientDrawable
            gradientDrawable.setColor(0xffdd0000.toInt())
        }

        holder.textName.setText(member.name)
        holder.itemView.setOnClickListener {
            //TODO: set current task and switch to timer tab
        }
    }

    override fun getItemCount(): Int {
        return memberList.size
    }

    //Adapter 需要一個 ViewHolder，只要實作它的 constructor 就好，保存起來的view會放在itemView裡面
    internal inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var imageId: ImageView
        var textName: TextView

        init {
            imageId = itemView.findViewById<View>(R.id.imageId) as ImageView
            textName = itemView.findViewById<View>(R.id.textName) as TextView
        }
    }

    init {
        this.context = context
        this.memberList = memberList
    }
}

class Member {
    var image = 0
    var name: String? = null

    constructor(image: Int, name: String?) : super() {
        this.image = image
        this.name = name
    }

}