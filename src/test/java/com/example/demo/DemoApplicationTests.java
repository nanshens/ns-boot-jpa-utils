package com.example.demo;

import ns.boot.jpa.utils.UtilsApplication;
import ns.boot.jpa.utils.entity.Customer;
import ns.boot.jpa.utils.entity.SalesOrder;
import ns.boot.jpa.utils.jpa.Query;
import ns.boot.jpa.utils.jpa.TestQuery;
import ns.boot.jpa.utils.jpa.entity.QueryFilter;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

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
		Query<SalesOrder> query = new Query<>();
//		query.leftJoin("address");
		query.and(QueryFilter.eq("customer.address.id", "1"));
		TestQuery<SalesOrder> testQuery = new TestQuery<>();
		return salesOrderRepo.findAll(query);
	}

	public List testQuery1() {

		Query<SalesOrder> query = new Query<>();
		return salesOrderRepo.findAll(query);
	}
}
