package com.epam.idea.core.repository.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.hibernate.cfg.AvailableSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = {"com.epam.idea.core.repository"})
@PropertySource({"classpath:db/datasource.properties", "classpath:db/hibernate.properties"})
public class PersistenceConfig {

	@Autowired
	private Environment env;

	@Value("classpath:db/sql/schema.sql")
	private Resource schemaScript;

	@Value("classpath:db/sql/test-data.sql")
	private Resource dataScript;

	@Bean(destroyMethod = "close")
	public DataSource dataSource(Environment env) {
		HikariConfig dataSourceConfig = new HikariConfig();
//        dataSourceConfig.setDataSourceClassName(env.getRequiredProperty("dataSourceClassName"));
		dataSourceConfig.setDriverClassName(env.getRequiredProperty("dataSource.driver"));
		dataSourceConfig.setJdbcUrl(env.getRequiredProperty("dataSource.url"));
		dataSourceConfig.setUsername(env.getRequiredProperty("dataSource.username"));
		dataSourceConfig.setPassword(env.getRequiredProperty("dataSource.password"));
		//dataSourceConfig.addDataSourceProperty("cachePrepStmts", env.getRequiredProperty("dataSource.cachePrepStmts"));
		//dataSourceConfig.addDataSourceProperty("prepStmtCacheSize", env.getRequiredProperty("dataSource.prepStmtCacheSize"));
		//dataSourceConfig.addDataSourceProperty("prepStmtCacheSqlLimit", env.getRequiredProperty("dataSource.prepStmtCacheSqlLimit"));
		//dataSourceConfig.addDataSourceProperty("useServerPrepStmts", env.getRequiredProperty("dataSource.useServerPrepStmts"));
		return new HikariDataSource(dataSourceConfig);
	}

	@Bean
	@DependsOn({"dataSourceInitializer"})
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(Environment env, DataSource dataSource) {
		LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactoryBean.setDataSource(dataSource);
		entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		entityManagerFactoryBean.setPackagesToScan("com.epam.idea.domain");

		Properties jpaProperties = new Properties();

		//Configures the used database dialect. This allows Hibernate to create SQL
		//that is optimized for the used database.
		jpaProperties.put(AvailableSettings.DIALECT,
				env.getRequiredProperty(AvailableSettings.DIALECT));
		//Specifies the action that is invoked to the database when the Hibernate
		//SessionFactory is created or closed.
		jpaProperties.put(AvailableSettings.HBM2DDL_AUTO,
				env.getRequiredProperty(AvailableSettings.HBM2DDL_AUTO)
		);

		//Declares the “depth” for outer joins when the mapping objects have associations
		//with other mapped objects. This setting prevents Hibernate from fetching too much
		//data with a lot of nested associations.
		jpaProperties.put(AvailableSettings.MAX_FETCH_DEPTH,
				env.getRequiredProperty(AvailableSettings.MAX_FETCH_DEPTH)
		);

		//The number of records from the underlying JDBC ResultSet that Hibernate should
		//use to retrieve the records from the database for each fetch.
		jpaProperties.put(AvailableSettings.STATEMENT_FETCH_SIZE,
				env.getRequiredProperty(AvailableSettings.STATEMENT_FETCH_SIZE)
		);

		//Instructs Hibernate on the number of update operations that should be grouped
		//together into a batch.
		jpaProperties.put(AvailableSettings.STATEMENT_BATCH_SIZE,
				env.getRequiredProperty(AvailableSettings.STATEMENT_BATCH_SIZE)
		);

		//If the value of this property is true, Hibernate writes all SQL
		//statements to the console.
		jpaProperties.put(AvailableSettings.SHOW_SQL,
				env.getRequiredProperty(AvailableSettings.SHOW_SQL)
		);

		//If the value of this property is true, Hibernate will format the SQL
		//that is written to the console.
		jpaProperties.put(AvailableSettings.FORMAT_SQL,
				env.getRequiredProperty(AvailableSettings.FORMAT_SQL)
		);

		entityManagerFactoryBean.setJpaProperties(jpaProperties);

		return entityManagerFactoryBean;
	}

	@Bean
	public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory);
		return transactionManager;
	}

	@Bean
	public DataSourceInitializer dataSourceInitializer(DataSource dataSource) {
		final DataSourceInitializer initializer = new DataSourceInitializer();
		initializer.setDataSource(dataSource);
		initializer.setDatabasePopulator(databasePopulator());
		return initializer;
	}

	private DatabasePopulator databasePopulator() {
		final ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
		populator.addScript(schemaScript);
		populator.addScript(dataScript);
		return populator;
	}

	// PropertySourcesPlaceholderConfigurer has to be static in order to kick in very early in the context initialization process.
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyPlaceHolderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}
}
