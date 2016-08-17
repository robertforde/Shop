# Shop
Java Swing POS Application

This is a Swing application for a retail outlet. It uses Swing, Spring DI, JDBC and mysql. I wrote this application after/while teaching myself Swing.

#Installation
Download source code and in Eclipse File Menu->Import->General->ExistingProjects into Workspace
Browse to the directory where you downloaded to and click finish
You need a MySql Database 
You need to change the username and password in the spring.xml file in the src directory to your MySql username and password
There is a Shop.sql sql dump file in the src directory which can be imported into your MySql and this will create the database and necessary tables.

# The application functionality:

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
          On Selection lines on quote displayed, convert button to open retail/trade order screen quote
  
  Accounts
  
      List of all accounts displayed: name, address, phone, balance
      Can filter by above fields
      On selection of account - detail screen loaded with name, balance and last 20 transactions
          A statement can be displayed for the account for a specific ate range
          A payment can be made against the account
          An trade order can be entered for the account
          A trade quote can be entered for the account
          A list of account Quotes can be displayed and converted to orders if required
  
  Refunds
  
      List of Refunds displayed with many filters to find an order to refund against
          Type, No, Name, Address, Date Range, Phone Value, Item Code or Description of item on quote
      On Selection lines on quote displayed broken down to a quantity of 1 on each line
          User selects the lines to refund which opens a frame showing to be refunded items with values
          Refund button on to be refunded performs the refund.
  
  Product maintenance
  
      The list of Products are displayed - filters available are code and description
          User can perform CRUD operations on Products
  
  Customer maintenance
  
      The list of Customers are displayed - filters available are name, address and phone
          User can perform CRUD operations on Customers
  
  Tradesmen Maintenance
  
      The list of Tradesmen are displayed - filters available are name, address and phone
          User can perform CRUD operations on Tradesmen
      
  Settings
  
      Various Vat and Receipt printing settings
          Choose whether or not to print icons on the top of in the body of the page with their X an Y positions
          Choose whether or not to print vertical grid lines on a receipt
          Enter various lines of footer text that can be applied to the receipt's footer
          Enter the current Vat Rate
