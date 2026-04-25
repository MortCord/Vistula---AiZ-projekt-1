import Algrotytmy.*;
import Metryki.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.FileWriter;
import java.util.*;
import java.util.List;

public class Main {

    static AlgrorytmSortowania[] algorithms = {
            new HeapSort(),
            new MergeSort(),
            new QuickSort(),
            new BubbleSort(),
    };

    static String[] dataTypes = {"losowe", "rosnace", "malejace", "czesciowo"};

    static int[] sizes = {100, 1000, 5000, 10000, 50000};

    static Map<String, List<Long>> timeData = new HashMap<>();
    static Map<String, List<Long>> memoryData = new HashMap<>();
    static Map<String, List<Long>> compData = new HashMap<>();
    static Map<String, List<Long>> swapData = new HashMap<>();

    static JPanel chartContainer = new JPanel(new BorderLayout());

    public static void main(String[] args) {

        JFrame frame = new JFrame("Projekt 1");
        frame.setSize(1100, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton runBtn = new JButton("Uruchom testy");

        frame.setLayout(new BorderLayout());
        frame.add(runBtn, BorderLayout.NORTH);

        runBtn.addActionListener(e -> runTests(frame));

        frame.setVisible(true);
    }

    static void runTests(JFrame frame) {

        String[] columns = {"Algorytm", "typ danych", "n", "czas", "porównania", "zamiany", "pamięć"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);

        timeData.clear();
        memoryData.clear();
        compData.clear();
        swapData.clear();

        try {
            FileWriter fw = new FileWriter("results.txt");
            fw.write("alg,type,n,time,comp,swap,mem\n");

            for (AlgrorytmSortowania alg : algorithms) {

                List<Long> tList = new ArrayList<>();
                List<Long> mList = new ArrayList<>();
                List<Long> cList = new ArrayList<>();
                List<Long> sList = new ArrayList<>();

                for (int n : sizes) {

                    long sumTime = 0;
                    long sumMem = 0;
                    long sumComp = 0;
                    long sumSwap = 0;

                    for (String type : dataTypes) {

                        int[] data;

                        switch (type) {
                            case "rosnace": data = DataSet.ascending(n); break;
                            case "malejace": data = DataSet.descending(n); break;
                            case "czesciowo": data = DataSet.partiallySorted(n); break;
                            default: data = DataSet.random(n);
                        }

                        Metryki m = new Metryki();

                        long memBefore = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

                        long start = System.nanoTime();
                        alg.sort(data.clone(), m);
                        long end = System.nanoTime();

                        long memAfter = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

                        long time = end - start;
                        long memUsed = Math.max(memAfter - memBefore, m.pamiec);

                        // SUMY DO WYKRESU
                        sumTime += time;
                        sumMem += memUsed;
                        sumComp += m.porownania;
                        sumSwap += m.zamiany;

                        // ZAPIS DO PLIKU
                        fw.write(alg.getName() + "," + type + "," + n + "," + time + "," +
                                m.porownania + "," + m.zamiany + "," + memUsed + "\n");

                        model.addRow(new Object[]{
                                alg.getName(),
                                type,
                                n,
                                time,
                                m.porownania,
                                m.zamiany,
                                memUsed
                        });
                    }

                    int k = dataTypes.length;

                    tList.add(sumTime / k);
                    mList.add(sumMem / k);
                    cList.add(sumComp / k);
                    sList.add(sumSwap / k);

                }

                timeData.put(alg.getName(), tList);
                memoryData.put(alg.getName(), mList);
                compData.put(alg.getName(), cList);
                swapData.put(alg.getName(), sList);
            }

            fw.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        JTable table = new JTable(model);
        JScrollPane scroll = new JScrollPane(table);

        JPanel controls = new JPanel();

        JButton timeBtn = new JButton("Czas");
        JButton memBtn = new JButton("Pamięć");
        JButton compBtn = new JButton("Porównania");
        JButton swapBtn = new JButton("Zamiany");

        controls.add(timeBtn);
        controls.add(memBtn);
        controls.add(compBtn);
        controls.add(swapBtn);

        timeBtn.addActionListener(e -> updateChart(timeData, "Czas działania", "ns"));

        memBtn.addActionListener(e -> updateChart(memoryData, "Pamięć", "bytes"));

        compBtn.addActionListener(e -> updateChart(compData, "Porównania", "liczba"));
        swapBtn.addActionListener(e -> updateChart(swapData, "Zamiany", "liczba"));

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(controls, BorderLayout.NORTH);
        bottomPanel.add(chartContainer, BorderLayout.CENTER);

        JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT, scroll, bottomPanel);

        frame.getContentPane().removeAll();
        frame.add(split);
        frame.revalidate();

        updateChart(timeData, "Czas działania", "ns");
    }

    static void updateChart(Map<String, List<Long>> data, String title, String yLabel) {
        chartContainer.removeAll();
        chartContainer.add(new Grafiki(data, sizes, title, yLabel), BorderLayout.CENTER);
        chartContainer.revalidate();
        chartContainer.repaint();
    }
}