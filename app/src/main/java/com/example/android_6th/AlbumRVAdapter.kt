package com.example.android_6th

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.android_6th.databinding.ItemAlbumBinding
import com.google.gson.Gson

class AlbumRVAdapter(private val albumList: ArrayList<Album>): RecyclerView.Adapter<AlbumRVAdapter.ViewHolder>() {

    interface MyItemClickListener{
        fun onItemClick(album: Album)
        fun onRemoveAlbum(position: Int)

        // player img 클릭 시 함수
        fun onPlayerClick(position: Int)
    }

    // 외부에서 전달받은 Listener 객체를 Adapter에서 사용할 수 있도록 따로 저장할 변수 선언
    private lateinit var mItemClickListener: MyItemClickListener
    fun setMyItemClickListener(itemClickListener: MyItemClickListener){
        mItemClickListener = itemClickListener
    }

    // Item 데이터 추가 삭제 및 수정
    fun addItem(album: Album){
        albumList.add(album)
        notifyDataSetChanged() //데이터 바뀜을 알리는 함수
    }
    fun removeItem(position: Int){
        albumList.removeAt(position)
        notifyDataSetChanged() //데이터 바뀜을 알리는 함수
    }

    // Item 미니플레이어 전환
    fun changeMiniPlayer(position: Int){
        var song = Song()
        song.title = albumList[position].title.toString()
        song.singer = albumList[position].singer.toString()
        Log.d("selectsong", song.title)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): AlbumRVAdapter.ViewHolder {
        //itemview 객체 생성
        val binding: ItemAlbumBinding = ItemAlbumBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AlbumRVAdapter.ViewHolder, position: Int) { //position = indexID
        holder.bind(albumList[position])

        //Fragement 전환
        holder.itemView.setOnClickListener{ mItemClickListener.onItemClick(albumList[position]) }

        // play버튼 클릭 시 미니플레이어 가수, 제목 변경
        holder.binding.itemAlbumPlayImgIv.setOnClickListener{ mItemClickListener.onPlayerClick(position) }

        // 타이틀 클릭 시 해당데이터 삭제
//        holder.binding.itemAlbumTitleTv.setOnClickListener { mItemClickListener.onRemoveAlbum(position) }
    }

    //Dataset 크기를 알려주는 함수
    override fun getItemCount(): Int = albumList.size

    inner class ViewHolder(val binding: ItemAlbumBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(album: Album){
            binding.itemAlbumTitleTv.text = album.title
            binding.itemAlbumSingerTv.text = album.singer
            binding.itemAlbumCoverImgIv.setImageResource(album.coverImg!!)
        }
    }
}