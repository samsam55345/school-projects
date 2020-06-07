# 4061_project4
/* test machine: CSEL-KH4250-18 
 * date: 12/04/19
 * name: Orion Grebe , Sami Frank
 * x500: grebe013 , fran0942 */
 
<p>Orion Grebe, Sami Frank</p>
<p>grebe013,    fran0942</p>

<p>Compilation:</p>
<p>To compile the server, please navigate to the PA4_SERVER folder in a terminal and type 'make clean' and then 'make'.</p> 
<p>To compile the client, in a seperate terminal or computer, please navigate to PA4_CLIENT folder and type 'make clean' and then 'make'</p> 
  
<p>How to run:</p>
<p>To run the program successfully, you must begin running the server before the client. In the PA4_SERVER folder while in the terminal, type ./server <port_number>. The port number is any unsigned integet.</p> 
<p><To run the client, in your seperate terminal or computer, in the PA4_CLIENT folder type ./client <Folder Name> <# of Mappers> <Server IP> <Server Port>. The folder name is the name of the root folder to be traversed, the # of mappers indicates the number of mapper processes to be spawned by the master, server IP is the IP address of the server to connect to, and the server port is the same port number that you used to launch the server.</p> 

<p>Contributions:</p>
<p><pre>Orion: client.c</pre></p> 
<p><pre>Sami: server.c</pre></p> 

<p>Assumptions:</p>
<p><pre>No assumptions outside the project document were made. </pre></p> 
 
<p>Issues:</p>
<p>Our program unfortunately does not work correctly. While on the client side, we are able to fork the processes just fine and get the corrct ID's, the server side somehow ends up reading that the mapper ID equals 0 after the first connection, despite us not altering this ID value at all throughout our code. </p> 

<p>We did not attempt extra credit. </p>
