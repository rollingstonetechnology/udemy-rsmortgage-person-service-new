package com.rollingstone.domain;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RESTAPIErrorInfo {
	public final String errorDetail;
	public final String errorMessage;

	public RESTAPIErrorInfo(Exception ex, String detail) {
		this.errorMessage = ex.getLocalizedMessage();
		this.errorDetail = detail;
	}
}