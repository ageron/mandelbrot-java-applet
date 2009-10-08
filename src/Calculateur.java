/* Decompiled by Mocha from Calculateur.class */
/* Originally compiled from Fractales.java */

import java.awt.*;

class Calculateur extends Thread
{
    boolean arreter;
    Image image;
    Fractales fractales;
    ZoneFractale zone;
    Afficheur afficheur;
    Dimension dim;

    void arreter()
    {
        arreter = true;
    }

    Calculateur(Fractales fractales, ZoneFractale zone)
    {
        arreter = false;
        this.zone = zone;
        dim = zone.getSize();
        image = zone.image;
        this.fractales = fractales;
        start();
        afficheur = new Afficheur(zone);
    }

    Color calcCouleur(double x, double y)
    {
        int iter;
        double u = x;
        double v = y;
        double oldu = u;
        for (iter = 0; iter < Fractales.maxIterations; iter++)
        {
            u = u * u - v * v - x;
            v = 2.0 * oldu * v - y;
            oldu = u;
            double carreDist = u * u + v * v;
            if (carreDist > Fractales.maxRayon)
                break;
        }
        return zone.tabCouleur[iter];
    }

    public void run()
    {
        Graphics g = image.getGraphics();
        int dimX = dim.width;
        int dimY = dim.height;
        double x1 = fractales.x1;
        double y1 = fractales.y1;
        double x2 = fractales.x2;
        double y2 = fractales.y2;
        double h = (x2 - x1) / dimX * 10.0;
        double v = (y2 - y1) / dimY * 10.0;
        double x = x1 + h / 2.0;
        double y = y1 + v / 2.0;
        for (int j = 0; j < dimY; j += 10)
        {
            int i;
            for (i = 0; i < dimX && !arreter; i += 10)
            {
                g.setColor(calcCouleur(x, y));
                g.fillRect(i, j, 9, 9);
                x += h;
            }
            x = x1;
            y += v;
        }
        h /= 10.0;
        v /= 10.0;
        x = x1 + h / 2.0;
        y = y1 + v / 2.0;
        for (int j = 0; j < dimY; j++)
        {
            int i;
            for (i = 0; i < dimX && !arreter; i++)
            {
                g.setColor(calcCouleur(x, y));
                g.drawLine(i, j, i, j);
                x += h;
            }
            x = x1;
            y += v;
        }
        afficheur.arreter();
        try
        {
            afficheur.join();
        }
        catch (InterruptedException e)
        {
        }
    }
}
