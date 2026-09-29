erDiagram
    DUENO {
        Long id PK
        String nombre
        String correo UK
        String password
    }

    ADMINISTRADOR {
        Long id PK
        String nombre
        String correo UK
        String password
    }

    VETERINARIO {
        Long id PK
        String nombre
        String correo UK
        String password
        String especialidad
    }

    MASCOTA {
        Long id PK
        String nombre
        String especie
        String raza
        Integer edad
        Double peso
        String enfermedad
        String fotoUrl
        Boolean activa
        Long dueno_id FK
    }

    DROGA {
        Long id PK
        String nombre
        Double precioVenta
        Double precioCompra
        Integer unidadesDisponibles
        Integer unidadesVendidas
    }

    TRATAMIENTO {
        Long id PK
        LocalDate fecha
        String observaciones
        Long mascota_id FK
        Long veterinario_id FK
        Long droga_id FK
    }

    DUENO ||--o{ MASCOTA : "tiene"
    MASCOTA ||--o{ TRATAMIENTO : "recibe"
    VETERINARIO ||--o{ TRATAMIENTO : "prescribe"
    DROGA ||--o{ TRATAMIENTO : "es suministrada en"

