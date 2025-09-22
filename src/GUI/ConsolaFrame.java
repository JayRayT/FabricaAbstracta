package GUI;

import javax.swing.*;
import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import personajes.*;

public class ConsolaFrame extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextArea consolaArea;
    
    public ConsolaFrame(Juego personaje, String nombrePersonaje) {
        setTitle("Consola - " + nombrePersonaje);
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        initComponents();
        mostrarPersonajeEnConsola(personaje, nombrePersonaje);
    }
    
    private void initComponents() {
        consolaArea = new JTextArea();
        consolaArea.setEditable(false);
        consolaArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        consolaArea.setBackground(Color.BLACK);
        consolaArea.setForeground(Color.GREEN);
        consolaArea.setCaretColor(Color.WHITE);
        
        JScrollPane scrollPane = new JScrollPane(consolaArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.GRAY), 
            "Salida de Consola"
        ));
        
        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
        
        // Botón para cerrar
        JButton cerrarButton = new JButton("Cerrar Consola");
        cerrarButton.addActionListener(e -> dispose());
        cerrarButton.setBackground(new Color(220, 220, 220));
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(cerrarButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    private void mostrarPersonajeEnConsola(Juego personaje, String nombrePersonaje) {
        // Capturar la salida de System.out
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        PrintStream old = System.out;
        System.setOut(ps);
        
        // Mostrar el personaje (la salida se capturará en el ByteArrayOutputStream)
        consolaArea.append("=== CONSOLA - " + nombrePersonaje.toUpperCase() + " ===\n\n");
        personaje.mostrarPersonaje();
        
        // Restaurar System.out
        System.out.flush();
        System.setOut(old);
        
        // Mostrar la salida capturada en el JTextArea
        consolaArea.append(baos.toString());
        
        // Mover el cursor al inicio
        consolaArea.setCaretPosition(0);
    }
}
