package com.epam.idea.core.repository.config.db;

import com.epam.idea.core.repository.config.support.DatabaseConfigProfile;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Profile(DatabaseConfigProfile.H2_EMBEDDED)
@Configuration
@PropertySource("classpath:/db/h2database.properties")
public class JpaH2EmbeddedConfig {
}
