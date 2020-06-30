# Coffemachine java-angular test

## requirements installed on the host machine
  - docker
  - docker-compose 
  
## Projects organizations
    - `coffemachine` is the backend java project
    - `coffemachine-angular` is the front end angular project. 
    - Each project has a Dockerfile to build an isolated image.
    - docker-complose.yml is the configuration demo's environment.
    - `builder.sh` is the script to simplify build/run/stop the demo environment.

### how to test it
   - `bash builder.sh build` script to build the docker containers.
   - `bash builder.sh setup` to initialize the backend with data.
   - `bash builder.sh run ` to execute docker-compose up.
   - `bash builder.sh stop` to stop the services.

### Endpoints
   - browser endpoint http://localhost:4200/
   - backend endpoints http://localhost:8080/api/v1/**
