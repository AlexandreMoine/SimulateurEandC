package vue;

import exception.FusionException;
import metier.Personnage;
import metier.Simulation;
import persistance.SimulationManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.stream.Collectors;

public class FenetreSimulation extends JFrame {

    private Simulation simulation;

    private JLabel labTourEtJour;
    private JButton btnEntrainement;
    private JButton btnCombat;
    private JButton btnSauvegarde;
    private JButton btnFusion;
    private JPanel panelPersonnages;
    private ArrayList<PanelPersonnage> panelPersonnageList;
    private boolean canFusion;

    public FenetreSimulation(Simulation simulation) {
        this.simulation = simulation;
        this.panelPersonnageList = new ArrayList<>();
        this.canFusion = false;
        this.setTitle("Simulateur Entrainement & Combat");
        this.setSize(1300, 900);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.init();
        this.setVisible(true);
    }

    private void init() {
        this.labTourEtJour = new JLabel("Tour n°" + this.simulation.getTour() + " - Jour " + this.simulation.getJour());
        this.labTourEtJour.setBounds(500, 100, 200, 50);
        this.labTourEtJour.setFont(new Font("Calibri", Font.BOLD, 25));

        this.btnEntrainement = new JButton("Commencer l'entrainement du jour");
        this.btnEntrainement.setBounds(900, 60, 250, 50);
        this.btnEntrainement.addActionListener(new Btnlistener(1));

        this.btnCombat = new JButton("Commencer le combat");
        this.btnCombat.setBounds(900, 120, 250, 50);
        this.btnCombat.addActionListener(new Btnlistener(2));
        this.btnCombat.setEnabled(false);

        this.btnFusion = new JButton("Fusionner");
        this.btnFusion.setBounds(900, 180, 250, 50);
        this.btnFusion.addActionListener(new Btnlistener(3));
        this.btnFusion.setEnabled(false);

        this.btnSauvegarde = new JButton("Sauvegarder");
        this.btnSauvegarde.setBounds(900, 240, 250, 50);
        this.btnSauvegarde.addActionListener(new Btnlistener(4));
        this.btnSauvegarde.setEnabled(true);

        this.panelPersonnages = new JPanel();
        this.panelPersonnages.setBounds(100, 380, 1100, 450);
        this.panelPersonnages.setLayout(null);

        this.add(this.labTourEtJour);
        this.add(this.btnEntrainement);
        this.add(this.btnCombat);
        this.add(this.btnSauvegarde);
        this.add(this.btnFusion);
        this.add(this.panelPersonnages);

        this.initPanelPersonnages();
        this.activerBoutons();
    }

    private void initPanelPersonnages() {
        this.panelPersonnages.removeAll();
        this.panelPersonnageList.clear();
        int x = 10;
        int y = 10;
        for(Personnage p : this.simulation.getPersonnages()) {
            PanelPersonnage panelPersonnage = new PanelPersonnage(p);
            panelPersonnage.setLocation(x, y);
            panelPersonnage.addMouseListener(new ClicPanelPersonnage());
            this.panelPersonnageList.add(panelPersonnage);
            this.panelPersonnages.add(panelPersonnage);
            x += 220;
            if(x > 980) {
                x = 10;
                y = 240;
            }
        }
    }

    private void activerBoutons() {
        if(simulation.estTerminee()) {
            btnEntrainement.setEnabled(false);
            btnCombat.setEnabled(false);
        } else if(simulation.getJour() > 10) {
            btnEntrainement.setEnabled(false);
            btnCombat.setEnabled(true);
        } else {
            btnEntrainement.setEnabled(true);
            btnCombat.setEnabled(false);
        }
    }

    private void startFusion() {
        if(!this.simulation.estTerminee()) {
            this.canFusion = true;
            JOptionPane.showMessageDialog(null, "Clique sur les personnages que tu souhaites fusionner.");
        }
    }

    private long getNbPanelsCliques() {
        return panelPersonnageList.stream().filter(PanelPersonnage::getEstClique).count();
    }

    private class Btnlistener implements ActionListener {

        private final int typeAction;

        public Btnlistener(int typeAction) {
            this.typeAction = typeAction;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            switch(this.typeAction) {
                case 1:
                    canFusion = false;
                    btnFusion.setEnabled(false);
                    int jourActuel = simulation.getJour();
                    simulation.startEntrainement();
                    JOptionPane.showMessageDialog(null,
                            "Fin du jour " + jourActuel,
                            "Entrainement",
                            JOptionPane.INFORMATION_MESSAGE);

                    panelPersonnageList.forEach(PanelPersonnage::reload);
                    activerBoutons();
                    if(simulation.getJour() <= 10) {
                        labTourEtJour.setText("Tour n°" + simulation.getTour() + " - Jour " + simulation.getJour());
                    }
                    break;
                case 2:
                    simulation.startCombat();
                    JOptionPane.showMessageDialog(null,
                            "Le combat est terminé",
                            "Combat",
                            JOptionPane.INFORMATION_MESSAGE);
                    panelPersonnageList.forEach(PanelPersonnage::reload);
                    activerBoutons();
                    labTourEtJour.setText("Tour n°" + simulation.getTour() + " - Jour " + simulation.getJour());
                    startFusion();
                    break;
                case 3:
                    java.util.List<PanelPersonnage> listePanelChoisis = panelPersonnageList.stream().filter(PanelPersonnage::getEstClique).collect(Collectors.toList());
                    PanelPersonnage panelPersonnage1 = listePanelChoisis.get(0);
                    PanelPersonnage panelPersonnage2 = listePanelChoisis.get(1);

                    try {
                        simulation.fusionner(panelPersonnage1.getPersonnage(), panelPersonnage2.getPersonnage());
                        initPanelPersonnages();
                        panelPersonnageList.forEach(PanelPersonnage::reload);
                        repaint();
                        btnFusion.setEnabled(getNbPanelsCliques() == 2);
                    } catch (FusionException ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                    }

                    break;
                case 4:
                    SimulationManager.ecrire(simulation);
                    JOptionPane.showMessageDialog(null, "La simulation est sauvegardée.");
                    break;
            }
        }
    }

    private class ClicPanelPersonnage extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            if(canFusion) {
                PanelPersonnage panelPersonnage = (PanelPersonnage) e.getSource();
                panelPersonnage.setEstClique(!panelPersonnage.getEstClique() && getNbPanelsCliques() < 2);
                btnFusion.setEnabled(getNbPanelsCliques() == 2);
            }
        }
    }
}
