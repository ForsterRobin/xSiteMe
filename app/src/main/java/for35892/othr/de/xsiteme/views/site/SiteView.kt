package for35892.othr.de.xsiteme.views.site

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.RatingBar.OnRatingBarChangeListener
import for35892.othr.de.xsiteme.R
import for35892.othr.de.xsiteme.helpers.readImageFromPath
import for35892.othr.de.xsiteme.models.SiteModel
import for35892.othr.de.xsiteme.views.BaseView
import kotlinx.android.synthetic.main.activity_site.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.toast


class SiteView : BaseView(), AnkoLogger {

    lateinit var presenter: SitePresenter
    var site = SiteModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_site)
        toolbarAdd.title = title
        setSupportActionBar(toolbarAdd)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        presenter = initPresenter (SitePresenter(this)) as SitePresenter

        chooseImage.setOnClickListener { presenter.doSelectImage() }

        siteLocation.setOnClickListener { presenter.doSetLocation() }

        checkBoxVisited.setOnClickListener { presenter.doChangeVisited() }

        checkBoxFavourite.setOnClickListener { presenter.doChangeFavourite() }

        addListenerOnRatingBar()
    }

    fun addListenerOnRatingBar() {
        ratingBar.onRatingBarChangeListener =
                OnRatingBarChangeListener { ratingBar, newRating, fromUser -> presenter.doChangeRating(newRating) }
    }

    override fun showSite(site: SiteModel) {
        siteTitle.setText(site.title)
        description.setText(site.description)
        additionalNotes.setText(site.additionalNotes)
        siteImage.setImageBitmap(readImageFromPath(this, site.image))
        if (site.image != null) {
            chooseImage.setText(R.string.change_site_image)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_site, menu)
        if (presenter.edit) {
            menu.getItem(0).setVisible(true)
            toolbarAdd.title = getString(R.string.title_activity_site_edit)
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.item_delete -> {
                presenter.doDelete()
            }
            R.id.item_save -> {
                if (siteTitle.text.toString().isEmpty()) {
                    toast(R.string.enter_site_title)
                } else {
                    presenter.doAddOrSave(siteTitle.text.toString(), description.text.toString(), additionalNotes.text.toString())
                }
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) {
            presenter.doActivityResult(requestCode, resultCode, data)
        }
    }

    override fun onBackPressed() {
        presenter.doCancel()
    }
}