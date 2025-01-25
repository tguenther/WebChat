# WebChat Project

## Overview
This project is a web-based chat application.

## Prerequisites
- Docker
- Docker Compose (if applicable)

## Setup Instructions

1. Clone the repository.
2. Open the project in your preferred IDE.
3. Modify the `ChatController` class to set `PROJECT_ID` to your own Google AI project ID.

### Modifying ChatController

Open the file `src/main/java/com/example/webchat/controller/ChatController.java` and set the `PROJECT_ID` variable to your own Google AI project ID.

```java
// filepath: /c:/Users/tguen/Desktop/LocalProjects/WebChat/src/main/java/com/example/webchat/controller/ChatController.java
// ...existing code...
private static final String PROJECT_ID = "your-google-ai-project-id";
// ...existing code...
```

### Creating a Google AI Project

To create a Google AI project, follow the instructions on [cloud.google.com](https://cloud.google.com/).

### Getting an API Key

1. Go to the [Google Cloud Console](https://console.cloud.google.com/).
2. Select your project or create a new one.
3. Navigate to the "APIs & Services" > "Credentials" page.
4. Click "Create credentials" and select "API key".
5. Save the API key's JSON file and rename it to `client-key.json`.

4. Build and run the project.

## Building the Docker Image
To build the Docker image for the WebChat application, run the following command in the project directory:

```sh
docker build -t webchat:latest .
```

## Running the Application
To run the application using Docker with credentials, use the following command:

```sh
docker run -p 8080:8080 -e GOOGLE_APPLICATION_CREDENTIALS=/path/to/credentials.json webchat:latest
```

For example:

```sh
docker run -p 8080:8080 -e GOOGLE_APPLICATION_CREDENTIALS=/app/client-key.json webchat:latest
```

This will start the application, map port 8080 on your local machine to port 8080 in the Docker container, and set the environment variable for Google application credentials.

## Stopping the Application
To stop the running Docker container, first find the container ID using:

```sh
docker ps
```

Then stop the container using:

```sh
docker stop <container_id>
```

Replace `<container_id>` with the actual container ID.

## Additional Information
For more details on using Docker, refer to the [Docker documentation](https://docs.docker.com/).
