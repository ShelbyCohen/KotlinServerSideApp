package full.stack.kotlin

import full.stack.kotlin.model.Movie
import org.junit.Assert.assertTrue
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient

class Neo4JMoviesServiceTest {
    private val client = WebTestClient
            .bindToServer()
            .baseUrl("http://localhost:8080/movies")
            .build()

    private val typeRef = object : ParameterizedTypeReference<List<Movie>>() {}

    fun assertMovieExists(movies: List<Movie>, title: String) {
        val exists = movies.any { it.title == title }
        assertTrue("No movie called $title", exists)
    }

    @Test
    fun moviesCanBeFoundByTitle() {
        val title = "Avatar"
        this.client.get().uri("/byTitle/$title")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().is2xxSuccessful
                .expectBodyList(Movie::class.java)
                .hasSize(1)
                .returnResult()
                .responseBody
                .apply {
                    assertEquals(this!![0].title, title)
                }
    }

    @Test
    fun moviesCanBeFoundByTitleAndCastSize() {
        val title = "Avatar"
        val expectedCastSize = 13
        this.client.get().uri("/byTitleAddCastSize/$title")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().is2xxSuccessful
                .expectBody()
                .jsonPath("\$[0].movie.title").isEqualTo(title)
                .jsonPath("\$[0].castSize").isEqualTo(expectedCastSize)
    }

    @Test
    fun moviesCanBeFoundByPhrase() {
        val phrase = "Star"
        val expectedSize = 77
        this.client.get().uri("/byPhrase/$phrase")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().is2xxSuccessful
                .expectBody<List<Movie>>(typeRef)
                .returnResult()
                .apply {
                    assertNotNull(responseBody)
                    val movies = responseBody ?: emptyList()
                    assertEquals(77, movies.size)
                    assertMovieExists(movies, "Star Wars: Episode IV - A New Hope")
                    assertMovieExists(movies, "Star Trek IV: The Voyage Home")
                    assertMovieExists(movies, "Fist of the North Star")
                }
    }

    @Test
    fun moviesCanBeFoundByActorId() {
        val actorId = 3
        this.client.get().uri("/byActorId/$actorId")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().is2xxSuccessful
                .expectBody<List<Movie>>(typeRef)
                .returnResult()
                .apply {
                    assertNotNull(responseBody)
                    val movies = responseBody ?: emptyList()
                    assertMovieExists(movies, "Star Wars: Episode IV - A New Hope")
                    assertMovieExists(movies, "Indiana Jones and the Last Crusade")
                }
    }

    @Test
    fun moviesCanBeFoundByFilmography() {
        val size = 50
        val expectedSize = 21
        this.client.get().uri("/byFilmography/$size")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().is2xxSuccessful
                .expectBodyList(Movie::class.java)
                .hasSize(expectedSize)
    }
}
