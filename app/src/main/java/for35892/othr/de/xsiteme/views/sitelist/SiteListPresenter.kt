package for35892.othr.de.xsiteme.views.sitelist

import for35892.othr.de.xsiteme.main.MainApp
import for35892.othr.de.xsiteme.models.SiteModel
import for35892.othr.de.xsiteme.views.map.SiteMapsView
import for35892.othr.de.xsiteme.views.site.SiteView
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.startActivityForResult


class SiteListPresenter(val activity: SiteListView) {

    var app: MainApp

    init {
        app = activity.application as MainApp
    }

    fun getSites() = app.sites.findAll()

    fun doAddSite() {
        activity.startActivityForResult<SiteView>(0)
    }

    fun doEditSite(site: SiteModel) {
        activity.startActivityForResult(activity.intentFor<SiteView>().putExtra("site_edit", site), 0)
    }

    fun doShowSitesMap() {
        activity.startActivity<SiteMapsView>()
    }
}