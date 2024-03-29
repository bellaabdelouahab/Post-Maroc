package Main;

/* Copyright (c) 2015, Oracle and/or its affiliates. All rights reserved.*/

/*
 DESCRIPTION
 The code sample demonstrates Universal Connection Pool (UCP) as a client
 side connection pool and does the following.    
 (a)Set the connection factory class name to 
 oracle.jdbc.pool.OracleDataSource before getting a connection.   
 (b)Set the driver connection properties(e.g.,defaultNChar,includeSynonyms).
 (c)Set the connection pool properties(e.g.,minPoolSize, maxPoolSize). 
 (d)Get the connection and perform some database operations.     

 Step 1: Enter the Database details in DBConfig.properties file. 
 USER, PASSWORD, UCP_CONNFACTORY and URL are required.                   
 Step 2: Run the sample with "ant UCPSample"

 NOTES
 Use JDK 1.7 and above  

 MODIFIED    (MM/DD/YY)
 nbsundar    02/13/15 - Creation (Contributor - tzhou)
 */
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import oracle.ucp.jdbc.PoolDataSourceFactory;
import oracle.ucp.jdbc.PoolDataSource;

public class UCPSample {
  // final static String DB_URL="jdbc:oracle:thin:@(description= (retry_count=20)(retry_delay=3)(address=(protocol=tcps)(port=1522)(host=adb.me-jeddah-1.oraclecloud.com))(connect_data=(service_name=g07ffc3ff8150c0_nfs315_high.adb.oraclecloud.com)))";
  // // Use the TNS Alias name along with the TNS_ADMIN - For ATP and ADW
  // // final static String DB_URL="jdbc:oracle:thin:@dbname_alias?TNS_ADMIN=/Users/test/wallet_dbname";
  // final static String DB_USER = "admin";
  // final static String DB_PASSWORD = "Abdobella4624";
  // final static String CONN_FACTORY_CLASS_NAME="oracle.jdbc.pool.OracleDataSource";


  final static String DB_URL="jdbc:oracle:thin:@nfs315_high?TNS_ADMIN=Wallet_NFS315";
  final static String DB_USER = "admin";
  final static String DB_PASSWORD = "Abdobella4624";
  final static String CONN_FACTORY_CLASS_NAME="oracle.jdbc.pool.OracleDataSource";



  /*
   * The sample demonstrates UCP as client side connection pool.
   */
  public  void run() throws Exception {
    PoolDataSource pds = PoolDataSourceFactory.getPoolDataSource();
    pds.setConnectionFactoryClassName(CONN_FACTORY_CLASS_NAME);
    pds.setURL(DB_URL);
    pds.setUser(DB_USER);
    pds.setPassword(DB_PASSWORD);
    pds.setConnectionPoolName("JDBC_UCP_POOL");
    pds.setInitialPoolSize(5);
    pds.setMinPoolSize(5);
    pds.setMaxPoolSize(20);
    pds.setTimeoutCheckInterval(5);
    pds.setInactiveConnectionTimeout(10);
    Properties connProps = new Properties();
    connProps.setProperty("fixedString", "false");
    connProps.setProperty("remarksReporting", "false");
    connProps.setProperty("restrictGetTables", "false");
    connProps.setProperty("includeSynonyms", "false");
    connProps.setProperty("defaultNChar", "false");
    connProps.setProperty("AccumulateBatchResult", "false");
    try (Connection conn = pds.getConnection()) {
      System.out.println(DB_URL + "    is connected");
      System.out.println("Available connections after checkout: "
          + pds.getAvailableConnectionsCount());
      System.out.println("Borrowed connections after checkout: "
          + pds.getBorrowedConnectionsCount());
    }
    catch (SQLException e) {
      System.out.println("UCPSample - " + "SQLException occurred : "
          + e.getMessage());
    }
  }

  /*
   * Creates an EMP table and does an insert, update and select operations on
   * the new table created.
   */
  public static void doSQLWork(Connection conn) {
    try {
      // conn.setAutoCommit(false);
      // Prepare a statement to execute the SQL Queries.
      Statement statement = conn.createStatement();


      String rs = "select * from TPM_ ";
      ResultSet  result = statement.executeQuery(rs);
      while (result.next()) {
        System.out.println(result.getString(1));
      }
      String qry2 = "INSERT INTO TPM_ (V1) values ('test')";
      statement.executeUpdate(qry2);
      // Create table EMP
      statement.executeUpdate("create table EMP(EMPLOYEEID NUMBER,"
          + "EMPLOYEENAME VARCHAR2 (20))");
      System.out.println("New table EMP is created");
      // Insert some records into the table EMP
      statement.executeUpdate("insert into EMP values(1, 'Jennifer Jones')");
      statement.executeUpdate("insert into EMP values(2, 'Alex Debouir')");
      System.out.println("Two records are inserted.");

      // Update a record on EMP table.
      statement.executeUpdate("update EMP set EMPLOYEENAME='Alex Deborie'"
          + " where EMPLOYEEID=2");
      System.out.println("One record is updated.");

      // Verify the table EMP
      ResultSet resultSet = statement.executeQuery("select * from EMP");
      System.out.println("\nNew table EMP contains:");
      System.out.println("EMPLOYEEID" + " " + "EMPLOYEENAME");
      System.out.println("--------------------------");
      while (resultSet.next()) {
        System.out.println(resultSet.getInt(1) + " " + resultSet.getString(2));
      }
      System.out.println("\nSuccessfully tested a connection from UCP");
    }
    catch (SQLException e) {
      System.out.println("UCPSample - "
          + "doSQLWork()- SQLException occurred : " + e.getMessage());
    }
    finally {
      // Clean-up after everything
      try (Statement statement = conn.createStatement()) {
        statement.execute("drop table EMP");
      }
      catch (SQLException e) {
        System.out.println("UCPSample - "
            + "doSQLWork()- SQLException occurred : " + e.getMessage());
      }
    }
  }
}