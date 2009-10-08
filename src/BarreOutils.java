/* Decompiled by Mocha from BarreOutils.class */
/* Originally compiled from Fractales.java */

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EventObject;

class BarreOutils extends Panel implements ActionListener
{
    Button zoomIn;
    Button zoomOut;
    Button couleurs;
    Button quitter;
    Fractales fractales;

    BarreOutils(Fractales fractales)
    {
        zoomIn = new Button("Zoom selection");
        zoomOut = new Button("Zoom arriere");
        couleurs = new Button("Redessiner");
        quitter = new Button("Arreter");
        this.fractales = fractales;
        setBackground(Color.black);
        zoomIn.addActionListener(this);
        zoomOut.addActionListener(this);
        couleurs.addActionListener(this);
        quitter.addActionListener(this);
        add(zoomIn);
        add(zoomOut);
        add(couleurs);
        add(quitter);
    }

    public void actionPerformed(ActionEvent e)
    {
        Object obj = e.getSource();
        if (obj == zoomIn)
            fractales.zoomIn();
        else if (obj == zoomOut)
            fractales.zoomOut();
        else if (obj == couleurs)
            fractales.couleurs();
        else if (obj == quitter)
            fractales.terminer();
    }
}
