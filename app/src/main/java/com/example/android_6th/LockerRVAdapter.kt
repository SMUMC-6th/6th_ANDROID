package com.example.android_6th

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.android_6th.databinding.ActivityMainBinding
import com.example.android_6th.databinding.ItemAlbumBinding
import com.example.android_6th.databinding.ItemLockerBinding

class LockerRVAdapter(private val lockerList:ArrayList<Locker>): RecyclerView.Adapter<LockerRVAdapter.ViewHolder>() {

    interface MyItemClickListener{
        //외부에서 클릭 이벤트 받을려면 리스너 객체 받아야 함.
        fun onItemClick()
        fun onRemoveLocker(position: Int)
    }

    private lateinit var mItemClickListener:MyItemClickListener
    fun setMyItemClickListener(itemClickListener: MyItemClickListener){
        mItemClickListener = itemClickListener
    }

    fun removeItem(position: Int){
        lockerList.removeAt(position)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): LockerRVAdapter.ViewHolder {
        val binding:ItemLockerBinding = ItemLockerBinding.inflate(LayoutInflater.from(viewGroup.context),viewGroup,false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LockerRVAdapter.ViewHolder, position: Int) {
        holder.bind(lockerList[position])
        /*holder.itemView.setOnClickListener { mItemClickListener.onItemClick() }*/
        holder.binding.itemAlbumMoreIv.setOnClickListener { mItemClickListener.onRemoveLocker(position) }
    }

    override fun getItemCount(): Int = lockerList.size
    inner class ViewHolder(val binding: ItemLockerBinding):RecyclerView.ViewHolder(binding.root){

        fun bind(locker: Locker){
            binding.itemAlbumTitleTv.text = locker.title
            binding.itemAlbumSingerTv.text = locker.singer
            binding.itemAlbumImgIv.setImageResource(locker.coverImg!!)
        }

    }

}
