package for35892.othr.de.xsiteme.models.firebase

import android.content.Context
import android.graphics.Bitmap
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import for35892.othr.de.xsiteme.helpers.readImageFromPath
import for35892.othr.de.xsiteme.models.SiteModel
import for35892.othr.de.xsiteme.models.SiteStore
import org.jetbrains.anko.AnkoLogger
import java.io.ByteArrayOutputStream
import java.io.File

class SiteFireStore(val context: Context) : SiteStore, AnkoLogger {

    val sites = ArrayList<SiteModel>()
    lateinit var userId: String
    lateinit var db: DatabaseReference
    lateinit var st: StorageReference

    override fun findAll(): List<SiteModel> {
        return sites
    }

    override fun findFavourites(): List<SiteModel> {
        return sites.filter { site -> site.favourite }
    }

    override fun findById(id: Long): SiteModel? {
        return sites.find { p -> p.id == id }
    }

    override fun create(site: SiteModel) {
        userId = FirebaseAuth.getInstance().currentUser!!.uid
        val key = db.child("users").child(userId).child("sites").push().key
        site.fbId = key!!
        sites.add(site)
        db.child("users").child(userId).child("sites").child(key).setValue(site)
        updateImage(site)
    }

    override fun update(site: SiteModel) {
        var foundSite: SiteModel? = sites.find { p -> p.fbId == site.fbId }
        if (foundSite != null) {
            foundSite.title = site.title
            foundSite.description = site.description
            foundSite.visited = site.visited
            foundSite.dateVisited = site.dateVisited
            foundSite.favourite = site.favourite
            foundSite.rating = site.rating
            foundSite.image = site.image
            foundSite.additionalNotes = site.additionalNotes
            foundSite.lat = site.lat
            foundSite.lng = site.lng
            foundSite.zoom = site.zoom
        }

        db.child("users").child(userId).child("sites").child(site.fbId).setValue(site)
        if ((site.image.length) > 0 && (site.image[0] != 'h')) {
            updateImage(site)
        }
    }

    override fun delete(site: SiteModel) {
        db.child("users").child(userId).child("sites").child(site.fbId).removeValue()
        sites.remove(site)
    }

    override fun clear() {
        sites.clear()
    }

    fun fetchSites(sitesReady: () -> Unit) {
        val valueEventListener = object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
            }
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataSnapshot.children.mapNotNullTo(sites) { it.getValue<SiteModel>(SiteModel::class.java) }
                sitesReady()
            }
        }
        userId = FirebaseAuth.getInstance().currentUser!!.uid
        db = FirebaseDatabase.getInstance().reference
        st = FirebaseStorage.getInstance().reference
        clear()
        db.child("users").child(userId).child("sites").addListenerForSingleValueEvent(valueEventListener)
    }

    fun updateImage(site: SiteModel) {
        if (site.image != "") {
            val fileName = File(site.image)
            val imageName = fileName.getName()

            var imageRef = st.child(userId + '/' + imageName)
            val baos = ByteArrayOutputStream()
            val bitmap = readImageFromPath(context, site.image)

            bitmap?.let {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
                val data = baos.toByteArray()
                val uploadTask = imageRef.putBytes(data)
                uploadTask.addOnFailureListener {
                    println(it.message)
                }.addOnSuccessListener { taskSnapshot ->
                    taskSnapshot.metadata!!.reference!!.downloadUrl.addOnSuccessListener {
                        site.image = it.toString()
                        db.child("users").child(userId).child("sites").child(site.fbId).setValue(site)
                    }
                }
            }
        }
    }
}