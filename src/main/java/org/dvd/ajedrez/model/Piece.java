package org.dvd.ajedrez.model;

public class Piece {
    private int id;

    private Position position;
    private String tipo;
    private String color;

    public Piece(int id, int x, int y, String tipo,String color) {
        this.id = id;
        position = new Position(x, y);
        this.tipo = tipo;
        this.color = color;
    }

    public Piece() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public boolean canMovePiece(Position newPosition) {
        if (newPosition.equals(position)) {
            return false;
        }
        switch (tipo) {
            case "T"://Torre
                if (newPosition.getX() == position.getX()
                        || newPosition.getY() == position.getY()) {
                    return true;
                }

                break;
            case "H"://Caballo
                /*
                 * x+1 y y+3
                 * x-1 y y+3
                 * x+3 y y-1
                 * x+3 y y+1
                 * x+1 y y-3
                 * x-1 y y-3
                 * x-3 y y+1
                 * x-3 y y-1
                 * */
                if ((newPosition.getX() == position.getX() + 1 && newPosition.getY() == position.getY() + 3)
                        || (newPosition.getX() == position.getX() - 1 && newPosition.getY() == position.getY() + 3)
                        || (newPosition.getX() == position.getX() + 3 && newPosition.getY() == position.getY() - 1)
                        || (newPosition.getX() == position.getX() + 3 && newPosition.getY() == position.getY() + 1)
                        || (newPosition.getX() == position.getX() + 1 && newPosition.getY() == position.getY() - 3)
                        || (newPosition.getX() == position.getX() - 1 && newPosition.getY() == position.getY() - 3)
                        || (newPosition.getX() == position.getX() - 3 && newPosition.getY() == position.getY() + 1)
                        || (newPosition.getX() == position.getX() - 3 && newPosition.getY() == position.getY() - 1)
                ) {

                    return true;
                }
            case "B"://Alfil
                break;
            case "Q"://Reina
                break;
            case "K"://Rey
                if ((newPosition.getX() == position.getX() + 1 && newPosition.getY() == position.getY() + 1)
                        || (newPosition.getX() == position.getX() + 1 && newPosition.getY() == position.getY())
                        || (newPosition.getX() == position.getX() - 1 && newPosition.getY() == position.getY())
                        || (newPosition.getX() == position.getX() - 1 && newPosition.getY() == position.getY() + 1)
                        || (newPosition.getX() == position.getX() && newPosition.getY() == position.getY() + 1)
                        || (newPosition.getX() == position.getX() && newPosition.getY() == position.getY() - 1)
                        || (newPosition.getX() == position.getX() + 1 && newPosition.getY() == position.getY() - 1)
                        || (newPosition.getX() == position.getX() - 1 && newPosition.getY() == position.getY() - 1)) {
                    return true;
                }
                break;
            case "P"://Peon
                //todo diferenciar entre movimiento y ataque de peon
                //Black
                if(color.equals("B")){
                    if ((newPosition.getX() == position.getX() && newPosition.getY() == position.getY() + 1)
                            || (newPosition.getX() == position.getX() + 1 && newPosition.getY() == position.getY() + 1)
                            || (newPosition.getX() == position.getX() - 1 && newPosition.getY() == position.getY() + 1)
                    ) {

                        return true;
                    }
                }
                //White
                if(color.equals("W")){
                    if ((newPosition.getX() == position.getX() && newPosition.getY() == position.getY() - 1)
                            || (newPosition.getX() == position.getX() + 1 && newPosition.getY() == position.getY() - 1)
                            || (newPosition.getX() == position.getX() - 1 && newPosition.getY() == position.getY() - 1)
                    ) {
                        return true;
                    }
                }
                break;
        }

        return false;
    }
}
