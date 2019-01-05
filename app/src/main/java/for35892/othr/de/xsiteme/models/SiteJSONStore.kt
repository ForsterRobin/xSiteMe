package xsiteme.models

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import org.jetbrains.anko.AnkoLogger
import for35892.othr.de.xsiteme.helpers.*
import for35892.othr.de.xsiteme.models.SiteStore
import for35892.othr.de.xsiteme.models.SiteModel
import java.util.*

val JSON_FILE = "sites.json"
val gsonBuilder = GsonBuilder().setPrettyPrinting().create()
val listType = object : TypeToken<java.util.ArrayList<SiteModel>>() {}.type

fun generateRandomId(): Long {
  return Random().nextLong()
}

class SiteJSONStore : SiteStore, AnkoLogger {

  val context: Context
  var sites = mutableListOf<SiteModel>()

  constructor (context: Context) {
    this.context = context
    if (exists(context, JSON_FILE)) {
      deserialize()
    }
  }

  override fun findAll(): MutableList<SiteModel> {
    return sites
  }

  override fun findFavourites(): MutableList<SiteModel> {
    return sites.filter { site -> site.favourite }.toMutableList()
  }

  override fun findById(id: Long): SiteModel? {
    val foundSite: SiteModel? = sites.find { it.id == id }
    return foundSite
  }

  override fun create(site: SiteModel) {
    site.id = generateRandomId()
    sites.add(site)
    serialize()
  }

  override fun update(site: SiteModel) {
    val sitesList = findAll() as ArrayList<SiteModel>
    var foundSite: SiteModel? = sitesList.find { p -> p.id == site.id }
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
    serialize()
  }

  override fun delete(site: SiteModel) {
    sites.remove(site)
    serialize()
  }

  override fun clear() {
    sites.clear()
  }

  private fun serialize() {
    val jsonString = gsonBuilder.toJson(sites, listType)
    write(context, JSON_FILE, jsonString)
  }

  private fun deserialize() {
    val jsonString = read(context, JSON_FILE)
    sites = Gson().fromJson(jsonString, listType)
  }
}
