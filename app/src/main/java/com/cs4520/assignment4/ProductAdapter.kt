package com.cs4520.assignment4

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cs4520.assignment4.databinding.FragmentItemViewBinding
import com.cs4520.assignment4.databinding.FragmentProductListBinding

class ProductAdapter(
    private val products: List<Product>,
) : RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            FragmentItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = products[position]
        holder.bind(product)
    }

    override fun getItemCount(): Int = products.size

    class ViewHolder(private val binding: FragmentItemViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {

            if (product.expiryDate != null) {
                binding.date.visibility = View.VISIBLE
            } else {
                binding.date.visibility = View.GONE
            }

            when (product.type) {
                "Equipment" -> {
                    binding.root.setBackgroundColor(Color.parseColor("#E06666"))
                    binding.imageview.setImageResource(R.drawable.equipment)
                }

                "Food" -> {
                    binding.root.setBackgroundColor(Color.parseColor("#FFD965"))
                    binding.imageview.setImageResource(R.drawable.food)
                }
            }

            binding.name.text = product.name
            binding.date.text = product.expiryDate
            binding.price.text = String.format("%.2f", product.price)

        }
    }
}