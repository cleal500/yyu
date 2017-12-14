import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JOptionPane;
import static java.awt.Color.green;
import static java.lang.Integer.parseInt;
import static javax.swing.JOptionPane.*;

public class Fenetre extends JFrame {

    private JPanel panneauRegles = new JPanel ();
    private int indexer = 1;
    private JTextArea AffResultat;
    private ArrayList<JTextField> listDesClee = new ArrayList<> ();
    private ArrayList<JTextField> listDesChaines = new ArrayList<> ();

    public Fenetre() {
        this.setTitle ("L-Système");
        this.setSize (625, 600);
        this.setLocationRelativeTo (null);
        this.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        this.setLayout (new GridBagLayout());
        this.setResizable(false);

        // Contrainte pour le cadre principal. Il s'agit du gros bloc dans lequel tout les autres blocs
        // seront situé
        GridBagConstraints contrainteCadre = new GridBagConstraints ();

        // Bouton pour ajouter des éléments.
        JButton ajouter = new JButton ("Ajouter");
        ajouter.addActionListener (this::actionPerformedAjouter);


        // bouton pour supprimer des éléments.
        JButton supprimer = new JButton ("Supprimer");
        supprimer.addActionListener (this::actionPerformSupprimer);

        // Afficahge des boutons ajouter et supprimer.
        JPanel affBouton = new JPanel (new FlowLayout ());
        affBouton.add (ajouter, 0);
        affBouton.add (supprimer, 1);
        contrainteCadre.gridx = 0;
        contrainteCadre.gridy = 3;
        contrainteCadre.weightx = 0;
        this.add (affBouton, contrainteCadre);

        // Contruction du panneau principal qui va contenir les règles.
        panneauRegles.setPreferredSize (new Dimension (300, 400));
        panneauRegles.setLayout (new GridBagLayout ());
        panneauRegles.setBackground (Color.orange);
        panneauRegles.setBorder (LineBorder.createBlackLineBorder ());


        // Ajout du panneauRegles au panneau principal
        contrainteCadre.gridx = 0;
        contrainteCadre.gridy = 1;
        contrainteCadre.weighty = 0;
        this.add (panneauRegles, contrainteCadre);


        // Ajout du panneau resultat.
        JPanel resultat = new JPanel ();
        resultat.setPreferredSize (new Dimension (300, 400));
        resultat.setBorder (LineBorder.createBlackLineBorder ());
        resultat.setBackground (Color.cyan);

        // Ajout de la partie ou vont s'imprimer les résultat dans le panneau.
        AffResultat = new JTextArea (24, 24);
        AffResultat.setLineWrap (true);
        AffResultat.setWrapStyleWord (true);
        AffResultat.setBackground (Color.cyan);
        JScrollPane jsp = new JScrollPane (AffResultat);
        jsp.setBorder (null);
        resultat.add (jsp);

        // On ajoute le tout au panneau principal.
        contrainteCadre.gridx = 1;
        contrainteCadre.gridy = 1;
        contrainteCadre.weighty = 0;
        this.add (resultat, contrainteCadre);



        // Panneau avec les instruction de basse ( nombre d'itration et axiome depart).
        JPanel depart = new JPanel (new GridLayout (3, 2));

        JTextField axiomeEntre = new JTextField ();
        JFormattedTextField NBRAxiomeText = new JFormattedTextField (NumberFormat.getIntegerInstance ());

        // Écoute le clavier pour appliqué immédiatement les modifications au texte pour qu'il soit comforme
        // au condition. (Pas d'espace)
        axiomeEntre.addKeyListener (new KeyListener () {
            @Override
            public void keyTyped(KeyEvent e) { }
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode () == KeyEvent.VK_PASTE) {
                    axiomeEntre.setText (axiomeEntre.getText ().replaceAll ("\\s+", ""));
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyChar () == ' ') {
                    axiomeEntre.setText (axiomeEntre.getText ().replace (String.valueOf (e.getKeyChar ()), ""));
                }
            }
        });

        // Affichage des éléments de départs.
        NBRAxiomeText.setPreferredSize (new Dimension (100, 20));
        JLabel axiomeEtiquette = new JLabel ("Axiome :");
        depart.add (axiomeEtiquette, 0);
        depart.add (axiomeEntre, 1);
        JLabel NBREtiquette = new JLabel ("Nombre d'itération :");
        depart.add (NBREtiquette, 2);
        depart.add (NBRAxiomeText, 3);

        // On ajoute le tout au panneau principal.
        contrainteCadre.gridx = 1;
        contrainteCadre.gridy = 0;
        contrainteCadre.weighty = 0;
        this.add (depart, contrainteCadre);


        // Boutom qui permet de mettre en action le programe et qui valide les entrés.
        JButton interpreter = new JButton ("Interpreter");
        interpreter.addActionListener (e -> SwingUtilities.invokeLater (() -> {

            // Variable pour lancer la verifivation et construire le L-Systeme.
            ArrayList<Regle> data = new ArrayList<> ();
            String axiomeDepart = axiomeEntre.getText ();
            int NBAxiome = 0;
            boolean estValide = true;

            // On essaie de prendre le chiffre dans le nombre d'itération de départ. Et on vérifie s'il n'est pas 0.
            try {
                NBAxiome = parseInt (NBRAxiomeText.getText ());
                if (NBAxiome <= 0) {
                    throw new NumberFormatException ();
                }

                if (listDesClee.size () == listDesChaines.size ()) {
                    for (int i = 0; i < listDesClee.size (); i++) {
                        boolean copie = false;

                        try {
                            char clee = listDesClee.get (i).getText ().charAt (0);

                            String chaine = listDesChaines.get (i).getText ();

                            for (Regle aData : data) {
                                if (clee == aData.clee) {
                                    copie = true;
                                }
                            }
                            if (copie) {
                                estValide = false;
                                showMessageDialog (null,
                                        "Vous Devez Entrer qu'une seul règle PAR CLÉE. " +
                                                "Les doublons sont interdi!",
                                        "Erreur", JOptionPane.ERROR_MESSAGE);
                            } else {
                                data.add (new Regle (clee, chaine));
                            }
                        } catch (StringIndexOutOfBoundsException t1) {
                            estValide = false;
                            showMessageDialog (null,
                                    "Vous Devez Entrer 1 Amoxiome par clées",
                                    "Erreur", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } else {
                    showMessageDialog (null, "ERREUR FATAL",
                            "Erreur Fatal", JOptionPane.ERROR_MESSAGE);
                    System.exit (1);
                }
                if (axiomeDepart.length () <= 0) {
                    estValide = false;
                    showMessageDialog (null,
                            "AXIOME invalide, il doit y avoir au moins 1 amoxiome de depart.",
                            "Erreur Amoxiome", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException t2) {
                estValide = false;
                showMessageDialog (null,
                        "Nombre d'itération invalide, il doit être plus grand que 0.",
                        "Erreur NBREtiquette. Itération", JOptionPane.ERROR_MESSAGE);
            }

            if (estValide) {
                Fenetre.this.affichage (data, NBAxiome, axiomeDepart);
            }

        }));

        // Partie qui affiche le bouton interprété
        JPanel inter = new JPanel ();
        inter.add (interpreter);
        contrainteCadre.gridx = 1;
        contrainteCadre.gridy = 3;
        contrainteCadre.weighty = 0;
        this.add (inter, contrainteCadre);

        panneauRegles.revalidate ();
        this.setVisible (true);
    }

    private void affichage(ArrayList<Regle> data, int NBD, String axiome) {
        AffResultat.removeAll ();
        AffResultat.setText (null);

        LSystem L = new LSystem ();

        for (Regle x : data) {
            L.ajouterRegle (x.clee, x.chaine);
        }

        // On affiche et on valide les résultats
        AffResultat.append (L.apply (NBD, axiome));
        panneauRegles.revalidate ();
        panneauRegles.repaint ();
    }

    private void actionPerformedAjouter(ActionEvent e) {
        SwingUtilities.invokeLater (() -> {

            // On refait tout le panneau à chaque fois.
            panneauRegles.removeAll ();

            // Création des JTextField pour les chaines et clées.
            JTextField regleUtil = new JTextField ();
            regleUtil.setSize (100, 200);
            regleUtil.setBackground (Color.green);
            regleUtil.addKeyListener (new KeyListener () {
                @Override
                public void keyTyped(KeyEvent e1) { }

                @Override
                public void keyPressed(KeyEvent e1) { }

                @Override
                public void keyReleased(KeyEvent e1) {
                    if (e1.getKeyChar () == ' ') {
                        regleUtil.setText (regleUtil.getText ().replace (String.valueOf (e1.getKeyChar ()), ""));
                    }
                }
            });

            // On Crée les champs qui s'occupe de la clée.
            JTextField cleeUtil = new JTextField ();
            cleeUtil.setSize (60, 60);
            cleeUtil.setBackground (Color.green);
            cleeUtil.addMouseListener (new MouseAdapter () {
                @Override
                public void mouseClicked(MouseEvent e1) {
                    cleeUtil.setText ("");
                }
            });

            cleeUtil.addKeyListener (new KeyListener () {
                @Override
                public void keyTyped(KeyEvent e1) {

                    if (cleeUtil.getText ().length () > 0) {
                        e1.consume ();
                    }
                }

                @Override
                public void keyPressed(KeyEvent e1) { }

                @Override
                public void keyReleased(KeyEvent e1) {
                    if (e1.getKeyChar () == ' ') {
                        cleeUtil.setText (cleeUtil.getText ().replace (String.valueOf (e1.getKeyChar ()), ""));
                    }
                }
            });
            listDesChaines.add (regleUtil);
            listDesClee.add (cleeUtil);

            // Création des contraintes pour les JTextField
            GridBagConstraints contrainteChaine = new GridBagConstraints ();
            GridBagConstraints ContrainteClee = new GridBagConstraints ();


            // On ajoute les JtextField pour les clee et les chaines.
            for (int i = 0; i < indexer; i++) {

                contrainteChaine.gridx = 1;
                contrainteChaine.fill = GridBagConstraints.HORIZONTAL;
                contrainteChaine.weightx = 0.5;
                contrainteChaine.insets = new Insets (5, 5, 5, 5);
                contrainteChaine.gridy = i;

                ContrainteClee.gridx = 0;
                ContrainteClee.fill = GridBagConstraints.HORIZONTAL;
                ContrainteClee.weightx = 0.1;
                ContrainteClee.insets = new Insets (5, 5, 5, 5);
                ContrainteClee.gridy = i;

                // On ajoute le tout au panneau.
                panneauRegles.add (listDesChaines.get (i), contrainteChaine);
                panneauRegles.add (listDesClee.get (i), ContrainteClee);
            }
            // Pour enliger le programme de haut en bas.
            GridBagConstraints p = new GridBagConstraints ();
            p.gridx = 0;
            p.gridy = indexer;
            p.weighty = 1;
            panneauRegles.add (new JLabel (), p);

            // Tenir le compte de où on est on utilise l'indexer
            indexer++;

            // On actualise le tout.
            panneauRegles.updateUI ();
            panneauRegles.revalidate ();
            panneauRegles.repaint ();
        });
    }

    private void actionPerformSupprimer(ActionEvent e) {
        SwingUtilities.invokeLater (() -> {
            panneauRegles.remove (listDesChaines.get (listDesChaines.size () - 1));
            panneauRegles.remove (listDesClee.get (listDesClee.size () - 1));

            listDesChaines.remove (listDesChaines.size () - 1);
            listDesClee.remove (listDesClee.size () - 1);

            indexer--;
            panneauRegles.updateUI ();
            panneauRegles.revalidate ();
            panneauRegles.repaint ();
        });
    }
}







