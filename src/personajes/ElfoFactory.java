package personajes;

public class ElfoFactory implements PersonajeFactory {
    @Override
    public Cuerpo crearCuerpo() { return new CuerpoElfo(); }
    @Override
    public Arma crearArma() { return new ArmaElfo(); }
    @Override
    public Montura crearMontura() { return new MonturaElfo(); }
    @Override
    public Armadura crearArmadura() { return new ArmaduraElfo(); }
}
