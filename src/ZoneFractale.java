/* Decompiled by Mocha from ZoneFractale.class */
/* Originally compiled from Fractales.java */

import java.awt.*;
import java.awt.event.*;

class ZoneFractale extends Canvas implements MouseListener, MouseMotionListener, ComponentListener
{
    Fractales fractales;
    Image image;
    Calculateur calculateur;
    int sourisX1;
    int sourisY1;
    int sourisX2;
    int sourisY2;
    int cadreX1;
    int cadreY1;
    int cadreX2;
    int cadreY2;
    boolean cadreDefini;
    Color tabCouleur[];

    void melangeCouleurs()
    {
        double r = 0;
        double g = 0;
        double b = 0;
        double rv=3;
        double gv=5;
        double bv=7;
        double rd=1;
        double gd=1;
        double bd=1;
       
        for (int i = 0; i < Fractales.maxIterations; i++)
        {
            rv=3.0/Math.log(i/10.0+10);
            gv=5.0/Math.log(i/10.0+10);
            bv=7.0/Math.log(i/10.0+10);
            r+=rd*rv;
            g+=gd*gv;
            b+=bd*bv;
            if (r >= 256) {r=511.9999-r; rd=-rd;}
            if (g >= 256) {g=511.9999-g; gd=-gd;}
            if (b >= 256) {b=511.9999-b; bd=-bd;}
            if (r < 0) { r=-r; rd=-rd; }
            if (g < 0) { g=-g; gd=-gd; }
            if (b < 0) { b=-b; bd=-bd; }
            
            tabCouleur[i] = new Color((int)r, (int)g, (int)b);
        }
        tabCouleur[Fractales.maxIterations] = Color.black;
    }

    void zoomIn()
    {
        if (cadreDefini)
        {
            cadreDefini = false;
            arreter();
            if (cadreX2 - cadreX1 <= 0)
                cadreX2 = 1;
            if (cadreY2 - cadreY1 <= 0)
                cadreY2 = 1;
            Dimension dim = getSize();
            double xg = (double)cadreX1 * (fractales.x2 - fractales.x1) / dim.width + fractales.x1;
            double yh = (double)cadreY1 * (fractales.y2 - fractales.y1) / dim.height + fractales.y1;
            double xd = (double)cadreX2 * (fractales.x2 - fractales.x1) / dim.width + fractales.x1;
            double yb = (double)cadreY2 * (fractales.y2 - fractales.y1) / dim.height + fractales.y1;
            fractales.x1 = xg;
            fractales.y1 = yh;
            fractales.x2 = xd;
            fractales.y2 = yb;
            image = null;
            repaint();
        }
    }

    void zoomOut()
    {
        cadreDefini = false;
        arreter();
        double width = fractales.x2 - fractales.x1;
        double height = fractales.y2 - fractales.y1;
        fractales.x1 -= width;
        fractales.x2 += width;
        fractales.y1 -= height;
        fractales.y2 += height;
        image = null;
        repaint();
    }

    void couleurs()
    {
        melangeCouleurs();
        arreter();
        image = null;
        repaint();
    }

    void arreter()
    {
        if (calculateur != null)
        {
            calculateur.arreter();
            try
            {
                calculateur.join();
            }
            catch (InterruptedException e)
            {
            }
        }
    }

    public void mouseClicked(MouseEvent e)
    {
        cadreDefini = false;
        repaint();
    }

    public void mouseEntered(MouseEvent e)
    {
    }

    public void mouseExited(MouseEvent e)
    {
    }

    public void mousePressed(MouseEvent e)
    {
        cadreDefini = true;
        sourisX1 = e.getX();
        sourisY1 = e.getY();
        sourisX2 = sourisX1;
        sourisY2 = sourisY1;
        cadreX1 = sourisX1;
        cadreY1 = sourisY1;
        cadreX2 = sourisX2;
        cadreY2 = sourisY2;
    }

    public void mouseReleased(MouseEvent e)
    {
    }

    void dessineRectangle()
    {
        if (sourisX2 == sourisX1)
            sourisX2++;
        if (sourisY2 == sourisY1)
            sourisY2++;
        Dimension dim = getSize();
        double widthActuel = Math.abs(sourisX2 - sourisX1);
        double heightActuel = Math.abs(sourisY2 - sourisY1);
        double rapportVoulu = (double)dim.width / dim.height;
        double rapportActuel = widthActuel / heightActuel;
        if (rapportVoulu > rapportActuel)
            heightActuel = widthActuel / rapportVoulu;
        else
            widthActuel = heightActuel * rapportVoulu;
        if (sourisX2 > sourisX1)
        {
            cadreX1 = sourisX1;
            cadreX2 = (int)((double)cadreX1 + widthActuel);
        }
        else
        {
            cadreX2 = sourisX1;
            cadreX1 = (int)((double)sourisX1 - widthActuel);
        }
        if (sourisY2 > sourisY1)
        {
            cadreY1 = sourisY1;
            cadreY2 = (int)((double)cadreY1 + heightActuel);
        }
        else
        {
            cadreY2 = sourisY1;
            cadreY1 = (int)((double)sourisY1 - heightActuel);
        }
        Graphics g = getGraphics();
        g.setColor(Color.white);
        g.drawRect(cadreX1, cadreY1, (int)widthActuel, (int)heightActuel);
    }

    public void mouseDragged(MouseEvent e)
    {
        sourisX2 = e.getX();
        sourisY2 = e.getY();
        paint(getGraphics());
    }

    public void mouseMoved(MouseEvent e)
    {
    }

    public void componentHidden(ComponentEvent e)
    {
    }

    public void componentMoved(ComponentEvent e)
    {
    }

    public void componentResized(ComponentEvent e)
    {
        cadreDefini = false;
        arreter();
        image = null;
        repaint();
    }

    public void componentShown(ComponentEvent e)
    {
    }

    ZoneFractale(Fractales fractales)
    {
        cadreDefini = false;
        tabCouleur = new Color[Fractales.maxIterations+1];
        this.fractales = fractales;
        setBackground(Color.black);
        melangeCouleurs();
        addMouseListener(this);
        addMouseMotionListener(this);
        addComponentListener(this);
    }

    void lancerImage()
    {
        Dimension dim = getSize();
        image = createImage(dim.width, dim.height);
        calculateur = new Calculateur(fractales, this);
    }

    public void paint(Graphics g)
    {
        if (image == null)
            lancerImage();
        g.drawImage(image, 0, 0, this);
        if (cadreDefini)
            dessineRectangle();
    }
}
