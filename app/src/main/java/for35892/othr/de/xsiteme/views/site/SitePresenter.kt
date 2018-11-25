package for35892.othr.de.xsiteme.views.site

import android.content.Intent
import android.widget.CheckBox
import for35892.othr.de.xsiteme.R
import for35892.othr.de.xsiteme.helpers.showImagePicker
import for35892.othr.de.xsiteme.main.MainApp
import for35892.othr.de.xsiteme.models.Location
import for35892.othr.de.xsiteme.models.SiteModel
import for35892.othr.de.xsiteme.views.BasePresenter
import for35892.othr.de.xsiteme.views.BaseView
import for35892.othr.de.xsiteme.views.VIEW


class SitePresenter(view: BaseView): BasePresenter(view) {

    val IMAGE_REQUEST = 1
    val LOCATION_REQUEST = 2

    var site = SiteModel()
    var defaultLocation = Location(49.021273, 12.098629, 15f)
    var visited = true
    var edit = false;

    init {
        app = view.application as MainApp
        if (view.intent.hasExtra("site_edit")) {
            edit = true
            site = view.intent.extras.getParcelable<SiteModel>("site_edit")
            visited = site.visited
            view.findViewById<CheckBox>(R.id.checkBox).isChecked = visited
            view.showSite(site)
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
        view?.finish()
    }

    fun doCancel() {
        view?.finish()
    }

    fun doDelete() {
        app.sites.delete(site)
        view?.finish()
    }

    fun doSelectImage() {
        showImagePicker(view!!, IMAGE_REQUEST)
    }

    fun doSetLocation() {

        if (!edit) {
            view?.navigateTo(VIEW.LOCATION, LOCATION_REQUEST, "location", defaultLocation)
        } else {
            view?.navigateTo(VIEW.LOCATION, LOCATION_REQUEST, "location", Location(site.lat, site.lng, site.zoom))
        }
    }

    fun doChangeVisited() {
        site.visited = !visited
    }

    override fun doActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        when (requestCode) {
            IMAGE_REQUEST -> {
                site.image = data.data.toString()
            }
            LOCATION_REQUEST -> {
                val location = data.extras.getParcelable<Location>("location")
                site.lat = location.lat
                site.lng = location.lng
                site.zoom = location.zoom
            }
        }
    }
}