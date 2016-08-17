# Shop
Java Swing POS Application

This is a Swing application for a retail outlet. It uses Swing, Spring DI, JDBC and mysql. I wrote this application after/while teaching myself Swing.

The application functionality:

  Retail Orders
    
      The ability to add Retail Orders
      
      Apply discounts
      Gross Profit percent and value calculated and siplayed on screen
      Add customer details to the order
      Choose order pay type
      Print receipts
    
  Trade orders
  
      The ability to add Trade orders
      Gross Profit percent and value calculated and siplayed on screen
      Add tradesmen details to the order
      Choose order pay type
      print receipts details
      
  Suspended Orders
  
      Add retail or trade suspended orders
      
      List of Suspended Orders Displayed, can filter Retail or Trade Suspended Orders
      Enter a Payment against a Suspended Order
      Select a Suspended Order To Process
          Release Order Lines from the Suspended Order
          Add new Lines to the Suspended Order and re-print
          Remove Lines from a Suspended Order
  
  Quotations
  
      Add Retail Quotes similar to orders without payment type
      Add Trade Quotes similar to orders without payment type
      Convert a quote into an order
          List of Quotations displayed with many filters to find quotation
              Type, No, Name, Address, Date Range, Phone Value, Item Code or Description of item on quote
          On Selection detail lines on quote displayed convert button to open retail/trade order screen with this quote
  
  Accounts
  
  Refunds
  
  Item maintenance
  
      Perform CRUD operations on Items
  
  Customer maintenance
  
      Perform CRUD operations on Customers
  
  Tradesmen Maintenance
  
      Perform CRUD operations on Tradesmen
      
  Settings
