package for35892.othr.de.xsiteme.main

import android.app.Application
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import for35892.othr.de.xsiteme.models.SiteStore
import for35892.othr.de.xsiteme.models.firebase.SiteFireStore

class MainApp : Application(), AnkoLogger {

    lateinit var sites: SiteStore

    override fun onCreate() {
        super.onCreate()
        sites = SiteFireStore(applicationContext)
        info("Site started")
    }
}