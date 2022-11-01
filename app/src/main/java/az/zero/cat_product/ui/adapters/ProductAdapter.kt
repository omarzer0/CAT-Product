package az.zero.cat_product.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import az.zero.cat_product.R
import az.zero.cat_product.databinding.ItemProductBinding
import az.zero.cat_product.models.Product
import com.bumptech.glide.Glide

class ProductAdapter : ListAdapter<Product, ProductAdapter.ProductViewHolder>(ProductDiffUtils) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ItemProductBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )

        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }


    inner class ProductViewHolder(private val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(product: Product) {
            binding.apply {

                Glide.with(ivProductImage.context)
                    .load(product.productImage)
                    .placeholder(R.drawable.ic_launcher_background)
                    .into(ivProductImage)

                tvProductTitle.text = product.title
                tvProductPrice.text = "$${product.price}"
            }
        }
    }

    object ProductDiffUtils : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product) = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Product, newItem: Product) = oldItem == newItem

    }

}