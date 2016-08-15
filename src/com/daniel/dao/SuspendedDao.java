package com.daniel.dao;

import java.util.List;

import com.daniel.model.SuspendedOrder;
import com.daniel.model.SuspendedOrderLine;
import com.daniel.model.SuspendedPayment;

public interface SuspendedDao {
	
	/**
	 * Method to update the rounding, totalPostRounding and balance fields of a suspendheader in the database
	 * @param rounding The new rounding value for this order
	 * @param postRounding The new postRounding value for this order
	 * @param balance The new balance value for this order
	 * @param receiptNo The receiptNo of the Suspended order to update
	 */
	public void updateRoundingHeader(float rounding, String postRounding, float balance, int receiptNo);
	
	/**
	 * Method to return a list of all of the Suspended Order Headers from the database that are still open
	 * @param type A String holding the saletype of the open suspended orders to rerieve
	 * @return Returns a List of SuspendedOrder objects of the requested sale type that are open
	 */
	public List<SuspendedOrder> suspendedHeadersListOpen(String type) ;
	
	/**
	 * Method to get the next receipt number from the suspendheader table in the database
	 * @return Returns the newxt receipt number as an int 
	 */ 
	public int findNextReceiptNo();
	
	/**
	 * Method to insert a suspended header  of an order into the suspendheader table
	 * @param order The SuspendedOrder object that is to be inserted
	 */
	public void insertHeaderOrder(SuspendedOrder order);
	
	/**
	 * Method to insert a suspended order line into the suspendeddetail table in the database
	 * @param orderLine The suspendedOrderLine object to be inserted into the database
	 */
	public void insertRetailDetailOrder(SuspendedOrderLine orderLine);
	
	/**
	 * Method to update the suspendheader table in the database with the details of a SuspendedOrder that is passed in 
	 * @param order The SuspendedOrder object that is to be updated in the database
	 */
	public void updateHeaderOrder(SuspendedOrder order);
	
	/**
	 * Method to delete all of the detail lines, from the database, for a specific suspended order header
	 * @param receiptNo The receiptNo of the Suspended Order whose detail lines will be deleted
	 */
	public void deleteDetailOrder(int receiptNo);
	
	/**
	 * Method to mark a suspendheader order as deleted in the database and then call a method to mark all of it's suspenddetail order lines 
	 * as deleted also
	 * @param receiptNo The receiptNo of the Suspended Order to be marked as deleted
	 * @return Returns a 1 if the records was successfully deleted, otherwise returns a 0
	 */
	public int makeOrderDeleted(int receiptNo);
	
	/**
	 * Method to mark all suspenddetail order lines for a suspended order number as deleted in the database
	 * @param receiptNo The receiptNo of the Suspended Order whose lines are to be marked as deleted
	 */
	public void makeOrderLinesDeleted(int receiptNo);

	/**
	 * Method to find a Suspended Order from the suspendheader table in the database and return it as a SuspendedOrder object
	 * @param receiptNo The receiptNo of the Suspended Order to find
	 * @return Returns a SuspendedOrder object that was found in the database
	 */
	public SuspendedOrder findSuspendOrderByNumber(int receiptNo);
	
	/**
	 * Method to return a list of SuspendedOrderLines from the database based on a Suspended Order Receipt No that is passed in
	 * @param receiptNo The receiptNo of the Suspended Order whose lines we are finding
	 * @return Returns a List of SuspendedOrderLine objects that have been found for the receiptNo that was searched
	 */
	public List<SuspendedOrderLine> findSuspendedOrderLinesByReceiptNo(int receiptNo);
	
	/**
	 * Method to find a Suspended Order Line (by ReceiptNo and LineNo) from the database and return it
	 * @param receiptNo The receiptNo of the Suspended Order whose line we want to return
	 * @param lineNo The line of the Suspended Order that we want to find
	 * @return Returns a SuspendedOrderLine object with the details of the line requested
	 */
	public SuspendedOrderLine findSuspendOrderLineByReceiptNoLineNo(int receiptNo, int lineNo);
	
	/**
	 * Method to change an order line (receiptNo & lineNo passed) by an amount that is passed in
	 * @param receiptNo The receiptNo of the Suspended Order to be changed
	 * @param lineNo The line of the Suspended Order to be changed
	 * @param changeQty The Qty dispatched
	 */
	public void changeSuspendOrderLineDispatched(int receiptNo, int lineNo, int changeQty);
	
	/**
	 * Method to stamp todays date in the dispatchedDate field of a suspended order line in the database (receiptNo & lineNo passed)
	 * @param receiptNo The receiptNo of the Suspended Order whose line is to be stamped
	 * @param lineNo The line of the Suspended Order to be stamped
	 */
	public void stampSuspendOrderLineDispatchedDate(int receiptNo, int lineNo);
	
	/**
	 * Method to return the number of lines on a Suspended Order that have not been dispatched yet
	 * @param receiptNo The receiptNo of the Suspended Order whose non dispatched lines to be counted
	 * @return Returns the number of lines as an int
	 */
	public int countNonDispatchedSuspendedOrderLines(int receiptNo);
	
	/**
	 * Method to stamp todays date on an order whose receiptNo is passed in
	 * @param receiptNo The receiptNo of the Suspended Order to be stamped
	 */
	public void stampSuspendOrderDispatchedDate(int receiptNo);
	
	/**
	 * Method to save a payment to the suspendpayment table and reduce the suspended order's balance by the payment amount
	 * @param payment The SuspendedPayment object to be saved to the database
	 * @return Returns the balance of the Suspended Order after the payment
	 */
	public float processPayment(SuspendedPayment payment);
	
}
