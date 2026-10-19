<p align="center">
  <img src="assets/logo.png" alt="Asset Manager logo" width="180">
</p>

# Artifact Manager

## Overview

Artifact Manager is a Spring Boot application designed to manage artifacts and their associated images. The application provides a RESTful API for creating, updating, and deleting artifacts along with their metadata and images.

### Features

- Store artifact details including manufacturer, name, date of manufacture, description, original price, current value, and serial number
- Associate multiple images with each artifact
- Add descriptions to each image
- Store images on disk rather than in the database
- RESTful API for all operations

## Prerequisites

- Java 17 or higher
- Maven 3.6.0 or higher
- Docker and Docker Compose (for containerized deployment)
- Sufficient disk space for storing artifact images

## Building and Running Locally

### Clone the Repository

```bash
git clone https://github.com/yourusername/artifact-manager.git
cd artifact-manager
```

### Build the Application

```bash
mvn clean package
```

### Run the Application

```bash
java -jar target/artifact-manager-0.0.1-SNAPSHOT.jar
```

By default, the application will:
- Use an in-memory H2 database
- Store images in the `./uploads` directory
- Run on port 8080

You can access the application at http://localhost:8080

## Docker Deployment

### Build the Docker Image

```bash
docker build -t artifact-manager:latest .
```

### Run with Docker Compose

Docker Compose is configured to mount a volume for persistent image storage:

```bash
docker-compose up -d
```

This will:
- Build and start the application container
- Mount the `./uploads` directory from the host to `/app/uploads` in the container
- Expose the application on port 8080

### Custom Image Storage Location

To use a different location for storing images, modify the volume mapping in `docker-compose.yml`:

```yaml
volumes:
  - /your/custom/path:/app/uploads
```

## API Documentation

### Artifact Endpoints

#### Create a New Artifact

- **Endpoint**: `POST /api/artifacts`
- **Content-Type**: `application/json`
- **Request Body**:
```json
{
  "manufacturer": "String",
  "name": "String",
  "dateOfManufacture": "YYYY-MM-DD",
  "description": "String",
  "originalPrice": 0.0,
  "currentValue": 0.0,
  "serialNumber": "String"
}
```
- **Response**: Returns the created artifact with an assigned ID

#### Get All Artifacts

- **Endpoint**: `GET /api/artifacts`
- **Response**: Returns a list of all artifacts

#### Get an Artifact by ID

- **Endpoint**: `GET /api/artifacts/{id}`
- **Response**: Returns the artifact with the specified ID

#### Update an Artifact

- **Endpoint**: `PUT /api/artifacts/{id}`
- **Content-Type**: `application/json`
- **Request Body**: Same as for creating a new artifact
- **Response**: Returns the updated artifact

#### Delete an Artifact

- **Endpoint**: `DELETE /api/artifacts/{id}`
- **Response**: Returns a success message

### Artifact Image Endpoints

#### Add an Image to an Artifact

- **Endpoint**: `POST /api/artifacts/{artifactId}/images`
- **Content-Type**: `multipart/form-data`
- **Form Parameters**:
  - `file`: The image file
  - `description`: Description for the image
- **Response**: Returns the created image metadata

#### Get All Images for an Artifact

- **Endpoint**: `GET /api/artifacts/{artifactId}/images`
- **Response**: Returns a list of all images associated with the artifact

#### Get an Image by ID

- **Endpoint**: `GET /api/artifacts/{artifactId}/images/{imageId}`
- **Response**: Returns the image metadata with the specified ID

#### Update Image Description

- **Endpoint**: `PUT /api/artifacts/{artifactId}/images/{imageId}`
- **Content-Type**: `application/json`
- **Request Body**:
```json
{
  "description": "New description"
}
```
- **Response**: Returns the updated image metadata

#### Delete an Image

- **Endpoint**: `DELETE /api/artifacts/{artifactId}/images/{imageId}`
- **Response**: Returns a success message

#### Get Image File

- **Endpoint**: `GET /api/artifacts/{artifactId}/images/{imageId}/file`
- **Response**: Returns the image file with appropriate content type

## Configuration Options

The application can be configured using environment variables or by modifying the `application.properties` file.

### Main Configuration Options

| Property | Default | Description |
|----------|---------|-------------|
| `spring.datasource.url` | `jdbc:h2:mem:artifactdb` | Database connection URL |
| `spring.datasource.username` | `sa` | Database username |
| `spring.datasource.password` | ` ` | Database password |
| `spring.jpa.hibernate.ddl-auto` | `update` | Hibernate DDL auto strategy |
| `storage.location` | `uploads` | Location for storing images |
| `server.port` | `8080` | Server port |

### Environment Variables

When running with Docker, you can set these environment variables in the `docker-compose.yml` file:

```yaml
environment:
  - SPRING_DATASOURCE_URL=jdbc:h2:mem:artifactdb
  - SPRING_DATASOURCE_USERNAME=sa
  - SPRING_DATASOURCE_PASSWORD=
  - STORAGE_LOCATION=/app/uploads
  - SERVER_PORT=8080
```

### Using a Different Database

To use a different database like MySQL or PostgreSQL:

1. Add the appropriate database driver to the `pom.xml`
2. Configure the datasource properties with your database connection details
3. Rebuild the application

Example for MySQL:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/artifactdb
spring.datasource.username=root
spring.datasource.password=password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
```

## Troubleshooting

### Image Upload Issues

- Ensure the upload directory exists and has appropriate permissions
- Check the maximum file size settings in `application.properties`

### Database Connectivity

- Verify database connection details
- Ensure the database server is running and accessible

## License

This project is licensed under the MIT License - see the LICENSE file for details.

# asset_manager
