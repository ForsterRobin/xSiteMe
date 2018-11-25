package for35892.othr.de.xsiteme.views.settings

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import for35892.othr.de.xsiteme.R
import for35892.othr.de.xsiteme.models.SiteModel
import for35892.othr.de.xsiteme.views.BaseView
import kotlinx.android.synthetic.main.activity_settings.*
import org.jetbrains.anko.AnkoLogger

class SettingsView : BaseView(), AnkoLogger {
    lateinit var presenter: SettingsPresenter
    var site = SiteModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        toolbarSettings.title = title
        setSupportActionBar(toolbarSettings)

        presenter = SettingsPresenter(this)

        presenter.doShowNumberOfSites()
        presenter.doShowNumberVisited()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_settings, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.item_back -> {
                presenter.doGoBack()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        presenter.doGoBack()
    }
}
