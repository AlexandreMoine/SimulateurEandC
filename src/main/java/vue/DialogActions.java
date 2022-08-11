package vue;

import metier.Action;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;

public class DialogActions extends JDialog {

    private LinkedHashMap<Integer, LinkedHashMap<Integer, Action>> actions;
    private LinkedHashMap<Integer, JLabel> labelsActions;
    private JLabel labTour;
    private JButton btnPrecedent;
    private JButton btnSuivant;
    private int tour;

    public DialogActions(LinkedHashMap<Integer, LinkedHashMap<Integer, Action>> actions) {
        this.actions = actions;
        this.labelsActions = new LinkedHashMap<>();
        this.tour = this.getMinTour();
        this.setTitle("Actions du personnage");
        this.setModalityType(ModalityType.APPLICATION_MODAL);
        this.setSize(550, 530);
        this.setLayout(null);
        this.setLocationRelativeTo(null);
        this.init();
        this.setVisible(true);
    }

    private void init() {
        this.labTour = new JLabel("");
        this.labTour.setBounds(200, 50, 100, 25);

        int y = 100;
        for(int i = 1; i <= 10; i++) {
            JLabel labAction = new JLabel("");
            labAction.setBounds(150, y, 300, 25);
            y += 30;
            this.labelsActions.put(i, labAction);
        }

        this.btnPrecedent = new JButton("<=");
        this.btnPrecedent.setBounds(100, 420, 50, 30);
        this.btnPrecedent.addActionListener(new BtnListener('p'));

        this.btnSuivant = new JButton("=>");
        this.btnSuivant.setBounds(400, 420, 50, 30);
        this.btnSuivant.addActionListener(new BtnListener('s'));

        this.add(this.labTour);
        this.add(this.btnPrecedent);
        this.add(this.btnSuivant);
        this.labelsActions.values().forEach(this::add);
        this.afficherActions();
    }

    private int getMinTour() {
        Optional<Integer> minOptional = this.actions.keySet().stream().min(Comparator.naturalOrder());
        return minOptional.orElse(1);
    }

    private int getMaxTour() {
        Optional<Integer> minOptional = this.actions.keySet().stream().max(Comparator.naturalOrder());
        return minOptional.orElse(1);
    }

    private String getActionString(metier.Action action) {
        return action.getEntrainement() + " ("
                + action.getPtsPv() + " PV"
                + ", " + action.getPtsAtt() + " ATT"
                + ", " + action.getPtsDef() + " DEF)";
    }

    private void afficherActions() {
        this.labTour.setText("Tour n° " + this.tour);

        LinkedHashMap<Integer, Action> actionsTour = this.actions.get(this.tour);

        this.labelsActions.values().forEach(lab -> lab.setText(""));

        if(actionsTour != null) {
            for(Integer jour : actionsTour.keySet()) {
                this.labelsActions.get(jour).setText("Jour " + jour + " : " + this.getActionString(actionsTour.get(jour)));
            }
        }
    }

    private class BtnListener implements ActionListener {

        private char btnAct;

        public BtnListener(char btnAct) {
            this.btnAct = btnAct;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if(this.btnAct == 'p') {
                tour--;
                if(tour < getMinTour()) {
                    tour = getMinTour();
                }
            } else {
                tour++;
                if(tour > getMaxTour()) {
                    tour = getMaxTour();
                }
            }
            afficherActions();
        }
    }


}
