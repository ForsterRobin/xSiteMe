package for35892.othr.de.xsiteme.views

import android.content.Intent
import android.os.Parcelable
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import for35892.othr.de.xsiteme.models.SiteModel
import for35892.othr.de.xsiteme.views.editlocation.EditLocationView
import for35892.othr.de.xsiteme.views.login.LoginView
import for35892.othr.de.xsiteme.views.map.SiteMapView
import for35892.othr.de.xsiteme.views.settings.SettingsView
import for35892.othr.de.xsiteme.views.site.SiteView
import for35892.othr.de.xsiteme.views.sitelist.SiteListView
import org.jetbrains.anko.AnkoLogger

val IMAGE_REQUEST = 1
val LOCATION_REQUEST = 2

enum class VIEW {
    LOCATION, SITE, MAP, LIST, SETTINGS, LOGIN
}

open abstract class BaseView() : AppCompatActivity(), AnkoLogger {

    var basePresenter: BasePresenter? = null

    fun navigateTo(view: VIEW, code: Int = 0, key: String = "", value: Parcelable? = null) {
        var intent = Intent(this, SiteListView::class.java)
        when (view) {
            VIEW.LOCATION -> intent = Intent(this, EditLocationView::class.java)
            VIEW.SITE -> intent = Intent(this, SiteView::class.java)
            VIEW.MAP -> intent = Intent(this, SiteMapView::class.java)
            VIEW.LIST -> intent = Intent(this, SiteListView::class.java)
            VIEW.SETTINGS -> intent = Intent(this, SettingsView::class.java)
            VIEW.LOGIN -> intent = Intent(this, LoginView::class.java)
        }
        if (key != "") {
            intent.putExtra(key, value)
        }
        startActivityForResult(intent, code)
    }

    fun initPresenter(presenter: BasePresenter): BasePresenter {
        basePresenter = presenter
        return presenter
    }

    fun init(toolbar: Toolbar) {
        toolbar.title = title
        setSupportActionBar(toolbar)
    }

    override fun onDestroy() {
        basePresenter?.onDestroy()
        super.onDestroy()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) {
            basePresenter?.doActivityResult(requestCode, resultCode, data)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        basePresenter?.doRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    open fun showSite(site: SiteModel) {}
    open fun showSites(sites: List<SiteModel>) {}
    open fun showProgress() {}
    open fun hideProgress() {}
}