package Metryki;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;

public class Grafiki extends JPanel {

    private Map<String, List<Long>> data;
    private int[] xValues;
    private String title;
    private String yLabel;

    public Grafiki(Map<String, List<Long>> data, int[] xValues, String title, String yLabel) {
        this.data = data;
        this.xValues = xValues;
        this.title = title;
        this.yLabel = yLabel;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int width = getWidth();
        int height = getHeight();
        int margin = 60;

        // osie
        g.drawLine(margin, height - margin, width - margin, height - margin); // X
        g.drawLine(margin, margin, margin, height - margin); // Y

        // tytul
        g.drawString(title, width / 2 - 50, 20);
        g.drawString(yLabel, 10, margin - 10);

        // max Y
        long globalMax = 1;
        for (List<Long> list : data.values()) {
            for (long v : list) {
                if (v > globalMax) globalMax = v;
            }
        }

        Color[] colors = {Color.RED, Color.BLUE, Color.GREEN, Color.ORANGE, Color.MAGENTA};
        int colorIndex = 0;

        // legenda
        int legendY = 40;

        for (String alg : data.keySet()) {

            List<Long> values = data.get(alg);
            g.setColor(colors[colorIndex % colors.length]);

            g.fillRect(width - 180, legendY, 10, 10);
            g.drawString(alg, width - 160, legendY + 10);
            legendY += 20;

            // rysowanie linii
            for (int i = 0; i < values.size() - 1; i++) {

                int x1 = getX(i, width, margin);
                int x2 = getX(i + 1, width, margin);

                int y1 = getY(values.get(i), height, margin, globalMax);
                int y2 = getY(values.get(i + 1), height, margin, globalMax);

                g.drawLine(x1, y1, x2, y2);
            }

            colorIndex++;
        }

        // os x
        g.setColor(Color.BLACK);

        for (int i = 0; i < xValues.length; i++) {
            int x = getX(i, width, margin);
            g.drawString(String.valueOf(xValues[i]), x - 15, height - margin + 20);
        }

        // opis osi x
        g.drawString("n (rozmiar danych)", width / 2 - 50, height - 10);
    }

    private int getX(int i, int width, int margin) {
        return margin + i * (width - 2 * margin) / (xValues.length - 1);
    }

    private int getY(long value, int height, int margin, long max) {

        double logValue = Math.log10(value + 1);
        double logMax = Math.log10(max + 1);

        return height - margin - (int) (logValue / logMax * (height - 2 * margin));
    }
}