# Dogtors - Hospital Veterinario
Proyecto de Desarrollo Web
Sistema de gestión veterinaria. 
El sistema permite a los veterinarios gestionar los expedientes de los pacientes, y a los dueños de mascotas consultar el estado clínico en tiempo real.

## Arquitectura del Proyecto
El proyecto está desarrollado bajo el framework **Spring Boot** utilizando el patrón de diseño **MVVM (Model-View-ViewModel)** y una arquitectura basada en capas:
- **Capa de Controladores (`controller`):** Recibe las peticiones HTTP, delega la lógica de negocio a los servicios y retorna las vistas.
- **Capa de Servicios (`service`):** Contiene la lógica de negocio (ej. validación de login, filtrado de mascotas) y centraliza el manejo de errores lanzando excepciones. Cumple con el principio de Inyección de Dependencias (`@Autowired`).
- **Capa de Persistencia (`repository`):** En este sprint, simula una base de datos utilizando colecciones en memoria (`HashMap`).
- **Capa de Dominio (`entities`):** Contiene los modelos de datos principales (`Dueno` y `Mascota`).

## Tecnologías y Herramientas
- **Backend:** Java 17, Spring Boot, Spring Web
- **Frontend:** HTML5, Tailwind CSS, Bootstrap 5.3, Vanilla CSS
- **Motor de plantillas:** Thymeleaf
- **Herramienta de Construcción:** Maven

## Estructura del Sistema

### Flujo del Veterinario (Administración)
El veterinario tiene acceso sin restricciones a la gestión de datos maestros:
- **Gestión de Dueños:** Puede listar, crear, editar y borrar perfiles de dueños (`/duenos/lista`, `/duenos/nueva`).
- **Gestión de Mascotas:** Puede ver el listado de todos los pacientes del hospital (`/mascotas`), ver expedientes, y registrar nuevos ingresos o editarlos (`/mascotas/nueva`). Durante el registro de un paciente, el veterinario **selecciona** de una lista al dueño correspondiente.

### Flujo del Cliente (Dueño de mascota)
- **Login Unificado:** El dueño ingresa sus credenciales en `/duenos/login`.
- **Portal de Cliente:** Una vez autenticado, es redirigido a `/duenos/perfil/{id}`, donde puede visualizar la información y estado clínico ("Estable" / "Crítico") de sus mascotas en un formato de tarjetas, sin posibilidad de modificarlas.

## Ejecución
1. Clonar este repositorio.
2. Abrir el proyecto en **Visual Studio Code** (asegúrate de tener instalado el *Extension Pack for Java* y *Spring Boot Dashboard*).
3. Ir a la pestaña "Spring Boot Dashboard" y ejecutar `DogtorsApplication`.
4. Ingresar desde tu navegador a `http://localhost:8080`.

## Vistas y Mockups (En progreso)
El sistema ha unificado el uso de Tailwind CSS para la mayoría de sus vistas internas, con integración de Bootstrap en tablas de datos específicas y CSS nativo en el landing page. Las vistas actuales se están ajustando para reflejar el diseño exacto de los mockups en Figma.
