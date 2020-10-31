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

import org.apache.thrift.TException;
import org.apache.thrift.transport.TTransportException;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.protocol.TBinaryProtocol;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
// Generated code
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class FileStoreHandler implements FileStore.Iface {

	private List<NodeID> fingerTable=null;
	private NodeID curNode=null;
	private Map<String, Map<String,String>> fileStorage=null;
	public static int zero=0;
	public FileStoreHandler(String ipPort) {
		// TODO Auto-generated constructor stub
		this.curNode=new NodeID(getSHA256(ipPort), ipPort.split(":")[0],Integer.parseInt( ipPort.split(":")[1]));
		fileStorage= new LinkedHashMap<String, Map<String,String>>();
	}
	
	@Override
	public void writeFile(RFile rFile) throws TException, SystemException {
		// TODO Auto-generated method stub
		//System.out.println(" in write file ");
		NodeID node = findSucc(getSHA256(rFile.getMeta().getFilename()));
		//NodeID node = findSucc("a");
		
		if(node.equals(curNode) == false) {
			SystemException systemException = new SystemException();
			throw new SystemException().setMessage("error in write file, node is not serving this file key"
					+ "please check on other node");
		}
		System.out.println("currenot "+ curNode.getId() + " | "+ curNode.getIp() + " | "+curNode.getPort());
		System.out.println("hash  >> "+getSHA256(rFile.getMeta().getFilename()) );
		//System.out.println(" milind ");
		//System.out.println(node.getId() + " | "+ node.getIp() + " | "+ node.getPort());
		//System.out.println("before version "+rFile.getMeta().getVersion()+ " map "+ fileStorage);
		if(fileStorage.containsKey(rFile.getMeta().getFilename())) {
			
			Map<String, String > newMap=fileStorage.get(rFile.getMeta().getFilename());
			int temp =Integer.parseInt(newMap.get("V"))+1;
			newMap.put("V", temp+"");
 			//	System.out.println(fileStorage);
			fileStorage.put(rFile.getMeta().getFilename(),
					newMap);	
			
		}else {
			Map<String, String> map = new LinkedHashMap<String, String>();
			map.put("V", "0");
			map.put("C",rFile.getContent() );
			fileStorage.put(rFile.getMeta().getFilename(), map);
			//System.out.println("misseddd  ");
		}
	}

	@Override
	public RFile readFile(String filename) throws TException, SystemException {
		// TODO Auto-generated method stub
		//System.out.println("inside read fing tab size "+fingerTable.size());
		
		String key = getSHA256(filename);
		NodeID nodeID= findSucc(key);
		
		if(nodeID.equals(curNode) == false) {
			SystemException systemException = new SystemException();
			throw new SystemException().setMessage("error in read file, node is not serving  this file key, please check on"
					+ "other node");
		}
		
		
		RFile rFile = new RFile();
		RFileMetadata rFileMetadata= new RFileMetadata();
		
		if(fileStorage.get(filename) != null) {
		Map<String, String> map= fileStorage.get(filename);

			rFile.setContent(map.get("C"));
			rFile.setContentIsSet(true);
			rFileMetadata.setFilename(filename);
			rFileMetadata.setFilenameIsSet(true);
			rFileMetadata.setVersion(Integer.parseInt(map.get("V")));
			rFileMetadata.setVersionIsSet(true);
			rFile.setMeta(rFileMetadata);
			rFile.setMetaIsSet(true);
			
			
		}else {
			throw new SystemException().setMessage("error in read file, requested file is not available on this node");
		}
		
		
		return rFile;
	}

	@Override
	public void setFingertable(List<NodeID> node_list) throws TException {
		// TODO Auto-generated method stub
		fingerTable=node_list;
		System.out.println("fing tab size "+node_list.size());
		for(NodeID n: node_list) {
			System.out.println(n.getId() +"|"+n.getIp()+"|"+ n.getPort()+"|");
		}
	}

	@Override
	public NodeID findSucc(String key) throws TException, SystemException {
		// TODO Auto-generated method stub
		//System.out.println("in find succ ");
		NodeID nodeID= findPred(key);
		//System.out.println(" ooo ");
		TTransport transport = null;
		FileStore.Client client = null;
		try {
		//	System.out.println(nodeID.getIp()+ " .||. "+ nodeID.getPort());
			transport = new TSocket(nodeID.getIp(), nodeID.getPort());
			transport.open();

			TProtocol protocol = new TBinaryProtocol(transport);
			client = new FileStore.Client(protocol);
		} catch (Exception e) {
			//System.err.println("Exception occured:" + e.getMessage());
			System.exit(0);
		}
	//	System.out.println(" ppp ");
		return client.getNodeSucc();
		//return getNodeSucc();

		
	}

	@Override
	public NodeID findPred(String key) throws TException, SystemException {
		// TODO Auto-generated method stub
		//System.out.println("in  findPred "+ curNode.getId());
		// chedck against first finger table entry for break condition
		if(fingerTable == null) {

			throw new SystemException().setMessage("finger table is empty, please run init script");
		
		}
		
		if(isBetween(key, curNode.getId(), fingerTable.get(zero).getId())) {
			//System.out.println(" curNode >> ");
			return curNode;
		}
		
		// if break condition did not meet then 
		// below for loop to check from bottom up 
		//System.out.println(" ishid ");
			for(int i =fingerTable.size()-1; i>zero ;i--) {
				NodeID fingNode= fingerTable.get(i);
				if(isBetween(fingNode.getId() , curNode.getId(), key)) {
	
					TTransport transport = new TSocket(fingNode.getIp(), fingNode.getPort());
					transport.open();

					TProtocol protocol = new TBinaryProtocol(transport);
					FileStore.Client client = new FileStore.Client(protocol);
					return client.findPred(key);
						
				}
			}
	
		
			//System.out.println(" return curNode");
		return curNode;
	}

	@Override
	public NodeID getNodeSucc() throws TException, SystemException {
		// TODO Auto-generated method stub
		//System.out.println(" getNodeSucc  ");
		if(fingerTable == null) {

			throw new SystemException().setMessage("finger table is empty, please run init script");
		
		}
		return fingerTable.get(zero);
	}
	

	
	public  String getSHA256(String str)  {
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
				hstr.insert(zero, '0');	
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
	
	public boolean isBetween(String key, String curId, String fingTabId) {

		if (curId.compareTo(fingTabId) < zero) {
			boolean flag1 = key.compareTo(fingTabId) > zero;
			boolean flag2 = key.compareTo(fingTabId) <= zero;
			if (flag1 && flag2) {
				return true;
			}

			return false;
		} else {
			
			boolean flag1 = key.compareTo(fingTabId) < zero && key.compareTo(fingTabId) <= zero;
			if (flag1) {
				return true;
			}
			//
			boolean flag2 = key.compareTo(fingTabId) > zero && key.compareTo(fingTabId) >= zero;
			if (flag2) {
				return true;
			}
			

			return false;
		}

		// return false;
	}
}

