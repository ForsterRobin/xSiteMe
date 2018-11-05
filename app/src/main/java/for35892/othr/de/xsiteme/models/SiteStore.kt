package for35892.othr.de.xsiteme.models

interface SiteStore {
  fun findAll(): List<SiteModel>
  fun create(site: SiteModel)
  fun update(site: SiteModel)
  fun delete(site: SiteModel)
  fun findById(id:Long) : SiteModel?
}