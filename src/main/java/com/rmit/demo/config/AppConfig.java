package com.rmit.demo.config;

import com.rmit.demo.model.*;
import com.rmit.demo.service.*;
import com.rmit.demo.model.Category;
import com.rmit.demo.model.Product;
import com.rmit.demo.model.Customer;
import com.rmit.demo.model.Provider;
import com.rmit.demo.model.Staff;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@EnableWebMvc
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.rmit.demo.repository")
public class AppConfig {

    @Bean
    public Product product() {
        return new Product();
    }

    @Bean
    public ProductService productService() {
        return new ProductService();
    }

    @Bean
    public Customer customer() {
        return new Customer();
    }

    @Bean
    public CustomerService customerService() {
        return new CustomerService();
    }

    @Bean
    public Provider provider() {
        return new Provider();
    }

    @Bean
    public ProviderService providerService() {
        return new ProviderService();
    }

    @Bean
    public Staff staff() {
        return new Staff();
    }

    @Bean
    public StaffService staffService() {
        return new StaffService();
    }

    @Bean
    public Order order() {
        return new Order();
    }

    @Bean
    public OrderDetail orderDetail() {
        return new OrderDetail();
    }

    @Bean
    public OrderDetailService orderDetailService() {
        return new OrderDetailService();
    }

    @Bean
    public OrderService orderService() {
        return new OrderService();
    }

    @Bean
    public ReceivingNote receivingNote() {
        return new ReceivingNote();
    }

    @Bean
    public ReceivingNoteService receivingNoteService() {
        return new ReceivingNoteService();
    }

    @Bean
    public ReceiveDetail receiveDetail() {return new ReceiveDetail();}

    @Bean
    public ReceiveDetailService receiveDetailService() {
        return new ReceiveDetailService();
    }

    // Define beans for Category assets
    @Bean
    public Category category() {
        return new Category();
    }

    @Bean
    public CategoryService categoryService() {
        return new CategoryService();
    }

    @Bean
    public DeliveryNote deliveryNote() {
        return new DeliveryNote();
    }

    @Bean
    public DeliveryNoteService deliveryNoteService() {
        return new DeliveryNoteService();
    }

    @Bean
    public DeliveryDetail deliveryDetail() {
        return new DeliveryDetail();
    }


    @Bean
    public SaleInvoice saleInvoice() {
        return new SaleInvoice();
    }

    @Bean
    public SaleInvoiceService saleInvoiceService() {
        return new SaleInvoiceService();
    }

    @Bean
    public SaleDetail saleDetail() {
        return new SaleDetail();
    }

    @Bean
    public StatsService statsService() {
        return new StatsService();
    }



    @Autowired
    private Environment env;

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setDatabase(Database.POSTGRESQL);
        vendorAdapter.setGenerateDdl(true);

        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource());
        em.setPackagesToScan("com.rmit.demo.model");
        em.setJpaVendorAdapter(vendorAdapter);
        em.setJpaProperties(additionalProperties());

        return em;
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl(env.getProperty("spring.datasource.url"));
        dataSource.setUsername(env.getProperty("spring.datasource.username"));
        dataSource.setPassword(env.getProperty("spring.datasource.password"));
        return dataSource;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(emf);

        return transactionManager;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    private Properties additionalProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto", env.getProperty("spring.jpa.hibernate.ddl-auto"));
        properties.setProperty("hibernate.dialect", env.getProperty("spring.jpa.properties.hibernate.dialect"));
        properties.setProperty("hibernate.current_session_context_class", env.getProperty("spring.jpa.properties.hibernate.current_session_context_class"));
        properties.setProperty("hibernate.jdbc.lob.non_contextual_creation", env.getProperty("spring.jpa.properties.hibernate.jdbc.lob.non_contextual_creation"));
        properties.setProperty("hibernate.show_sql", env.getProperty("spring.jpa.show-sql"));
        properties.setProperty("hibernate.format_sql", env.getProperty("spring.jpa.properties.hibernate.format_sql"));
        return properties;
    }
}
