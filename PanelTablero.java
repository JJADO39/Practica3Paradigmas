import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class PanelTablero extends JPanel {
    private static final Map<String, String> PIEZAS_UNICODE = new HashMap<>();
    static {
        //negras
        PIEZAS_UNICODE.put("r", "♜");
        PIEZAS_UNICODE.put("n", "♞");
        PIEZAS_UNICODE.put("b", "♝");
        PIEZAS_UNICODE.put("q", "♛");
        PIEZAS_UNICODE.put("k", "♚");
        PIEZAS_UNICODE.put("p", "♟");
        //blancas
        PIEZAS_UNICODE.put("R", "♖");
        PIEZAS_UNICODE.put("N", "♘");
        PIEZAS_UNICODE.put("B", "♗");
        PIEZAS_UNICODE.put("Q", "♕");
        PIEZAS_UNICODE.put("K", "♔");
        PIEZAS_UNICODE.put("P", "♙");
    }

    private final Color COLOR_CASILLA_CLARA = new Color(240, 217, 181);
    private final Color COLOR_CASILLA_OSCURA = new Color(181, 136, 99);

    private String[][] estadoDelTablero;

    public PanelTablero() {
        this.estadoDelTablero = new String[8][8];
        limpiarTablero();
    }

    public void actualizarTablero(String[][] nuevoEstado) {
        this.estadoDelTablero = nuevoEstado;
        repaint();
    }

    public void limpiarTablero() {
        for (int f = 0; f < 8; f++) {
            for (int c = 0; c < 8; c++) {
                estadoDelTablero[f][c] = "";
            }
        }
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        int tamanoPanel = Math.min(getWidth(), getHeight());
        int tamanoCasilla = tamanoPanel / 8;

        int inicioX = (getWidth() - (tamanoCasilla * 8)) / 2;
        int inicioY = (getHeight() - (tamanoCasilla * 8)) / 2;

        Font fuentePieza = new Font("SansSerif", Font.PLAIN, (int)(tamanoCasilla * 0.75));
        g2d.setFont(fuentePieza);
        FontMetrics fm = g2d.getFontMetrics();

        for (int fila = 0; fila < 8; fila++) {
            for (int col = 0; col < 8; col++) {

                if ((fila + col) % 2 == 0) {
                    g2d.setColor(COLOR_CASILLA_CLARA);
                } else {
                    g2d.setColor(COLOR_CASILLA_OSCURA);
                }
                g2d.fillRect(inicioX + col * tamanoCasilla, inicioY + fila * tamanoCasilla, tamanoCasilla, tamanoCasilla);

                String pieza = estadoDelTablero[fila][col];
                if (pieza != null && !pieza.isEmpty()) {
                    String piezaUnicode = PIEZAS_UNICODE.get(pieza);

                    int x = inicioX + col * tamanoCasilla + (tamanoCasilla - fm.stringWidth(piezaUnicode)) / 2;
                    int y = inicioY + fila * tamanoCasilla + ((tamanoCasilla - fm.getHeight()) / 2) + fm.getAscent();

                    g2d.setColor(Color.BLACK);
                    g2d.drawString(piezaUnicode, x, y);
                }
            }
        }
    }
}

