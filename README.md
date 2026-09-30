# Order Event Tracker

This is a simple Spring Boot project that exposes a small product catalog and accepts order requests.

## What it does

- `GET /api` shows the available products
- `POST /api?itemId=<id>` places an order for a valid product
- Valid product IDs are from 1 to 12

## How to run

Requirements:
- Java 21
- Maven

From the project root, run:

```bash
./mvnw spring-boot:run
```

On Windows:

```powershell
./mvnw.cmd spring-boot:run
```

Then open:

```text
http://localhost:8080/api
```

## Examples

View products:

```bash
curl http://localhost:8080/api
```

Place an order:

```bash
curl -X POST "http://localhost:8080/api?itemId=3"
```

Response:

```text
Order Placed.
```

If the ID is invalid, the app returns an error message:

```text
Invalid order id passed.
```
