# cs457-557-fall2020-pa2-milindparlawar123
cs457-557-fall2020-pa2-milindparlawar123 created by GitHub Classroom

Steps to run the project:
Make sure that you are in cs457-557-fall2020-pa2-milindparlawar123 project folder.

1.thrift -gen java chord.thrift     --> to generate gen-java
2.make                              --> to compile
3. ./server.sh  < port number>      --> to start the node
4. ./init nodes.txt                 --> to populate finger table
5. ./client.sh < ip> <port number>  --> to start the client 
6. control + c                      --> to stop the node/server
  
Brief Description :
1. I have placed testing.txt file in project folder where client can read it.
2. If you want to write other files to node/server, please place file in project main folder. Change the fileName in JavaClient.java file.

Input - Output:

Node/server :  
mparlaw1@remote01:~/milind/cs457-557-fall2020-pa2-milindparlawar123$ ./server.sh 9002
My node hash value : d9dc3780c557f6d00f64dfc981afba864364693cb91b5b439398eebda61e75bf
......................................................................
Started the simple server...
finger table loaded
......................................................................

Client : 
1st run :
mparlaw1@remote07:~/milind/cs457-557-fall2020-pa2-milindparlawar123$ ./client.sh 128.226.114.201 9002
write to node is requested and hash key of file is 6097aeb067f3d1c10f51ca8dde3966b11202ecf023ef584af248fa9fa5c54fad
.................................................................
succsessfully read done : 
key : 6097aeb067f3d1c10f51ca8dde3966b11202ecf023ef584af248fa9fa5c54fad | File Name :  | testing.txt |  Version : 0 |  Content : hi i am 
milind 

.................................................................

2nd run :

mparlaw1@remote07:~/milind/cs457-557-fall2020-pa2-milindparlawar123$ ./client.sh 128.226.114.201 9002
write to node is requested and hash key of file is 6097aeb067f3d1c10f51ca8dde3966b11202ecf023ef584af248fa9fa5c54fad
.................................................................
succsessfully read done : 
key : 6097aeb067f3d1c10f51ca8dde3966b11202ecf023ef584af248fa9fa5c54fad | File Name :  | testing.txt |  Version : 1 |  Content : hi i am 
milind 
.................................................................

If finger table is not loaded
mparlaw1@remote07:~/milind/cs457-557-fall2020-pa2-milindparlawar123$ ./client.sh 128.226.114.201 9002
write to node is requested and hash key of file is 6097aeb067f3d1c10f51ca8dde3966b11202ecf023ef584af248fa9fa5c54fad
SystemException(message:finger table is empty, please run init script)
	at FileStore$writeFile_result$writeFile_resultStandardScheme.read(FileStore.java:1686)
	at FileStore$writeFile_result$writeFile_resultStandardScheme.read(FileStore.java:1672)
	at FileStore$writeFile_result.read(FileStore.java:1622)
	at org.apache.thrift.TServiceClient.receiveBase(TServiceClient.java:88)
	at FileStore$Client.recv_writeFile(FileStore.java:79)
	at FileStore$Client.writeFile(FileStore.java:66)
	at JavaClient.writingTestCasePositive(JavaClient.java:87)
	at JavaClient.perform(JavaClient.java:69)
	at JavaClient.main(JavaClient.java:59)

