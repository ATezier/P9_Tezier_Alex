# Medilabo Project

Medilabo is an application designed to manage patient data, medical reports, and risk analysis, with a focus on diabetes screening.

## Microservices Overview

- **microservice_patient**: Handles CRUD operations for patient data. Utilizes MySQL database for storage. Provides various REST endpoints for managing patient information, including:
    - `/patient/list`: Retrieves a list of patients.
    - `/patient/get`: Retrieves a patient by first name and last name.
    - `/patient/get/{id}`: Retrieves a patient by ID.
    - `/patient/create`: Creates a new patient.
    - `/patient/update/{id}`: Updates an existing patient.
    - `/patient/delete/{id}`: Deletes a patient.

- **microservice_report**: Manages medical reports associated with patients. Utilizes MongoDB for storage. Provides REST endpoints for creating, updating, deleting, and retrieving reports, as well as fetching report history for a specific patient, including:
    - `/report/list`: Retrieves a list of reports.
    - `/report/get/{id}`: Retrieves a report by ID.
    - `/report/create`: Creates a new report.
    - `/report/update/{id}`: Updates an existing report.
    - `/report/delete/{id}`: Deletes a report.
    - `/report/getByPid/{pid}`: Retrieves the medical report history of a patient based on the patient ID.

- **microservice_risk_analyser**: Calculates risk levels for diabetes based on medical reports, patient age, and gender. Provides a REST endpoint for determining the risk level for a given patient, accessible via:
    - `/risk-analyser/{pid}`: Calculates the risk level for a specific patient ID.

- **gateway**: Manages traffic and implements basic authentication.

- **microservice_ui**: Provides a user interface for interacting with patient data and reports. The UI interface offers the following endpoints:
    - `/patient/list` (homepage): GET request to display a list of patients.
    - `/patient/get/{id}`: GET request to display information about a patient, including medical reports and risk level.
    - `/patient/add`: GET and POST requests for adding a new patient.
    - `/patient/update/{id}`: GET and POST requests for updating an existing patient.
    - `/patient/delete/{id}`: GET request for deleting a patient.
    - `/report/add/{pid}`: GET request to add a new medical report for a specific patient.
    - `/report/add`: POST request to add a new medical report.
    - `/report/update/{rid}`: GET and POST requests for updating an existing medical report.
    - `/report/delete/{pid}/{rid}`: GET request for deleting a medical report.

- **microservice_ui**: Provides a user interface for interacting with patient data and reports.

## Prerequisites

Before deploying the Medilabo project using Docker, ensure the following steps are completed:

1. **Externalize Database Credentials**: Update the `application.properties` files in `microservice_patient` and `microservice_report` with the appropriate database credentials.
2. **Compile Microservices**: Compile the microservices using the `mvn package` command, ensuring dependencies are compiled in the correct order. Since `microservice_ui` and `microservice_risk_analyser` depend on `microservice_patient` and `microservice_report`, ensure that these services are up and running before compiling `microservice_ui` and `microservice_risk_analyser`.
3. **Execute Docker Compose**: Deploy the Medilabo project with Docker using the provided `docker-compose.yml` file.

Following these steps ensures that all dependencies are met and that the microservices are compiled and deployed in the correct order, resulting in a functional deployment of the Medilabo project for diabetes risk screening.