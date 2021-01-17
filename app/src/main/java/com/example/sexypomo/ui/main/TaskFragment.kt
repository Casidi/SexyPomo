package com.example.sexypomo.ui.main

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.ShapeDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.sexypomo.R
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [TaskFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TaskFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }


    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_task, container, false)
        val recyclerView = root.findViewById(R.id.recyclerView) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)
        val memberList: MutableList<Member> = ArrayList()
        memberList.add(Member(1, R.drawable.bike, "白沙屯海灘1"))
        memberList.add(Member(2, R.drawable.pencil, "白沙屯海灘2"))
        memberList.add(Member(3, android.R.drawable.ic_dialog_email, "白沙屯海灘3"))
        memberList.add(Member(4, android.R.drawable.ic_dialog_email, "白沙屯海灘4"))
        memberList.add(Member(5, android.R.drawable.ic_dialog_email, "白沙屯海灘5"))
        memberList.add(Member(6, android.R.drawable.ic_dialog_email, "白沙屯3"))
        memberList.add(Member(7, android.R.drawable.ic_dialog_email, "後龍1"))
        memberList.add(Member(8, android.R.drawable.ic_dialog_email, "後龍2"))
        recyclerView.adapter = this.context?.let { MemberAdapter(it, memberList) }
        return root
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                TaskFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
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

        holder.textId.setText(java.lang.String.valueOf(member.id))
        holder.textName.setText(member.name)
        holder.itemView.setOnClickListener {
            val imageView = ImageView(context)
            imageView.setImageResource(member.image)
            val toast = Toast(context)
            toast.setView(imageView)
            toast.duration = Toast.LENGTH_SHORT
            toast.show()
        }
    }

    override fun getItemCount(): Int {
        return memberList.size
    }

    //Adapter 需要一個 ViewHolder，只要實作它的 constructor 就好，保存起來的view會放在itemView裡面
    internal inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var imageId: ImageView
        var textId: TextView
        var textName: TextView

        init {
            imageId = itemView.findViewById<View>(R.id.imageId) as ImageView
            textId = itemView.findViewById<View>(R.id.textId) as TextView
            textName = itemView.findViewById<View>(R.id.textName) as TextView
        }
    }

    init {
        this.context = context
        this.memberList = memberList
    }
}

class Member {
    var id = 0
    var image = 0
    var name: String? = null

    constructor() : super() {}
    constructor(id: Int, image: Int, name: String?) : super() {
        this.id = id
        this.image = image
        this.name = name
    }

}