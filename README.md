# Order service
Order service for my microservice project which handles all the requests regarding the order model from my frontend applications

## Case
Microservices E-commerce project consists of several microservices. Together they build an e-commerce distributed software system which includes:

## Backend
😃 User service -  see the repository [here](https://github.com/JordanRad/s3-account-service)

📦 Order service  -  see the repository [here](https://github.com/JordanRad/s3-order-service)

🏬 Product service -  see the repository [here](https://github.com/JordanRad/s3-product-service)

🌐 Discovery server -  see the repository [here](https://github.com/JordanRad/s3-discovery-server)

🔀 Gateway -  see the repository [here](https://github.com/JordanRad/s3-proxy)

## Frontend

🖥️ Customer application - see the repository [here](https://github.com/JordanRad/s3-microservices-client)

🖥️ Admin application -see the repository [here](https://github.com/JordanRad/s3-microservices-client)

## API Endpoints
```
GET:     /api/orders/getAll
GET:     /api/orders/getAllNew
GET:     /api/orders/getByOrderNumber/{orderNumber}
POST:    /api/orders/
```
## Architecture
<img height ="800" src="https://github.com/JordanRad/s3-microservices-client/blob/master/documentation/ProjectDiagram.png">

