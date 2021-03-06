package org.wit.site.models

import for35892.othr.de.xsiteme.models.SiteStore
import for35892.othr.de.xsiteme.models.SiteModel
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

var lastId = 0L

internal fun getId(): Long {
  return lastId++
}

class SiteMemStore : SiteStore, AnkoLogger {

  val sites = ArrayList<SiteModel>()

  override fun findAll(): List<SiteModel> {
    return sites
  }

  override fun findFavourites(): List<SiteModel> {
    return sites.filter { site -> site.favourite }
  }

  override fun findById(id: Long): SiteModel? {
    val foundSite: SiteModel? = sites.find { it.id == id }
    return foundSite
  }

  override fun create(site: SiteModel) {
    site.id = getId()
    sites.add(site)
    logAll()
  }

  override fun update(site: SiteModel) {
    var foundSite: SiteModel? = sites.find { p -> p.id == site.id }
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
      logAll();
    }
  }

  override fun delete(site: SiteModel) {
    sites.remove(site)
  }

  override fun clear() {
    sites.clear()
  }

  fun logAll() {
    sites.forEach { info("${it}") }
  }
}