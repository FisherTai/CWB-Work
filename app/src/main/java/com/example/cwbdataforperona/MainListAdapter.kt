package com.example.cwbdataforperona

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.cwbdataforperona.databinding.ItemDataBinding
import com.example.cwbdataforperona.databinding.ItemImageBinding
import com.example.cwbdataforperona.mint.MintPOJO

class MainListAdapter(private val context: Context) :
    ListAdapter<MintPOJO, RecyclerView.ViewHolder>(MintDiffCallback()) {

    companion object {
        private const val TYPE_DATA_VIEW = 1
        private const val TYPE_IMAGE_VIEW = 2
    }

    private var imageCount = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return if (viewType == TYPE_IMAGE_VIEW) {
            ImageViewHolder(
                ItemImageBinding.inflate(layoutInflater, parent, false)
            )
        } else {
            DataViewHolder(
                ItemDataBinding.inflate(layoutInflater, parent, false)
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is DataViewHolder) {
            val timeBean = getItem(position)
            holder.binding.apply {
                tvStartTime.text = timeBean?.startTime
                tvEndTime.text = timeBean?.endTime
                val parameter = timeBean?.parameterName + timeBean?.parameterUnit
                tvParameter.text = parameter
            }
        } else {
            imageCount++
        }
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    override fun getItemViewType(position: Int): Int {
        return if(getItem(position).isImage) TYPE_IMAGE_VIEW else TYPE_DATA_VIEW
    }


    inner class DataViewHolder(val binding: ItemDataBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                val intent = Intent(context, SecondActivity::class.java)
                val content =
                    "${binding.tvStartTime.text}\n${binding.tvEndTime.text}\n${binding.tvParameter.text}"
                intent.putExtra("content", content)
                context.startActivity(intent)
            }
        }
    }

    inner class ImageViewHolder(binding: ItemImageBinding) : RecyclerView.ViewHolder(binding.root)

    class MintDiffCallback : DiffUtil.ItemCallback<MintPOJO>() {
        override fun areItemsTheSame(oldItem: MintPOJO, newItem: MintPOJO): Boolean {
            return oldItem.startTime == newItem.startTime
        }

        override fun areContentsTheSame(oldItem: MintPOJO, newItem: MintPOJO): Boolean {
            return oldItem == newItem
        }
    }
}