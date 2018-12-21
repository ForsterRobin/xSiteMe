package for35892.othr.de.xsiteme.views.settings

import for35892.othr.de.xsiteme.views.BasePresenter
import for35892.othr.de.xsiteme.views.BaseView
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsPresenter(view: BaseView): BasePresenter(view) {

    fun doGoBack() {
        view?.finish()
    }

    fun doShowLoginData() {
        view!!.emailvalue.text = app.emailAdress
        view!!.passwordvalue.text = app.password
    }

    fun doShowNumberOfSites() {
        var count = app.sites.findAll().count()
        view!!.numberofsitesvalue.text = count.toString()
    }

    fun doShowNumberVisited() {
        var count = app.sites.findAll().filter { it.visited }.count()
        view!!.numbervisitedvalue.text = count.toString()
    }
}