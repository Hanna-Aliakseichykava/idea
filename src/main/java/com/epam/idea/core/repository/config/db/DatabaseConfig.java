package com.epam.idea.core.repository.config.db;

import java.util.Properties;
import javax.sql.DataSource;

public interface DatabaseConfig {
	String SCHEMA_SCRIPT = "classpath:db/sql/schema.sql";
	String DATA_SCRIPT = "classpath:db/sql/test-data.sql";

	DataSource dataSource();
	Properties jpaProperties();
}
