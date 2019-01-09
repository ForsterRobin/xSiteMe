package for35892.othr.de.xsiteme.views.sitelist

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.*
import kotlinx.android.synthetic.main.activity_site_list.*
import for35892.othr.de.xsiteme.R
import for35892.othr.de.xsiteme.models.SiteModel
import for35892.othr.de.xsiteme.views.BaseView

class SiteListView : BaseView(), SiteListener {

    lateinit var presenter: SiteListPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_site_list)
        init(toolbarMain)

        presenter = initPresenter(SiteListPresenter(this)) as SiteListPresenter

        val layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        presenter.loadSites()
    }

    override fun showSites(sites: List<SiteModel>) {
        recyclerView.adapter = SiteAdapter(sites, this)
        recyclerView.adapter?.notifyDataSetChanged()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.item_add -> presenter.doAddSite()
            R.id.item_map -> presenter.doShowSitesMap()
            R.id.item_settings -> presenter.doShowSettings()
            R.id.item_logout -> presenter.doLogout()
            R.id.item_toggleFavourites -> presenter.toggleFavourites()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSiteClick(site: SiteModel) {
        presenter.doEditSite(site)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        presenter.loadSites()
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onBackPressed() {
        // in order to not be able to get back to the login screen
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        if (!presenter.favouritesShown) {
            menu.findItem(R.id.item_toggleFavourites).title = "Show Favourites"
        } else {
            menu.findItem(R.id.item_toggleFavourites).title = "Show all Sites"
        }
        return super.onPrepareOptionsMenu(menu)
    }
}