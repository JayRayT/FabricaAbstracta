package personajes;

public class HumanoFactory implements PersonajeFactory {
    @Override
    public Cuerpo crearCuerpo() { return new CuerpoHumano(); }
    @Override
    public Arma crearArma() { return new ArmaHumano(); }
    @Override
    public Montura crearMontura() { return new MonturaHumano(); }
    @Override
    public Armadura crearArmadura() { return new ArmaduraHumano(); }
}
