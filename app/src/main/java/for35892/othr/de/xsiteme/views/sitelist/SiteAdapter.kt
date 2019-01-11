package for35892.othr.de.xsiteme.views.sitelist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.card_site.view.*
import for35892.othr.de.xsiteme.R
import for35892.othr.de.xsiteme.models.SiteModel

interface SiteListener {
    fun onSiteClick(site: SiteModel)
}

class SiteAdapter constructor(private var sites: List<SiteModel>,
                                   private val listener: SiteListener) : androidx.recyclerview.widget.RecyclerView.Adapter<SiteAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        return MainHolder(LayoutInflater.from(parent.context).inflate(R.layout.card_site, parent, false))
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val site = sites[holder.adapterPosition]
        holder.bind(site, listener)
    }

    override fun getItemCount(): Int = sites.size

    class MainHolder constructor(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {

        fun bind(site: SiteModel, listener: SiteListener) {
            val lat = String.format("%.4f", site.lat).toDouble()
            val lng = String.format("%.4f", site.lng).toDouble()
            val coordinates = "($lat, $lng)"
            itemView.siteTitle.text = site.title
            itemView.description.text = site.description
            itemView.coordinates.text = coordinates
            //itemView.imageIcon.setImageBitmap(readImageFromPath(itemView.context, site.image))
            Glide.with(itemView.context).load(site.image).into(itemView.imageIcon);
            itemView.setOnClickListener { listener.onSiteClick(site) }
            if(site.visited) {
                itemView.visited.visibility = View.VISIBLE
            } else {
                itemView.visited.visibility = View.GONE
            }
        }
    }
}