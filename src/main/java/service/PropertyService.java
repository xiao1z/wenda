package service;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;

@Service
public class PropertyService extends PropertyPlaceholderConfigurer implements InitializingBean{

	@Override
	public void afterPropertiesSet() throws Exception {
		super.setFileEncoding("UTF-8");
		super.setLocation(new InputStreamResource(this.getClass().getResourceAsStream("config.properties")));
		
	}
}
