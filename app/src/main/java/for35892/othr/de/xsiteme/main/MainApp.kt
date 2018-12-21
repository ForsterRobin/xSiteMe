package for35892.othr.de.xsiteme.main

import android.app.Application
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import for35892.othr.de.xsiteme.models.SiteStore
import xsiteme.models.SiteJSONStore

class MainApp : Application(), AnkoLogger {

    lateinit var sites: SiteStore
    lateinit var emailAdress: String
    lateinit var password: String       //TODO: Email and pw needs to be stored somewhere else

    override fun onCreate() {
        super.onCreate()
        sites = SiteJSONStore(applicationContext)
        info("Site started")
    }
}