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
3. If nodes.txt file is not loading then put extra space after port number in it.

