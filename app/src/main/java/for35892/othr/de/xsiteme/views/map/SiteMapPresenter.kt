package for35892.othr.de.xsiteme.views.map

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.content_site_maps.*
import for35892.othr.de.xsiteme.helpers.readImageFromPath
import for35892.othr.de.xsiteme.main.MainApp

class SiteMapPresenter(val view: SiteMapsView) {

    var app: MainApp

    init {
        app = view.application as MainApp
    }

    fun doConfigureMap(map: GoogleMap) {
        map.uiSettings.setZoomControlsEnabled(true)
        map.setOnMarkerClickListener(view)
        app.sites.findAll().forEach {
            val loc = LatLng(it.lat, it.lng)
            val options = MarkerOptions().title(it.title).position(loc)
            map.addMarker(options).tag = it.id
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, it.zoom))
        }
    }

    fun doOnMarkerClick(marker: Marker): Boolean{
        val tag = marker.tag as Long
        val site = app.sites.findById(tag)
        view.currentTitle.text = site!!.title
        view.currentDescription.text = site!!.description
        view.imageView.setImageBitmap(readImageFromPath(view, site.image))
        return true
    }

}