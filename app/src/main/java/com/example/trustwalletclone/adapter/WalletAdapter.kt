package com.example.trustwalletclone.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.trustwalletclone.databinding.WalletItemBinding
import com.example.trustwalletclone.model.Wallet

class WalletAdapter(private val clickListener: WalletListener) :
    ListAdapter<Wallet, WalletAdapter.ViewHolder>(WallDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, clickListener)
    }

    class ViewHolder private constructor(private val binding: WalletItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Wallet, clickListener: WalletListener) {
            binding.wallet = item
            binding.walletListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = WalletItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

class WallDiffCallback : DiffUtil.ItemCallback<Wallet>() {
    override fun areItemsTheSame(oldItem: Wallet, newItem: Wallet): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Wallet, newItem: Wallet): Boolean {
        return oldItem == newItem
    }
}

class WalletListener(val clickListener: (walletName: Wallet) -> Unit) {
    fun onClick(wallet: Wallet) = clickListener(wallet)
}
