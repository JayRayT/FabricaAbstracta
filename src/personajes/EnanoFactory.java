package personajes;

public class EnanoFactory implements PersonajeFactory {
    @Override
    public Cuerpo crearCuerpo() { return new CuerpoEnano(); }
    @Override
    public Arma crearArma() { return new ArmaEnano(); }
    @Override
    public Montura crearMontura() { return new MonturaEnano(); }
    @Override
    public Armadura crearArmadura() { return new ArmaduraEnano(); }
}
