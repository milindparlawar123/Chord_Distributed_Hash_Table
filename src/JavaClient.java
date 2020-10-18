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

import org.apache.thrift.TException;
import org.apache.thrift.transport.TSSLTransportFactory;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TSSLTransportFactory.TSSLTransportParameters;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;

public class JavaClient {
  public static void main(String [] args) {

    if (args.length != 3) {
      System.out.println("Please enter 'simple' or 'secure' ip/host port");
      System.exit(0);
    }
    System.out.println(" before try inside client "+ " arg1 "+args[1] + " args2 "+args[2]);
    try {
      TTransport transport;
      boolean flag=false;
      if (args[0].contains("simple") ) {
    	  System.out.println("inside simpple " + args[0]);
        transport = new TSocket(args[1], Integer.valueOf(args[2]));
        transport.open();
      }
      else {
        /*
         * Similar to the server, you can use the parameters to setup client parameters or
         * use the default settings. On the client side, you will need a TrustStore which
         * contains the trusted certificate along with the public key. 
         * For this example it's a self-signed cert. 
         */
    	  System.out.println("inside client "+ " arg1 "+args[1] + " args2 "+args[2]);
        TSSLTransportParameters params = new TSSLTransportParameters();
        params.setTrustStore("/home/cs557-inst/thrift-0.13.0/lib/java/test/.truststore", "thrift", "SunX509", "JKS");
        /*
         * Get a client transport instead of a server transport. The connection is opened on
         * invocation of the factory method, no need to specifically call open()
         */
        transport = TSSLTransportFactory.getClientSocket(args[1], Integer.valueOf(args[2]), 0, params);
      }

      System.out.println(" ready to binary proto ");
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
	  System.out.println("before readFile()");
     if ( null == client.readFile("")) {
    	 System.out.println("nullllll");
     }
    System.out.println("readFile()");

  }
}
