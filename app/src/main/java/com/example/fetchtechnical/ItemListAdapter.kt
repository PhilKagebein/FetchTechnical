package com.example.fetchtechnical

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.fetchtechnical.databinding.ItemModelViewHolderBinding
import com.example.fetchtechnical.models.ItemModel

class ItemListAdapter(private val resources: Resources): ListAdapter<ItemModel, ItemListAdapter.ItemModelViewHolder>(DiffCallback()) {

    inner class ItemModelViewHolder(private val binding: ItemModelViewHolderBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(currentItem: ItemModel) {

            binding.apply {
                idTextView.text = resources.getString(R.string.item_id_text, currentItem.id.toString())
                listIdTextView.text = resources.getString(R.string.item_list_id_text, currentItem.listId.toString())
                nameTextView.text = resources.getString(R.string.item_name_text, currentItem.name)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemModelViewHolder {
        val binding = ItemModelViewHolderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemModelViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemModelViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class DiffCallback: DiffUtil.ItemCallback<ItemModel>() {

        override fun areContentsTheSame(oldItem: ItemModel, newItem: ItemModel): Boolean {
            return when {
                oldItem.id != newItem.id -> false
                oldItem.name != newItem.name -> false
                oldItem.listId != newItem.listId -> false
                else -> true
            }
        }

        override fun areItemsTheSame(oldItem: ItemModel, newItem: ItemModel): Boolean {
            return oldItem.id == newItem.id
        }
    }
}