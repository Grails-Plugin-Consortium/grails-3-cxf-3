package org.grails.example

import grails.boot.GrailsApp
import grails.boot.config.GrailsAutoConfiguration
import org.apache.cxf.Bus
import org.apache.cxf.bus.spring.SpringBus
import org.apache.cxf.jaxws.JaxWsServerFactoryBean
import org.apache.cxf.jaxws.support.JaxWsServiceFactoryBean
import org.apache.cxf.transport.servlet.CXFServlet
import org.springframework.boot.context.embedded.ServletRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ImportResource

@ImportResource(["classpath:META-INF/cxf/cxf.xml",
        "classpath:META-INF/cxf/cxf-extension-jaxws.xml",
        "classpath:META-INF/cxf/cxf-servlet.xml"])
class Application extends GrailsAutoConfiguration {

    static void main(String[] args) {
        GrailsApp.run(Application)
    }

    @Bean
    public ServletRegistrationBean cxfServlet() {
        ServletRegistrationBean servlet = new ServletRegistrationBean(new CXFServlet(), "/services/*");
        servlet.setLoadOnStartup(1);
        return servlet;
    }

    @Bean(name = Bus.DEFAULT_BUS_ID)
    public SpringBus springBus() {
        return new SpringBus();
    }

    @Bean
    public JaxWsServiceFactoryBean importantServiceFactoryBean() {
        JaxWsServiceFactoryBean serviceFactoryBean = new JaxWsServiceFactoryBean()
        serviceFactoryBean.serviceClass = ImportantService
        serviceFactoryBean.bus = springBus()
        serviceFactoryBean
    }

    @Bean
    public JaxWsServerFactoryBean importantServerFactoryBean() {
        JaxWsServerFactoryBean svrFactory = new JaxWsServerFactoryBean(importantServiceFactoryBean());
        svrFactory.serviceClass = ImportantService.class;
        svrFactory.address = "/important";
        svrFactory.serviceBean = importantService();
        svrFactory.create()
        svrFactory
    }

    @Bean
    public ImportantService importantService() {
        new ImportantServiceImpl();
    }
}