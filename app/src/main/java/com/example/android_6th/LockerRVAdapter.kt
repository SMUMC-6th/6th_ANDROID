package com.example.android_6th

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.android_6th.databinding.ItemLockerBinding

class LockerRVAdapter(private val albumList: ArrayList<Locker>): RecyclerView.Adapter<LockerRVAdapter.ViewHolder>() {

    interface MyItemClickListener{
        //fun onItemClick(album: Album)
        fun onRemoveSong(position: Int)
    }

    // 외부에서 전달받은 Listener 객체를 Adapter에서 사용할 수 있도록 따로 저장할 변수 선언
    private lateinit var mItemClickListener: MyItemClickListener
    fun setMyItemClickListener(itemClickListener: MyItemClickListener){
        mItemClickListener = itemClickListener
    }

    // Item 데이터 삭제
    fun removeItem(position: Int){
        albumList.removeAt(position)
        notifyDataSetChanged() //데이터 바뀜을 알리는 함수
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): LockerRVAdapter.ViewHolder {
        //itemview 객체 생성
        val binding: ItemLockerBinding = ItemLockerBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LockerRVAdapter.ViewHolder, position: Int) { //position = indexID
        holder.bind(albumList[position])

        // 타이틀 클릭 시 해당데이터 삭제
        holder.binding.itemLockerPlayerMoreImgIv.setOnClickListener { mItemClickListener.onRemoveSong(position) }

        // toggle swtich 선택 or 미선택
        holder.binding.itemLockerToggleOff.setOnClickListener{ holder.Switch(isSwitch = true ) }
        holder.binding.itemLockerToggleOn.setOnClickListener{ holder.Switch(isSwitch = false ) }
    }


    //Dataset 크기를 알려주는 함수
    override fun getItemCount(): Int = albumList.size

    inner class ViewHolder(val binding: ItemLockerBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(album: Locker){
            binding.itemLockerTitleTv.text = album.title
            binding.itemLockerSingerTv.text = album.singer
            binding.itemLockerCoverImgIv.setImageResource(album.coverImg!!)
        }

        fun Switch(isSwitch: Boolean){
            if(isSwitch){
                binding.itemLockerToggleOff.visibility = View.GONE
                binding.itemLockerToggleOn.visibility = View.VISIBLE
            }
            else{
                binding.itemLockerToggleOff.visibility = View.VISIBLE
                binding.itemLockerToggleOn.visibility = View.GONE
            }
        }
    }
}