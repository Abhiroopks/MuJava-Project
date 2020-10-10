// 
// Decompiled by Procyon v0.5.36
// 

package openjava.test;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JOptionPane;
import java.text.ParseException;
import javax.swing.text.MaskFormatter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import javax.swing.SwingUtilities;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;
import javax.swing.border.Border;
import javax.swing.BorderFactory;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Insets;
import javax.swing.KeyStroke;
import javax.swing.Icon;
import javax.swing.Box;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URI;
import java.awt.GridBagConstraints;
import java.awt.LayoutManager;
import java.awt.GridBagLayout;
import java.awt.Component;
import javax.swing.ImageIcon;
import java.net.URL;
import javax.swing.JProgressBar;
import javax.swing.JButton;
import java.awt.Dimension;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JFormattedTextField;
import javax.swing.JComboBox;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.event.KeyListener;
import java.awt.event.ActionListener;

public class Crosslexic implements ActionListener, KeyListener
{
    private static final long serialVersionUID = 1L;
    public static final String version = "@VERSION@";
    public static final Integer MAXSIZE;
    public static JFrame clFrame;
    public static JPanel clPanel;
    public static JPanel letterPanel;
    public static JPanel wlengthPanel;
    public static JPanel buttonPanel;
    protected static JMenuBar menuBar;
    protected static JMenu menu;
    protected static JMenuItem helpMenuItem;
    protected static JMenuItem aboutMenuItem;
    protected static JMenuItem licMenuItem;
    private static boolean menu_activated;
    private static JComboBox wordlengthComboBox;
    private static int wordlength;
    private static JFormattedTextField[] letterText;
    private static int key_code;
    protected static JLabel wlengthLabel;
    protected static JLabel matchesLabel;
    private static DefaultListModel listModel;
    protected static JList dictlist;
    protected static JScrollPane listScrollPane;
    protected static Dimension listDimension;
    private static myListCellRenderer iconListCellRenderer;
    private static JButton searchButton;
    private static JButton clearButton;
    private static JProgressBar progressBar;
    private static Thread searchThread;
    protected static Thread repaintlistThread;
    protected static URL dictionaryResource;
    protected static URL imgMatch;
    protected static URL imgHelp;
    protected static URL imgAlert;
    protected static URL imgAbout;
    protected static URL imgGuru;
    protected static final String imgMatchPath = "lib/resource/match.png";
    protected static final String imgHelpPath = "lib/resource/help.png";
    protected static final String imgAlertPath = "lib/resource/alert.png";
    protected static final String imgAboutPath = "lib/resource/about.png";
    protected static final String imgGuruPath = "lib/resource/arthurguru.png";
    protected static ImageIcon iconMatch;
    protected static ImageIcon iconHelp;
    protected static ImageIcon iconAlert;
    protected static ImageIcon iconAbout;
    protected static ImageIcon iconGuru;
    
    public Crosslexic() {
        JFrame.setDefaultLookAndFeelDecorated(true);
        (Crosslexic.clFrame = new JFrame("Solve crossword puzzles.")).setDefaultCloseOperation(3);
        this.addWidgets();
        Crosslexic.clFrame.setJMenuBar(Crosslexic.menuBar);
        Crosslexic.clFrame.getContentPane().add(Crosslexic.clPanel, "Center");
        Crosslexic.clFrame.pack();
        Crosslexic.clFrame.setLocationRelativeTo(null);
        Crosslexic.clFrame.setVisible(true);
    }
    
    public String getAppletInfo() {
        return "Crosslexic @VERSION@ by Arthur Gouros.";
    }
    
    protected static void addtolist(final ImageIcon imageIcon, final String s) {
        if (Crosslexic.repaintlistThread == null) {
            (Crosslexic.repaintlistThread = new Thread(new repaintlist())).start();
        }
    }
    
    protected static void addtolist_now(final ImageIcon imageIcon, final String s) {
        try {
            try {
                Crosslexic.listModel.wait(200L);
            }
            catch (Exception ex) {}
            final int n = (Crosslexic.listModel.getSize() > 0) ? (Crosslexic.listModel.getSize() - 1) : 0;
            Crosslexic.dictlist.setSelectedIndex(n);
            Crosslexic.dictlist.ensureIndexIsVisible(n);
        }
        catch (Exception ex2) {
            Crosslexic.listModel.addElement(createLabel(Crosslexic.iconAlert, "Oh bugger! Jlist scroll bug!"));
            Crosslexic.listModel.addElement(createLabel(null, "> You may have to click Clear and try again."));
            Crosslexic.searchThread = null;
            app_setEnabled(true);
        }
        Crosslexic.dictlist.repaint();
    }
    
    private void addWidgets() {
        Crosslexic.clPanel = new JPanel(new GridBagLayout());
        final GridBagConstraints constraints = new GridBagConstraints();
        URI uri = null;
        try {
            uri = new URI("file:///" + System.getProperty("user.dir") + "lib/resource/rthurguru.png");
            Crosslexic.imgGuru = uri.toURL();
        }
        catch (URISyntaxException ex) {
            ex.printStackTrace();
        }
        catch (MalformedURLException ex2) {
            ex2.printStackTrace();
        }
        Crosslexic.iconGuru = new ImageIcon(Crosslexic.imgGuru);
        try {
            uri = new URI("file:///" + System.getProperty("user.dir") + "/lib/resource/alert.png");
        }
        catch (URISyntaxException ex3) {
            ex3.printStackTrace();
        }
        try {
            Crosslexic.imgAlert = uri.toURL();
        }
        catch (MalformedURLException ex4) {
            ex4.printStackTrace();
        }
        Crosslexic.iconAlert = new ImageIcon(Crosslexic.imgAlert);
        try {
            uri = new URI("file:///" + System.getProperty("user.dir") + "/lib/resource/help.png");
        }
        catch (URISyntaxException ex5) {
            ex5.printStackTrace();
        }
        try {
            Crosslexic.imgHelp = uri.toURL();
        }
        catch (MalformedURLException ex6) {
            ex6.printStackTrace();
        }
        Crosslexic.iconHelp = new ImageIcon(Crosslexic.imgHelp);
        (Crosslexic.menuBar = new JMenuBar()).add(Box.createHorizontalGlue());
        (Crosslexic.menu = new JMenu("Help")).setMnemonic(65);
        Crosslexic.menuBar.add(Crosslexic.menu);
        (Crosslexic.helpMenuItem = new JMenuItem("Help", Crosslexic.iconHelp)).setMnemonic(72);
        Crosslexic.helpMenuItem.setAccelerator(KeyStroke.getKeyStroke(72, 8));
        Crosslexic.helpMenuItem.getAccessibleContext().setAccessibleDescription("Help");
        Crosslexic.helpMenuItem.addActionListener(this);
        Crosslexic.menu.add(Crosslexic.helpMenuItem);
        Crosslexic.menu.addSeparator();
        try {
            uri = new URI("file:///" + System.getProperty("user.dir") + "/lib/resource/about.png");
        }
        catch (URISyntaxException ex7) {
            ex7.printStackTrace();
        }
        try {
            Crosslexic.imgAbout = uri.toURL();
        }
        catch (MalformedURLException ex8) {
            ex8.printStackTrace();
        }
        Crosslexic.iconAbout = new ImageIcon(Crosslexic.imgAbout);
        (Crosslexic.aboutMenuItem = new JMenuItem("About", Crosslexic.iconAbout)).setMnemonic(65);
        Crosslexic.aboutMenuItem.setAccelerator(KeyStroke.getKeyStroke(65, 8));
        Crosslexic.aboutMenuItem.getAccessibleContext().setAccessibleDescription("About");
        Crosslexic.aboutMenuItem.addActionListener(this);
        Crosslexic.menu.add(Crosslexic.aboutMenuItem);
        Crosslexic.menu.addSeparator();
        (Crosslexic.licMenuItem = new JMenuItem("License", Crosslexic.iconAlert)).setMnemonic(76);
        Crosslexic.licMenuItem.setAccelerator(KeyStroke.getKeyStroke(76, 8));
        Crosslexic.licMenuItem.getAccessibleContext().setAccessibleDescription("License");
        Crosslexic.licMenuItem.addActionListener(this);
        Crosslexic.menu.add(Crosslexic.licMenuItem);
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 0.0;
        constraints.weighty = 0.0;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.anchor = 19;
        constraints.insets = new Insets(2, 2, 10, 2);
        constraints.fill = 1;
        Crosslexic.clPanel.add(new JLabel("Crosslexic @VERSION@ by Arthur Gouros", 0), constraints);
        (Crosslexic.wlengthPanel = new JPanel(new GridLayout(1, 0))).add(new JLabel("Word Length: "));
        setWordlength(Crosslexic.MAXSIZE);
        setWordlengthComboBox(new JComboBox());
        for (int i = 1; i <= Crosslexic.MAXSIZE; ++i) {
            getWordlengthComboBox().addItem(new Integer(i).toString());
        }
        Crosslexic.wlengthPanel.add(getWordlengthComboBox());
        getWordlengthComboBox().setPrototypeDisplayValue(Crosslexic.MAXSIZE);
        getWordlengthComboBox().setSelectedItem(Crosslexic.MAXSIZE.toString());
        getWordlengthComboBox().addActionListener(this);
        getWordlengthComboBox().setToolTipText("Click this to select your word length.");
        Crosslexic.wlengthPanel.add(Box.createHorizontalGlue());
        Crosslexic.wlengthPanel.add(Box.createHorizontalGlue());
        constraints.insets = new Insets(2, 2, 2, 2);
        constraints.gridy = 1;
        constraints.anchor = 10;
        Crosslexic.clPanel.add(Crosslexic.wlengthPanel, constraints);
        (Crosslexic.letterPanel = new JPanel(new GridLayout(1, Crosslexic.MAXSIZE, 2, 0))).setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.black), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        setLetterText(new JFormattedTextField[(int)Crosslexic.MAXSIZE]);
        constraints.gridy = 2;
        Crosslexic.clPanel.add(Crosslexic.letterPanel, constraints);
        Crosslexic.matchesLabel = new JLabel("Matches", 2);
        constraints.gridy = 3;
        Crosslexic.clPanel.add(Crosslexic.matchesLabel, constraints);
        (Crosslexic.listModel = new DefaultListModel()).ensureCapacity(200);
        (Crosslexic.dictlist = new JList(Crosslexic.listModel)).setFixedCellWidth(100);
        Crosslexic.dictlist.setSelectionMode(0);
        Crosslexic.dictlist.setSelectedIndex(0);
        Crosslexic.dictlist.setLayoutOrientation(0);
        Crosslexic.dictlist.setVisibleRowCount(10);
        Crosslexic.iconListCellRenderer = new myListCellRenderer();
        Crosslexic.dictlist.setCellRenderer(Crosslexic.iconListCellRenderer);
        Crosslexic.iconListCellRenderer.setFont(Crosslexic.dictlist.getFont());
        Crosslexic.listScrollPane = new JScrollPane(Crosslexic.dictlist);
        constraints.gridheight = 10;
        constraints.gridy = 4;
        Crosslexic.clPanel.add(Crosslexic.listScrollPane, constraints);
        Crosslexic.buttonPanel = new JPanel(new GridLayout(1, 0));
        setClearButton(new JButton("Clear"));
        Crosslexic.buttonPanel.add(getClearButton());
        getClearButton().addActionListener(this);
        getClearButton().setToolTipText("Click this button to clear the word.");
        Crosslexic.buttonPanel.add(Box.createHorizontalGlue());
        try {
            uri = new URI("file:///" + System.getProperty("user.dir") + "/lib/resource/match.png");
        }
        catch (URISyntaxException ex9) {
            ex9.printStackTrace();
        }
        try {
            Crosslexic.imgMatch = uri.toURL();
        }
        catch (MalformedURLException ex10) {
            ex10.printStackTrace();
        }
        Crosslexic.iconMatch = new ImageIcon(Crosslexic.imgMatch);
        setSearchButton(new JButton("Search", Crosslexic.iconMatch));
        Crosslexic.buttonPanel.add(getSearchButton());
        getSearchButton().addActionListener(this);
        getSearchButton().setToolTipText("Click this button to search for matching words.");
        Crosslexic.buttonPanel.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 20));
        constraints.gridheight = 1;
        constraints.gridy = 14;
        Crosslexic.clPanel.add(Crosslexic.buttonPanel, constraints);
        Crosslexic.progressBar = new JProgressBar(0, 100);
        constraints.gridy = 15;
        Crosslexic.clPanel.add(Crosslexic.progressBar, constraints);
        Crosslexic.progressBar.setValue(0);
        Crosslexic.progressBar.setStringPainted(true);
        Crosslexic.matchesLabel.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
        Crosslexic.clPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.black), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        addtolist_now(null, "Line 1");
        addtolist_now(null, "Line 2");
        addtolist_now(null, "Line 3");
        addtolist_now(null, "Line 4");
        addtolist_now(null, "Line 5");
        addtolist_now(null, "Line 4");
        addtolist_now(null, "Line 7");
        addtolist_now(null, "Line 8");
        addtolist_now(null, "Line 9");
        addtolist_now(null, "Line 10");
        Crosslexic.listDimension = Crosslexic.dictlist.getSize();
        Crosslexic.listScrollPane.setSize(Crosslexic.listDimension);
        Crosslexic.dictlist.setMinimumSize(Crosslexic.listDimension);
        Crosslexic.listScrollPane.setMinimumSize(Crosslexic.listDimension);
        Crosslexic.listScrollPane.setMaximumSize(Crosslexic.listDimension);
        Crosslexic.listModel.clear();
        addtolist_now(null, "Enter letters and click Search.");
        try {
            Crosslexic.dictionaryResource = new URI("file://" + System.getProperty("user.dir") + "/lib/resource/dictionarywords").toURL();
        }
        catch (URISyntaxException ex11) {
            ex11.printStackTrace();
        }
        catch (MalformedURLException ex12) {
            ex12.printStackTrace();
        }
    }
    
    public void destroy() {
        try {
            SwingUtilities.invokeAndWait(new Runnable() {
                @Override
                public void run() {
                    Crosslexic.clFrame.getContentPane().removeAll();
                }
            });
        }
        catch (Exception ex) {}
    }
    
    public void dispose() {
        Crosslexic.clFrame.dispose();
    }
    
    @Override
    public void actionPerformed(final ActionEvent actionEvent) {
        final Object source = actionEvent.getSource();
        if (source == getSearchButton()) {
            if (getSearchButton().getText() == "Search") {
                if (Crosslexic.menu_activated) {
                    Crosslexic.listModel.clear();
                    Crosslexic.menu_activated = false;
                }
                (Crosslexic.searchThread = new Thread(new Search())).start();
                getSearchButton().grabFocus();
            }
            else {
                if (Crosslexic.searchThread.isAlive()) {
                    Crosslexic.searchThread = null;
                }
                try {
                    Crosslexic.searchThread.join();
                    Thread.sleep(100L);
                }
                catch (Exception ex) {}
                addtolist(Crosslexic.iconAlert, "Search aborted at " + Crosslexic.progressBar.getValue() + "%.");
            }
            return;
        }
        if (source == getClearButton()) {
            Crosslexic.listModel.clear();
            Crosslexic.progressBar.setValue(0);
            addtolist_now(null, "Enter letters and click Search.");
            return;
        }
        if (source == getWordlengthComboBox()) {
            if (Crosslexic.menu_activated) {
                Crosslexic.listModel.clear();
                Crosslexic.menu_activated = false;
            }
            setWordlength(Integer.parseInt(getWordlengthComboBox().getSelectedItem().toString()));
            addtolist_now(null, "Word length set to " + getWordlength() + " characters.");
            return;
        }
        if (source == Crosslexic.helpMenuItem) {
            app_help();
            Crosslexic.menu_activated = true;
            return;
        }
        if (source == Crosslexic.aboutMenuItem) {
            app_about();
            Crosslexic.menu_activated = true;
            return;
        }
        if (source == Crosslexic.licMenuItem) {
            app_license();
            Crosslexic.menu_activated = true;
        }
    }
    
    @Override
    public void keyTyped(final KeyEvent keyEvent) {
        keyEvent.getSource();
    }
    
    public int getLettersLength() {
        return 0;
    }
    
    @Override
    public void keyPressed(final KeyEvent keyEvent) {
        Crosslexic.key_code = keyEvent.getKeyCode();
    }
    
    @Override
    public void keyReleased(final KeyEvent keyEvent) {
        Crosslexic.key_code = keyEvent.getKeyCode();
    }
    
    protected MaskFormatter createFormatter(final String mask) {
        MaskFormatter maskFormatter = null;
        try {
            maskFormatter = new MaskFormatter(mask);
        }
        catch (ParseException ex) {
            addtolist_now(Crosslexic.iconAlert, "Java formatter for character input is bad.");
        }
        return maskFormatter;
    }
    
    protected static void app_setEnabled(final boolean enabled) {
        Crosslexic.menu.setEnabled(enabled);
        getWordlengthComboBox().setEnabled(enabled);
        getClearButton().setEnabled(enabled);
        if (enabled) {
            getSearchButton().setText("Search");
        }
        else {
            getSearchButton().setText("Stop");
        }
    }
    
    protected static void app_help() {
        Crosslexic.listModel.clear();
        Crosslexic.progressBar.setValue(0);
        addtolist_now(Crosslexic.iconHelp, "Crosslexic @VERSION@ Help.");
        addtolist_now(null, " ");
        addtolist_now(null, "Crosslexic scans through a dictionary looking for");
        addtolist_now(null, "words that match the entered characters. This is");
        addtolist_now(null, "ideal for solving crosswords puzzles. It's also");
        addtolist_now(null, "called cheating!");
        addtolist_now(null, " ");
        addtolist_now(null, "You could use a Thesaurus or even the Unix grep");
        addtolist_now(null, "command, however a rich personal vocabulary will");
        addtolist_now(null, "always serve you better in life.");
        addtolist_now(null, " ");
        addtolist_now(null, "Usage:");
        addtolist_now(null, "Select the length of the word you want to solve.");
        addtolist_now(null, "Type in the known characters in the text boxes and");
        addtolist_now(null, "click on the 'Search' button. Results will be placed");
        addtolist_now(null, "in the 'Matches' field. Only alphabetic characters");
        addtolist_now(null, "are allowed.");
        addtolist_now(null, " ");
        addtolist_now(null, "You can clear individual characters by pressing the");
        addtolist_now(null, "BACKSPACE, DELETE or SPACEBAR keys. You can");
        addtolist_now(null, "overwrite individual characters by placing the cursor");
        addtolist_now(null, "in the text box and pressing any alphabetic key.");
        addtolist_now(null, " ");
        addtolist_now(null, "You can revise your characters and search again or");
        addtolist_now(null, "you can click the 'Clear' button to start afresh.");
        addtolist_now(null, " ");
        addtolist_now(null, "Progress of the search through the dictionary");
        addtolist_now(null, "is displayed below and if the search is taking too");
        addtolist_now(null, "long you can click the 'Stop' button.");
        addtolist_now(null, " ");
        addtolist_now(null, "Refer to the license for terms and conditions.");
        addtolist_now(null, " ");
        addtolist_now(Crosslexic.iconGuru, "Enjoy, Arthur Gouros, 2010.");
        Crosslexic.dictlist.setSelectedIndex(0);
        Crosslexic.dictlist.ensureIndexIsVisible(0);
    }
    
    protected static void app_about() {
        Crosslexic.listModel.clear();
        Crosslexic.progressBar.setValue(0);
        addtolist_now(Crosslexic.iconAbout, "Crosslexic @VERSION@ by Arthur Gouros.");
        addtolist_now(null, "This Java program evolved from the Phonetics");
        addtolist_now(null, "code base which also does dictionary searches");
        addtolist_now(null, "for matching words in a similar way. It's only");
        addtolist_now(null, "purpose is to find that elusive word.");
        addtolist_now(null, " ");
        addtolist_now(null, "Yes, Arthur Gouros likes doing crosswords. ");
        addtolist_now(null, " ");
        addtolist_now(null, "Refer to the license for terms and conditions.");
        addtolist_now(null, " ");
        addtolist_now(Crosslexic.iconGuru, "Enjoy, Arthur Gouros.");
        Crosslexic.dictlist.setSelectedIndex(0);
        Crosslexic.dictlist.ensureIndexIsVisible(0);
    }
    
    protected static void app_license() {
        Crosslexic.listModel.clear();
        Crosslexic.progressBar.setValue(0);
        JOptionPane.showMessageDialog(Crosslexic.clFrame, "Crosslexic, a tool for solving crossword puzzles.\nCopyright (c) 2010-2011, Arthur Gouros\nAll rights reserved.\n\nRedistribution and use in source and binary forms, with or without\nmodification, are permitted provided that the following conditions are met:\n\n- Redistributions of source code must retain the above copyright notice,\n  this list of conditions and the following disclaimer.\n- Redistributions in binary form must reproduce the above copyright notice,\n  this list of conditions and the following disclaimer in the documentation\n  and/or other materials provided with the distribution.\n- Neither the name of Arthur Gouros nor the names of its contributors\n  may be used to endorse or promote products derived from this software\n  without specific prior written permission.\n\nTHIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS \"AS IS\"\nAND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE\nIMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE\nARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE\nLIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR\nCONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF\nSUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS\nINTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN\nCONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)\nARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE\nPOSSIBILITY OF SUCH DAMAGE.", "Crosslexic license", -1);
    }
    
    protected static JLabel createLabel(final ImageIcon icon, final String text) {
        final JLabel label = new JLabel();
        label.setIcon(icon);
        label.setText(text);
        return label;
    }
    
    public static void main(final String[] array) {
        try {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    final Crosslexic crosslexic = new Crosslexic();
                }
            });
        }
        catch (Exception ex) {
            System.err.println("GUI didn't successfully complete");
        }
    }
    
    public static JComboBox getWordlengthComboBox() {
        return Crosslexic.wordlengthComboBox;
    }
    
    public static void setWordlengthComboBox(final JComboBox wordlengthComboBox) {
        Crosslexic.wordlengthComboBox = wordlengthComboBox;
    }
    
    public static int getWordlength() {
        return Crosslexic.wordlength;
    }
    
    public static void setWordlength(final int wordlength) {
        Crosslexic.wordlength = wordlength;
    }
    
    public static JButton getSearchButton() {
        return Crosslexic.searchButton;
    }
    
    public static void setSearchButton(final JButton searchButton) {
        Crosslexic.searchButton = searchButton;
    }
    
    public static JButton getClearButton() {
        return Crosslexic.clearButton;
    }
    
    public static void setClearButton(final JButton clearButton) {
        Crosslexic.clearButton = clearButton;
    }
    
    public static JFormattedTextField[] getLetterText() {
        return Crosslexic.letterText;
    }
    
    public static void setLetterText(final JFormattedTextField[] letterText) {
        Crosslexic.letterText = letterText;
    }
    
    public static DefaultListModel getListModel() {
        return Crosslexic.listModel;
    }
    
    public static boolean isMenu_activated() {
        return Crosslexic.menu_activated;
    }
    
    public static int getKey_code() {
        return Crosslexic.key_code;
    }
    
    public static JProgressBar getProgressBar() {
        return Crosslexic.progressBar;
    }
    
    public static Thread getSearchThread() {
        return Crosslexic.searchThread;
    }
    
    public static void setSearchThread(final Thread searchThread) {
        Crosslexic.searchThread = searchThread;
    }
    
    static {
        MAXSIZE = 20;
        Crosslexic.menu_activated = false;
        Crosslexic.searchThread = null;
        Crosslexic.repaintlistThread = null;
    }
    
    protected static class myListCellRenderer extends DefaultListCellRenderer
    {
        private static final long serialVersionUID = 1L;
        
        @Override
        public Component getListCellRendererComponent(final JList list, final Object o, final int n, final boolean b, final boolean b2) {
            this.setOpaque(true);
            this.setBackground(b ? list.getSelectionBackground() : list.getBackground());
            this.setForeground(b ? list.getSelectionForeground() : list.getForeground());
            if (o instanceof JLabel) {
                final JLabel label = (JLabel)o;
                this.setText(label.getText());
                this.setIcon(label.getIcon());
            }
            else if (o instanceof String) {
                this.setText((String)o);
            }
            else {
                this.setText(o.toString());
            }
            return this;
        }
    }
    
    private static class repaintlist implements Runnable
    {
        @Override
        public void run() {
            try {
                Thread.sleep(200L);
                try {
                    Crosslexic.getListModel().wait(200L);
                }
                catch (Exception ex) {}
                final int n = (Crosslexic.getListModel().getSize() > 0) ? (Crosslexic.getListModel().getSize() - 1) : 0;
                Crosslexic.dictlist.setSelectedIndex(n);
                Crosslexic.dictlist.ensureIndexIsVisible(n);
            }
            catch (Exception ex2) {
                Crosslexic.getListModel().addElement(Crosslexic.createLabel(Crosslexic.iconAlert, "Oh bugger! Jlist scroll bug!"));
                Crosslexic.getListModel().addElement(Crosslexic.createLabel(null, "> You may have to click Clear and try again."));
                Crosslexic.setSearchThread(null);
                Crosslexic.app_setEnabled(true);
            }
            Crosslexic.dictlist.repaint();
            Crosslexic.repaintlistThread = null;
        }
    }
}
