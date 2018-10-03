package full.stack.kotlin.model

import org.springframework.data.neo4j.annotation.QueryResult

@QueryResult
class MoviePlusCastSize {
    var movie: Movie? = null
    var castSize: Long? = null
}