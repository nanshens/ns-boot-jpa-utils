package ns.boot.jpa.utils.queryinfo;

import lombok.Data;

import java.time.LocalTime;

@Data
public abstract class BaseQueryInfo {
	private String id;
	private String code;
	private LocalTime createTime;
	private String createBy;
}
