package com.example.android_6th

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import androidx.constraintlayout.helper.widget.Carousel.Adapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android_6th.databinding.ItemAlbumBinding

class AlbumRVAdapter(private val albumList: ArrayList<Album>): RecyclerView.Adapter<AlbumRVAdapter.ViewHolder>() {
    //강의 클릭이벤트 2붘 30초? 이게 뭔말임
    interface MyitemClickListener{
        fun onItemClick(album: Album)
        fun onRemoveAlbum(position: Int)

    }
    //외부에서 클릭이벤트 사용 위해 리스너 객체 받아야 함
    private  lateinit var  mIClickListener: MyitemClickListener
    fun setMyitemClickListener(itemClickListener: MyitemClickListener){
        mIClickListener = itemClickListener

    }

    fun addItem(album: Album){
        albumList.add(album)
        notifyDataSetChanged()
    }

    fun removeItem(position: Int){
        albumList.removeAt(position)
        notifyDataSetChanged()
    }

    //뷰 홀더 생성해줘야 할 떄 호출되는 함수임
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): AlbumRVAdapter.ViewHolder {
        val binding: ItemAlbumBinding = ItemAlbumBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ViewHolder(binding)
    }
// 데이터가 바인딩 될 때 마다 호출되는 함수, 클릭 이벤트는 주로 여기서 처리함 position 값을 가지고 있어서 그럼
    override fun onBindViewHolder(holder: AlbumRVAdapter.ViewHolder, position: Int) {
        holder.bind(albumList[position]) //bind함수가 포지션에서 받아온 값을 객체에 넣어준다.
        holder.itemView.setOnClickListener { mIClickListener.onItemClick(albumList[position])}
        /*holder.binding.itemAlbumTitleTv.setOnClickListener { mIClickListener.onRemoveAlbum(position) }*/

    }

    override fun getItemCount(): Int = albumList.size

    inner class ViewHolder(val binding: ItemAlbumBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(album: Album) {
            binding.itemAlbumTitleTv.text = album.title
            binding.itemAlbumSingerTv.text = album.singer
            binding.itemAlbumCoverImgIv.setImageResource(album.coverImg!!)


        }

    }
}





















