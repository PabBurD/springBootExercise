# Spring Boot Rest Exercise

## Datasource configuration

In this project we have decided to use a embedded H2 database. Here is our configuration.

```
spring.h2.console.enabled=true
spring.h2.console.path=/h2

spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.username=sa
spring.datasource.password=
spring.datasource.driver-class-name=org.h2.Driver

spring.jpa.hibernate.ddl-auto=create-drop
```
## Application model

Following the requerements, our model has three tables.

The first one called Account, has five attributes:

- Id (Long, sequential)
- Name (String)
- Currency (Currency)
- Balance (Money)
- Treasury (Boolean)

Also, we have created Currency table. This one only has a single attribute (Id) and it will be used as currency catalog.
When the application starts, we have an initial script that loads all the catalogs available.

```
INSERT INTO CURRENCY VALUES ('EUR');
INSERT INTO CURRENCY VALUES ('USD');
INSERT INTO CURRENCY VALUES ('GBP');
```

Finally, we have created Money table. In this case, we created a sequential id for unique balance for each account and another attribute called balance where we set the account balance value.

## DTOs

We have created three DTOs for send needed data for our bussines logic.

### AccountDto
- Id (Long)
- Name (String)
- Currency (String)
- Balance (Double)
- Treasury (Boolean)

### TransferDto

- Origin Account Dto (AccountDto)
- Destination Account Dto (AccountDto)
- Amount (Double)

### TransferParamDto

- Origin Account Id (Long)
- Destination Account Id (Long)
- Amount (Double)

## API endpoints

To achieve all the requeriments, we have created four endpoints to request.

- GET: /accountapi/account/{id} -> Finds a single account. Return an AccountDTO with all data from searched account. 
- POST: /accountapi/account -> In request body, we will send a DTO with all needed data for create an account. Return an AccountDto with all data from created account.
- PUT: /accountapi/account/{id} -> In request body, we will send a DTO with all needed data for update an account. Return an AccountDto with all data from updated account.
- PUT: /accountapi/transfer -> In request body, we will send a DTO with account origin id, account destination id and amount to send between accounts. Return a TransferDto with all data related to money transaction between accounts.

## Service

Firstly, we have created an interface where we will define all our service methods. After that, we're going to implement this methods with our application business logic.

- findAccount(Long id);
- createAccount(AccountDto accountDto);
- updateAccount(Long id, AccountDto accountDto);
- doMoneyTransfer(TransferParamDto transferParamDto);

## Test

For testing, we used the Eclipse plugin EclEmma to check code coverage by all test.

After execute both units and components test, we reached 99,1% code coverage.

Also, in component test, we have used the annotation @Sql which we can execute our custom sql script for a single test.

