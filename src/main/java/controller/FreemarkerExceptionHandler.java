package controller;

import java.io.Writer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import freemarker.core.Environment;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

public class FreemarkerExceptionHandler implements TemplateExceptionHandler{

	private static final Logger logger = LoggerFactory.getLogger(FreemarkerExceptionHandler.class);
	
	@Override
	public void handleTemplateException(TemplateException templateException, Environment arg1, Writer arg2)
			throws TemplateException {
		// TODO Auto-generated method stub
		logger.error("freemarker exception :"+templateException.getMessage());
		throw new IllegalArgumentException("freemarker exception");
	}

}
