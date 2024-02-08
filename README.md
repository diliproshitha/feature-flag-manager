# Feature Toggle Manager

## Quick Start
<b>Step 1:</b> Install a docker version 4.X.X. [Docker Installation](https://docs.docker.com/get-docker/) \
<b>Step 2:</b> Run command `docker-compose up` to start the application. \
<b>Step 3:</b> Once the services are up, open the browser and navigate to `http://localhost` to access the FE app. Swagger UI can be accessed at `http://localhost:8080/swagger-ui/index.html#/`

## Details
### Front end
 - Front end project can be found in `feature-flag-manager-frontend` directory.
 - Build using React and Chakra UI component library.
 - Node version: v18.16.1 NPM version: 9.5.1

### Back end
 - Back end project can be found in `feature-flag-manager-backend` directory.
 - Built using Java 17 and Spring Boot 3.
 - Postgres 16.1-alpine is used as the database.
 - Flyway is used for database migration.

If you are running the backend locally, then you need to set following environment variables and change them accordingly:
- POSTGRES_HOST=localhost
- POSTGRES_PORT=5432
- POSTGRES_DB=FEATURE_FLAG_MANAGER
- POSTGRES_USER=feature-flag-manager-user
- POSTGRES_PASSWORD=feature-flag-manager-password

If you have any questions, please feel free to reach out to me.

#### Cheers!
