package com.epam.idea.core.repository.config.db;

import com.epam.idea.core.repository.config.support.DatabaseConfigProfile;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Profile(DatabaseConfigProfile.MYSQL)
@Configuration
@PropertySource("classpath:/db/mysql.properties")
public class JpaMySqlConfig {
}
