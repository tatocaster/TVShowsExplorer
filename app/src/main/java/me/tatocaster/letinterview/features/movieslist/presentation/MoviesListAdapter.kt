package me.tatocaster.letinterview.features.movieslist.presentation

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.florent37.glidepalette.GlidePalette
import kotlinx.android.synthetic.main.item_tv_show.view.*
import me.tatocaster.letinterview.R
import me.tatocaster.letinterview.entity.Pallete
import me.tatocaster.letinterview.entity.TvShow
import me.tatocaster.letinterview.utils.GlideApp
import org.joda.time.format.DateTimeFormat
import java.util.*

class MoviesListAdapter(private val context: Context,
                        private val listener: (Int, TvShow, Pallete) -> Unit,
                        private val similarShows: Boolean = false) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val tvShowsData = arrayListOf<TvShow>()
    private val TYPE_SIMILAR_SHOW = 1
    private val TYPE_SHOW = 2

    fun updateData(data: ArrayList<TvShow>) {
        when {
            tvShowsData.isNotEmpty() && (tvShowsData[0].name != data[0].name) -> {
                val tvShowsSize = tvShowsData.size
                tvShowsData.addAll(data)
                notifyItemRangeInserted(tvShowsSize, data.size)
            }
            else -> { // if refreshed and first items are the same
                tvShowsData.clear()
                tvShowsData.addAll(data)
                notifyDataSetChanged()
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (similarShows) TYPE_SIMILAR_SHOW else TYPE_SHOW
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TYPE_SIMILAR_SHOW)
            SimilarShowsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_similar_tv_show, parent, false))
        else
            ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_tv_show, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = tvShowsData[position]
        if (holder.itemViewType == TYPE_SIMILAR_SHOW)
            (holder as SimilarShowsViewHolder).bindView(item)
        else
            (holder as ViewHolder).bindView(position, item)
    }

    override fun getItemCount(): Int {
        return tvShowsData.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var palleteColor: Pallete = Pallete()

        fun bindView(position: Int, item: TvShow) {
            itemView.setOnClickListener({ _ ->
                listener(position, item, palleteColor)
            })

            val formatter = DateTimeFormat.forPattern("yyyy-MM-dd")
            val firstAirDate = formatter.parseLocalDate(item.firstAirDate)

            itemView.textViewTvShowTitle.text = item.name
            itemView.textViewTvShowDate.text = firstAirDate.year.toString()
            itemView.textViewTvShowVotes.text = item.voteAverage.toString()

            val url = "https://image.tmdb.org/t/p/w500/${item.posterPath}"
            GlideApp.with(context)
                    .load(url)
                    // prepare pallete before the details view will load
                    .listener(GlidePalette.with(url)
                            .intoCallBack({ pallete ->
                                val swatch = pallete?.darkMutedSwatch
                                palleteColor.bodyColor = swatch?.rgb ?: ContextCompat.getColor(context, R.color.colorPrimary)
                                palleteColor.textColor = swatch?.titleTextColor ?: ContextCompat.getColor(context, R.color.textColor)
                            })
                    )
                    .into(itemView.imageViewTvShowPoster)
        }

    }

    inner class SimilarShowsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(item: TvShow) {
            itemView.textViewTvShowTitle.text = item.name
            val url = "https://image.tmdb.org/t/p/w300/${item.posterPath}"
            GlideApp.with(context)
                    .load(url)
                    .into(itemView.imageViewTvShowPoster)
        }

    }
}