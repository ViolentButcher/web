package edu.njupt.feng.web.entity.common;

import java.io.Serializable;


public class JsonData implements Serializable {

	private static final long serialVersionUID = 1L;

	private Object data; // 数据
	private String msg;// 描述

	public JsonData() {
	}

	public JsonData( Object data, String msg) {
		this.data = data;
		this.msg = msg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	@Override
	public String toString() {
		return "JsonData [data=" + data + ", msg=" + msg
				+ "]";
	}

}
