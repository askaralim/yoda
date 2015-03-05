package com.yoda.mail.model;

import com.yoda.BaseEntity;

public class Email extends BaseEntity {
	private String mailSmtpHost;

	private String mailSmtpPort;

	private String mailSmtpAccount;

	private String mailSmtpPassword;

	private String mailFromPwdReset;

	private String subjectPwdReset;

	private String mailFromCustSales;

	private String subjectCustSales;

	private String mailFromNotification;

	private String subjectNotification;

	public String getMailSmtpHost() {
		return mailSmtpHost;
	}

	public void setMailSmtpHost(String mailSmtpHost) {
		this.mailSmtpHost = mailSmtpHost;
	}

	public String getMailFromPwdReset() {
		return mailFromPwdReset;
	}

	public void setMailFromPwdReset(String mailFromPwdReset) {
		this.mailFromPwdReset = mailFromPwdReset;
	}

	public String getMailSmtpAccount() {
		return mailSmtpAccount;
	}

	public void setMailSmtpAccount(String mailSmtpAccount) {
		this.mailSmtpAccount = mailSmtpAccount;
	}

	public String getMailSmtpPassword() {
		return mailSmtpPassword;
	}

	public void setMailSmtpPassword(String mailSmtpPassword) {
		this.mailSmtpPassword = mailSmtpPassword;
	}

	public String getMailFromCustSales() {
		return mailFromCustSales;
	}

	public void setMailFromCustSales(String mailFromCustSales) {
		this.mailFromCustSales = mailFromCustSales;
	}

	public String getSubjectCustSales() {
		return subjectCustSales;
	}

	public void setSubjectCustSales(String subjectCustSales) {
		this.subjectCustSales = subjectCustSales;
	}

	public String getSubjectPwdReset() {
		return subjectPwdReset;
	}

	public void setSubjectPwdReset(String subjectPwdReset) {
		this.subjectPwdReset = subjectPwdReset;
	}

	public String getMailFromNotification() {
		return mailFromNotification;
	}

	public void setMailFromNotification(String mailFromNotification) {
		this.mailFromNotification = mailFromNotification;
	}

	public String getSubjectNotification() {
		return subjectNotification;
	}

	public void setSubjectNotification(String subjectNotification) {
		this.subjectNotification = subjectNotification;
	}

	public String getMailSmtpPort() {
		return mailSmtpPort;
	}

	public void setMailSmtpPort(String mailSmtpPort) {
		this.mailSmtpPort = mailSmtpPort;
	}
}