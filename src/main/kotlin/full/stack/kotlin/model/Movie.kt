package full.stack.kotlin.model

import org.neo4j.ogm.annotation.GeneratedValue
import org.neo4j.ogm.annotation.Id
import org.neo4j.ogm.annotation.NodeEntity
import org.neo4j.ogm.annotation.Relationship
import org.neo4j.ogm.annotation.Relationship.INCOMING

@NodeEntity
class Movie {
    @Id
    @GeneratedValue
    var imdbId: Long? = null
    var studio: String? = null
    var releaseDate: String? = null
    var runtime: Long? = null
    var description: String? = null
    var language: String? = null
    var title: String? = null
    var version: Long? = null
    var trailer: String? = null
    var imageUrl: String? = null
    var genre: String? = null
    var tagline: String? = null
    var lastModified: String? = null
    var id: String? = null
    var homepage: String? = null
    @Relationship(type = "ACTS_IN", direction = INCOMING)
    var actors: Set<Actor>? = null
}
