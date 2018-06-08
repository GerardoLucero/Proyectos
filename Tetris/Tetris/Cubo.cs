using System;
using System.Collections.Generic;
using System.Drawing;
using System.Text;
using System.Windows.Forms;

namespace Tetris
{
    class Cuadrado
    {
        private int x1;
        private int y1;
        private int ancho;
        private Color color;
        Color borrar = Control.DefaultBackColor;
        Graphics g;

        public Cuadrado(int x1, int y1, Color c, int a,Graphics g )
        {
            this.x1 = x1;
            this.y1 = y1;
            this.color = c;
            this.ancho = a;

            DibujarCubo( g);
        }

        public float getx1()
        {
            return x1;
        }

        public void setx1(int x)
        {
            this.x1 = x;
        }


        public float gety1()
        {
            return y1;
        }

        public void sety1(int y)
        {
            this.y1 = y;
        }

        public Color getColor()
        {
            return this.color;
        }

        public void setx1(Color c)
        {
            this.color = c;
        }


        //Metodos
        public void DibujarCubo(Graphics g)
        {
            SolidBrush brush = new SolidBrush(this.color);
            Pen mypen = new Pen(Color.Black);


            g.FillRectangle(brush, new Rectangle(this.x1, this.y1, this.ancho, this.ancho));
            g.DrawRectangle(mypen, new Rectangle(this.x1, this.y1, this.ancho, this.ancho));

            brush.Dispose();
            mypen.Dispose();

        }


        public void MoverCubo(int x1, int y1)
        {
            //Borrar anterior
            SolidBrush brush = new SolidBrush(this.borrar);
            Pen mypen = new Pen(Color.Black);
            g.FillRectangle(brush, new Rectangle(this.x1, this.y1, this.ancho, this.ancho));
            g.DrawRectangle(mypen, new Rectangle(this.x1, this.y1, this.ancho, this.ancho));


            //Setear posicion nueva
            setx1(x1);
            sety1(y1);

            //Redibujar
            SolidBrush brush1 = new SolidBrush(this.color);
            Pen mypen1 = new Pen(Color.Black);
            g.FillRectangle(brush1, new Rectangle(this.x1, this.y1, this.ancho, this.ancho));
            g.DrawRectangle(mypen1, new Rectangle(this.x1, this.y1, this.ancho, this.ancho));


        }

    }
}
