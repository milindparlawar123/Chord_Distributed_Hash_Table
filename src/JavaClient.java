/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

// Generated code

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.thrift.TException;
import org.apache.thrift.transport.TSSLTransportFactory;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TSSLTransportFactory.TSSLTransportParameters;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;

public class JavaClient {
  public static void main(String [] args) {

    try {
      TTransport transport=null;
      boolean flag=false;
      if (args.length ==3) {
 
        transport = new TSocket(args[1], Integer.valueOf(args[2]));
        transport.open();
      }
      else {
    	  System.out.println("please provide required argumnets : ip/ port");
    	  System.exit(0);
      }
 
      TProtocol protocol = new  TBinaryProtocol(transport);
      FileStore.Client client = new FileStore.Client(protocol);

      perform(client);

      transport.close();
    } catch (TException x) {
      x.printStackTrace();
    } 
  }

  private static void perform(FileStore.Client client) throws TException
  {
	  writingTestCasePositive(client);
      System.out.println(".................................................................");
	  readTestCasePositive(client);
	  System.out.println(".................................................................");
  

  }
  private static void writingTestCasePositive(FileStore.Client client) throws TException, SystemException   {
	  RFileMetadata fileMetadata= new RFileMetadata();
	  String fileName="testing.txt";
	  fileMetadata.setFilename(fileName);
	  RFile rFile = new RFile();
	  rFile.setMeta(fileMetadata);
	
	  rFile.setContent(readFileFromSystem(fileName));
	  

	  System.out.println("write to node is requested and hash key of file is "+ getSHA256(fileName));
		client.writeFile(rFile);
	  
  }
  private static void readTestCasePositive(FileStore.Client client) throws TException, SystemException   {
		
	  
	    String fileName="testing.txt";
		RFile rFile=client.readFile(fileName);
		System.out.println("succsessfully read done : ");
		String key=getSHA256(fileName);
		System.out.println("key : "+key +" | "+"File Name : "+" | "  +rFile.getMeta().getFilename() +" | "+ " Version : "+ rFile.getMeta().getVersion() +" | "+" Content : "+ rFile.getContent());
	  
  }
  public static String readFileFromSystem(String fName) {
	  String data="";
	  File file= new File(fName);
	  
	  try {
		BufferedReader fileReader= new BufferedReader(new FileReader(file) );
		String l=null;
		while( null != (l=fileReader.readLine())) {
			data=data+l+"\n";
		}
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	  
	  
	  return data;
	  
  }
  public static  String getSHA256(String str)  {
		MessageDigest messageDigest= null;
		try {
			messageDigest = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		StringBuilder hstr= null;
		try {
			hstr = new StringBuilder(
					new BigInteger(1, messageDigest.digest(str.getBytes(StandardCharsets.UTF_8))).toString(16));
			
			for(int i =hstr.length() ;i<= 31 ; i++) {
				hstr.insert(0, '0');	
			}
		} 
		catch (NumberFormatException e) {
			e.printStackTrace();
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return hstr.toString();
	} 
}
