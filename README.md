# file-viewer-app

The application serves as a file-viewer for dropbox backend based upon your dropbox account's access-key issued.

The application consists of two components:
1. REST based backend service, which communicates with Dropbox with their client API. It does the following:
   a. Lists the files and folder inside a given folder of your dropbox
   b. Able to download any file from a folder.
   c. Able to upload any file to the given folder inside dropbox
2. UI application is provided with screens to be able to view the list of files in a given folder and to be able to download files
   using a click, also a form to upload file in a given folder
   
   It a spring boot based application, service and ui apps run independently. We need to provide configurations about the service and ui app's
   port number, service root and intermediate folder where downloads and uploads are handled.
   
   To build the service you require to run mvn clean install on the file-viewer-service project
   
   
   The jar will be built file-viewer-service.jar
   This can be executed using 
   
   
   java -jar file-viewer-service.jar --Dspring.config.location=file:file-viewer-service.properties
   
   The UI application can be build similiar to service project and it is available as file-viewer-ui.war.
   This can be executed using 
   
   
   java -jar file-viewer-ui.war --Dspring.config.location=file:file-viewer-ui.properties
   
   If we built the root of the project, both jar and war will be built.

   
