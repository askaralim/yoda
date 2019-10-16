package com.taklip.yoda.model;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.util.Collection;
import java.util.List;

public class Response<T> {
	private Integer status;
	private String message;
	private T data;

	public Response(Integer status, String message, T data) {
		this.status = status;
		this.message = message;
		this.data = data;
	}

	public String toJson() {
		T t = this.getData();
		if (t instanceof List || t instanceof Collection) {
			return JSONObject.toJSONString(this, SerializerFeature.WriteNullListAsEmpty);
		} else {
			return JSONObject.toJSONString(this, SerializerFeature.WriteMapNullValue);
		}
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
}