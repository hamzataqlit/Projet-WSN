import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

public class LeachClusteringApp extends JFrame implements ActionListener {
  private static final int NODES_COUNT = 100;
  private static final int ROUNDS = 100;

  private JLabel[] nodeLabels;
  private JLabel[] stateLabels;
  private JButton resetButton;

  private ArrayList<Node> nodes;

  public LeachClusteringApp() {
    super("LEACH Clustering");
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setLayout(new GridLayout(NODES_COUNT + 1, 2));

    nodeLabels = new JLabel[NODES_COUNT];
    stateLabels = new JLabel[NODES_COUNT];
    resetButton = new JButton("Reset");
    resetButton.addActionListener(this);

    nodes = new ArrayList<Node>();

    for (int i = 0; i < NODES_COUNT; i++) {
      nodeLabels[i] = new JLabel("Node " + i);
      stateLabels[i] = new JLabel("");
      add(nodeLabels[i]);
      add(stateLabels[i]);
    }

    add(resetButton);
    setSize(400, 200);
    setVisible(true);
  }

  public static void main(String[] args) {
    LeachClusteringApp app = new LeachClusteringApp();
    app.startClustering();
  }

  private void startClustering() {
    for (int i = 0; i < NODES_COUNT; i++) {
      nodes.add(new Node(i));
    }

    for (int i = 0; i < ROUNDS; i++) {
      for (Node node : nodes) {
        node.run();
      }

      for (Node node : nodes) {
        stateLabels[node.getId()].setText(node.getState());
      }
    }
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == resetButton) {
      nodes.clear();
      for (int i = 0; i < NODES_COUNT; i++) {
        nodes.add(new Node(i));
        stateLabels[i].setText("");
      }
      startClustering();
    }
  }

  class Node {
    private int id;
    private boolean isClusterHead;
    private double energy;

    public Node(int id) {
      this.id = id;
      this.isClusterHead = false;
      this.energy = new Random().nextDouble() * 100;
    }

    public void run() {
      double p = new Random().nextDouble();
      double threshold = calculateThreshold();
      if (p < threshold) {
          becomeClusterHead();
        } else {
          joinCluster();
        }
      }

      private void becomeClusterHead() {
        this.isClusterHead = true;
      }

      private void joinCluster() {
        this.isClusterHead = false;
      }

      private double calculateThreshold() {
        // Energy consumption formula goes here
        return 0;
      }

      public int getId() {
        return id;
      }

      public String getState() {
        return isClusterHead ? "CH" : "";
      }
    }
  }

