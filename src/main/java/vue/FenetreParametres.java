package vue;

import metier.Simulation;
import persistance.SimulationManager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FenetreParametres extends JFrame {

    private JButton btnCommencer;
    private JButton btnChargerSimulation;
    private JButton btnAide;
    private JTextField txtNbTours;
    private JTextField txtNbPersonnages;

    public FenetreParametres() {
        this.setTitle("Simulation Entrainement et Combat");
        this.setSize(700, 700);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.init();
        this.setVisible(true);
    }

    private void init() {
        JLabel labNbTours = new JLabel("Nb tours : ");
        labNbTours.setBounds(250, 200, 60, 25);

        JLabel labNbPersonnages = new JLabel("Nb persos : ");
        labNbPersonnages.setBounds(250, 250, 70, 25);

        this.txtNbTours = new JTextField();
        this.txtNbTours.setBounds(320, 200, 100, 25);

        this.txtNbPersonnages = new JTextField();
        this.txtNbPersonnages.setBounds(320, 250, 100, 25);

        this.btnCommencer = new JButton("Commencer");
        this.btnCommencer.setBounds(280, 300, 120, 50);
        this.btnCommencer.addActionListener(new BtnListener(1));

        this.btnChargerSimulation = new JButton("Charger simulation existante");
        this.btnChargerSimulation.setBounds(240, 400, 200, 40);
        this.btnChargerSimulation.addActionListener(new BtnListener(2));
        this.activerBoutonCharger();

        this.btnAide = new JButton("?");
        this.btnAide.setBounds(520, 250, 50, 50);
        this.btnAide.addActionListener(new BtnListener(3));

        this.add(labNbTours);
        this.add(labNbPersonnages);
        this.add(this.txtNbTours);
        this.add(this.txtNbPersonnages);
        this.add(this.btnCommencer);
        this.add(this.btnChargerSimulation);
        this.add(this.btnAide);
    }

    private void activerBoutonCharger() {
        boolean actif = Files.exists(Paths.get(SimulationManager.lienFichierSimulation));
        this.btnChargerSimulation.setEnabled(actif);
    }

    private int verifierSaisie(int saisie) {
        if(saisie <= 0) {
            return 1;
        } else if(saisie > 10) {
            return 10;
        }
        return saisie;
    }

    private class BtnListener implements ActionListener {

        private final int typeAction;

        public BtnListener(int typeAction) {
            this.typeAction = typeAction;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            switch (this.typeAction) {
                case 1 :
                    boolean isIncomplet = txtNbTours.getText().isEmpty() || txtNbPersonnages.getText().isEmpty();
                    if(isIncomplet) {
                        JOptionPane.showMessageDialog(null,"Champs vides.", "Erreur de saisie", JOptionPane.ERROR_MESSAGE);
                    } else {
                        try {
                            int nbTours = Integer.parseInt(txtNbTours.getText());
                            int nbPersonnages = Integer.parseInt(txtNbPersonnages.getText());
                            nbTours = verifierSaisie(nbTours);
                            nbPersonnages = verifierSaisie(nbPersonnages);
                            Simulation simulation = new Simulation(nbTours, nbPersonnages);
                            setVisible(false);
                            new FenetreSimulation(simulation);
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(null,
                                    "Les champs ne doivent pas avoir de lettres.",
                                    "Erreur de saisie",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    break;
                case 2 :
                    setVisible(false);
                    new FenetreSimulation(SimulationManager.lire());
                    break;
                case 3 :
                    JOptionPane.showMessageDialog(null,
                            "Les options doivent être comprises entre 1 et 10.",
                            "Saisie des paramètres",
                            JOptionPane.INFORMATION_MESSAGE);
                    break;
            }
        }
    }



}
