package configuration;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerView;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

import freemarker.template.utility.XmlEscape;
import intercepter.LoginRequiredIntercepter;
import intercepter.PassportIntercepter;

@Configuration
@EnableWebMvc
@EnableAspectJAutoProxy
@ComponentScan(basePackages={"controller","aspect","intercepter"})
public class WebConfig extends WebMvcConfigurerAdapter{
	
	@Autowired
	PassportIntercepter passportIntercepter;
	
	@Autowired
	LoginRequiredIntercepter loginRequiredIntercepter;
	
	@Bean
	public ViewResolver freeMarkerViewResolver()
	{
		FreeMarkerViewResolver resolver=new FreeMarkerViewResolver();
		resolver.setViewClass(FreeMarkerView.class);
		resolver.setContentType("text/html;charset=utf-8");
		resolver.setSuffix(".ftl");
		resolver.setOrder(0);
		return resolver;
	}
	
	@Bean 
	public FreeMarkerConfigurer freeMarkerConfigurer()
	{
		FreeMarkerConfigurer configurer = new FreeMarkerConfigurer();
		configurer.setTemplateLoaderPath("/WEB-INF/templates/");
		configurer.setDefaultEncoding("UTF-8");
	
		Map<String, Object> map= new HashMap<String,Object>();
		map.put("xml_escape",new XmlEscape());
		configurer.setFreemarkerVariables(map);
		return configurer;
	}
	
	@Bean
	public ViewResolver commonViewResolver()
	{
		InternalResourceViewResolver resolver=new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/templates/");
		resolver.setSuffix(".html");
		resolver.setExposeContextBeansAsAttributes(true);
		resolver.setOrder(1);
		return resolver;
	}
	
	
	
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		// TODO Auto-generated method stub
		configurer.enable();
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// TODO Auto-generated method stub
		registry.addResourceHandler("/static/**").addResourceLocations("/WEB-INF/static/");
		//registry.addResourceHandler("user/static/**").addResourceLocations("/WEB-INF/static/");
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// TODO Auto-generated method stub
		registry.addInterceptor(passportIntercepter);
		registry.addInterceptor(loginRequiredIntercepter).addPathPatterns("/user/**");
		registry.addInterceptor(loginRequiredIntercepter).addPathPatterns("/timeline/**");
		super.addInterceptors(registry);
	}
	
	
}
