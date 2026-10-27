# Entrega 2 (Sprint Final) — Checklist y Resumen · Dogtors

Documento de control con todos los requisitos técnicos y funcionales exigidos para la **Entrega 2**, agrupados por módulo para facilitar la revisión y pruebas en equipo.

---

## 1. Requisitos Técnicos y de Arquitectura

- [x] **Componentes visuales pequeños en Angular**
  - **Dashboard Admin (`admin-dashboard`):** `KpiCardsComponent` + `TopTreatmentsComponent`
  - **Directorio Veterinarios (`veterinario-list`):** `VeterinarioTableComponent`
  - **Directorio Mascotas (`mascota-list`):** `MascotaTableComponent`
  - **Directorio Dueños (`dueno-list`):** `DuenoTableComponent`
  - **Expediente Mascota (`mascota-detail`):** `PetInfoComponent` + `TreatmentsHistoryComponent`
  - **Detalle Dueño (`dueno-detail`):** `DuenoInfoComponent` + `DuenoMascotasComponent`
  - **Perfil Cliente (`perfil-dueno`):** `PerfilPetCardComponent`
  - **Formularios (`mascota-form`, `dueno-form`, `veterinario-form`):** `FormFieldComponent`
  - **Landing Page (`landing`):** `HeroSection`, `ServicesSection`, `ProcessSection`, `FaqSection`, `FooterSection`

- [x] **Servicios en Angular (`src/app/services/`)**
  - `AuthService`, `MascotaService`, `DuenoService`, `VeterinarioService`, `TratamientoService`, `DrogaService`, `DashboardService`.

- [x] **Arquitectura en capas en el Backend (Spring Boot)**
  - Separación estricta: `Controller` $\rightarrow$ `Service` (Interfaz + `Impl`) $\rightarrow$ `Repository` $\rightarrow$ `Entities` + `DTO`.

- [x] **Cero objetos anidados devueltos por el Backend**
  - Todas las relaciones `@ManyToOne` y `@OneToMany` en `Dueno`, `Mascota`, `Veterinario` y `Tratamiento` tienen `@JsonIgnore`.
  - Cada entidad expone propiedades escalares planas (`duenoId`, `duenoNombre`, `duenoCedula`, `veterinarioId`, `veterinarioNombre`, `drogaId`, `drogaNombre`, `drogaPrecioVenta`, `numeroAtenciones`).
  - Se incluyen endpoints paso a paso: `/mascotas/{id}/dueno`, `/mascotas/{id}/tratamientos`, `/dashboard/kpis`, `/dashboard/tratamientos-por-medicamento` y `/dashboard/top-tratamientos`.

- [x] **Comentarios en Frontend y Backend**
  - Documentación clara en controladores, servicios, entidades, modelos y componentes.

- [x] **Diseño visual igual o superior a Thymeleaf**
  - Interfaz hospitalaria unificada y responsiva en todas las vistas (incluyendo el nuevo portal de Administrador).

---

## 2. Requisitos Funcionales por Módulo

### Mascotas
- [x] **CRUD completo de Mascota:** crear (`/mascotas/nueva`), listar (`/mascotas`), ver expediente (`/mascotas/:id`) y editar (`/mascotas/editar/:id`).
- [x] **Activar / Desactivar mascota:** cambio de estado hospitalario (`PUT /mascotas/{id}/estado`) desde la tabla y desde el expediente.

### Dueños (Clientes)
- [x] **CRUD completo de Dueño:** incluye **cédula**, **nombre**, **correo**, **celular** y **contraseña**.
- [x] **Eliminación en cascada:** al eliminar un dueño (`DELETE /duenos/{id}`) se eliminan automáticamente sus mascotas asociadas.

### Veterinarios (Portal del Administrador)
- [x] **CRUD desde el Portal Admin:** listar (`/admin/veterinarios`), crear (`/admin/veterinarios/nuevo`) y editar (`/admin/veterinarios/editar/:id`) con cédula, nombre, especialidad, correo, foto, estado y **número de atenciones** (calculado como el conteo de tratamientos suministrados).
- [x] **Activar / Desactivar y Eliminar veterinarios:**
  - Cambio de estado con `PUT /veterinarios/{id}/estado`.
  - Eliminación con `DELETE /veterinarios/{id}` **sin borrar los tratamientos que haya suministrado** (se conserva el nombre y especialidad en el historial clínico de las mascotas).
- [x] **Bloqueo de Login a veterinario inactivo:** si el veterinario está desactivado (`activo = false`), el backend rechaza el inicio de sesión y muestra alerta en `/login`.
- [x] **Barra de búsqueda de veterinarios:** filtra en tiempo real por **cédula**, **nombre** o **especialidad**.

### Medicamentos y Tratamientos
- [x] **Carga de Excel de medicamentos:** carga automática al iniciar el servidor (`DataLoader` con `FastExcel`) y carga manual desde `/medicamentos`.
- [x] **Asignar tratamiento desde Veterinario (`/tratamientos`):**
  - Asigna automáticamente el veterinario de la sesión activa sin volver a preguntarlo.
  - Disminuye en `1` las unidades disponibles y aumenta en `1` las unidades vendidas.
  - **No permite asignar** si el medicamento tiene `0` unidades disponibles.
  - **No permite asignar** si la mascota está inactiva (`activa = false`).

### Portales y Accesos por Rol
- [x] **Login Universal (`/login`):** soporta **Cliente (Dueño)**, **Veterinario** y **Administrador**.
- [x] **Portal de Dueño (`/perfil/:id`):** el cliente solo puede ver sus propias mascotas y consultar el expediente con los tratamientos suministrados en modo lectura.
- [x] **Portal de Veterinario (`/mascotas` y `/mis-pacientes`):**
  - Barra de búsqueda para buscar mascotas por su **nombre**.
  - Filtro para ver únicamente las **mascotas que ha tratado anteriormente** (`GET /veterinarios/{id}/mascotas-tratadas`).

### Dashboard del Administrador (`/admin/dashboard`)
Muestra los **9 indicadores solicitados**:
1. Cantidad total de tratamientos administrados en el último mes.
2. Cantidad de tratamientos por tipo de medicamento en el último mes *(tabla medicamento vs. cantidad)*.
3. Cantidad de veterinarios activos en la plataforma.
4. Cantidad de veterinarios inactivos en la plataforma.
5. Cantidad de mascotas totales en la veterinaria.
6. Cantidad de mascotas activas (en tratamiento) en la veterinaria.
7. Ventas totales de la veterinaria.
8. Ganancias totales netas de la veterinaria.
9. Top 3 tratamientos/medicamentos con más unidades vendidas.

---

## 3. Credenciales de Prueba (en `/login`)

| Rol | Correo | Contraseña | Vista Inicial |
|---|---|---|---|
| **Administrador** | `admin1@dogtors.com` | `admin123` | `/admin/dashboard` |
| **Veterinario (Activos: 1 al 5)** | `vet1@dogtors.com` | `vet123` | `/mascotas` |
| **Veterinario (Inactivo)** | `vet6@dogtors.com` | `vet123` | *Login bloqueado* |
| **Dueño / Cliente (1 al 50)** | `usuario1@dogtors.com` | `1234` | `/perfil/1` |

### Dashboard del Administrador (`/admin/dashboard`)
Muestra los **9 indicadores solicitados**:
1. Cantidad total de tratamientos administrados en el último mes.
2. Cantidad de tratamientos por tipo de medicamento en el último mes *(tabla medicamento vs. cantidad)*.
3. Cantidad de veterinarios activos en la plataforma.
4. Cantidad de veterinarios inactivos en la plataforma.
5. Cantidad de mascotas totales en la veterinaria.
6. Cantidad de mascotas activas (en tratamiento) en la veterinaria.
7. Ventas totales de la veterinaria.
8. Ganancias totales netas de la veterinaria.
9. Top 3 tratamientos/medicamentos con más unidades vendidas.

---

## 3. Credenciales de Prueba (en `/login`)

| Rol | Correo | Contraseña | Vista Inicial |
|---|---|---|---|
| **Administrador** | `admin1@dogtors.com` | `admin123` | `/admin/dashboard` |
| **Veterinario (Activos: 1 al 5)** | `vet1@dogtors.com` | `vet123` | `/mascotas` |
| **Veterinario (Inactivo)** | `vet6@dogtors.com` | `vet123` | *Login bloqueado* |
| **Dueño / Cliente (1 al 50)** | `usuario1@dogtors.com` | `1234` | `/perfil/1` |

