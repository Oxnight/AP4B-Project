import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI {
    private UVManager uvManager;
    private Eleve eleve;
    private Problemes probleme;
    private JTextArea outputArea;
    private JTextField inputField;
    private JFrame frame;
    private JPanel panel;

    public GUI(UVManager uvManager, Eleve eleve) {
        this.uvManager = uvManager;
        this.eleve = eleve;
        this.probleme = new Problemes(uvManager, eleve);
        initUI();
    }

    private void initUI() {
        frame = new JFrame("Gestion des Problèmes");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        panel = new JPanel(new BorderLayout());
        frame.add(panel);

        outputArea = new JTextArea();
        outputArea.setEditable(false);
        panel.add(new JScrollPane(outputArea), BorderLayout.CENTER);

        inputField = new JTextField();
        panel.add(inputField, BorderLayout.SOUTH);

        inputField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleUserInput(inputField.getText());
                inputField.setText("");
            }
        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        askForIdentification();
    }

    private void askForIdentification() {
        panel.removeAll();
        panel.setLayout(new BorderLayout());

        JPanel centerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        JLabel label = new JLabel("Veuillez vous identifier :");
        centerPanel.add(label, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        JTextField nameField = new JTextField(20);
        centerPanel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton submitButton = new JButton("Soumettre");
        submitButton.setPreferredSize(new Dimension(200, 50));
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eleve.setName(nameField.getText());
                askForUVSelection();
            }
        });
        centerPanel.add(submitButton, gbc);

        panel.add(centerPanel, BorderLayout.CENTER);

        panel.revalidate();
        panel.repaint();
    }

    private void askForUVSelection() {
        panel.removeAll();
        panel.setLayout(new GridLayout(uvManager.getListeUV().size() + 2, 1));

        JLabel label = new JLabel("Choisissez une UV :");
        panel.add(label);

        // Boucle pour ajouter les boutons pour chaque UV
        for (UV uv : uvManager.getListeUV()) {
            JButton uvButton = new JButton(uv.getName());
            uvButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    eleve.setCurrentUV(uv);
                    System.out.println("UV sélectionnée : " + uv.getName());
                    displayProblemWithOptions();
                }
            });
            panel.add(uvButton);
        }

        // Rafraîchissement de l'interface
        panel.revalidate();
        panel.repaint();
    }

    private void displayProblemWithOptions() {
        panel.removeAll();
        panel.setLayout(new BorderLayout());

        // Récupération et affichage de l'énoncé du problème
        String enonce = probleme.getEnonce();
        if (enonce == null || enonce.isEmpty()) {
            enonce = "Aucun problème disponible pour cette UV.";
        }

        // Affichage de l'énoncé du problème
        JLabel problemLabel = new JLabel(enonce);
        problemLabel.setHorizontalAlignment(SwingConstants.CENTER);
        problemLabel.setVerticalAlignment(SwingConstants.TOP);
        panel.add(problemLabel, BorderLayout.NORTH);

        // Création des boutons pour les actions
        JPanel buttonPanel = new JPanel(new GridLayout(3, 1));

        JButton hintButton = new JButton("Demander un indice");
        hintButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String indice = JOptionPane.showInputDialog(frame, "Entrez votre hypothèse :");
                if (indice != null && !indice.trim().isEmpty()) {
                    probleme.interroger();
                    outputArea.append("Indice demandé.\n");
                }
            }
        });
        buttonPanel.add(hintButton);

        JButton guessButton = new JButton("Proposer une hypothèse");
        guessButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String hypothese = JOptionPane.showInputDialog(frame, "Entrez votre hypothèse :");
                if (hypothese != null && !hypothese.trim().isEmpty()) {
                    probleme.effectuerHypothese(hypothese);
                    outputArea.append("Hypothèse proposée : " + hypothese + "\n");
                }
            }
        });
        buttonPanel.add(guessButton);

        JButton quitButton = new JButton("Quitter");
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });
        buttonPanel.add(quitButton);

        panel.add(buttonPanel, BorderLayout.CENTER);

        // Rafraîchissement de l'interface
        panel.revalidate();
        panel.repaint();
    }

    private void handleUserInput(String input) {
        // Logique pour gérer les entrées utilisateur
    }
}