package es.cnc;

import java.util.List;

import javax.servlet.MultipartConfigElement;

import org.springframework.boot.context.embedded.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;

import es.cnc.suscripciones.front.export.excel.util.ExcelViewResolver;
import es.cnc.suscripciones.front.export.pdf.itext.util.ITextPdfViewResolver;
import es.cnc.suscripciones.front.export.pdf.jasper.util.JasperPdfViewResolver;

@Configuration
@EnableWebMvc
public class WebMVCConfig extends WebMvcConfigurerAdapter {
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/**")
			.addResourceLocations("file:recibosWeb/")
			.setCachePeriod(0);
		registry.addResourceHandler("/recibosApp/**")
			.addResourceLocations("file:///Y:/Development/WorkspaceParroquia/recibosExt6/recWorkspace/")
			.setCachePeriod(0);
		
	    registry.addResourceHandler("swagger-ui.html")
	      .addResourceLocations("classpath:/META-INF/resources/");
	 
	    registry.addResourceHandler("/webjars/**")
	      .addResourceLocations("classpath:/META-INF/resources/webjars/");
		
	}
	
	
	/* Here we register the Hibernate4Module into an ObjectMapper, then set this custom-configured ObjectMapper
     * to the MessageConverter and return it to be added to the HttpMessageConverters of our application*/
	@Bean
    public MappingJackson2HttpMessageConverter jacksonMessageConverter(){
        MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();

        ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        
        //Registering Hibernate4Module to support lazy objects
        Hibernate4Module module = new Hibernate4Module();
        module.disable(Hibernate4Module.Feature.FORCE_LAZY_LOADING);
        		
        mapper.registerModule(module);

        messageConverter.setObjectMapper(mapper);
        return messageConverter;

    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        //Here we add our custom-configured HttpMessageConverter
        converters.add(jacksonMessageConverter());
        super.configureMessageConverters(converters);
    }
    
    /*
     * Configure ContentNegotiatingViewResolver
     */
//    @Bean
//    public ViewResolver contentNegotiatingViewResolver(ContentNegotiationManager manager) {
//        ContentNegotiatingViewResolver resolver = new ContentNegotiatingViewResolver();
//        resolver.setContentNegotiationManager(manager);
// 
//        // Define all possible view resolvers
//        List<ViewResolver> resolvers = new ArrayList<ViewResolver>();
//        resolvers.add(pdfViewResolver());
// 
////        resolvers.add(jaxb2MarshallingXmlViewResolver());
////        resolvers.add(jsonViewResolver());
////        resolvers.add(jspViewResolver());
////        resolvers.add(excelViewResolver());
//         
//        resolver.setViewResolvers(resolvers);
//        return resolver;
//    }
    
    /*
     * Configure ContentNegotiationManager
     */
//    @Override
//    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
//        configurer.ignoreAcceptHeader(true).defaultContentType(
//                MediaType.TEXT_HTML);
//    }
    
    /*
     * Configure View resolver to provide PDF output using lowagie pdf library to
     * generate PDF output for an object content
     */
    @Bean
    public ViewResolver iTextPDFViewResolver() {
        return new ITextPdfViewResolver();
    }
    
    /*
     * Configure View resolver to provide PDF output using lowagie pdf library to
     * generate PDF output for an object content
     */
    @Bean
    public ViewResolver jasperPDFViewResolver() {
        return new JasperPdfViewResolver();
    }
    
    /*
     * Configure View resolver to provide PDF output using lowagie pdf library to
     * generate PDF output for an object content
     */
    @Bean
    public ViewResolver excelViewResolver() {
        return new ExcelViewResolver();
    }
    
    
//    @Bean(name = "multipartResolver")
//    public CommonsMultipartResolver multipartResolver() {
//        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
//        multipartResolver.setMaxUploadSize(100000);
//        return multipartResolver;
//    }

    @Bean
    public MultipartConfigElement multipartConfigElement() {
    	MultipartConfigFactory factory = new MultipartConfigFactory();
    	factory.setMaxFileSize("128KB");
    	factory.setMaxRequestSize("128KB");
    	return factory.createMultipartConfig();
    }
}
