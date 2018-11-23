package for35892.othr.de.xsiteme.views.settings

import for35892.othr.de.xsiteme.main.MainApp
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsPresenter(val activity: SettingsView) {

    var app: MainApp

    init {
        app = activity.application as MainApp
    }

    fun doGoBack() {
        activity.finish()
    }

    fun doShowNumberOfSites() {
        var count = app.sites.findAll().count()
        activity.numberofsitesvalue.text = count.toString()
    }

    fun doShowNumberVisited() {
        var count = app.sites.findAll().filter { it.visited }.count()
        activity.numbervisitedvalue.text = count.toString()
    }
}