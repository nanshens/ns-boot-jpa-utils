package ns.boot.jpa.utils.queryinfo;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
public abstract class BaseQueryInfo {
	private String id;
	private String code;
	private LocalDate createDate;
//	private String createBy;
	private Integer page;
	private Integer limit;
	private List<String> orderDesc;
	private List<String> orderAsc;

}
