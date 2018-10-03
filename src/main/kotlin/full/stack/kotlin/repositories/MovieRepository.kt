package full.stack.kotlin.repositories

import full.stack.kotlin.model.Actor
import full.stack.kotlin.model.Movie
import full.stack.kotlin.model.MoviePlusCastSize
import org.springframework.data.neo4j.annotation.Query
import org.springframework.data.neo4j.repository.Neo4jRepository

interface MovieRepository : Neo4jRepository<Movie, Long> {

    fun findByTitle(title: String): List<Movie>
    fun findByTitleContaining(work: String): List<Movie>

    @Query("MATCH (m:Movie)<-[:ACTS_IN]-(a:Actor {id: {0}}) RETURN m")
    fun findByActorId(actorId: String): List<Movie>

    @Query("""
    MATCH (a:Actor)-[:ACTS_IN]->(m:Movie)
    WITH a, collect(m) AS filmograpy
    WHERE size(filmograpy) >= {0}
    RETURN a
    """)
    fun findActorsByFilmography(minMovies: Long): List<Actor>

    @Query("""
    MATCH (movie:Movie {title: {0}})<-[ACTS_IN]-(actor:Actor)
	WITH movie, size(collect(actor)) as castSize
	RETURN movie, castSize
    """)
    fun findPlusCastSize(title: String): List<MoviePlusCastSize>

    @Query("""
    MATCH (m:Movie)<-[:ACTS_IN]-(a:Actor)
    WHERE a.name STARTS WITH {0}
    RETURN m SKIP {1} LIMIT {2}
    """)
    fun findByActorName(actorName: String, skip: Long, limit: Long): List<Movie>

}