# TallerMecánico API

API REST para la gestión de un taller mecánico: clientes, vehículos, mecánicos y órdenes de servicio. Proyecto de práctica construido para consolidar fundamentos de Spring Boot con una arquitectura y reglas de negocio parecidas a un proyecto real.

## Contexto

El taller donde trabaja el cliente llevaba el control de sus reparaciones en cuadernos y hojas de Excel, lo que generaba pérdida de historial, cobros mal calculados y falta de trazabilidad sobre qué mecánico atendió cada vehículo. Este backend digitaliza ese proceso.

## Tecnologías

- Java 21
- Spring Boot 3
- Spring Web
- Spring Data JPA + Hibernate
- PostgreSQL
- Bean Validation
- JUnit 5 + Mockito + AssertJ (testing)
- H2 (base de datos en memoria, solo para tests)
- Maven

## Entidades y relaciones

- **Cliente** → puede tener varios **Vehículo** (1:N)
- **Vehiculo** → puede tener varias **OrdenServicio** (1:N)
- **Mecanico** → puede tener asignadas varias **OrdenServicio** (1:N)
- **OrdenServicio** → contiene varias **LineaServicio** (1:N, con cascada)

## Cómo levantar el proyecto localmente

### Requisitos previos

- Java 21 instalado
- PostgreSQL corriendo localmente
- Maven (o usar el wrapper `./mvnw` incluido en el proyecto)

### Pasos

1. Clona el repositorio:
   ```bash
   git clone git@github.com:AlexisGRs/SpringPracticeTaller.git
   cd SpringPracticeTaller
   ```

2. Crea la base de datos en PostgreSQL:
   ```sql
   CREATE DATABASE tallermecanico_db;
   ```

3. Copia el archivo de configuración de ejemplo y ajústalo con tus propios datos:
   ```bash
   cp src/main/resources/application-example.properties src/main/resources/application.properties
   ```
   Edita `application.properties` con el usuario y contraseña de tu PostgreSQL local (o define las variables de entorno `DB_USERNAME` y `DB_PASSWORD` en tu sistema/IDE, que es lo que el archivo espera por defecto).

4. Corre la aplicación:
   ```bash
   ./mvnw spring-boot:run
   ```

   La API queda disponible en `http://localhost:8080`.

### Variables de entorno

| Variable      | Descripción                          | Valor por defecto |
|---------------|---------------------------------------|--------------------|
| `DB_USERNAME` | Usuario de PostgreSQL                 | `postgres`         |
| `DB_PASSWORD` | Contraseña de PostgreSQL              | `root`             |

## Endpoints principales

### Clientes
```
POST   /api/clientes
GET    /api/clientes
GET    /api/clientes/{id}
PUT    /api/clientes/{id}
DELETE /api/clientes/{id}
```

### Vehículos
```
POST   /api/clientes/{clienteId}/vehiculos
GET    /api/clientes/{clienteId}/vehiculos
GET    /api/vehiculos/{id}
PUT    /api/vehiculos/{id}
DELETE /api/vehiculos/{id}
```

### Mecánicos
```
POST   /api/mecanicos
GET    /api/mecanicos
GET    /api/mecanicos/{id}
PUT    /api/mecanicos/{id}
DELETE /api/mecanicos/{id}
```

### Órdenes de servicio
```
POST   /api/ordenes
GET    /api/ordenes/{id}
GET    /api/ordenes?estado=&mecanicoId=&vehiculoId=&desde=&hasta=&page=&size=
PATCH  /api/ordenes/{id}/estado
```

### Ejemplo: crear una orden de servicio

```json
POST /api/ordenes
{
  "vehiculoId": 1,
  "mecanicoId": 1,
  "lineaServicioRequests": [
    { "descripcion": "Cambio de aceite", "costo": 450.00 },
    { "descripcion": "Revision de frenos", "costo": 300.00 }
  ]
}
```

Respuesta esperada: `201 Created`, con `estadoOrden: "RECIBIDA"` y `costoTotal` calculado automáticamente a partir de las líneas.

## Reglas de negocio

- Una orden no puede crearse sin al menos una línea de servicio.
- El estado de una orden sigue una secuencia fija: `RECIBIDA → EN_PROCESO → FINALIZADA → ENTREGADA`, sin saltos ni retrocesos.
- Un mecánico no puede tener más de 3 órdenes `EN_PROCESO` simultáneamente.
- No se puede eliminar un cliente que tenga vehículos registrados.
- No se puede eliminar (ni "activar" nuevamente) un vehículo con órdenes de servicio activas (no `ENTREGADA`).

## Decisiones técnicas

- **Soft delete en `Vehiculo`**: en vez de un `DELETE` físico, el campo `activo` marca el vehículo como inactivo. Esto se decidió porque un `DELETE` real viola la integridad referencial con `ordenes_servicio` — el historial de órdenes de un vehículo se conserva aunque el vehículo deje de estar activo.
- **Manejo global de excepciones con jerarquía**: las excepciones de negocio extienden `RecursoNoEncontradoException` (→ `404`) o `ReglaDeNegocioException` (→ `409`), lo que permite manejar todas las excepciones de un mismo tipo con un solo `@ExceptionHandler` en vez de uno por cada clase concreta.
- **Búsquedas dinámicas con `Specification`**: los filtros de `GET /api/ordenes` son opcionales y combinables (estado, mecánico, vehículo, rango de fechas), construidos con Spring Data JPA Specifications en vez de múltiples Query Methods fijos.

## Testing

El proyecto incluye tres tipos de test:

- **`@DataJpaTest`** sobre repositories, para validar los Query Methods propios contra una base de datos H2 en memoria.
- **Mockito** sobre services, para probar la lógica de negocio (cálculo de costos, máquina de estados, límite de órdenes por mecánico) sin depender de una base de datos real.

Para correr los tests:
```bash
./mvnw test
```

## Posibles mejoras futuras

Estas son extensiones naturales del proyecto, no implementadas por ahora:

- **Documentación interactiva de la API** con Swagger/OpenAPI.
- **Autenticación y autorización** con Spring Security + JWT.
- **Contenerización** con Docker (aplicación + PostgreSQL en `docker-compose`).
- **Frontend** (por ejemplo con Angular o React) que consuma esta API.
- Filtrar vehículos inactivos por defecto en los listados generales.

## Colección de Postman

Ver `postman/TallerMecanico.postman_collection.json` para una colección exportada con ejemplos de cada endpoint, incluyendo casos de error.
