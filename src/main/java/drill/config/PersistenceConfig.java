//package drill.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.jdbc.datasource.DriverManagerDataSource;
//import org.springframework.orm.jpa.JpaTransactionManager;
//import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
//import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
//import org.springframework.transaction.PlatformTransactionManager;
//
//@Configuration
//@EnableJpaRepositories(basePackages = {"drill.models"})
//public class PersistenceConfig {
////	@Bean
////	public DriverManagerDataSource dataSource() {
////		DriverManagerDataSource ds = new DriverManagerDataSource();
////		ds.setDriverClassName("com.mysql.jdbc.Driver");
////		ds.setUrl("jdbc:mysql://localhost:3306/drill_test");
////		ds.setUsername("root");
////		ds.setPassword("");
////		return ds;
////	}
//	
//	@Bean
//	public DriverManagerDataSource dataSource() {
//		DriverManagerDataSource ds = new DriverManagerDataSource();
//		ds.setDriverClassName("org.h2.Driver");
//		ds.setUrl("jdbc:h2:mem:datajpa");
//		ds.setUsername("sa");
//		ds.setPassword("");
//		return ds;
//	}
//	
////	
//	@Bean
//	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
//		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
//		vendorAdapter
//				.setDatabase(org.springframework.orm.jpa.vendor.Database.MYSQL);
//		vendorAdapter.setGenerateDdl(true);
//		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
//		factory.setJpaVendorAdapter(vendorAdapter);
//		System.out.println("");
//		factory.setPackagesToScan("drill.models");
//		factory.setDataSource(dataSource());
//		return factory;
//	}
//
//	@Bean
//	public PlatformTransactionManager transactionManager() {
//		JpaTransactionManager txManager = new JpaTransactionManager();
//		txManager.setEntityManagerFactory(entityManagerFactory().getObject());
//		return txManager;
//	}
//}
