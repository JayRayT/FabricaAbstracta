Juan David Rayo Tejada - 202321020023
Jonnatan Camargo Camacho - 20231020204
# Fabrica de Personajes (Patrón Abstract Factory)

Este proyecto implementa el **Patrón de Diseño Abstract Factory** en Java.  
Su objetivo es mostrar cómo crear familias de objetos relacionados sin acoplar el código a clases concretas.

---

## 📂 Estructura del proyecto

- **Productos abstractos**  
  Interfaces que definen las características comunes de los objetos a crear:
  - `Cuerpo`
  - `Arma`
  - `Montura`
  - `Armadura`

- **Fábrica abstracta**  
  - `PersonajeFactory` → Interfaz que declara los métodos de creación para cada tipo de producto.

- **Fábricas concretas**  
  Implementaciones de `PersonajeFactory` que crean conjuntos específicos de personajes (por ejemplo, guerrero, mago, arquero, etc.).

- **Clases concretas (productos)**  
  Implementaciones de cada producto (ejemplo: `Espada`, `Arco`, `Caballo`, `Dragón`, `ArmaduraLigera`, `ArmaduraPesada`).

- **Cliente**  
  Clase principal que utiliza la fábrica para crear y mostrar personajes
