package full.stack.kotlin

import org.neo4j.ogm.session.SessionFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment

@Configuration
class IterationThreeConfig(private val env: Environment) {
    @Bean
    fun configuration(): org.neo4j.ogm.config.Configuration {
        val username = env.getProperty("instil.neo4j.user")
        val password = env.getProperty("instil.neo4j.password")
        return org.neo4j.ogm.config.Configuration.Builder()
                .uri("bolt://localhost")
                .credentials(username, password)
                .build()
    }

    @Bean
    fun sessionFactory(): SessionFactory {
        return SessionFactory(configuration(), "full.stack.kotlin.model")
    }
}