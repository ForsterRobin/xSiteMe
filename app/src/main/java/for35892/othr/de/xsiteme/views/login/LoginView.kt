package for35892.othr.de.xsiteme.views.login


import android.os.Bundle
import for35892.othr.de.xsiteme.R
import for35892.othr.de.xsiteme.views.BaseView
import for35892.othr.de.xsiteme.views.site.SitePresenter
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_site.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.email


class LoginView : BaseView(), AnkoLogger {

    lateinit var presenter: LoginPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
       super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        presenter = initPresenter (LoginPresenter(this)) as LoginPresenter

        loginButton.setOnClickListener { presenter.doLogin(emailAdress.text.toString(), password.text.toString()) }
    }
}