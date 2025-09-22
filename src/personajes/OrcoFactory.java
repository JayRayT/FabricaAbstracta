package personajes;


public class OrcoFactory implements PersonajeFactory {
    @Override
    public Cuerpo crearCuerpo() { return new CuerpoOrco(); }
    @Override
    public Arma crearArma() { return new ArmaOrco(); }
    @Override
    public Montura crearMontura() { return new MonturaOrco(); }
    @Override
    public Armadura crearArmadura() { return new ArmaduraOrco(); }
}