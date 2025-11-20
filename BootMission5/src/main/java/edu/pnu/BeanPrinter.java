package edu.pnu;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class BeanPrinter implements ApplicationRunner{
	
	private final ApplicationContext context;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		String[] beanNames = context.getBeanDefinitionNames();
		for (String name : beanNames) {
			Object bean = context.getBean(name);
			String cname = bean.getClass().getName();
			int idx = cname.lastIndexOf(".");
			System.out.println(cname.substring(idx+1));
		}
		System.out.println("Count:"+beanNames.length);
		
	}
	
	
	
}
