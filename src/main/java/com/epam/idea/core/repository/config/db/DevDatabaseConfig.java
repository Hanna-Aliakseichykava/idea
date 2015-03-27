package com.epam.idea.core.repository.config.db;

import java.util.Properties;
import javax.sql.DataSource;

import com.epam.idea.core.repository.config.support.DatabaseConfigProfile;
import org.hibernate.cfg.AvailableSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

@Profile(DatabaseConfigProfile.DEV)
@Configuration
@PropertySource("classpath:/db/dev.properties")
public class DevDatabaseConfig implements DatabaseConfig {

	@Autowired
	private Environment env;

	@Override
	@Bean
	public DataSource dataSource() {
		return new EmbeddedDatabaseBuilder()
				.setType(EmbeddedDatabaseType.H2)
				.addScript(DatabaseConfig.SCHEMA_SCRIPT)
				.addScript(DatabaseConfig.DATA_SCRIPT)
				.build();
	}

	@Override
	@Bean
	public Properties jpaProperties() {
		final Properties jpaProperties = new Properties();
		jpaProperties.put(AvailableSettings.DIALECT,
				this.env.getRequiredProperty(AvailableSettings.DIALECT));
		jpaProperties.put(AvailableSettings.HBM2DDL_AUTO,
				this.env.getRequiredProperty(AvailableSettings.HBM2DDL_AUTO)
		);
		jpaProperties.put(AvailableSettings.MAX_FETCH_DEPTH,
				this.env.getRequiredProperty(AvailableSettings.MAX_FETCH_DEPTH)
		);
		jpaProperties.put(AvailableSettings.STATEMENT_FETCH_SIZE,
				this.env.getRequiredProperty(AvailableSettings.STATEMENT_FETCH_SIZE)
		);
		jpaProperties.put(AvailableSettings.STATEMENT_BATCH_SIZE,
				this.env.getRequiredProperty(AvailableSettings.STATEMENT_BATCH_SIZE)
		);
		jpaProperties.put(AvailableSettings.SHOW_SQL,
				this.env.getRequiredProperty(AvailableSettings.SHOW_SQL)
		);
		jpaProperties.put(AvailableSettings.FORMAT_SQL,
				this.env.getRequiredProperty(AvailableSettings.FORMAT_SQL)
		);
		return jpaProperties;
	}
}
