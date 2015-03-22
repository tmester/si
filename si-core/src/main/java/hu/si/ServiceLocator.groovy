package hu.si

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope("singleton")
public class ServiceLocator implements ApplicationContextAware{
	private static final Logger logger = LoggerFactory.getLogger(ServiceLocator.class);
	private static ApplicationContext springContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		ServiceLocator.springContext = applicationContext;
	}
	
	public static Map<String, Object> getBeansWithAnnotation(Class<? extends Annotation> annotationType) throws BeansException {
		return springContext.getBeansWithAnnotation(annotationType);
	}
	
	public static <T> T getBean(Class<T> requiredType) {
		if (springContext == null) {
			logger.error("springContext null ServiceLocator-ban!");
		}
		return springContext.getBean(requiredType);
	}
	
	public static <T> T getBean(Class<T> requiredType, Object... args) {
		return (T) springContext.getBean(StringUtils.uncapitalize(requiredType.getSimpleName()), args);
	}

	public static <T> List<T> getBeans(Class<T> requiredType) {
		return new ArrayList<T>(springContext.getBeansOfType(requiredType).values());
	}

	public static ApplicationContext getAppContext() {
		return springContext;
	}
}
