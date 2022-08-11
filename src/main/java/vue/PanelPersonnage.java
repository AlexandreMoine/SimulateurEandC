package vue;

import metier.Personnage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PanelPersonnage extends JPanel {

    private Personnage personnage;
    private JLabel labId;
    private JProgressBar progressBarPv;
    private JLabel labAtt;
    private JLabel labDef;
    private JButton btnActions;
    private boolean estClique;

    public PanelPersonnage(Personnage personnage) {
        this.personnage = personnage;
        this.estClique = false;
        this.setSize(200, 200);
        this.setLayout(null);
        this.init();
        this.setVisible(true);
    }

    private void init() {
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        this.labId = new JLabel(this.personnage.getId());
        this.labId.setBounds(50, 10, 100, 30);

        this.progressBarPv = new JProgressBar();
        this.progressBarPv.setMinimum(0);
        this.progressBarPv.setMaximum(100);
        this.progressBarPv.setValue(this.personnage.getPv().getPourcent());
        this.progressBarPv.setStringPainted(true);
        this.progressBarPv.setString(this.personnage.getPv().getValeur() + "/" + this.personnage.getPv().getMax());
        this.progressBarPv.setForeground(Color.GREEN);
        this.progressBarPv.setBounds(10, 60, 180, 20);

        this.labAtt = new JLabel("ATT : " + this.personnage.getAtt());
        this.labAtt.setBounds(10, 90, 80, 20);

        this.labDef = new JLabel("DEF : " + this.personnage.getDef());
        this.labDef.setBounds(10, 120, 80, 20);

        this.btnActions = new JButton("Actions");
        this.btnActions.setBounds(70, 160, 80, 30);
        this.btnActions.addActionListener(new BtnActionsListener());

        this.add(this.labId);
        this.add(this.progressBarPv);
        this.add(this.labAtt);
        this.add(this.labDef);
        this.add(this.btnActions);
    }

    public void reload() {
        this.labAtt.setText("ATT : " + this.personnage.getAtt());
        this.labDef.setText("DEF : " + this.personnage.getDef());
        this.progressBarPv.setValue(this.personnage.getPv().getPourcent());
        this.progressBarPv.setString(this.personnage.getPv().getValeur() + "/" + this.personnage.getPv().getMax());
        if(this.personnage.getPv().getValeur() == 0) {
            this.labId.setText(this.personnage.getId() + " (KO)");
        }
    }

    public boolean getEstClique() {
        return this.estClique;
    }

    public void setEstClique(boolean estClique) {
        this.estClique = estClique;
        this.modifierCouleurBordure();
    }

    public Personnage getPersonnage() {
        return personnage;
    }

    private void modifierCouleurBordure() {
        if(this.estClique) {
            setBorder(BorderFactory.createLineBorder(Color.RED, 2));
        } else {
            setBorder(BorderFactory.createLineBorder(Color.BLACK));
        }
    }


    private class BtnActionsListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            new DialogActions(personnage.getActions());
        }
    }
}
