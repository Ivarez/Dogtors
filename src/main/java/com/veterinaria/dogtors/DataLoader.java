package com.veterinaria.dogtors;

import com.veterinaria.dogtors.entities.Dueno;
import com.veterinaria.dogtors.entities.Mascota;
import com.veterinaria.dogtors.repository.DuenoRepository;
import com.veterinaria.dogtors.repository.MascotaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;

@Component
@Transactional
public class DataLoader implements CommandLineRunner {

    @Autowired
    private DuenoRepository duenoRepository;

    @Autowired
    private MascotaRepository mascotaRepository;

    @Override
    public void run(String... args) {
        Random random = new Random(42);

        // ===================== 50 DUENOS =====================
        String[] nombres = {
            "Juan Pérez", "María García", "Carlos López", "Ana Martínez", "Pedro Rodríguez",
            "Laura Sánchez", "Diego Ramírez", "Sofía Torres", "Andrés Herrera", "Camila Díaz",
            "Felipe Morales", "Valentina Castro", "Santiago Ortiz", "Isabella Vargas", "Mateo Gómez",
            "Daniela Rojas", "Sebastián Mendoza", "Gabriela Flores", "Nicolás Reyes", "Mariana Jiménez",
            "Alejandro Cruz", "Paula Navarro", "David Espinoza", "Natalia Guerrero", "Tomás Delgado",
            "Carolina Ríos", "Emilio Suárez", "Andrea Peña", "Ricardo Molina", "Lucía Medina",
            "Fernando Aguilar", "Paola Sandoval", "Martín Contreras", "Catalina Vega", "Jorge Paredes",
            "Adriana Silva", "Miguel Acosta", "Tatiana Ibarra", "Rafael Salazar", "Diana Campos",
            "Enrique Parra", "Fernanda León", "Gustavo Cárdenas", "Marcela Ramos", "Roberto Guzmán",
            "Claudia Herrera", "Óscar Bravo", "Verónica Núñez", "Alberto Pacheco", "Mónica Lozano"
        };

        for (int i = 0; i < nombres.length; i++) {
            Dueno dueno = Dueno.builder()
                    .nombre(nombres[i])
                    .correo("usuario" + (i + 1) + "@dogtors.com")
                    .password("1234")
                    .build();
            duenoRepository.save(dueno);
        }

        List<Dueno> duenos = duenoRepository.findAll();
        int cantidadDuenos = duenos.size();

        // ===================== 100 MASCOTAS =====================
        String[] nombresMascotas = {
            "Max", "Luna", "Rocky", "Bella", "Toby", "Lola", "Bruno", "Nala", "Rex", "Coco",
            "Duke", "Molly", "Zeus", "Daisy", "Thor", "Chloe", "Simba", "Mia", "Lucky", "Rosie",
            "Shadow", "Ruby", "Jack", "Lily", "Buddy", "Zoe", "Charlie", "Maggie", "Oscar", "Sadie",
            "Tucker", "Gracie", "Bear", "Roxy", "Murphy", "Abby", "Leo", "Penny", "Buster", "Ginger",
            "Sam", "Cleo", "Milo", "Sophie", "Finn", "Stella", "Archie", "Princess", "Bentley", "Willow",
            "Hank", "Olive", "Jasper", "Pearl", "Diesel", "Hazel", "Moose", "Ivy", "Baxter", "Piper",
            "Ace", "Athena", "Apollo", "Nova", "Cody", "Ellie", "Cooper", "Layla", "Ranger", "Bailey",
            "Jax", "Emma", "Scout", "Annie", "Louie", "Cinnamon", "Tank", "Fiona", "Winston", "Misty",
            "Bandit", "Sasha", "Rocco", "Pepper", "Chester", "Honey", "Frankie", "Izzy", "Gus", "Dixie",
            "Marley", "Angel", "Bo", "Cookie", "Rusty", "Trixie", "Blue", "Dulce", "Odin", "Canela"
        };

        String[] especies = {"Perro", "Gato"};
        String[] razasPerro = {"Golden Retriever", "Labrador", "Bulldog Francés", "Pastor Alemán", "Poodle",
                "Beagle", "Husky Siberiano", "Chihuahua", "Schnauzer", "Pitbull", "Rottweiler", "Dálmata", "Boxer", "Cocker Spaniel", "Mestizo"};
        String[] razasGato = {"Persa", "Siamés", "Maine Coon", "Angora", "Bengalí",
                "Ragdoll", "Británico", "Esfinge", "Mestizo"};
        String[] enfermedades = {
            "Dermatitis alérgica", "Otitis externa", "Gastroenteritis", "Fractura en pata derecha",
            "Parásitos intestinales", "Infección urinaria", "Tumor benigno", "Displasia de cadera",
            "Insuficiencia renal", "Diabetes tipo I", "Parvovirus", "Moquillo",
            "Enfermedad periodontal", "Obstrucción intestinal", "Lesión ocular",
            "Control rutinario", "Esterilización", "Vacunación anual", "Revisión post-operatoria",
            "Herida por mordedura"
        };
        String[] fotos = {
            "https://images.unsplash.com/photo-1543466835-00a7907e9de1?w=400",
            "https://images.unsplash.com/photo-1587300003388-59208cc962cb?w=400",
            "https://images.unsplash.com/photo-1548199973-03cce0bbc87b?w=400",
            "https://images.unsplash.com/photo-1583511655857-d19b40a7a54e?w=400",
            "https://images.unsplash.com/photo-1574158622682-e40e69881006?w=400",
            "https://images.unsplash.com/photo-1573865526739-10659fec78a5?w=400",
            "https://images.unsplash.com/photo-1561037404-61cd46aa615b?w=400",
            "https://images.unsplash.com/photo-1596854407944-bf87f6fdd49e?w=400"
        };

        for (int i = 0; i < 100; i++) {
            String especie = especies[random.nextInt(2)];
            String raza;
            if (especie.equals("Perro")) {
                raza = razasPerro[random.nextInt(razasPerro.length)];
            } else {
                raza = razasGato[random.nextInt(razasGato.length)];
            }

            // Asociar a un dueno aleatorio
            int indiceDueno = random.nextInt(cantidadDuenos);
            Dueno dueno = duenos.get(indiceDueno);

            Mascota mascota = Mascota.builder()
                    .nombre(nombresMascotas[i])
                    .especie(especie)
                    .raza(raza)
                    .edad(random.nextInt(15) + 1)
                    .peso(Math.round((random.nextDouble() * 40 + 1) * 10.0) / 10.0)
                    .enfermedad(enfermedades[random.nextInt(enfermedades.length)])
                    .fotoUrl(fotos[random.nextInt(fotos.length)])
                    .activa(random.nextInt(10) > 1) // 80% estables, 20% criticos
                    .dueno(dueno)
                    .build();

            mascotaRepository.save(mascota);
        }
    }
}
