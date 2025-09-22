package personajes;


import GUI.PersonajesGUI;

public class Main {
    public static void main(String[] args) {
       

        // Lanzar la interfaz gráfica
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new PersonajesGUI().setVisible(true);
            }
        });
    }
}