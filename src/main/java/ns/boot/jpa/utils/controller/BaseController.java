package ns.boot.jpa.utils.controller;

import com.alibaba.fastjson.JSONObject;
import ns.boot.jpa.starter.result.Result;
import ns.boot.jpa.utils.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author ns
 */
@RestController
public class BaseController {
	@Autowired
	TestService testService;

	@GetMapping(value = "/find", produces = "application/json")
	public Result method(@RequestBody JSONObject jsonObject) {
		return testService.find(jsonObject);
	}

	@GetMapping(value = "/find1")
	public List method() {
		return testService.find1();
	}
}
