package ns.boot.jpa.utils.service;

import ns.boot.jpa.starter.JpaQuery;
import ns.boot.jpa.starter.entity.QueryFilter;
import ns.boot.jpa.starter.service.FindService;
import ns.boot.jpa.utils.entity.Customer;
import ns.boot.jpa.utils.jpa.Query;
import ns.boot.jpa.utils.queryinfo.CustomerQueryInfo;
import ns.boot.jpa.utils.repository.AddressRepo;
import ns.boot.jpa.utils.repository.CustomerRepo;
import ns.boot.jpa.utils.repository.EmployeeRepo;
import ns.boot.jpa.utils.repository.ItemRepo;
import ns.boot.jpa.utils.repository.JobRepo;
import ns.boot.jpa.utils.repository.SalesOrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author nanshen
 */
@Service
public class TestService extends FindService {
	@Autowired AddressRepo addressRepo;
	@Autowired CustomerRepo customerRepo;
	@Autowired EmployeeRepo employeeRepo;
	@Autowired ItemRepo itemRepo;
	@Autowired JobRepo jobRepo;
	@Autowired SalesOrderRepo salesOrderRepo;

	public void getCustomer() {
		Query<Customer> query = new Query<>();
		
	}

	public List find1() {
		List re = new ArrayList();

//		1.select * from customer
//		where code > '3' or name='2' or address_id ='1';

		JpaQuery query1 = new JpaQuery<Customer>();
		query1.or(QueryFilter.ge("code", "3"),
				QueryFilter.eq("name", "2"),
				QueryFilter.eq("address.id", "1"));

//		2.select * from customer
//		where code > '3' and name='2' and address_id ='1';

		JpaQuery query2 = new JpaQuery<Customer>();
		query2.and(QueryFilter.ge("code", "3"),
				QueryFilter.eq("name", "2"),
				QueryFilter.eq("address.id", "1"));

//		3.select * from customer
//		where code > '3' or (name='2' and address_id ='1');

		JpaQuery query3 = new JpaQuery<Customer>();
		query3.or(QueryFilter.ge("code", "3")
				.childAnd(QueryFilter.eq("name", "2"),
						QueryFilter.eq("address.id", "1"))
				.or(QueryFilter.ge("code", "3"));

//		4.select * from customer
//		where code < '4' and (name='2' or address_id ='1');

		JpaQuery query4 = new JpaQuery<Customer>();
		query4.and(QueryFilter.le("code", "4"))
				.childOr(QueryFilter.eq("name", "2"),
						QueryFilter.eq("address.id", "1"));


//		re = customerRepo.findAll(query);


		CustomerQueryInfo queryInfo = new CustomerQueryInfo();






		return re;
	}
}
