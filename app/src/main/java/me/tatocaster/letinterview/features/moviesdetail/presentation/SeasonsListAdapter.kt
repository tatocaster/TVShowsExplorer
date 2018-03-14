package me.tatocaster.letinterview.features.moviesdetail.presentation

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.florent37.glidepalette.GlidePalette
import kotlinx.android.synthetic.main.item_season.view.*
import me.tatocaster.letinterview.R
import me.tatocaster.letinterview.entity.Season
import me.tatocaster.letinterview.utils.GlideApp
import org.joda.time.format.DateTimeFormat

class SeasonsListAdapter(private val context: Context, private val seasonsData: List<Season>) : RecyclerView.Adapter<SeasonsListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_season, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = seasonsData[position]
        holder.bindView(item)
    }

    override fun getItemCount(): Int {
        return seasonsData.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(item: Season) {
            val formatter = DateTimeFormat.forPattern("yyyy-MM-dd")

            if (item.airDate != null) {
                val firstAirDate = formatter.parseLocalDate(item.airDate)
                itemView.seasonAired.text = firstAirDate.year.toString()
            }
            itemView.seasonNumber.text = "# ${item.seasonNumber}"
            itemView.episodesCount.text = "Episodes ${item.episodeCount}"

            val url = "https://image.tmdb.org/t/p/w300/${item.posterPath}"
            GlideApp.with(context)
                    .load(url)
                    .listener(GlidePalette.with(url)
                            .intoCallBack({ pallete ->
                                val swatch = pallete?.vibrantSwatch
                                itemView.textsWrapper.setBackgroundColor(swatch?.rgb
                                        ?: ContextCompat.getColor(context, R.color.colorPrimary))
                            })
                    )
                    .into(itemView.seasonPoster)
        }

    }
}
