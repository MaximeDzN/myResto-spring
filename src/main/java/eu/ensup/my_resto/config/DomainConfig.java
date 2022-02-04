package eu.ensup.my_resto.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;


/**
 * The type Domain config.
 */
@Configuration
@EntityScan("eu.ensup.my_resto.domain")
@EnableJpaRepositories("eu.ensup.my_resto.repos")
@EnableTransactionManagement
public class DomainConfig {
}
