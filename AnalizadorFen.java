import javax.swing.*;
import java.awt.*;

public class AnalizadorFen {
        private JFrame ventana;
        private JTextField campoTextoFEN;
        private JButton botonAnalizar;
        private JLabel etiquetaError;
        private PanelTablero panelTablero;
        private final ValidadorFEN miValidador;

        public AnalizadorFen() {
            miValidador = new ValidadorFEN();
            crearVentana();
        }

        private void crearVentana() {

            ventana = new JFrame("Analizador FEN - Proyecto Final");
            ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            ventana.setLayout(new BorderLayout());
            ventana.setSize(500, 580);
            ventana.setLocationRelativeTo(null);

            JPanel panelSuperior = new JPanel(new FlowLayout());

            campoTextoFEN = new JTextField(40);

            campoTextoFEN.setText("");

            botonAnalizar = new JButton("Pintar Tablero");

            panelSuperior.add(new JLabel("FEN: "));
            panelSuperior.add(campoTextoFEN);
            panelSuperior.add(botonAnalizar);

            panelTablero = new PanelTablero();

            etiquetaError = new JLabel(" ");
            etiquetaError.setForeground(Color.RED);
            etiquetaError.setHorizontalAlignment(SwingConstants.CENTER);
            etiquetaError.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            botonAnalizar.addActionListener(e -> {
                presionarBotonAnalizar();
            });

            ventana.add(panelSuperior, BorderLayout.NORTH);
            ventana.add(panelTablero, BorderLayout.CENTER);
            ventana.add(etiquetaError, BorderLayout.SOUTH);
            ventana.setVisible(true);
        }

        private void presionarBotonAnalizar() {
            String fenIngresado = campoTextoFEN.getText().trim();

            try {
                String[][] estadoTablero = miValidador.analizar(fenIngresado);
                panelTablero.actualizarTablero(estadoTablero);
                etiquetaError.setText(" ");

            } catch (ErrorDeFen ex) {
                etiquetaError.setText("Cadena FEN inválida: " + ex.getMessage());
                panelTablero.limpiarTablero();
            }
        }

        public static void main() {

            SwingUtilities.invokeLater(() -> {
                new AnalizadorFen();
            });
        }
    }

