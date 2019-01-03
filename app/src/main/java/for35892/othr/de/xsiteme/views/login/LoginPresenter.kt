package for35892.othr.de.xsiteme.views.login

import com.google.firebase.auth.FirebaseAuth
import for35892.othr.de.xsiteme.models.firebase.SiteFireStore
import for35892.othr.de.xsiteme.views.BasePresenter
import for35892.othr.de.xsiteme.views.BaseView
import for35892.othr.de.xsiteme.views.VIEW
import org.jetbrains.anko.toast

class LoginPresenter(view: BaseView) : BasePresenter(view) {

    var auth: FirebaseAuth = FirebaseAuth.getInstance()
    var fireStore: SiteFireStore? = null

    init {
        if (app.sites is SiteFireStore) {
            fireStore = app.sites as SiteFireStore
        }
    }

    fun doLogin(email: String, password: String) {
        if (email.isEmpty() || password.isEmpty()) {
            view?.toast("Sign Up Failed: Email and password must not be empty")
        } else {
            view?.showProgress()
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(view!!) { task ->
                if (task.isSuccessful) {
                    if (fireStore != null) {
                        fireStore!!.fetchSites {
                            view?.hideProgress()
                            view?.navigateTo(VIEW.LIST)
                        }
                    } else {
                        view?.hideProgress()
                        view?.navigateTo(VIEW.LIST)
                    }
                } else {
                    view?.hideProgress()
                    view?.toast("Sign Up Failed: ${task.exception?.message}")
                }
            }
        }
    }

    fun doSignUp(email: String, password: String) {
        if (email.isEmpty() || password.isEmpty()) {
            view?.toast("Sign Up Failed: Email and password must not be empty")
        } else {
            view?.showProgress()
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(view!!) { task ->
                if (task.isSuccessful) {
                    view?.navigateTo(VIEW.LIST)
                } else {
                    view?.toast("Sign Up Failed: ${task.exception?.message}")
                }
                view?.hideProgress()
            }
        }
    }
}
