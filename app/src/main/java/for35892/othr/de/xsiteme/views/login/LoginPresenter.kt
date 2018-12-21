package for35892.othr.de.xsiteme.views.login

import android.content.Intent
import android.view.View
import android.widget.CheckBox
import for35892.othr.de.xsiteme.R
import for35892.othr.de.xsiteme.helpers.showImagePicker
import for35892.othr.de.xsiteme.main.MainApp
import for35892.othr.de.xsiteme.models.Location
import for35892.othr.de.xsiteme.models.SiteModel
import for35892.othr.de.xsiteme.views.*
import kotlinx.android.synthetic.main.activity_login.*

class LoginPresenter(view: BaseView) : BasePresenter(view) {
    init {
        app = view.application as MainApp
    }

    fun doLogin(emailAdress: String, password: String) {


        if(password == "pw123") { //TODO: serious pw check needs to be added
            app.emailAdress = emailAdress
            app.password = password
            view?.navigateTo(VIEW.LIST, 0, "", null)
        } else {
            view!!.loginFailed.visibility = View.VISIBLE;
        }
    }
}
