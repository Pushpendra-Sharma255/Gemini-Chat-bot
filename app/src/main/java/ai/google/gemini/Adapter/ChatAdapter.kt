package ai.google.gemini.Adapter

import ai.google.gemini.Models.ChatModel
import ai.google.gemini.databinding.ChatAdapterItemBinding
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class ChatAdapter(private val chatItems: ArrayList<ChatModel>) : Adapter<ChatAdapter.Holder>() {
    class Holder(val binding: ChatAdapterItemBinding) : ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding =
            ChatAdapterItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun getItemCount(): Int {
        Log.d("Chat_Size", chatItems.size.toString())
        return chatItems.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val item = chatItems[position]
        holder.binding.llGemini.visibility = View.GONE
        holder.binding.llUser.visibility = View.GONE
        if (item.isGeminiText) {
            holder.binding.llGemini.visibility = View.VISIBLE
            holder.binding.tvGemini.text = item.text
        } else {
            holder.binding.llUser.visibility = View.VISIBLE
            holder.binding.tvUser.text = item.text
        }
    }
}