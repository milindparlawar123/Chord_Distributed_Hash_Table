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

// Generated code
import java.util.HashMap;
import java.util.List;

public class FileStoreHandler implements FileStore.Iface {

	private List<NodeID> fingerTable;
	@Override
	public void writeFile(RFile rFile) throws TException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public RFile readFile(String filename) throws TException {
		// TODO Auto-generated method stub
		System.out.println("fing tab size "+fingerTable.size());
		return new RFile();
	}

	@Override
	public void setFingertable(List<NodeID> node_list) throws TException {
		// TODO Auto-generated method stub
		fingerTable=node_list;
		System.out.println("fing tab size "+node_list.size());
	}

	@Override
	public NodeID findSucc(String key) throws TException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NodeID findPred(String key) throws TException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NodeID getNodeSucc() throws TException {
		// TODO Auto-generated method stub
		return null;
	}
	
}

