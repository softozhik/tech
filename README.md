# Equipment Management System

Система учета техники в компании на базе Spring Boot.

## Технологический стек

- Java 17
- Spring Boot 3.2.x
- Maven
- H2 Database (файловый режим)
- Spring Data JPA
- Spring Security (базовая аутентификация)
- Liquibase для миграций БД
- SpringDoc OpenAPI для документации API
- MapStruct для маппинга DTO ↔ Entity
- Lombok

## Структура проекта

```
com.yourname.equipment/
├── config/                     # Spring конфигурация
├── common/                     # Общие компоненты
│   ├── entity/BaseEntity
│   ├── dto/
│   └── exception/
├── company/                    # Модуль компаний
├── department/                 # Модуль отделов
├── user/                       # Модуль пользователей
├── equipment/                   # Модуль техники
└── license/                    # Модуль лицензий на ПО
```

## Запуск проекта

### Требования

- JDK 17 или выше
- Maven 3.6+

### Сборка и запуск

```bash
# Сборка проекта
mvn clean install

# Запуск приложения
mvn spring-boot:run
```

Приложение запустится на порту 8080.

## Доступ к приложению

- **API**: http://localhost:8080/api
- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **H2 Console**: http://localhost:8080/h2-console
  - JDBC URL: `jdbc:h2:file:./data/equipment-db`
  - Username: `sa`
  - Password: (пусто)

## Тестовые данные

При первом запуске автоматически создаются:

- **Компания**: "Demo Company"
- **Отделы**: "IT Department", "Development", "HR"
- **Пользователи**:
  - `admin@example.com` / `password` (роль: ADMIN)
  - `employee@example.com` / `password` (роль: EMPLOYEE)
- **Категории техники**: "Ноутбуки", "Компьютеры", "Мониторы", "Принтеры", "Другое"
- **Техника**: 3 единицы разных категорий

## API Endpoints

### Компании
- `GET /api/companies` - получить все компании
- `GET /api/companies/{id}` - получить компанию по ID
- `POST /api/companies` - создать компанию
- `PUT /api/companies/{id}` - обновить компанию
- `DELETE /api/companies/{id}` - удалить компанию

### Отделы
- `GET /api/departments/company/{companyId}` - получить отделы компании
- `GET /api/departments/{id}` - получить отдел по ID
- `POST /api/departments` - создать отдел
- `PUT /api/departments/{id}` - обновить отдел
- `DELETE /api/departments/{id}` - удалить отдел

### Пользователи
- `GET /api/users/company/{companyId}` - получить пользователей компании
- `GET /api/users/{id}` - получить пользователя по ID
- `POST /api/users` - создать пользователя
- `PUT /api/users/{id}` - обновить пользователя
- `DELETE /api/users/{id}` - удалить пользователя

### Техника
- `GET /api/equipment` - получить всю технику (с фильтрами: companyId, status, categoryId)
- `GET /api/equipment/{id}` - получить технику по ID
- `POST /api/equipment` - создать единицу техники
- `PUT /api/equipment/{id}` - обновить технику
- `DELETE /api/equipment/{id}` - удалить технику
- `POST /api/equipment/{id}/assign` - назначить технику пользователю
- `POST /api/equipment/{id}/return` - вернуть технику
- `GET /api/equipment/{id}/assignments` - история назначений
- `GET /api/equipment/{id}/repairs` - история ремонтов
- `POST /api/equipment/{id}/repairs` - создать запись о ремонте
- `GET /api/equipment/{id}/upgrades` - история апгрейдов
- `POST /api/equipment/{id}/upgrades` - создать запись об апгрейде

### Лицензии на ПО
- `GET /api/licenses/company/{companyId}` - получить лицензии компании
- `GET /api/licenses/{id}` - получить лицензию по ID
- `POST /api/licenses` - создать лицензию
- `PUT /api/licenses/{id}` - обновить лицензию
- `DELETE /api/licenses/{id}` - удалить лицензию
- `GET /api/licenses/company/{companyId}/expiring` - получить истекающие лицензии

### Отчеты
- `GET /api/reports/equipment-by-department?companyId={id}` - техника по отделам
- `GET /api/reports/equipment/{id}/total-cost` - общая стоимость техники
- `GET /api/reports/licenses-expiring?companyId={id}&days={days}` - истекающие лицензии

## Безопасность

- POST, PUT, DELETE операции доступны только для пользователей с ролью ADMIN
- GET операции доступны для всех аутентифицированных пользователей
- Используется базовая HTTP аутентификация (Basic Auth)
- Пароли хранятся в зашифрованном виде (BCrypt)

## База данных

База данных H2 создается автоматически в файле `./data/equipment-db.mv.db` при первом запуске.

Миграции Liquibase находятся в `src/main/resources/db/changelog/`.

## Документация API

Полная документация API доступна через Swagger UI: http://localhost:8080/swagger-ui.html
