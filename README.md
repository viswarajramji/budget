
# Budget Management System

## Overview

This budget management system is designed to handle budget creation, updates, deletion, and querying operations. The system leverages an event-driven architecture with Kafka for handling expense-related events and integrates with a relational database for persistence.

## Steps to Run the Application

1. **Clone the Repository**:  
   Clone the repository to your local machine using:
   ```
   git clone <repository-url>
   ```

2. **Navigate to the Project Directory**:  
   Move into the project directory:
   ```
   cd <project-directory>
   ```

3. **Install Dependencies**:  
   Install all necessary dependencies using Maven:
   ```
   mvn clean install
   ```

4. **Set Up the Database**:  
   Ensure your database is up and running. Update the database configurations in the `application.properties` or `application.yml` file as per your local setup.

5. **Start Kafka**:  
   Make sure Kafka is running. Configure the Kafka topics `budgetservice` and `notificationservice` as needed.

6. **Run the Application**:  
   Start the application using:
   ```
   mvn spring-boot:run
   ```

7. **Access the Application**:  
   The application will be running on `http://localhost:8080`. You can interact with the budget management system via the exposed REST APIs.

## Architecture Diagrams

### Event-Driven Flow

This diagram illustrates how various events consumed from Kafka are processed by the Budget Service, which subsequently updates the budget data and produces notifications if necessary.

```mermaid
graph TD
    
    KC[Kafka Consumer] -->|Consume 'Create Expense Event'| CEE[Create Expense Event]
    KC -->|Consume 'Delete Expense Event'| DEE[Delete Expense Event]
    KC -->|Consume 'Update Expense Event'| UEE[Update Expense Event]
    KC -->|Consume 'Delete User Event'| DUE[Delete User Event]

    CEE --> BS[Budget Service]
    DEE --> BS
    UEE --> BS
    DUE --> BS

    BS -->|Handle Create Expense Event| UBSC[Update Budget Spend Command Creator]
    BS -->|Handle Delete Expense Event| UBSC
    BS -->|Handle Update Expense Event| UBSC
    BS -->|Handle Delete User Event| DUEE[Delete User Event Executor]

    UBSC -->|Create Command| UBSAC[Update Budget Spend Amount Internal Command]
    UBSAC --> BS[Budget Service]
    
    BS --> UBSAE[Update Budget Spend Amount Executor]

    UBSAE -->|Update in Database| BR[Budget Repository]
    BR --> BE[Budget Entity]
    
    BE -->|Budget Data Updated| DB[(Database)]
    
    UBSAE -.->|Budget Exceeded?| BEE[Budget Exceed Event]

    BEE -->|Publish to 'notificationservice' Topic| KProd[Kafka Producer for Notification Service]
    KProd -->|Handle Notifications| NS[Notification Service]
    DUEE -->|Delete User Data| BR



```

### Command and Query Handling

This diagram represents how commands and queries are handled by the system. The `Budget Command Controller` and `Budget Query Controller` interact with the `Budget Service`, which dispatches tasks to respective executors that manipulate the `Budget Entity` via the `Budget Repository`.

```mermaid
graph TD
    CC[Budget Command Controller] -->|Create| CBC[Create Budget Command]
    CC -->|Update| UBC[Update Budget Command]
    CC -->|Delete| DBC[Delete Budget Command]

    CBC --> BS[Budget Service]
    UBC --> BS
    DBC --> BS

    BS --> CBE[Create Budget Executor]
    BS --> UBE[Update Budget Executor]
    BS --> DBE[Delete Budget Executor]

    CBE -->|Save to| BR[Budget Repository]
    UBE -->|Update in| BR
    DBE -->|Delete from| BR

    BR --> BE[Budget Entity]

    QC[Budget Query Controller] -->|Get by User ID| GBUI[Get Budget By User ID Query]
    QC -->|Get All| GABQ[Get All Budgets Query]

    GBUI --> BS
    GABQ --> BS

    BS --> GBUIE[Get Budget By User ID Executor]
    BS --> GABQE[Get All Budgets Query Executor]

    GBUIE -->|Fetch from| BR
    GABQE -->|Fetch from| BR

    BR <--> BE[Budget Entity]
```

## Key Components

### Commands

Commands represent actions that modify the state of the system. Below are the key command classes used in this system:

#### CreateBudgetCommand

```mermaid
classDiagram
    class CreateBudgetCommand {
        +Long userId
        +Category budgetType
        +Double amount
    }
```

Handles the creation of a new budget.

#### UpdateBudgetCommand

```mermaid
classDiagram
    class UpdateBudgetCommand {
        +Long budgetId
        +Long userId
        +Category budgetType
        +Double amount
    }
```

Handles the update of an existing budget.

#### DeleteBudgetCommand

```mermaid
classDiagram
    class DeleteBudgetCommand {
        +Long budgetId
    }
```

Handles the deletion of a budget.

#### UpdateBudgetSpendAmountInternalCommand

```mermaid
classDiagram
    class UpdateBudgetSpendAmountInternalCommand {
        +RecordType recordType
        +Long expenseId
        +Long userId
        +String expenseName
        +String expenseDescription
        +Category expenseType
        +Double amount
        +Double actualAmount
    }
```

Handles the internal update of the budget spend amount, triggered by expense-related events.

### Entities

Entities represent the core data models within the system. The primary entity in this system is:

#### Budget Entity

```mermaid
classDiagram
    class Budget {
        +Long id
        +Long userId
        +Category budgetType
        +Double amount
        +Double spent
    }
```

Represents a budget, including attributes such as `userId`, `budgetType`, `amount`, and `spent`.

### Events

Events represent changes or significant occurrences within the system that other components might need to respond to. The key event classes include:

#### CreateExpenseEvent

```mermaid
classDiagram
    class CreateExpenseEvent {
        +Long expenseId
        +Long userId
        +String expenseName
        +String expenseDescription
        +Category expenseType
        +Double amount
    }
```

Triggered when a new expense is created.

#### DeleteExpenseEvent

```mermaid
classDiagram
    class DeleteExpenseEvent {
        +Long expenseId
        +Long userId
        +String expenseName
        +String expenseDescription
        +Category expenseType
        +Double amount
    }
```

Triggered when an expense is deleted.

#### UpdateExpenseEvent

```mermaid
classDiagram
    class UpdateExpenseEvent {
        +Long expenseId
        +Long userId
        +String expenseName
        +String expenseDescription
        +Category expenseType
        +Double newAmount
        +Double diffAmount
    }
```

Triggered when an expense is updated.

#### DeleteUserEvent

```mermaid
classDiagram
    class DeleteUserEvent {
        +Long userId
    }
```

Triggered when a user is deleted, leading to the deletion of associated budgets.

#### BudgetExceedEvent

```mermaid
classDiagram
    class BudgetExceedEvent {
        +Long expenseId
        +Long userId
        +String expenseName
        +String expenseDescription
        +String expenseType
        +Double amount
        +Double actualAmount
        +String recordType
        +Long budgetId
        +String budgetType
        +Double budgetAmount
        +Double budgetSpent
        +String emailId
    }
```

Triggered when the spent amount exceeds the budget, leading to a notification being sent.

## Conclusion

This `README.md` provides an overview of the architecture, components, and data flow in the budget management system. The system is designed to handle budget-related operations efficiently using an event-driven approach for expense events and direct command handling for budget management tasks.
