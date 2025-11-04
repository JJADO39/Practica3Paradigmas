public class ValidadorFEN {
    public String[][] analizar(String fenCompleto) throws ErrorDeFen {
        if (fenCompleto == null || fenCompleto.isEmpty()) {
            throw new ErrorDeFen("La cadena FEN no puede estar vacía.");
        }

        String[] partes = fenCompleto.split("\\s+");
        if (partes.length != 6) {
            throw new ErrorDeFen("La cadena debe tener 6 partes separadas por espacios.");
        }

        String[][] tablero = validarPosicionPiezas(partes[0]);
        validarTurno(partes[1]);
        validarEnroque(partes[2]);
        validarAlPaso(partes[3]);
        validarMedioMovimiento(partes[4]);
        validarMovimientoCompleto(partes[5]);

        return tablero;
    }

    private String[][] validarPosicionPiezas(String posicion) throws ErrorDeFen {
        String[][] tablero = new String[8][8];
        String[] filas = posicion.split("/");

        if (filas.length != 8) {
            throw new ErrorDeFen("La posición de piezas debe tener 8 filas separadas por '/'.");
        }

        for (int f = 0; f < filas.length; f++) {
            String fila = filas[f];
            int contadorCasillas = 0;
            int c = 0;

            for (char caracter : fila.toCharArray()) {
                if (c >= 8) {
                    contadorCasillas++;
                    break;
                }

                if (Character.isDigit(caracter)) {
                    int numVacias = Character.getNumericValue(caracter);
                    if (numVacias < 1 || numVacias > 8) {
                        throw new ErrorDeFen("Número inválido '" + caracter + "' en la fila " + (f + 1) + ".");
                    }
                    for (int i = 0; i < numVacias; i++) {
                        if (c >= 8) break;
                        tablero[f][c] = "";
                        c++;
                    }
                    contadorCasillas += numVacias;
                } else if (Character.isLetter(caracter)) {
                    if ("pnbrqkPNBRQK".indexOf(caracter) == -1) {
                        throw new ErrorDeFen("Pieza inválida '" + caracter + "' en la fila " + (f + 1) + ".");
                    }
                    tablero[f][c] = String.valueOf(caracter);
                    c++;
                    contadorCasillas++;
                } else {
                    throw new ErrorDeFen("Carácter desconocido '" + caracter + "' en la fila " + (f + 1) + ".");
                }
            }

            if (contadorCasillas != 8) {
                throw new ErrorDeFen("La fila " + (f + 1) + " no suma 8 casillas (suma " + contadorCasillas + ").");
            }
        }
        return tablero;
    }

    private void validarTurno(String turno) throws ErrorDeFen {
        if (!turno.equals("w") && !turno.equals("b")) {
            throw new ErrorDeFen("El turno de mover debe ser 'w' o 'b'.");
        }
    }

    private void validarEnroque(String enroque) throws ErrorDeFen {
        if (!enroque.matches("^-|^(?!.*(.).*\\1)[KQkq]{1,4}$")) {
            throw new ErrorDeFen("Indicador de enroque inválido.");
        }
    }

    private void validarAlPaso(String alPaso) throws ErrorDeFen {
        if (!alPaso.matches("^-|[a-h][36]$")) {
            throw new ErrorDeFen("Casilla 'al paso' inválida (debe ser '-' o 'a3', 'h6', etc.).");
        }
    }

    private void validarMedioMovimiento(String medio) throws ErrorDeFen {
        if (!medio.matches("^\\d+$")) {
            throw new ErrorDeFen("El reloj de medias jugadas debe ser un número >= 0.");
        }
    }

    private void validarMovimientoCompleto(String completo) throws ErrorDeFen {
        if (!completo.matches("^[1-9]\\d*$")) {
            throw new ErrorDeFen("El contador de jugadas debe ser un número >= 1.");
        }
    }
}

