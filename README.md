# ITINERARIES MICRO SERVICE

Micro service connect to Neo4j DB to calculate the shortest routes.

# Technologies

* Spring Boot 2
* Spring Data Neo4j
* Neo4j DB

## Route service
Service responsible to create routes and return itineraries

Method	| Path	| Description	
------------- | ------------------------- | ------------- |
POST | /routes/	| Create a new route

- Example json API POST:
```
[
  {
    "from": "Sao Paulo",
    "destiny": "Macapa",
    "departureTime": "9:00",
    "arrivalTime": "11:00"
  },
  {
    "from": "Sao Paulo",
    "destiny": "Salvador",
    "departureTime": "9:00",
    "arrivalTime": "19:00"
  }
]
```

- Calculate the itineraries

Method	| Path	| Description	
------------- | ------------------------- | ------------- |
GET	| /itineraries/shortest-path-in-time?from={fromCity}&to={destinyCity}	| Return Shortest Way in Time
GET	| /itineraries/shortest-path?from={fromCity}&to={destinyCity}	| Return Shortest Way in Number of Connections

-Example json API GET calculateShortestTime:
```
{
  "arrivalCity": "Sao Paulo",
  "departureCity": "Salvador",
  "totalHours": 5
}
```

-Example json API GET calculateShortestConnections:
```
{
  "arrivalCity": "Sao Paulo",
  "departureCity": "Salvador",
  "totalConnections": 1
}
```

##### How to run

## To run you need the follow technologies installed
- Java 11
- Maven 3.3 or higher
- Docker and Docker Compose

### Running tests

```
mvn -f app/pom.xml clean test

```

## Building the project
Example:
```
mvn -f app/pom.xml clean package

```

## Running

First: Copy the file in the folder **app/src/test/resources/apoc-4.1.0.1-all.jar** to your **$HOME/neo4j/plugins**

Then :
```
docker-compose -f infra/docker-compose.yml up -d
mvn spring-boot:run
```
- After the services started we can create some routes to test
Example:
```
curl -X POST "http://localhost:8081/routes" -H "accept: */*" -H "Content-Type: application/json" -d "{ \"from\" : \"Sao Paulo\",\"destiny\" : \"Macapa\",\"departureTime\" : \"9:00\",\"arrivalTime\" : \"11:00\"}"
curl -X POST "http://localhost:8081/routes" -H "accept: */*" -H "Content-Type: application/json" -d "{ \"from\" : \"Macapa\",\"destiny\" : \"Rio de Janeiro\",\"departureTime\" : \"9:00\",\"arrivalTime\" : \"11:00\"}"
curl -X POST "http://localhost:8081/routes" -H "accept: */*" -H "Content-Type: application/json" -d "{ \"from\" : \"Rio de Janeiro\",\"destiny\" : \"Salvador\",\"departureTime\" : \"12:00\",\"arrivalTime\" : \"16:00\"}"
curl -X POST "http://localhost:8081/routes" -H "accept: */*" -H "Content-Type: application/json" -d "{ \"from\" : \"Sao Paulo\",\"destiny\" : \"Salvador\",\"departureTime\" : \"9:00\",\"arrivalTime\" : \"19:00\"}"
```

## Stopping
```
docker-compose -f infra/docker-compose.yml stop
```
## Requests Examples

```
Shortest Connections
http://localhost:8081/itineraries/shortest-path?from=Sao%20Paulo&to=Salvador

{
  "departureCity": "Sao Paulo",
  "arrivalCity": "Salvador",
  "totalConnections": 1
}
```

```
Shortest Time
http://localhost:8081/itineraries/shortest-path-in-time?from=Sao%20Paulo&to=Salvador

{
  "departureCity": "Sao Paulo",
  "arrivalCity": "Salvador",
  "totalHours": 8
}
```
