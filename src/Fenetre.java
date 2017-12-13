import javax.swing.*;
import javax.swing.border.LineBorder;

import java.awt.*;
import java.awt.event.*;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.border.LineBorder;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.MaskFormatter;
import javax.swing.JOptionPane;

import static java.lang.Integer.parseInt;
import static javax.swing.JOptionPane.*;

public class Fenetre extends JFrame {
    JFrame frame;
    JPanel panel = new JPanel();
JLabel axiome = new JLabel("Axiome :");
    JLabel NBR = new JLabel("Nombre d'itération :");
    JLabel resultatL = new JLabel("Résulat :");

    int indexer = 1;
 JTextArea result = new JTextArea(24, 24);
 ArrayList<JTextField> listDesClee = new ArrayList<JTextField>();
    ArrayList<JTextField> listDesChaines = new ArrayList<JTextField>();


    public Fenetre() {
        this.frame = new JFrame();
        this.setTitle("L-Système");
        this.setSize(700, 700);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new GridBagLayout());

        // Frame constraints
        GridBagConstraints contrainteCadre = new GridBagConstraints();



        JButton ajouter = new JButton("Ajouter");
        ajouter.addActionListener((ActionEvent e) -> {

            // Clear panel
            panel.removeAll();

            // Create label and text field
            JTextField regleUtil = new JTextField();
            regleUtil.setSize(100, 200);


            JTextField cleeUtil = new JTextField();
            cleeUtil.setSize(60, 60);

            listDesChaines.add(regleUtil);
            listDesClee.add(cleeUtil);

            // Create constraints
            GridBagConstraints textFieldConstraints = new GridBagConstraints();
            GridBagConstraints labelConstraints = new GridBagConstraints();


            // Add labels and text fields
            for (int i = 0; i < indexer; i++) {
                // Text field constraints
                textFieldConstraints.gridx = 1;

                textFieldConstraints.fill = GridBagConstraints.HORIZONTAL;
                textFieldConstraints.weightx = 0.5;
                textFieldConstraints.insets = new Insets(5, 5, 5, 5);
                textFieldConstraints.gridy = i;

                // Label constraints
                labelConstraints.gridx = 0;
                labelConstraints.fill = GridBagConstraints.HORIZONTAL;
                labelConstraints.weightx = 0.1;
                labelConstraints.insets = new Insets(5, 5, 5, 5);
                labelConstraints.gridy = i;

                // Add them to panel
                panel.add(listDesChaines.get(i), textFieldConstraints);
                panel.add(listDesClee.get(i), labelConstraints);
            }

            // Align components top-to-bottom
            GridBagConstraints c = new GridBagConstraints();
            c.gridx = 0;
            c.gridy = indexer;
            c.weighty = 1;
            panel.add(new JLabel(), c);

            // Increment indexer
            indexer++;
            panel.updateUI();


        });



        JButton supprimer = new JButton("Supprimer");
        supprimer.addActionListener(e -> {
            panel.remove(listDesChaines.get(listDesChaines.size() - 1));
            panel.remove(listDesClee.get(listDesClee.size() - 1));

            listDesChaines.remove(listDesChaines.size() - 1);
            listDesClee.remove(listDesClee.size() - 1);

            indexer--;

            panel.updateUI();
        });

        JPanel boutonp = new JPanel(new FlowLayout());


        boutonp.add(ajouter, 0);
        boutonp.add(supprimer, 1);

        contrainteCadre.gridx = 0;
        contrainteCadre.gridy = 3;
        contrainteCadre.weightx = 0;

        this.add(boutonp, contrainteCadre);

        // Construct panel
        panel.setPreferredSize(new Dimension(300, 400));
        panel.setLayout(new GridBagLayout());
        panel.setBorder(LineBorder.createBlackLineBorder());

        // Add panel to frame
        contrainteCadre.gridx = 0;
        contrainteCadre.gridy = 1;
        contrainteCadre.weighty = 0;

        this.add(panel, contrainteCadre);


        JPanel resultat = new JPanel();
        resultat.setPreferredSize(new Dimension(300, 400));
        resultat.setBorder(LineBorder.createBlackLineBorder());
        result.setLineWrap(true);
        result.setWrapStyleWord(true);
        JScrollPane jsp = new JScrollPane(result);
        resultat.add(jsp);


        contrainteCadre.gridx = 1;
        contrainteCadre.gridy = 1;
        contrainteCadre.weighty = 0;

        this.add(resultat, contrainteCadre);


        contrainteCadre.gridx = 1;
        contrainteCadre.gridy = 0;
        contrainteCadre.weighty = 0;

        JPanel demarer = new JPanel(new GridLayout(3, 2));


        JTextField axiomeEntre = new JTextField();
        JFormattedTextField NbrAxiome = new JFormattedTextField(NumberFormat.getIntegerInstance());



        NbrAxiome.setPreferredSize(new Dimension(100, 20));
        demarer.add(axiome, 0);
        demarer.add(axiomeEntre, 1);
        demarer.add(NBR, 2);
        demarer.add(NbrAxiome, 3);

        this.add(demarer, contrainteCadre);





        JButton interpreter = new JButton("Interpreter");
        interpreter.addActionListener(e -> {






            ArrayList<Regle> data = new ArrayList<>();

            if(listDesClee.size() == listDesChaines.size()) {
                for (int i = 0; i < listDesClee.size(); i++) {
                    String temp1;
                    String temp2;

                    char temp11;
                    boolean copie = false;

                    temp1=listDesClee.get(i).getText().trim().toUpperCase();
                    temp2=listDesChaines.get(i).getText().replaceAll("\\s+","").toUpperCase();


                    JTextField temp3 = new JTextField(temp2);
                     listDesChaines.set(i,temp3);

                     JTextField temp4 = new JTextField(temp1);
                     listDesClee.set(i,temp4);

                     temp11 = temp1.charAt(0);


                    for(int y =0; y<data.size();++y){
                        if(temp11 == data.get(y).clee){
                            copie = true;
                        }

                    }
                    if(temp1.length() != 1){
                        showMessageDialog(null, "Vous Devez Entrer qu'une seul Lettre dans la Clé.", "Erreur", JOptionPane.ERROR_MESSAGE);
                    }else if( copie) {
                        showMessageDialog(null, "Vous Devez Entrer qu'une seul règle PAR CLÉE.", "Erreur", JOptionPane.ERROR_MESSAGE);

                    }else{
                        data.add(new Regle(temp11,temp2));

                    }






                }
            }

            LSystem L = new LSystem();

          for (int z =0; z<data.size();++z){

                L.ajouterRegle(data.get(z).clee,data.get(z).chaine);

            }

            String n = axiomeEntre.getText().replaceAll("\\s+","").toUpperCase();


            int NBT = parseInt(NbrAxiome.getText());



            if(n.length() <=0) {
                showMessageDialog(null, "AXIOME invalide.", "Erreur", JOptionPane.ERROR_MESSAGE);

            }else if( !(NBT >= 0) ){
                showMessageDialog(null, "NOMBRE invalide.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }else{


                result.append(L.apply(NBT,n));

            }


            panel.updateUI();



        });

        JPanel inter = new JPanel();
        inter.add(interpreter);
        contrainteCadre.gridx = 1;
        contrainteCadre.gridy = 3;
        contrainteCadre.weighty = 0;
        this.add(inter, contrainteCadre);

        panel.updateUI();

        this.setVisible(true);

    }

}









