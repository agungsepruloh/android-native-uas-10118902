package com.example.trustwalletclone.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.trustwalletclone.databinding.WalkthroughItemBinding
import com.example.trustwalletclone.model.WalkthroughItem

class WalkthroughAdapter() :
    ListAdapter<WalkthroughItem, WalkthroughAdapter.ViewHolder>(WalkthroughDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class ViewHolder private constructor(private val binding: WalkthroughItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: WalkthroughItem) {
            binding.walkthroughItem = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = WalkthroughItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

class WalkthroughDiffCallback : DiffUtil.ItemCallback<WalkthroughItem>() {
    override fun areItemsTheSame(oldItem: WalkthroughItem, newItem: WalkthroughItem): Boolean {
        return oldItem.id == newItem.id
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: WalkthroughItem, newItem: WalkthroughItem): Boolean {
        return oldItem == newItem
    }

}
