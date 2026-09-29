classDiagram
    class Dueno {
        -Long id
        -String nombre
        -String correo
        -String password
        -List~Mascota~ mascotas
        +Dueno(String nombre, String correo, String password)
    }

    class Mascota {
        -Long id
        -String nombre
        -String especie
        -String raza
        -Integer edad
        -Double peso
        -String enfermedad
        -String fotoUrl
        -Boolean activa
        -Dueno dueno
        -List~Tratamiento~ tratamientos
        +Mascota(String nombre, String especie, String raza, Integer edad, Double peso, String enfermedad, String fotoUrl, Boolean activa)
    }

    class Veterinario {
        -Long id
        -String nombre
        -String correo
        -String password
        -String especialidad
        -List~Tratamiento~ tratamientos
        +Veterinario(String nombre, String correo, String password, String especialidad)
    }

    class Droga {
        -Long id
        -String nombre
        -Double precioVenta
        -Double precioCompra
        -Integer unidadesDisponibles
        -Integer unidadesVendidas
        -List~Tratamiento~ tratamientos
        +Droga(String nombre, Double precioVenta, Double precioCompra, Integer unidadesDisponibles, Integer unidadesVendidas)
    }

    class Tratamiento {
        -Long id
        -LocalDate fecha
        -String observaciones
        -Mascota mascota
        -Veterinario veterinario
        -Droga droga
        +Tratamiento(LocalDate fecha, String observaciones, Mascota mascota, Veterinario veterinario, Droga droga)
    }

    class Administrador {
        -Long id
        -String nombre
        -String correo
        -String password
        +Administrador(String nombre, String correo, String password)
    }

    Dueno "1" --> "*" Mascota : tiene
    Mascota "1" --> "*" Tratamiento : recibe
    Veterinario "1" --> "*" Tratamiento : prescribe
    Droga "1" --> "*" Tratamiento : suministrada_en

