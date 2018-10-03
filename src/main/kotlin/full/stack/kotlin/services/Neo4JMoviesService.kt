package full.stack.kotlin.services

import full.stack.kotlin.model.Actor
import full.stack.kotlin.model.Movie
import full.stack.kotlin.model.MoviePlusCastSize
import full.stack.kotlin.repositories.MovieRepository
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux

@CrossOrigin
@RestController
@RequestMapping("/movies")
class Neo4JMoviesService(val repo: MovieRepository) {
    @GetMapping("/byTitle/{title}")
    fun moviesByTitle(@PathVariable("title") title: String): Flux<Movie> {
        return Flux.fromIterable(repo.findByTitle(title))
    }

    @GetMapping("/byPhrase/{phrase}")
    fun moviesByPhrase(@PathVariable("phrase") phrase: String): Flux<Movie> {
        return Flux.fromIterable(repo.findByTitleContaining(phrase))
    }

    @GetMapping("/byActorId/{actorId}")
    fun moviesByActorId(@PathVariable("actorId") actorId: String): Flux<Movie> {
        return Flux.fromIterable(repo.findByActorId(actorId))
    }

    @GetMapping("/byTitleAddCastSize/{title}")
    fun moviesByTitlePlusCaseSize(@PathVariable("title") title: String): Flux<MoviePlusCastSize> {
        return Flux.fromIterable(repo.findPlusCastSize(title))
    }

    @GetMapping("/byFilmography/{minMovies}")
    fun moviesByActorId(@PathVariable("minMovies") minMovies: Long): Flux<Actor> {
        return Flux.fromIterable(repo.findActorsByFilmography(minMovies))
    }

    @GetMapping("/byActorName/{name}/{skip}/{limit}")
    fun moviesByActorName(
            @PathVariable("name") name: String,
            @PathVariable("skip") skip: Long,
            @PathVariable("limit") limit: Long
    ): Flux<Movie> {
        return Flux.fromIterable(repo.findByActorName(name, skip, limit))
    }
}