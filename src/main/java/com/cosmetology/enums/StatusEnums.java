package com.cosmetology.enums;



public enum  StatusEnums {
	STATUS_ENUMS_0(0, "待确认"),
	STATUS_ENUMS_1(1, "已确认"),
	STATUS_ENUMS_2(2, "已取消");
	private Integer code;
	private String msg;
	StatusEnums(Integer code, String msg) {
		this.code = code;
		this.msg = msg;
	}
	public Integer getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}


}
