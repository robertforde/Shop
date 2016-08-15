package com.daniel.model;

import java.util.Date;

/**
 * This class represents Suspended Order's Payment objects which are held in the suspendpayment table in the database
 * @author Robert Forde
 *
 */
public class SuspendedPayment {

	int paymentSuspenId;
	int SuspendOrder;
	Date payDate;
	String payType;
	float amount;
	String comment;
	
	
	public int getPaymentSuspenId() {
		return paymentSuspenId;
	}
	
	public void setPaymentSuspenId(int paymentSuspenId) {
		this.paymentSuspenId = paymentSuspenId;
	}
	
	public int getSuspendOrder() {
		return SuspendOrder;
	}
	
	public void setSuspendOrder(int suspendOrder) {
		SuspendOrder = suspendOrder;
	}
	
	public Date getPayDate() {
		return payDate;
	}
	
	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}
	
	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public float getAmount() {
		return amount;
	}
	
	public void setAmount(float amount) {
		this.amount = amount;
	}
	
	public String getComment() {
		return comment;
	}
	
	public void setComment(String comment) {
		this.comment = comment;
	}
	
}