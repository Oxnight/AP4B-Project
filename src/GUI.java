import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.border.LineBorder;

/**
 * Classe représentant l'interface graphique (GUI) permettant à l'élève d'interagir avec les problèmes d'UV.
 * Elle gère la fenêtre principale, les boutons, et l'affichage des problèmes et des résultats.
 */
public class GUI {
    private final UVManager uvManager;  // Le gestionnaire des UV.
    private final Eleve eleve;  // L'élève qui interagit avec le programme.
    private final Problemes probleme;  // L'objet représentant le problème en cours.
    private JTextArea outputArea;  // Zone d'affichage des résultats et des messages.
    private JTextField inputField;  // Champ de texte pour l'entrée de l'utilisateur.
    private JFrame frame;  // Fenêtre principale de l'interface graphique.
    private JPanel panel;  // Panneau contenant l'interface utilisateur.
    private JLabel studentInfoLabel;  // Étiquette pour afficher les informations de l'élève.

    /**
     * Constructeur pour initialiser l'interface graphique et les objets nécessaires.
     *
     * @param uvManager Le gestionnaire des UV.
     * @param eleve     L'élève interagissant avec le programme.
     */
    public GUI(UVManager uvManager, Eleve eleve) {
        this.uvManager = uvManager;
        this.eleve = eleve;
        this.probleme = new Problemes(uvManager, eleve);  // Création d'un objet Problemes pour gérer les problèmes.
        initUI();  // Initialisation de l'interface utilisateur.
    }

    /**
     * Méthode pour initialiser l'interface graphique (fenêtre, panneaux, boutons).
     * C'est ici que l'on configure tous les composants graphiques de la fenêtre principale.
     */
    private void initUI() {
        // Initialisation de la fenêtre principale
        frame = new JFrame("AP4B - Turing Machine");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);  // Taille de la fenêtre
        frame.getContentPane().setBackground(new Color(224, 240, 255));  // Couleur de fond de la fenêtre

        // Panneau principal qui contient tout le contenu
        panel = new JPanel(new BorderLayout());
        panel.setOpaque(false); // Panneau transparent
        frame.add(panel, BorderLayout.CENTER);

        // Zone de texte pour afficher les résultats
        outputArea = new JTextArea();
        outputArea.setEditable(false);  // Empêche l'édition par l'utilisateur
        panel.add(new JScrollPane(outputArea), BorderLayout.CENTER);

        // Champ de texte pour l'entrée de l'utilisateur
        inputField = new JTextField();
        panel.add(inputField, BorderLayout.SOUTH);

        // Lorsque l'utilisateur appuie sur "Entrée", le champ de texte est réinitialisé
        inputField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inputField.setText("");  // Réinitialise le champ de texte après chaque entrée
            }
        });

        // Panneau en bas pour afficher les boutons "Règle" et "Quitter"
        JPanel reglePanel = new JPanel(new BorderLayout());
        reglePanel.setOpaque(false); // Panneau transparent

        // Panneau pour les boutons avec un FlowLayout aligné à droite
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setOpaque(false); // Panneau transparent
        JButton regleButton = new JButton("Règles");
        regleButton.setPreferredSize(new Dimension(75, 35));  // Taille du bouton "Règle"
        styleButton(regleButton);

        // Affiche les règles du jeu lorsqu'on clique sur le bouton "Règle"
        regleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String regleTexte = "Règles du jeu : \n" +
                        "1. Vous devez résoudre les problèmes de chaque UV pour valider l'UV.\n" +
                        "2. Vous pouvez demander des indices pour chaque problème.\n" +
                        "3. Vous pouvez proposer une hypothèse pour chaque problème.\n" +
                        "4. Vous avez 3 tentatives pour chaque problème.\n" +
                        "5. Si vous échouez, vous ne validez pas l'UV.\n" +
                        "6. Si vous passez tous les problèmes, vous pouvez passer à l'UV suivante.\n" +
                        "7. Si vous validez toutes les UV, vous avez fini le TC (et passez en branche) !";
                JOptionPane.showMessageDialog(frame, regleTexte, "Règles", JOptionPane.INFORMATION_MESSAGE);  // Affiche une boîte de dialogue avec les règles
            }
        });
        buttonPanel.add(regleButton);

        // Création du bouton "Quitter"
        JButton quitButton = new JButton("Quitter");
        quitButton.setPreferredSize(new Dimension(75, 35));  // Taille du bouton "Quitter"
        styleButton(quitButton);

        // Action pour quitter l'application lorsqu'on clique sur le bouton "Quitter"
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();  // Ferme la fenêtre
            }
        });
        buttonPanel.add(quitButton);

        // Ajout du panneau des boutons au panneau des règles
        reglePanel.add(buttonPanel, BorderLayout.EAST);

        // Affichage des informations de l'élève dans l'interface
        studentInfoLabel = new JLabel();
        updateStudentInfo();  // Met à jour les informations de l'élève à l'initialisation
        reglePanel.add(studentInfoLabel, BorderLayout.WEST);
        studentInfoLabel.setVisible(false);  // L'étiquette est initialement cachée

        // Panneau pour le logo
        JPanel logoPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));  // Panneau avec FlowLayout pour centrer le logo
        logoPanel.setOpaque(false); // Panneau transparent
        ImageIcon logoIcon = new ImageIcon("img/logoUTBM.png"); // Chargement du logo
        Image resizedLogo = logoIcon.getImage().getScaledInstance(120, 55, Image.SCALE_SMOOTH);  // Redimensionne le logo
        ImageIcon resizedLogoIcon = new ImageIcon(resizedLogo);  // Crée une nouvelle icône redimensionnée
        JLabel logoLabel = new JLabel(resizedLogoIcon); // Crée une étiquette pour afficher le logo

        logoPanel.add(logoLabel); // Ajout du logo au panneau
        frame.add(logoPanel, BorderLayout.NORTH); // Ajout du panneau du logo en haut de la fenêtre

        frame.add(reglePanel, BorderLayout.SOUTH);  // Ajoute le panneau des règles et infos en bas

        frame.setLocationRelativeTo(null);  // Centre la fenêtre
        frame.setVisible(true);  // Rendre la fenêtre visible

        askForIdentification();  // Demander à l'élève de s'identifier
    }

    /**
     * Met à jour l'étiquette d'informations de l'élève.
     */
    private void updateStudentInfo() {
        String info = String.format("  Nom: %s | Tentatives restantes: %d | UV validées: %d",
                eleve.getName(), probleme.getTentativesRestantes(), eleve.getNombreUVvalidees());
        studentInfoLabel.setText(info);  // Affiche les informations de l'élève
    }

    /**
     * Applique un style personnalisé aux boutons.
     *
     * @param button Le bouton à styliser.
     */
    private void styleButton(JButton button) {
        button.setBackground(new Color(100, 149, 237)); // Couleur de fond initiale (bleu)
        button.setForeground(Color.WHITE); // Couleur du texte (blanc)
        button.setBorder(new LineBorder(new Color(70, 130, 180), 2)); // Bordure bleue foncée avec une épaisseur de 2px
        button.setFocusPainted(false); // Supprime le contour de focus par défaut

        // Ajout d'un écouteur pour modifier les couleurs au survol de la souris
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(135, 206, 235)); // Couleur plus claire lorsque la souris survole le bouton
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(100, 149, 237)); // Restauration de la couleur originale lorsque la souris quitte le bouton
            }
        });
    }

    /**
     * Affiche une interface demandant à l'élève de s'identifier.
     */
    private void askForIdentification() {
        panel.removeAll();  // Supprime tous les composants précédemment ajoutés
        panel.setLayout(new BorderLayout());  // Nouveau layout pour la zone centrale

        // Panneau central pour l'identification
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setOpaque(false); // Panneau transparent
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);  // Espacement autour des composants
        gbc.anchor = GridBagConstraints.CENTER;  // Centrer les composants

        // Texte de bienvenue centré
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;  // Étend sur deux colonnes pour être centré
        gbc.anchor = GridBagConstraints.CENTER;
        JLabel welcomeLabel = new JLabel("Bienvenue dans l'examen qui déterminera l'avenir de vos études");
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("", Font.BOLD, 16));
        centerPanel.add(welcomeLabel, gbc);

// Panel pour le label et le champ de texte (pour centrer ensemble)
        JPanel inputPanel = new JPanel();
        inputPanel.setOpaque(false); // Panneau transparent
        inputPanel.setLayout(new GridBagLayout());  // Utilisation de GridBagLayout pour aligner les éléments à l'intérieur
        GridBagConstraints inputGbc = new GridBagConstraints();

        // Label pour demander l'identification
        inputGbc.gridx = 0;
        inputGbc.gridy = 0;
        inputGbc.anchor = GridBagConstraints.WEST;  // Aligne le label à gauche dans le panel
        JLabel label = new JLabel("Veuillez vous identifier :");
        inputPanel.add(label, inputGbc);

        // Champ de texte pour entrer le nom avec un espace en bas du label
        inputGbc.gridx = 1;  // À droite du label
        inputGbc.gridy = 0;  // Même ligne que le label
        inputGbc.anchor = GridBagConstraints.CENTER;  // Centré par rapport à la cellule
        inputGbc.insets = new Insets(0, 30, 0, 0);  // Ajoute un petit espace en bas du label (0 haut, 10 gauche, 5 bas, 10 droite)
        JTextField nameField = new JTextField(20);
        nameField.setHorizontalAlignment(SwingConstants.CENTER);
        inputPanel.add(nameField, inputGbc);

        // Ajoutez le panel interne dans le conteneur principal, centré
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;  // Prend toute la largeur pour centrer
        gbc.anchor = GridBagConstraints.CENTER;
        centerPanel.add(inputPanel, gbc);

        // Bouton pour soumettre l'identification
        gbc.gridx = 0;
        gbc.gridy = 3;  // Déplace le bouton sous le champ de texte
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton submitButton = new JButton("Soumettre");
        submitButton.setPreferredSize(new Dimension(200, 50));
        styleButton(submitButton);
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText().trim();  // Récupère le nom entré
                if (name.isEmpty()) {  // Si le nom est vide, affiche un message d'erreur
                    JOptionPane.showMessageDialog(frame, "Le nom ne peut pas être vide. Veuillez entrer un nom.", "Erreur", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    eleve.setName(name);  // Définit le nom de l'élève
                    updateStudentInfo();  // Met à jour les informations de l'élève
                    askForUVSelection();  // Demande à l'élève de sélectionner une UV
                }
            }
        });
        centerPanel.add(submitButton, gbc);  // Ajoute le bouton au panneau

        // Permet de soumettre l'identification en appuyant sur "Entrée" après avoir entré le nom
        nameField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                submitButton.doClick();  // Simule un clic sur le bouton de soumission
            }
        });

        // Ajout du panneau central au panneau principal
        panel.add(centerPanel, BorderLayout.CENTER);

        panel.revalidate();  // Réajuste les composants après modification
        panel.repaint();  // Redessine les composants
    }

    /**
     * Affiche les UV disponibles pour que l'élève puisse en sélectionner une.
     */
    private void askForUVSelection() {
        panel.removeAll();  // Supprime tous les composants précédents
        studentInfoLabel.setVisible(true);  // Rendre l'étiquette des informations de l'élève visible

        panel.setLayout(new BorderLayout());  // Utilise un BorderLayout pour le panneau principal

        // Panneau pour le label centré
        JPanel labelPanel = new JPanel(new BorderLayout());
        labelPanel.setOpaque(false); // Panneau transparent
        JLabel label = new JLabel("Choisissez une UV :");
        label.setHorizontalAlignment(SwingConstants.CENTER);  // Centre le texte du label
        labelPanel.add(label, BorderLayout.CENTER);
        panel.add(labelPanel, BorderLayout.NORTH);  // Ajoute le panneau du label en haut

        // Panneau pour les boutons avec un GridBagLayout
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setOpaque(false); // Panneau transparent
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 0, 20);  // Espacement autour des composants
        gbc.gridx = GridBagConstraints.RELATIVE;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;

        // Pour chaque UV, un bouton est créé
        for (UV uv : uvManager.getListeUV()) {
            JButton uvButton = new JButton(uv.getName());
            uvButton.setPreferredSize(new Dimension(200, 100));  // Définit une taille préférée plus grande pour les boutons
            styleButton(uvButton);
            uvButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    eleve.setCurrentUV(uv);  // Définit l'UV actuelle de l'élève
                    eleve.resetScore();
                    displayProblemWithOptions();  // Affiche le problème associé à cette UV
                }
            });
            buttonPanel.add(uvButton, gbc);  // Ajoute le bouton de l'UV au panneau des boutons
        }

        panel.add(buttonPanel, BorderLayout.CENTER);  // Ajoute le panneau des boutons au centre

        panel.revalidate();
        panel.repaint();
    }

    /**
     * Affiche un problème avec des options pour l'élève (demander un indice ou proposer une hypothèse).
     */
    private void displayProblemWithOptions() {
        panel.removeAll();  // Supprime tous les composants précédents
        panel.setLayout(new BorderLayout());  // Nouveau layout avec une disposition en BorderLayout

        // Affiche l'énoncé du problème
        String enonce = probleme.getEnonce();
        if (enonce == null || enonce.isEmpty()) {
            enonce = "Aucun problème disponible pour cette UV.";
        }

        JLabel problemLabel = new JLabel(enonce);
        problemLabel.setHorizontalAlignment(SwingConstants.CENTER);
        problemLabel.setVerticalAlignment(SwingConstants.TOP);
        problemLabel.setPreferredSize(new Dimension(100, 75));
        problemLabel.setFont(new Font("", Font.PLAIN, 15));  // Taille et style de la police
        problemLabel.setBorder(BorderFactory.createEmptyBorder(35, 20, 0, 20));  // Marges autour de l'énoncé
        panel.add(problemLabel, BorderLayout.NORTH);

        // Panneau pour les boutons avec un GridBagLayout
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setOpaque(false); // Panneau transparent
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 20, 0, 20);  // Espacement autour des composants
        gbc.gridx = GridBagConstraints.RELATIVE;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;

        // Bouton pour demander un indice
        JButton hintButton = new JButton("Demander un indice");
        hintButton.setPreferredSize(new Dimension(200, 100));  // Taille du bouton "Indice"
        styleButton(hintButton);
        hintButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String indice = JOptionPane.showInputDialog(frame, "Entrez votre hypothèse :", "Indice", JOptionPane.QUESTION_MESSAGE);

                // Si l'UV est "PC20", on s'assure que l'hypothèse a un format valide
                if (eleve.getCurrentUV().getName().equals("PC20")) {
                    while (indice != null && !indice.matches("\\d{4}")) {
                        JOptionPane.showMessageDialog(frame, "L'hypothèse doit être composée de 4 chiffres. Veuillez réessayer.", "Erreur", JOptionPane.ERROR_MESSAGE);
                        indice = JOptionPane.showInputDialog(frame, "Entrez votre hypothèse :", "Indice", JOptionPane.QUESTION_MESSAGE);
                    }
                }

                // Affichage de l'indice si l'entrée n'est pas vide
                if (indice != null && !indice.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Votre hypothèse : " + indice + "\n\nIndice : \n" + probleme.interroger(indice, true), "Indice", JOptionPane.INFORMATION_MESSAGE);
                    outputArea.append("Indice demandé.\n");
                }
            }
        });
        buttonPanel.add(hintButton, gbc);  // Ajoute le bouton "Demander un indice"

        // Bouton pour proposer une hypothèse
        JButton guessButton = new JButton("Proposer une hypothèse");
        guessButton.setPreferredSize(new Dimension(200, 100));  // Taille du bouton "Hypothèse"
        styleButton(guessButton);
        guessButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String hypothese = JOptionPane.showInputDialog(frame, "Entrez votre hypothèse :", "Proposition", JOptionPane.QUESTION_MESSAGE);

                // Vérification pour l'UV "PC20" pour s'assurer que l'hypothèse est valide
                if (eleve.getCurrentUV().getName().equals("PC20")) {
                    while (hypothese != null && !hypothese.matches("\\d{4}")) {
                        JOptionPane.showMessageDialog(frame, "L'hypothèse doit être composée de 4 chiffres. Veuillez réessayer.", "Erreur", JOptionPane.ERROR_MESSAGE);
                        hypothese = JOptionPane.showInputDialog(frame, "Entrez votre hypothèse :", "Proposition", JOptionPane.QUESTION_MESSAGE);
                    }
                }

                // Si l'hypothèse n'est pas vide on l'évalue
                if (hypothese != null && !hypothese.trim().isEmpty()) {
                    Problemes.ValidationStatus status = probleme.effectuerHypothese(hypothese, true);
                    // Si la proposition est correcte, l'élève passe au problème suivant ou termine
                    if (status == Problemes.ValidationStatus.SUCCESS) {
                        JOptionPane.showMessageDialog(frame, "Bravo, vous avez résolu le problème !", "Succès", JOptionPane.INFORMATION_MESSAGE);
                        if (uvManager.getListeUV().isEmpty()) {
                            JOptionPane.showMessageDialog(frame, "Bravo, vous avez fini le TC !", "Félicitations", JOptionPane.INFORMATION_MESSAGE);
                            // Si l'élève a validé au moins 2 UV, il peut passer en FISE INFORMATIQUE
                            if (eleve.getNombreUVvalidees() >= 1) {
                                JOptionPane.showMessageDialog(frame, "Vous avez l'autorisation de partir en FISE INFORMATIQUE!", "Félicitations", JOptionPane.INFORMATION_MESSAGE);
                            } else {
                                JOptionPane.showMessageDialog(frame, "Convoqué devant le 2ème jury de suivi. Très mauvais semestre. Risque de réorientation.", "Dommage...", JOptionPane.ERROR_MESSAGE);             }
                            frame.dispose();
                            System.exit(0);
                        } else {
                            // Si l'élève a résolu tous les problèmes de l'UV actuelle
                            if (eleve.getCurrentUV().getListeProblemes().isEmpty()) {
                                JOptionPane.showMessageDialog(frame, "Tous les problèmes de cette UV ont été résolus.", "...", JOptionPane.INFORMATION_MESSAGE);
                                if (eleve.getScore() >= 2) {
                                    JOptionPane.showMessageDialog(frame, "Félicitations, vous avez validé l'UV !", "Félicitations", JOptionPane.INFORMATION_MESSAGE);
                                } else {
                                    JOptionPane.showMessageDialog(frame, "Vous n'avez pas validé l'UV.", "Echec", JOptionPane.ERROR_MESSAGE);
                                }
                                askForUVSelection();
                            } else {
                                JOptionPane.showMessageDialog(frame, "Voici un nouveau problème (il en reste encore " + eleve.getCurrentUV().getListeProblemes().size() + ").", "Nouveau problème", JOptionPane.INFORMATION_MESSAGE);
                                displayProblemWithOptions();
                            }
                        }
                    } else {
                        // Si la proposition échoue, gérer les erreurs et les tentatives restantes
                        if (status == Problemes.ValidationStatus.NO_MORE_TRIES) {
                            JOptionPane.showMessageDialog(frame, "Echec ! Vous avez épuisé toutes vos tentatives.", "Echec", JOptionPane.ERROR_MESSAGE);
                            if (uvManager.getListeUV().isEmpty()) {
                                JOptionPane.showMessageDialog(frame, "Bravo, vous avez fini le TC !", "Félicitations", JOptionPane.INFORMATION_MESSAGE);
                                if (eleve.getNombreUVvalidees() >= 1) {
                                    JOptionPane.showMessageDialog(frame, "Vous avez l'autorisation de partir en FISE INFORMATIQUE!", "Félicitations", JOptionPane.INFORMATION_MESSAGE);
                                } else {
                                    JOptionPane.showMessageDialog(frame, "Convoqué devant le 2ème jury de suivi. Très mauvais semestre. Risque de réorientation.", "Dommage...", JOptionPane.ERROR_MESSAGE);             }
                                frame.dispose();
                                System.exit(0);
                            } else {
                                // Si l'élève a résolu tous les problèmes de l'UV actuelle
                                if (eleve.getCurrentUV().getListeProblemes().isEmpty()) {
                                    JOptionPane.showMessageDialog(frame, "Tous les problèmes de cette UV ont été résolus.", "...", JOptionPane.INFORMATION_MESSAGE);
                                    if (eleve.getScore() >= 2) {
                                        JOptionPane.showMessageDialog(frame, "Félicitations, vous avez validé l'UV !", "Félicitations", JOptionPane.INFORMATION_MESSAGE);
                                    } else {
                                        JOptionPane.showMessageDialog(frame, "Vous n'avez pas validé l'UV.", "Echec", JOptionPane.ERROR_MESSAGE);
                                    }
                                    askForUVSelection();
                                } else {
                                    JOptionPane.showMessageDialog(frame, "Voici un nouveau problème (il en reste encore " + eleve.getCurrentUV().getListeProblemes().size() + ").", "Nouveau problème", JOptionPane.INFORMATION_MESSAGE);
                                    displayProblemWithOptions();
                                }
                            }
                        } else {
                            JOptionPane.showMessageDialog(frame, "Echec ! Il vous reste " + probleme.getTentativesRestantes() + " tentatives.", "Echec", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    outputArea.append("Hypothèse proposée : " + hypothese + "\n");
                    updateStudentInfo();
                }
            }
        });
        buttonPanel.add(guessButton, gbc);  // Ajoute le bouton "Proposer une hypothèse"

        // Ajoute les boutons au panneau principal
        panel.add(buttonPanel, BorderLayout.CENTER);

        panel.revalidate();
        panel.repaint();
    }
}