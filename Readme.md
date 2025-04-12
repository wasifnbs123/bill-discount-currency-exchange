
# 💸 Currency Discount Application

This is a Spring Boot-based RESTful service that calculates a discounted bill for a user and converts the total into a specified currency. Discounts are applied based on user type and item category.

---

## ✅ Features

- Role-based discounts:
  - 30% for Employees
  - 10% for Affiliates
  - 5% for Customers with >2 years
- $5 off every $100 on the bill (applied after percentage discounts)
- Grocery items **excluded** from percentage-based discounts
- Currency conversion via [ExchangeRate API](https://www.exchangerate-api.com)
- HTTP Basic Authentication for `/api/**` endpoints

---

## 🔒 Authentication

Basic Authentication is required for all `/api/**` endpoints.

### Default Credentials

| Username | Password   |
|----------|------------|
| `admin`  | `admin123` |

### Example (with `curl`):

```bash
curl -u user:password -X POST http://localhost:8080/api/calculate   -H "Content-Type: application/json"   -d '{
    "userType": "CUSTOMER",
    "customerYears": 3,
    "originalCurrency": "USD",
    "targetCurrency": "EUR",
    "items": [
      { "name": "Banana", "category": "GROCERY", "price": 100 },
      { "name": "TV", "category": "NON_GROCERY", "price": 300 }
    ]
  }'
```

---

## 📦 Technology Stack

- Java 17
- Spring Boot 3.4.4
- Spring Security (Basic Auth)
- Spring Validation
- Spring Cache (in-memory)
- JUnit 5 + Mockito
- Maven

---

## 🧾 REST API

### POST `/api/calculate`

**Request Body:**

```json
{
  "userType": "CUSTOMER",
  "customerYears": 3,
  "originalCurrency": "USD",
  "targetCurrency": "EUR",
  "items": [
    { "name": "Banana", "category": "GROCERY", "price": 100 },
    { "name": "TV", "category": "NON_GROCERY", "price": 300 }
  ]
}
```

**Response:**

```json
{
  "originalTotal": 400.0,
  "discountedTotal": 370.0,
  "finalTotalInTargetCurrency": 340.5
}
```

---

## ▶️ Running the Application

```bash
./mvnw clean spring-boot:run
```

The app runs on [http://localhost:8080](http://localhost:8080)

---

## 🧪 Testing & Coverage

To run unit tests:

```bash
./mvnw test
```

## Generate Test Coverage Report (Jacoco)
```bash
mvn clean verify
```
- Report output: `target/site/jacoco/index.html`

## Auto-formatting Java Code with Spotless
Ensure consistent code formatting using Google's Java Style Guide.

### Check Formatting
```bash
mvn spotless:check
```
Fails the build if formatting violations are found.

### Auto-format Code
```bash
mvn spotless:apply
```
Automatically fixes style issues.

## Build from Command Line
```bash
mvn clean install
```

## Static Code Analysis
```bash
mvn spotless:check       # Lint check (auto-fixable with apply)
```

---

Includes JUnit tests for:
- `BillDiscountService`
- `CurrencyExchangeService` (mocking external API)
- `BillController` (using MockMvc with auth)

---

## 💾 Caching

Currency exchange rates are cached via `@Cacheable("exchangeRates")` to reduce external API calls. In-memory cache is used by default.

---

## 🧩 UML Diagram

Core Classes:
- Rest Controller: `BillController`
- DTOs: `ItemBillRequest`, `Item`, `ItemBillResponse`
- Enums: `UserType`
- Services: `BillDiscountService`, `CurrencyExchangeService`
- Exception Handling: `GlobalExceptionHandler`
- Bean : `AppConfig`
- Authentication: `SecurityConfig` (for Basic Auth)


---

## 📝 Note

Make sure to replace the exchange rate API key in `CurrencyExchangeService` if needed.

---
