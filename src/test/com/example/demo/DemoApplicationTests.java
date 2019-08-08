package com.example.demo;

import lombok.SneakyThrows;
import ns.boot.jpa.utils.UtilsApplication;
import ns.boot.jpa.utils.entity.Customer;
import ns.boot.jpa.utils.entity.SalesOrder;
import ns.boot.jpa.utils.jpa.Query;
import ns.boot.jpa.utils.jpa.TestQuery;
import ns.boot.jpa.utils.jpa.entity.QueryFilter;
import ns.boot.jpa.utils.queryinfo.SalesOrderQueryInfo;
import ns.boot.jpa.utils.repository.AddressRepo;
import ns.boot.jpa.utils.repository.CustomerRepo;
import ns.boot.jpa.utils.repository.EmployeeRepo;
import ns.boot.jpa.utils.repository.ItemRepo;
import ns.boot.jpa.utils.repository.JobRepo;
import ns.boot.jpa.utils.repository.SalesOrderRepo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.reflections.Reflections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cglib.core.Predicate;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.Entity;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.annotation.Annotation;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {UtilsApplication.class})
public class DemoApplicationTests {

	@Autowired
	AddressRepo addressRepo;
	@Autowired
	CustomerRepo customerRepo;
	@Autowired
	EmployeeRepo employeeRepo;
	@Autowired
	ItemRepo itemRepo;
	@Autowired
	JobRepo jobRepo;
	@Autowired
	SalesOrderRepo salesOrderRepo;

	@Test
	public void contextLoads(){
		Reflections reflections = new Reflections("ns");

		Set<Class<?>> classesList = reflections.getTypesAnnotatedWith(Entity.class);
		long start = System.currentTimeMillis();
		List result = testQuery();
		long end = System.currentTimeMillis();
		System.out.println(end-start);
		System.out.println(result);
	}

	public List testQuery() {
		Query<SalesOrder> query = new Query<>();
//		query.leftJoin("address");
		query.and(QueryFilter.eq("customer.address.id", "1"));
		TestQuery<SalesOrder> testQuery = new TestQuery<>();
		return salesOrderRepo.findAll(query);
	}

	public List testQuery1() {
		SalesOrderQueryInfo queryInfo = new SalesOrderQueryInfo();
		queryInfo.setLimit(1);
		queryInfo.setPage(1);

		Query<SalesOrder> query = new Query<>();
		return salesOrderRepo.findAll(query);
	}

	@SneakyThrows
	public List<Class<?>> method(String packname){
		PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver(
				Thread.currentThread().getContextClassLoader());
		String path = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX +
				packname.replace(".", "/") + "/**/*.class";
		Resource[] resource = resolver.getResources(path);

		if (resource.length < 1) {
			return Collections.emptyList();
		}
		List<Class<?>> classes = new ArrayList<>();
		for (int i = 0; i < resource.length; i++) {
			classes.add(Thread.currentThread().getContextClassLoader()
					.loadClass(packname + '.' + resource[i].getFilename().replace(".class", "")));
		}
		return classes;
	}
}
