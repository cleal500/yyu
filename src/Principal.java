import javax.swing.*;

import static javax.swing.SwingUtilities.invokeLater;

public class Principal {
    public static void main( String[] args ) {

        Fenetre fen = new Fenetre ();
        invokeLater (LSystem::new);


    }

}
