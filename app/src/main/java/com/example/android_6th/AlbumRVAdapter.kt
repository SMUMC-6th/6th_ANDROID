package com.example.android_6th

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.android_6th.databinding.ItemAlbumBinding

class AlbumRVAdapter(private val albumList: ArrayList<Album>): RecyclerView.Adapter<AlbumRVAdapter.ViewHolder>() {

    interface MyItemClickListener{
        fun onItemClick(album: Album)
        fun onRemoveAlbum(position: Int)
        fun onPlayAlbum(album: Album) //재생 버튼 클릭
    }


    private lateinit var mItemClickListener: MyItemClickListener

    //외부에서 전달 받을 수 있는 함수 (전달 받은 Listener 객체를 저장)
    fun setMyItemClickListener(itemClickListener: MyItemClickListener){
        mItemClickListener = itemClickListener
    }


    fun addItem(album: Album){
        albumList.add(album)
        notifyDataSetChanged() //데이터가 바뀌었다는 것을 알려주어야 함 (어뎁터는 모르기 때문)
    }
    fun removeItem(position: Int){
        albumList.removeAt(position)
        notifyDataSetChanged() //데이터가 바뀌었다는 것을 알려주어야 함 (어뎁터는 모르기 때문)
    }


    //사용하고자 하는 ItemView 객체를 만든 후 이를 재활용하기 위한 작업
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): AlbumRVAdapter.ViewHolder {

        // ItemView 객체 생성
        val binding: ItemAlbumBinding = ItemAlbumBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        // ItemView 객체 생성 후 재활용 하기 위해 ViewHoder에 전달
        return ViewHolder(binding)
    }


    //ViewHoder에 데이터를 바인딩 해줄 때마다 호출되는 함수
    override fun onBindViewHolder(holder: AlbumRVAdapter.ViewHolder, position: Int) {
        holder.bind(albumList[position])

        // 클릭 이벤트 (오늘의 발매를 클릭했을 때 AlbumFragment로 이동)
        // 어뎁터에서는 interface가 필요
        holder.itemView.setOnClickListener { mItemClickListener.onItemClick(albumList[position])}

        // 오늘의 발매 음악 재생 버튼 클릭 시 miniplayer 동기화 (3주차 미션)
        holder.binding.itemAlbumPlayImgIv.setOnClickListener {
            mItemClickListener.onItemClick(albumList[position])
        }

        // 앨범의 타이틀을 클릭 시 삭제됨
        //holder.binding.itemAlbumTitleTv.setOnClickListener { mItemClickListener.onRemoveAlbum(position)}
    }

    // data set 크기를 알려주는 함수
    override fun getItemCount(): Int = albumList.size

    // ItemAlbumBinding: ItemView객체들을 재활용하기 위한 그릇
    // 매개변수로 ItemView객체를 받음
    inner class ViewHolder(val binding: ItemAlbumBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(album: Album){
            binding.itemAlbumTitleTv.text = album.title
            binding.itemAlbumSingerTv.text = album.singer
            binding.itemAlbumCoverImgIv.setImageResource(album.coverImg!!)

            // Play 버튼 클릭시 이벤트 처리
            binding.itemAlbumPlayImgIv.setOnClickListener {
                mItemClickListener.onItemClick(album)
            }
        }
    }

}