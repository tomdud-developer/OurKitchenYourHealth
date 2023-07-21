
# Warning! The project in a pause state because of studies, I return to this in August when I learn more about AWS and I will start using it in this project
# About Our Kitchen - Your Health

**Our Kitchen - Your Health** is a unique **FICTIONAL** catering company that specializes in providing fresh and healthy meals for its customers. 
Their primary focus is on ensuring that their customers receive only the best quality food that is both delicious and nutritious.
To achieve this goal, they have developed a strong and reliable system that takes care of everything from ordering to delivery.

The system that Our Kitchen - Your Health uses is designed to manage all aspects of the catering process, from taking orders and managing customers to planning deliveries and supplies.

## Tech stack

To ensure high accessibility and handle a large volume of requests, I opted for a **microservice architecture** in developing the backend project.
For this purpose, I chose to use **Java** as the programming language. By using microservices, the system can be designed to handle specific tasks independently,
making it more scalable and flexible. This approach also allows for easy maintenance and updates, as each microservice can be modified without affecting the others.
Overall, the use of microservices in Java provides a reliable and efficient framework to develop a robust backend system.


| Common                     | Microservices                               | Other                   |
|:---------------------------|:--------------------------------------------|:------------------------|
| `Java`                     | `Spring Api Gateway`                        | `Docker`                |
| `Spring Boot`              | `Distributed tracing - Zipkin`              | `Postman`               |
| `JWT Token`                | `Auth Service - Keycloak`                   | `Integration with PayU` |
| `Oauth2.0 Flow`            | `Spring Oauth2 Client and Resource Service` |                         |
| `MongoDB and MongoExpress` | `Discovery Service - Eureka`                |                         |
| `PostgreSQL`               | `OpenFeign`                                 |                         |
| `Reactive programming`     | `WebClient for reactive API`                |                         |
| `Oauth2.0 Flow`            | `OKHttp and Rest client`                    |                         |
|                            | `Kafka and Zookeeper`                       |                         |

# Microservices architecture

System is designed in microservices architecture. Each microservice has own bussines responsibility.

1. API gateway service
2. Eureka discovery server
3. Keycloak auth service
4. Meals microservice
5. Orders microservice
6. Payment microservice
7. Restaurant microservice
8. Resources microservice [not done yet]
9. [There will be anothers, but they haven't devloped yet]


![Architecture_diagram.svg](assets%2FArchitecture_diagram.svg)


## Requirements
The customer is the owner of approximately 100 restaurants and serves daily diet meals over the phone. 
The demand for their services has consistently increased, and as a result, they require reliable software support to manage these orders.
The orders should be taken on a website, where clients can potentially choose their preferred meals for each day. The restaurant location, which will serve the meals, should be assigned based on the client's domicile.

The dietetics team should have the option to compose meals by adding ingredients and substances. 
These meals should be available for every client at any location.

After take an order by client, he should confirm order by clicking link in email message, after that he could process the payment in PayU. 
After payment Order Status change to success payment and it can be processed to realisation.

Once a client places an order, they must confirm it by clicking a link in an email message. Upon confirmation, they can proceed to make the payment via PayU.
After a successful payment, the order status will be updated to "success payment" and can be processed for realization.

To ensure the smooth running of operations, the restaurants must have a daily list of ingredients and meals that they need to order and prepare for the following day.
This practice allows for proper planning and organization of resources, ensuring that the restaurants are adequately stocked and prepared to meet the demands of their customers.
