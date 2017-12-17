# Setup

Download and install a <a href="https://git-scm.com/download/win">GIT</a> client. After installing start the "Git Bash" 
and navigate to your workspace (some directory on your PC).

Execute

`git clone https://github.com/dasmo90/texting`

to copy the code to your local PC. Then change into the project's directory running

`cd texting`.

# Build

To build the project, download and install <a href="https://maven.apache.org/install.html">Maven</a> and run:

`mvn clean package`

_Hint: The first execution could take some minutes._ 

## Starting the server

To start the server run:

`java -jar texting-server/target/texting-server-1.0-SNAPSHOT.jar`

Now, the server is available on <a href="http://localhost:8080">http://localhost:8080</a>.

## Starting the client

To start the client just