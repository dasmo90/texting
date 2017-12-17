# Setup

Download and install a <a href="https://git-scm.com/download/win">GIT</a> client. After installing start the "Git Bash" 
and navigate to your workspace (some directory on your PC).

Execute

`git clone https://github.com/dasmo90/texting.git`

to copy the code to your local PC. Then change into the project's directory running

`cd texting`.

# Build

To build the project, download and install <a href="https://maven.apache.org/install.html">Maven</a> and run:

`mvn clean package`

_Hint: The first execution could take some minutes._ 

## Starting the server

To start the server run:

`mvn spring-boot:run`

Now, the server is available on <a href="http://localhost:8080">http://localhost:8080</a>.

## Starting the client

To start the client run:

`mvn clean package -Pdev-server`

Now, the client is available on <a href="http://localhost:8088">http://localhost:8088</a>.
