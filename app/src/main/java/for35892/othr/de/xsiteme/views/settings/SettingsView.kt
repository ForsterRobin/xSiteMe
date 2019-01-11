package for35892.othr.de.xsiteme.views.settings

import android.os.Bundle
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
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        presenter = SettingsPresenter(this)

        presenter.doShowLoginData()
        presenter.doShowNumberOfSites()
        presenter.doShowNumberVisited()
        presenter.doShowNumberFavourites()
    }

    override fun onBackPressed() {
        presenter.doGoBack()
    }
}
