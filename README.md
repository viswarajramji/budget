
# Budget Management System

## Overview

The Budget Management System is designed to handle budget creation, updates, deletion, and querying operations. The system leverages an event-driven architecture with Kafka for handling expense-related events and integrates with a relational database for persistence.

## Sample output:

![image](https://github.com/user-attachments/assets/f29a1a07-3521-4b96-820f-abd3468fe75b)

## Project Structure

- `api`: Core API interfaces.
- `command`: Classes encapsulating command details.
- `controller`: REST controllers managing HTTP requests.
- `event`: Event classes representing system actions.
- `executors`: Business logic processors for commands.
- `kafka`: Kafka message producers and consumers.
- `model`: Business entity data models.
- `query`: Data retrieval operations.
- `repo`: Database interaction.
- `service`: Business logic and service layer.

## Process Flow Diagram

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

    BR --> DB[(Budget Database)]

    QC[Budget Query Controller] -->|Get by User ID| GBUI[Get Budget By User ID Query]
    QC -->|Get All| GABQ[Get All Budgets Query]

    GBUI --> BS
    GABQ --> BS

    BS --> GBUIE[Get Budget By User ID Executor]
    BS --> GABQE[Get All Budgets Query Executor]

    GBUIE -->|Fetch from| BR
    GABQE -->|Fetch from| BR

    BR <--> DB[(Budget Database)]

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


## Getting Started

To run the service locally:

1. **Clone the repository**:
   ```bash
   git clone https://github.com/viswarajramji/budget.git
   cd budget
   ```

2. **Build the application**:
   ```bash
   ./mvnw clean install
   ```

3. **Start the application**:
   ```bash
   ./mvnw spring-boot:run
   ```

4. **Access the application** at `http://localhost:8082`.

5. **Access the database** at `http://localhost:8082/h2-console`.

**Note**: Ensure Kafka is running and the topic `userservice` is created.

## Swagger Endpoint

Access the Swagger UI to interact with the API:

- **URL**: `http://localhost:8082/swagger-ui.html`
