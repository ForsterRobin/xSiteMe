package for35892.othr.de.xsiteme.views.site

import android.content.Intent
import android.widget.CheckBox
import for35892.othr.de.xsiteme.R
import for35892.othr.de.xsiteme.helpers.showImagePicker
import for35892.othr.de.xsiteme.main.MainApp
import for35892.othr.de.xsiteme.models.Location
import for35892.othr.de.xsiteme.models.SiteModel
import for35892.othr.de.xsiteme.views.editlocation.EditLocationView
import org.jetbrains.anko.intentFor


class SitePresenter(val activity: SiteView) {

    val IMAGE_REQUEST = 1
    val LOCATION_REQUEST = 2

    var site = SiteModel()
    var location = Location(49.021273, 12.098629, 15f)
    var visited = true
    var app: MainApp
    var edit = false;

    init {
        app = activity.application as MainApp
        if (activity.intent.hasExtra("site_edit")) {
            edit = true
            site = activity.intent.extras.getParcelable<SiteModel>("site_edit")
            visited = site.visited
            activity.findViewById<CheckBox>(R.id.checkBox).isChecked = visited
            activity.showSite(site)
        }
    }

    fun doAddOrSave(title: String, description: String) {
        site.title = title
        site.description = description
        site.visited = visited
        if (edit) {
            app.sites.update(site)
        } else {
            app.sites.create(site)
        }
        activity.finish()
    }

    fun doCancel() {
        activity.finish()
    }

    fun doDelete() {
        app.sites.delete(site)
        activity.finish()
    }

    fun doSelectImage() {
        showImagePicker(activity, IMAGE_REQUEST)
    }

    fun doSetLocation() {
        if (site.zoom != 0f) {
            location.lat = site.lat
            location.lng = site.lng
            location.zoom = site.zoom
        }
        activity.startActivityForResult(
            activity.intentFor<EditLocationView>().putExtra("location", location),
            LOCATION_REQUEST
        )
    }

    fun doChangeVisited() {
        site.visited = !visited
    }

    fun doActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        when (requestCode) {
            IMAGE_REQUEST -> {
                site.image = data.data.toString()
            }
            LOCATION_REQUEST -> {
                location = data.extras.getParcelable<Location>("location")
                site.lat = location.lat
                site.lng = location.lng
                site.zoom = location.zoom
            }
        }
    }
}