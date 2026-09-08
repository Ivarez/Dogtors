# Dogtors - Sistema de Gestión Veterinaria

Dogtors es una aplicación web para la gestión de pacientes y clientes de una clínica veterinaria. Implementa dos portales distintos:
1. **Portal Veterinario**: Para los médicos, permitiendo CRUD completo de pacientes (mascotas) y dueños, visualizar expedientes, registrar ingresos y cambiar estados clínicos.
2. **Portal Cliente**: Para los dueños de mascotas, donde pueden iniciar sesión y visualizar el estado actual y expediente de sus mascotas hospitalizadas.

## Arquitectura y Capas

El proyecto está construido usando **Spring Boot** siguiendo estrictamente el patrón de **Arquitectura en Capas**:
- **Controladores (`controller`)**: Interceptan las peticiones HTTP, manejan la navegación y preparan el `Model` para las vistas. Delegan toda lógica de negocio al servicio.
- **Servicios (`service`)**: Interfaces e implementaciones (`ServiceImpl`). Centralizan la lógica de negocio, validaciones (como el Login) y manejo de errores. Las consultas a múltiples repositorios están protegidas con `@Transactional`.
- **Repositorios (`repository`)**: Interfaces que heredan de `JpaRepository`. Gestionan el acceso a datos.
- **Entidades (`entities`)**: Clases mapeadas a la base de datos usando JPA. Se usa Lombok (`@Getter`, `@Setter`, `@Builder`) de manera segura, excluyendo los campos relacionales en el `@ToString` para prevenir bucles infinitos.

## Base de Datos (H2 + JPA)

A partir del Sprint 4, el proyecto ha migrado a una base de datos relacional real usando **H2 Database** en memoria y **Spring Data JPA**.

- **Generación Automática**: Cada vez que se levanta el proyecto, la base de datos se borra y se re-crea gracias a la propiedad `spring.jpa.hibernate.ddl-auto=create-drop`.
- **Restricciones DDL**: Las columnas cuentan con validaciones estrictas (`nullable = false`, `unique = true`, `length = X`) desde las anotaciones `@Column`.
- **Relaciones JPA**: Se implementa una relación Bidireccional `@OneToMany` (Dueño) y `@ManyToOne` (Mascota) para unificar la dependencia.
- **Data Loader**: Al iniciar la app, un componente `CommandLineRunner` inyecta en la base de datos más de 50 dueños y 100 mascotas de prueba (usando el Patrón Builder para construir objetos robustos).
- **Consola H2**: Se puede visualizar gráficamente la DB ingresando a la URL `/h2` (JDBC URL: `jdbc:h2:mem:dogtorsdb`, User: `sa`, sin contraseña).

## Manejo de Errores

Implementamos un manejador global de excepciones en la capa de servicios:
1. **Excepciones Personalizadas**: `NotFoundException` (hereda de RuntimeException).
2. Se lanzan desde el servicio usando programación funcional (`.orElseThrow()`).
3. **`@ControllerAdvice`**: La clase `GlobalExceptionHandler` intercepta estos errores y redirige amigablemente a la página `error.html` sin mostrar la traza técnica al usuario final.

## Tecnologías

- Java 17
- Spring Boot 3.3.x (Web, Data JPA, Thymeleaf, DevTools)
- H2 Database
- Lombok
- HTML5, CSS3, Tailwind CSS (Portal Dueño, Expedientes), Bootstrap 5 (Tabla General)

## Ejecución

1. Asegúrate de tener **Java 17** instalado.
2. Abre el proyecto en un IDE compatible con Spring Boot (ej. VS Code con el *Spring Boot Dashboard* o IntelliJ IDEA).
3. Ejecuta la clase principal `DogtorsApplication.java`.
4. Ingresa a `http://localhost:8080` en tu navegador.
