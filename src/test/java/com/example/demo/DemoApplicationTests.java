package com.example.demo;

import ns.boot.jpa.utils.UtilsApplication;
import ns.boot.jpa.utils.entity.Customer;
import ns.boot.jpa.utils.entity.Sex;
import ns.boot.jpa.utils.jpa.Query;
import ns.boot.jpa.utils.jpa.entity.QueryParamsFilter;
import ns.boot.jpa.utils.repository.AddressRepo;
import ns.boot.jpa.utils.repository.CustomerRepo;
import ns.boot.jpa.utils.repository.EmployeeRepo;
import ns.boot.jpa.utils.repository.ItemRepo;
import ns.boot.jpa.utils.repository.JobRepo;
import ns.boot.jpa.utils.repository.SalesOrderRepo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.List;

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
	public void contextLoads() {
		long start = System.currentTimeMillis();
		List result = testQuery();
		long end = System.currentTimeMillis();
		System.out.println(end-start);
		System.out.println(result);
	}

	public List testQuery() {
		Query<Customer> query = new Query<>();
		query.and(QueryParamsFilter.gt("code", "3"));

		return customerRepo.findAll(query);
	}
}
