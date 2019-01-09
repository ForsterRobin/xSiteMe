package for35892.othr.de.xsiteme.views.site

import android.content.Intent
import android.widget.RatingBar
import for35892.othr.de.xsiteme.R.id.siteImage
import for35892.othr.de.xsiteme.helpers.readImageFromPath
import for35892.othr.de.xsiteme.helpers.showImagePicker
import for35892.othr.de.xsiteme.main.MainApp
import for35892.othr.de.xsiteme.models.Location
import for35892.othr.de.xsiteme.models.SiteModel
import for35892.othr.de.xsiteme.views.BasePresenter
import for35892.othr.de.xsiteme.views.BaseView
import for35892.othr.de.xsiteme.views.VIEW
import kotlinx.android.synthetic.main.activity_site.*
import java.text.SimpleDateFormat


class SitePresenter(view: BaseView): BasePresenter(view) {

    val IMAGE_REQUEST = 1
    val LOCATION_REQUEST = 2

    var site = SiteModel()
    var defaultLocation = Location(49.021273, 12.098629, 15f)
    var visited = false
    var dateVisited = ""
    var favourite = false
    var rating = 0f
    var edit = false;

    init {
        app = view.application as MainApp
        if (view.intent.hasExtra("site_edit")) {
            edit = true
            site = view.intent.extras.getParcelable<SiteModel>("site_edit")
            favourite = site.favourite
            visited = site.visited
            dateVisited = site.dateVisited
            rating = site.rating
            view.showSite(site)
        }
        view.checkBoxFavourite.isChecked = favourite
        view.checkBoxVisited.isChecked = visited
        view.dateVisited.text = dateVisited
        view.ratingBar.rating = rating
    }

    fun doAddOrSave(title: String, description: String, additionalNotes: String) {
        site.title = title
        site.description = description
        site.additionalNotes = additionalNotes
        site.visited = visited
        site.dateVisited = dateVisited
        site.favourite = favourite
        site.rating = rating
        site.additionalNotes = additionalNotes
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
        visited = !visited
        site.visited = visited
        if(visited) {
            val sdf = SimpleDateFormat("dd/MM/yyyy")
            dateVisited = "on " + sdf.format(System.currentTimeMillis())
            view!!.dateVisited.text = dateVisited
        } else {
            view!!.dateVisited.text = ""
        }
    }

    fun doChangeFavourite() {
        favourite = !favourite
        site.favourite = favourite
    }

    fun doChangeRating(newRating: Float) {
        rating = newRating
    }

    override fun doActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        when (requestCode) {
            IMAGE_REQUEST -> {
                site.image = data.data.toString()
                view?.showImage(site)}
            LOCATION_REQUEST -> {
                val location = data.extras.getParcelable<Location>("location")
                site.lat = location.lat
                site.lng = location.lng
                site.zoom = location.zoom
            }
        }
    }

    fun onRatingChanged(p0: RatingBar?, p1: Float, p2: Boolean) {

    }
}