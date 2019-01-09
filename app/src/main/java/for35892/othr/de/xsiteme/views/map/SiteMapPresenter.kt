package for35892.othr.de.xsiteme.views.map

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.content_site_maps.*
import for35892.othr.de.xsiteme.helpers.readImageFromPath
import for35892.othr.de.xsiteme.models.SiteModel
import for35892.othr.de.xsiteme.views.BasePresenter

class SiteMapPresenter(view: SiteMapView): BasePresenter(view){

    fun doConfigureMap(map: GoogleMap) {
        map.uiSettings.setZoomControlsEnabled(true)
        map.setOnMarkerClickListener(view as SiteMapView)
        app.sites.findAll().forEach {
            val loc = LatLng(it.lat, it.lng)
            val options = MarkerOptions().title(it.title).position(loc)
            map.addMarker(options).tag = it
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, it.zoom))
        }
    }

    fun doOnMarkerClick(marker: Marker): Boolean{
        val site = marker.tag as SiteModel

        val lat = String.format("%.4f", site.lat).toDouble()
        val lng = String.format("%.4f", site.lng).toDouble()
        val coordinates = "($lat, $lng)"

        view!!.currentTitle.text = site.title
        view!!.currentDescription.text = site.description
        view!!.currentCoordinates.text = coordinates
        view!!.imageView.setImageBitmap(readImageFromPath(view as SiteMapView, site.image))
        return true
    }

}