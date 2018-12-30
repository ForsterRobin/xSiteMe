package for35892.othr.de.xsiteme.views.login


import android.os.Bundle
import android.view.View
import for35892.othr.de.xsiteme.R
import for35892.othr.de.xsiteme.views.BaseView
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.AnkoLogger


class LoginView : BaseView(), AnkoLogger {

    lateinit var presenter: LoginPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
       super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        progressBar.visibility = View.GONE

        presenter = initPresenter (LoginPresenter(this)) as LoginPresenter

        loginButton.setOnClickListener { presenter.doLogin(emailAdress.text.toString(), password.text.toString()) }
        signUpButton.setOnClickListener { presenter.doSignUp(emailAdress.text.toString(), password.text.toString()) }
    }

    override fun showProgress() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progressBar.visibility = View.GONE
    }

    override fun onBackPressed() {
        // in order to not be able to get back when logged out
    }
}