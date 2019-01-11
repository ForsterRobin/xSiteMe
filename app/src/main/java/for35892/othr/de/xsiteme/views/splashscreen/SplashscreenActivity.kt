package for35892.othr.de.xsiteme.views.splashscreen

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import for35892.othr.de.xsiteme.R
import for35892.othr.de.xsiteme.views.login.LoginView

class SplashscreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)

        val background = object : Thread() {
            override fun run() {
                try{
                    Thread.sleep(5000)

                    val intent = Intent(baseContext, LoginView::class.java)
                    startActivity(intent)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
        background.start()
    }
}