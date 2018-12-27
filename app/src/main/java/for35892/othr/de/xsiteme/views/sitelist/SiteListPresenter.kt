package for35892.othr.de.xsiteme.views.sitelist

import for35892.othr.de.xsiteme.models.SiteModel
import for35892.othr.de.xsiteme.views.BasePresenter
import for35892.othr.de.xsiteme.views.BaseView
import for35892.othr.de.xsiteme.views.VIEW


class SiteListPresenter(view: BaseView): BasePresenter(view) {

    fun doAddSite() {
        view?.navigateTo(VIEW.SITE)
    }

    fun doEditSite(site: SiteModel) {
        view?.navigateTo(VIEW.SITE, 0, "site_edit", site)
    }

    fun doShowSitesMap() {
        view?.navigateTo(VIEW.MAP)
    }

    fun doShowSettings() {
        view?.navigateTo(VIEW.SETTINGS)
    }

    fun doShowLogin() {
        view?.navigateTo(VIEW.LOGIN)
    }

    fun loadSites() {
        view?.showSites(app.sites.findAll())
    }
}