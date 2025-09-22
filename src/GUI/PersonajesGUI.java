 package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import personajes.*;

public class PersonajesGUI extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel mainPanel;
    private JComboBox<String> personajeSelector;
    private JLabel imagenLabel;
    private JTextArea detallesArea;
    private JButton crearButton;
    private JButton mostrarConsolaButton;
    
    // Fábricas de personajes
    private PersonajeFactory[] factories = {
        new OrcoFactory(),
        new HumanoFactory(),
        new EnanoFactory(),
        new ElfoFactory()
    };
    
    // Nombres de personajes
    private String[] personajes = {"Orco", "Humano", "Enano", "Elfo"};
    
    public PersonajesGUI() {
        setTitle("Catálogo de Personajes de Fantasía");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 550);
        setLocationRelativeTo(null);
        
        initComponents();
        layoutComponents();
        setupEventHandlers();
        
        // Cargar el primer personaje
        actualizarVista(0);
    }
    
    private void initComponents() {
        mainPanel = new JPanel();
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        personajeSelector = new JComboBox<>(personajes);
        imagenLabel = new JLabel();
        imagenLabel.setHorizontalAlignment(JLabel.CENTER);
        imagenLabel.setVerticalAlignment(JLabel.CENTER);
        imagenLabel.setPreferredSize(new Dimension(250, 250));
        imagenLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        
        detallesArea = new JTextArea();
        detallesArea.setEditable(false);
        detallesArea.setLineWrap(true);
        detallesArea.setWrapStyleWord(true);
        detallesArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        detallesArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        
        crearButton = new JButton("Crear Personaje");
        mostrarConsolaButton = new JButton("Mostrar en Consola");
    }
    
    private void layoutComponents() {
        mainPanel.setLayout(new BorderLayout(10, 10));
        
        // Panel superior con selector
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(new JLabel("Selecciona un personaje:"));
        topPanel.add(personajeSelector);
        topPanel.add(crearButton);
        topPanel.add(mostrarConsolaButton);
        
        // Panel central con imagen y detalles
        JPanel centerPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        
        // Panel de imagen
        JPanel imagePanel = new JPanel(new BorderLayout());
        imagePanel.setBorder(BorderFactory.createTitledBorder("Apariencia"));
        imagePanel.add(imagenLabel, BorderLayout.CENTER);
        
        // Panel de detalles
        JPanel detailsPanel = new JPanel(new BorderLayout());
        detailsPanel.setBorder(BorderFactory.createTitledBorder("Detalles del Personaje"));
        detailsPanel.add(new JScrollPane(detallesArea), BorderLayout.CENTER);
        
        centerPanel.add(imagePanel);
        centerPanel.add(detailsPanel);
        
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        
        add(mainPanel);
    }
    
    private void setupEventHandlers() {
        personajeSelector.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int seleccion = personajeSelector.getSelectedIndex();
                actualizarVista(seleccion);
            }
        });
        
        crearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int seleccion = personajeSelector.getSelectedIndex();
                crearPersonaje(seleccion);
            }
        });
        
        mostrarConsolaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int seleccion = personajeSelector.getSelectedIndex();
                mostrarEnConsola(seleccion);
            }
        });
    }
    
    private void actualizarVista(int index) {
        String[] imagePaths = {
            "img/orco.png",
            "img/humano.png", 
            "img/enano.png",
            "img/elfo.png"
        };
        
        String[] nombres = {"ORCO", "HUMANO", "ENANO", "ELFO"};
        
        String imagePath = imagePaths[index];
        String textoImagen = nombres[index];
        
        try {
            // Intentar cargar la imagen desde diferentes ubicaciones
            java.net.URL imageUrl = getClass().getClassLoader().getResource(imagePath);
            
            if (imageUrl == null) {
                // Intentar cargar desde sistema de archivos
                imageUrl = new java.io.File(imagePath).toURI().toURL();
            }
            
            if (imageUrl != null) {
                BufferedImage originalImage = ImageIO.read(imageUrl);
                
                if (originalImage != null) {
                    // Calcular dimensiones manteniendo proporción
                    int containerSize = 250;
                    int originalWidth = originalImage.getWidth();
                    int originalHeight = originalImage.getHeight();
                    
                    double scaleFactor = Math.min(
                        (double) containerSize / originalWidth,
                        (double) containerSize / originalHeight
                    );
                    
                    int newWidth = (int) (originalWidth * scaleFactor);
                    int newHeight = (int) (originalHeight * scaleFactor);
                    
                    // Escalar la imagen manteniendo la calidad
                    Image scaledImage = originalImage.getScaledInstance(
                        newWidth, newHeight, Image.SCALE_SMOOTH);
                    
                    ImageIcon icon = new ImageIcon(scaledImage);
                    imagenLabel.setIcon(icon);
                    imagenLabel.setText("");
                    
                    // Centrar la imagen
                    imagenLabel.setHorizontalAlignment(JLabel.CENTER);
                    imagenLabel.setVerticalAlignment(JLabel.CENTER);
                    
                    // Mostrar detalles del personaje
                    Juego personaje = new Juego(factories[index]);
                    detallesArea.setText(obtenerDetallesPersonaje(personaje, personajes[index]));
                    return; // Salir si la imagen se cargó correctamente
                }
            }
            
            throw new IOException("No se pudo cargar la imagen: " + imagePath);
            
        } catch (Exception e) {
            // Fallback a texto
            imagenLabel.setIcon(null);
            imagenLabel.setText("<html><div style='text-align: center;'>" +
                              textoImagen + 
                              "<br/><span style='font-size: 10px; color: gray;'>" +
                              "Imagen no disponible</span></div></html>");
            imagenLabel.setFont(new Font("Arial", Font.BOLD, 18));
            imagenLabel.setForeground(Color.BLUE);
            imagenLabel.setBackground(Color.LIGHT_GRAY);
            imagenLabel.setOpaque(true);
            
            // Mostrar detalles del personaje incluso sin imagen
            Juego personaje = new Juego(factories[index]);
            detallesArea.setText(obtenerDetallesPersonaje(personaje, personajes[index]));
        }
    }
    
    private String obtenerDetallesPersonaje(Juego personaje, String nombrePersonaje) {
        StringBuilder detalles = new StringBuilder();
        detalles.append("=== ").append(nombrePersonaje.toUpperCase()).append(" ===\n\n");
        
        detalles.append("CARACTERÍSTICAS:\n");
        detalles.append("• ").append(getDescripcion(personaje.getCuerpo())).append("\n");
        detalles.append("• ").append(getDescripcion(personaje.getArma())).append("\n");
        detalles.append("• ").append(getDescripcion(personaje.getMontura())).append("\n");
        detalles.append("• ").append(getDescripcion(personaje.getArmadura())).append("\n");
        
        detalles.append("\nCOMPONENTES:\n");
        detalles.append("• Cuerpo: ").append(personaje.getCuerpo().getClass().getSimpleName()).append("\n");
        detalles.append("• Arma: ").append(personaje.getArma().getClass().getSimpleName()).append("\n");
        detalles.append("• Montura: ").append(personaje.getMontura().getClass().getSimpleName()).append("\n");
        detalles.append("• Armadura: ").append(personaje.getArmadura().getClass().getSimpleName()).append("\n");
        
        detalles.append("\n--- INFORMACIÓN ADICIONAL ---\n");
        detalles.append("Este personaje ha sido creado usando el\n");
        detalles.append("patrón Abstract Factory para garantizar\n");
        detalles.append("la consistencia entre todos sus componentes.");
        
        return detalles.toString();
    }
    
    // Método auxiliar para obtener descripciones
    private String getDescripcion(Object obj) {
        if (obj instanceof CuerpoOrco) return "Cuerpo robusto y verde de Orco";
        if (obj instanceof CuerpoHumano) return "Cuerpo ágil y adaptable de Humano";
        if (obj instanceof CuerpoEnano) return "Cuerpo bajo y fornido de Enano";
        if (obj instanceof CuerpoElfo) return "Cuerpo esbelto y elegante de Elfo";
        
        if (obj instanceof ArmaOrco) return "Gran hacha de guerra Orco";
        if (obj instanceof ArmaHumano) return "Espada templada de Humano";
        if (obj instanceof ArmaEnano) return "Martillo de guerra Enano";
        if (obj instanceof ArmaElfo) return "Arco largo mágico de Elfo";
        
        if (obj instanceof MonturaOrco) return "Lobo gigante como montura";
        if (obj instanceof MonturaHumano) return "Caballo noble como montura";
        if (obj instanceof MonturaEnano) return "Jabalí como montura";
        if (obj instanceof MonturaElfo) return "Ciervo místico como montura";
        
        if (obj instanceof ArmaduraOrco) return "Armadura pesada y tosca";
        if (obj instanceof ArmaduraHumano) return "Armadura de acero refinada";
        if (obj instanceof ArmaduraEnano) return "Armadura resistente y pesada";
        if (obj instanceof ArmaduraElfo) return "Armadura ligera y élfica";
        
        return "Descripción no disponible";
    }
    
    private void crearPersonaje(int index) {
        Juego personaje = new Juego(factories[index]);
        
        // Mostrar mensaje de confirmación
        JOptionPane.showMessageDialog(this, 
            "¡Personaje " + personajes[index] + " creado exitosamente!\n\n" +
            "Revisa el área de detalles para ver sus características.\n" +
            "Componentes creados:\n" +
            "• " + personaje.getCuerpo().getClass().getSimpleName() + "\n" +
            "• " + personaje.getArma().getClass().getSimpleName() + "\n" +
            "• " + personaje.getMontura().getClass().getSimpleName() + "\n" +
            "• " + personaje.getArmadura().getClass().getSimpleName(),
            "Personaje Creado", 
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void mostrarEnConsola(int index) {
        Juego personaje = new Juego(factories[index]);
        
        // Crear y mostrar la nueva ventana de consola
        ConsolaFrame consolaFrame = new ConsolaFrame(personaje, personajes[index]);
        consolaFrame.setVisible(true);
        
        // También mostrar en la consola real del IDE
        System.out.println("\n=== Mostrando en consola: " + personajes[index] + " ===");
        personaje.mostrarPersonaje();
    }
    
    public static void main(String[] args) {
        // Establecer el look and feel del sistema
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new PersonajesGUI().setVisible(true);
            }
        });
    }
}