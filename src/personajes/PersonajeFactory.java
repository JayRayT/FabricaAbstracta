package personajes;

public interface PersonajeFactory {
    Cuerpo crearCuerpo();
    Arma crearArma();
    Montura crearMontura();
    Armadura crearArmadura();
}
