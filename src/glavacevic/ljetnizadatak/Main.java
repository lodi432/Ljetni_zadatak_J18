/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package glavacevic.ljetnizadatak;

import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.text.Collator;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.tree.DefaultMutableTreeNode;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import javax.swing.DefaultListModel;
import java.util.ArrayList;
import java.util.Map;
import javax.swing.BorderFactory;
import javazoom.jlgui.basicplayer.BasicController;
import javazoom.jlgui.basicplayer.BasicPlayer;
import javazoom.jlgui.basicplayer.BasicPlayerEvent;
import javazoom.jlgui.basicplayer.BasicPlayerListener;
/**
 *
 * @author domagoj
 */
public class Main extends javax.swing.JFrame implements BasicPlayerListener {

    /**
     * Creates new form NewJFrame
     */
    
    private Connection veza;
    private PreparedStatement izraz;
    String putanja = System.getProperty("user.dir");

    
    
    //---------Validacija podataka
    
    
       private void oznaciGresku(JTextField polje) {
        polje.setBorder(BorderFactory.createLineBorder(Color.decode("#FF0000")));
        polje.requestFocus();
    }
    
    
    
    
    
    
    
    //---------Audio plejer 
    
    PlayList pl = new PlayList();
    ArrayList updateList = new ArrayList();
    BasicPlayer player = new BasicPlayer();
    File rxmSim;
    
    
    
    void updateList() {
        updateList = pl.getListSong();
        DefaultListModel model = new DefaultListModel();
        for (int i = 0; i < updateList.size(); i++) {
            int j = 1 + 1;
            model.add(i, j + " ovo " + ((File) updateList.get(i)).getName());
        }
        jList_plejlist.setModel(model);
    }
    
    
     //kontrole za plejer
    void add() {
        pl.add(this);
        
        updateList();
    }

    void remove() {
        try {
            int nekitamo = jList_plejlist.getLeadSelectionIndex();
            pl.ls.remove(nekitamo);
            updateList();
        } catch (Exception e) {

        }

    }

    void up() {
        try {
            int s1 = jList_plejlist.getLeadSelectionIndex();
            rxmSim = (File) pl.ls.get(s1);
            pl.ls.remove(s1);
            pl.ls.add(s1 - 1, rxmSim);
            updateList();
            jList_plejlist.setSelectedIndex(s1 - 1);
        } catch (Exception e) {

        }
    }

    void down() {
        try {
            int s1 = jList_plejlist.getLeadSelectionIndex();
            rxmSim = (File) pl.ls.get(s1);
            pl.ls.remove(s1);
            pl.ls.add(s1 + 1, rxmSim);
            updateList();
            jList_plejlist.setSelectedIndex(s1 + 1);
        } catch (Exception e) {

        }
    }
    
    
    void open() {
        pl.openPls(this);
        updateList();
    }

    void save() {
        pl.saveAsPlaylist(this);
        updateList();
    }

    File play1;
    static int a = 0;
    
   
    
    
    void pustaj() {
        if (a == 0) {
          
            
            try {

                int p1 = jList_plejlist.getSelectedIndex();
                play1 = (File) this.updateList.get(p1);

                player.open(new File(play1.getAbsolutePath()));

                a = 1;
                player.play();
                
            } catch (Exception e) {
                System.out.println("Problem playing file");

            }

            new Thread() {
                @Override
                public void run() {
                    try {

                        player.play();

                    } catch (Exception e) {

                    }

                }

            }.start();
        } else {

            a = 0;
            pustaj();
        }
    
        }
                   

    
//    boolean clicked = false;
    
    
      void pauza(){
        
         if (a == 1) {
            try {
                player.pause();

                a = 1;

            } catch (Exception e) {
                System.out.println("Problem playing file");
            }

            new Thread() {
                @Override
                public void run() {
                    try {

                        player.pause();

                    } catch (Exception e) {

                    }

                }

            }.start();
        } else {
            a = 0;
        }
    }
//            player.close();
         
    


    void stopiraj() {
        if (a != 0) {
            try {

                player.stop();

            } catch (Exception e) {
                System.out.println("Problem playing file");
//                System.out.println(e);

            }

            new Thread() {
                @Override
                public void run() {
                    try {
                        player.stop();
                    } catch (Exception e) {
                    }
                }

            }.start();
        } else {
            a = 0;
        }
    }
    
     void nastavi (){
        
         if (a != 0) {
            try {

                player.resume();

            } catch (Exception e) {
                System.out.println("Problem playing file");
//                System.out.println(e);

            }

            new Thread() {
                @Override
                public void run() {
                    try {
                        player.resume();
                    } catch (Exception e) {
                    }
                }

            }.start();
        } else {
            a = 0;
        }
        
    
    }
    
    
    File sa;

    void next() {
        if (a == 0) {
            try {
                int s1 = jList_plejlist.getSelectedIndex() + 1;
                sa = (File) this.pl.ls.get(s1);
                player.open(new File(sa.getAbsolutePath()));
                player.play();
                a = 1;
                jList_plejlist.setSelectedIndex(s1);
            } catch (Exception e) {
                System.out.println("Problem playing file");
                System.out.println(e);
            }

            new Thread() {
                @Override
                public void run() {
                    try {
                        player.play();

                    } catch (Exception e) {
                    }
                }
            }.start();
        } else {
//        player.close();
            a = 0;
            next();
        }

    }

    void previous() {
        if (a == 0) {
            try {
                int s1 = jList_plejlist.getSelectedIndex() - 1;
                sa = (File) this.pl.ls.get(s1);
                player.open(new File(sa.getAbsolutePath()));
                player.play();
                a = 1;
                jList_plejlist.setSelectedIndex(s1);
            } catch (Exception e) {
                System.out.println("Problem playing file");
                System.out.println(e);
            }

            new Thread() {
                @Override
                public void run() {
                    try {
                        player.play();

                    } catch (Exception e) {
                    }
                }
            }.start();
        } else {
//        player.close();
            a = 0;
            previous();
        }
    }

    
    


    
    public Main() {
        initComponents();
        
        
         try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        try {
             veza = DriverManager.getConnection("jdbc:mysql://localhost/technoj18?" +
                                   "user=root&password=&serverTimezone=CET&useUnicode=true&characterEncoding=utf-8&useSSL=false");
            ucitajIzBaze();
            
        } catch (SQLException ex) {
           ex.printStackTrace();
        }
         
        
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSplitPane1 = new javax.swing.JSplitPane();
        navigacijskiPanel = new javax.swing.JPanel();
        blank_Panel = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        blank_Panel1 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        btn_nadzorna = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        btn_programi = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        btn_artists = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        btn_music = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        btn_playerPanel = new javax.swing.JPanel();
        jLabel43 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        btn_era = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        blank_Panel2 = new javax.swing.JPanel();
        jLabel45 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        btn_git = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        btn_postavke = new javax.swing.JPanel();
        jLabel41 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        pozadinskiPanel = new javax.swing.JPanel();
        nadzorna_Panel = new javax.swing.JPanel();
        helloPanel = new javax.swing.JPanel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        table23 = new javax.swing.JTable();
        btn_zatvori3 = new javax.swing.JLabel();
        programi_Panel = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        lstArtisti = new javax.swing.JList<>();
        lblOpis = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        lbl_img = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        pnlPodaci = new javax.swing.JPanel();
        txtNaziv = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtOpis = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        img_link = new javax.swing.JLabel();
        btnObrisi = new javax.swing.JButton();
        btnPromjena = new javax.swing.JButton();
        btnDodajNovi = new javax.swing.JButton();
        jLabel38 = new javax.swing.JLabel();
        jCombo_zanr = new javax.swing.JComboBox<>();
        lbl_Path = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        zanr_out = new javax.swing.JLabel();
        btn_slika = new javax.swing.JButton();
        lbl_ID = new javax.swing.JLabel();
        jLabel54 = new javax.swing.JLabel();
        artist_Panel = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        records_Panel = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        lstPlayList = new javax.swing.JList<>();
        label_ajde = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTree1 = new javax.swing.JTree();
        jButton3 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        btn_dodajPlaylistu = new javax.swing.JButton();
        btn_openFile = new javax.swing.JButton();
        jLabel56 = new javax.swing.JLabel();
        era_Panel = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jLabel53 = new javax.swing.JLabel();
        audio_Panel = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        btnPlayme = new javax.swing.JButton();
        btn_DownP = new javax.swing.JButton();
        btn_stopme = new javax.swing.JButton();
        btn_nextt = new javax.swing.JButton();
        btnPrevious = new javax.swing.JButton();
        btn_addP = new javax.swing.JButton();
        btn_upP = new javax.swing.JButton();
        btn_removeP = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        jList_plejlist = new javax.swing.JList<>();
        btn_openP = new javax.swing.JButton();
        btn_SaveP = new javax.swing.JButton();
        jLabel47 = new javax.swing.JLabel();
        tgl_PauzaNastavi = new javax.swing.JToggleButton();
        jLabel48 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();
        postavke_Panel = new javax.swing.JPanel();
        lbl_prikaziTraku = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        helloPanel1 = new javax.swing.JPanel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel40 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jProgressBar1 = new javax.swing.JProgressBar();
        jLabel39 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jSplitPane1.setDividerLocation(300);

        navigacijskiPanel.setBackground(new java.awt.Color(69, 202, 255));

        blank_Panel.setBackground(new java.awt.Color(69, 202, 255));
        blank_Panel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                blank_PanelMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                blank_PanelMousePressed(evt);
            }
        });

        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jLabel15.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jLabel15.setForeground(java.awt.Color.white);
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout blank_PanelLayout = new javax.swing.GroupLayout(blank_Panel);
        blank_Panel.setLayout(blank_PanelLayout);
        blank_PanelLayout.setHorizontalGroup(
            blank_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(blank_PanelLayout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel15)
                .addContainerGap(197, Short.MAX_VALUE))
        );
        blank_PanelLayout.setVerticalGroup(
            blank_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(blank_PanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(blank_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel15)
                    .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE))
                .addContainerGap())
        );

        blank_Panel1.setBackground(new java.awt.Color(69, 202, 255));
        blank_Panel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                blank_Panel1MousePressed(evt);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                blank_Panel1MouseClicked(evt);
            }
        });

        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jLabel17.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jLabel17.setForeground(java.awt.Color.white);
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout blank_Panel1Layout = new javax.swing.GroupLayout(blank_Panel1);
        blank_Panel1.setLayout(blank_Panel1Layout);
        blank_Panel1Layout.setHorizontalGroup(
            blank_Panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(blank_Panel1Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel17)
                .addContainerGap(197, Short.MAX_VALUE))
        );
        blank_Panel1Layout.setVerticalGroup(
            blank_Panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(blank_Panel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(blank_Panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel17)
                    .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE))
                .addContainerGap())
        );

        btn_nadzorna.setBackground(new java.awt.Color(69, 202, 255));
        btn_nadzorna.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btn_nadzornaMousePressed(evt);
            }
        });

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/home-24.png"))); // NOI18N

        jLabel4.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jLabel4.setForeground(java.awt.Color.white);
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Nadzorna Ploča");

        javax.swing.GroupLayout btn_nadzornaLayout = new javax.swing.GroupLayout(btn_nadzorna);
        btn_nadzorna.setLayout(btn_nadzornaLayout);
        btn_nadzornaLayout.setHorizontalGroup(
            btn_nadzornaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btn_nadzornaLayout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4)
                .addContainerGap(86, Short.MAX_VALUE))
        );
        btn_nadzornaLayout.setVerticalGroup(
            btn_nadzornaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btn_nadzornaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(btn_nadzornaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel4)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE))
                .addContainerGap())
        );

        btn_programi.setBackground(new java.awt.Color(69, 202, 255));
        btn_programi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btn_programiMousePressed(evt);
            }
        });

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/arrow-213-16.png"))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jLabel2.setForeground(java.awt.Color.white);
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("CRUD");

        javax.swing.GroupLayout btn_programiLayout = new javax.swing.GroupLayout(btn_programi);
        btn_programi.setLayout(btn_programiLayout);
        btn_programiLayout.setHorizontalGroup(
            btn_programiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btn_programiLayout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addContainerGap(153, Short.MAX_VALUE))
        );
        btn_programiLayout.setVerticalGroup(
            btn_programiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btn_programiLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(btn_programiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE))
                .addContainerGap())
        );

        btn_artists.setBackground(new java.awt.Color(69, 202, 255));
        btn_artists.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btn_artistsMousePressed(evt);
            }
        });

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/user-16.png"))); // NOI18N

        jLabel6.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jLabel6.setForeground(java.awt.Color.white);
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Artist");

        javax.swing.GroupLayout btn_artistsLayout = new javax.swing.GroupLayout(btn_artists);
        btn_artists.setLayout(btn_artistsLayout);
        btn_artistsLayout.setHorizontalGroup(
            btn_artistsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btn_artistsLayout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel6)
                .addContainerGap(151, Short.MAX_VALUE))
        );
        btn_artistsLayout.setVerticalGroup(
            btn_artistsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btn_artistsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(btn_artistsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel6)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE))
                .addContainerGap())
        );

        btn_music.setBackground(new java.awt.Color(69, 202, 255));
        btn_music.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btn_musicMousePressed(evt);
            }
        });

        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/music-record-16.png"))); // NOI18N

        jLabel11.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jLabel11.setForeground(java.awt.Color.white);
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("Audio Server");

        javax.swing.GroupLayout btn_musicLayout = new javax.swing.GroupLayout(btn_music);
        btn_music.setLayout(btn_musicLayout);
        btn_musicLayout.setHorizontalGroup(
            btn_musicLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btn_musicLayout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel11)
                .addContainerGap(108, Short.MAX_VALUE))
        );
        btn_musicLayout.setVerticalGroup(
            btn_musicLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btn_musicLayout.createSequentialGroup()
                .addContainerGap(25, Short.MAX_VALUE)
                .addGroup(btn_musicLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addGap(21, 21, 21))
        );

        btn_playerPanel.setBackground(new java.awt.Color(69, 202, 255));
        btn_playerPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btn_playerPanelMousePressed(evt);
            }
        });

        jLabel43.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel43.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/audio-spectrum-16.png"))); // NOI18N

        jLabel44.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jLabel44.setForeground(java.awt.Color.white);
        jLabel44.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel44.setText("Music Player");

        javax.swing.GroupLayout btn_playerPanelLayout = new javax.swing.GroupLayout(btn_playerPanel);
        btn_playerPanel.setLayout(btn_playerPanelLayout);
        btn_playerPanelLayout.setHorizontalGroup(
            btn_playerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btn_playerPanelLayout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jLabel43, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel44)
                .addContainerGap(111, Short.MAX_VALUE))
        );
        btn_playerPanelLayout.setVerticalGroup(
            btn_playerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btn_playerPanelLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(btn_playerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel43, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel44, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(25, Short.MAX_VALUE))
        );

        btn_era.setBackground(new java.awt.Color(69, 202, 255));
        btn_era.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btn_eraMousePressed(evt);
            }
        });

        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/database-16.png"))); // NOI18N

        jLabel13.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jLabel13.setForeground(java.awt.Color.white);
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("ERA dijagram");

        javax.swing.GroupLayout btn_eraLayout = new javax.swing.GroupLayout(btn_era);
        btn_era.setLayout(btn_eraLayout);
        btn_eraLayout.setHorizontalGroup(
            btn_eraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btn_eraLayout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel13)
                .addContainerGap(107, Short.MAX_VALUE))
        );
        btn_eraLayout.setVerticalGroup(
            btn_eraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btn_eraLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(btn_eraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(25, Short.MAX_VALUE))
        );

        blank_Panel2.setBackground(new java.awt.Color(69, 202, 255));
        blank_Panel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                blank_Panel2MousePressed(evt);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                blank_Panel2MouseClicked(evt);
            }
        });

        jLabel45.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jLabel46.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jLabel46.setForeground(java.awt.Color.white);
        jLabel46.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout blank_Panel2Layout = new javax.swing.GroupLayout(blank_Panel2);
        blank_Panel2.setLayout(blank_Panel2Layout);
        blank_Panel2Layout.setHorizontalGroup(
            blank_Panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(blank_Panel2Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jLabel45, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel46)
                .addContainerGap(197, Short.MAX_VALUE))
        );
        blank_Panel2Layout.setVerticalGroup(
            blank_Panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(blank_Panel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(blank_Panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel46)
                    .addComponent(jLabel45, javax.swing.GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE))
                .addContainerGap())
        );

        btn_git.setBackground(new java.awt.Color(69, 202, 255));
        btn_git.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btn_gitMousePressed(evt);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_gitMouseClicked(evt);
            }
        });

        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/github-9-16.png"))); // NOI18N

        jLabel19.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jLabel19.setForeground(java.awt.Color.white);
        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel19.setText("Kod na Gitu");

        javax.swing.GroupLayout btn_gitLayout = new javax.swing.GroupLayout(btn_git);
        btn_git.setLayout(btn_gitLayout);
        btn_gitLayout.setHorizontalGroup(
            btn_gitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btn_gitLayout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(117, Short.MAX_VALUE))
        );
        btn_gitLayout.setVerticalGroup(
            btn_gitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btn_gitLayout.createSequentialGroup()
                .addGap(0, 23, Short.MAX_VALUE)
                .addGroup(btn_gitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(23, 23, 23))
        );

        btn_postavke.setBackground(new java.awt.Color(69, 202, 255));
        btn_postavke.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btn_postavkeMousePressed(evt);
            }
        });

        jLabel41.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel41.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/settings-4-16.png"))); // NOI18N

        jLabel42.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jLabel42.setForeground(java.awt.Color.white);
        jLabel42.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel42.setText("Postavke");

        javax.swing.GroupLayout btn_postavkeLayout = new javax.swing.GroupLayout(btn_postavke);
        btn_postavke.setLayout(btn_postavkeLayout);
        btn_postavkeLayout.setHorizontalGroup(
            btn_postavkeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btn_postavkeLayout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jLabel41, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel42)
                .addContainerGap(135, Short.MAX_VALUE))
        );
        btn_postavkeLayout.setVerticalGroup(
            btn_postavkeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btn_postavkeLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(btn_postavkeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel41, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel42, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(25, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout navigacijskiPanelLayout = new javax.swing.GroupLayout(navigacijskiPanel);
        navigacijskiPanel.setLayout(navigacijskiPanelLayout);
        navigacijskiPanelLayout.setHorizontalGroup(
            navigacijskiPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(blank_Panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(blank_Panel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(btn_nadzorna, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(btn_programi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(btn_artists, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(btn_music, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(btn_playerPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(btn_era, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(blank_Panel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(btn_git, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(btn_postavke, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        navigacijskiPanelLayout.setVerticalGroup(
            navigacijskiPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(navigacijskiPanelLayout.createSequentialGroup()
                .addComponent(blank_Panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(blank_Panel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(btn_nadzorna, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(btn_programi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(btn_artists, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(btn_music, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(btn_playerPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(btn_era, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(blank_Panel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(btn_git, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(btn_postavke, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jSplitPane1.setLeftComponent(navigacijskiPanel);

        pozadinskiPanel.setLayout(new java.awt.CardLayout());

        helloPanel.setBackground(new java.awt.Color(69, 202, 255));

        jLabel25.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jLabel25.setForeground(java.awt.Color.white);
        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel25.setText("Nadzorna Ploča");

        jLabel26.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        jLabel26.setForeground(java.awt.Color.white);
        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel26.setText("Dobro došli na moju aplikaciju ________________");

        javax.swing.GroupLayout helloPanelLayout = new javax.swing.GroupLayout(helloPanel);
        helloPanel.setLayout(helloPanelLayout);
        helloPanelLayout.setHorizontalGroup(
            helloPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(helloPanelLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(helloPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel25)
                    .addComponent(jLabel26))
                .addGap(108, 108, 108))
        );
        helloPanelLayout.setVerticalGroup(
            helloPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(helloPanelLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jLabel25)
                .addGap(30, 30, 30)
                .addComponent(jLabel26)
                .addContainerGap(150, Short.MAX_VALUE))
        );

        table23.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"8/8/2018", "Java Aplikacija", "Osijek"},
                {"5/8/2018", "Php Aplikacija", "Osijek"},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Datum", "Naziv", "Mjesto"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        table23.setGridColor(java.awt.Color.white);
        jScrollPane1.setViewportView(table23);

        btn_zatvori3.setBackground(java.awt.Color.red);
        btn_zatvori3.setFont(new java.awt.Font("Ubuntu", 1, 32)); // NOI18N
        btn_zatvori3.setForeground(java.awt.Color.white);
        btn_zatvori3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btn_zatvori3.setText("X");
        btn_zatvori3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btn_zatvori3MousePressed(evt);
            }
        });

        javax.swing.GroupLayout nadzorna_PanelLayout = new javax.swing.GroupLayout(nadzorna_Panel);
        nadzorna_Panel.setLayout(nadzorna_PanelLayout);
        nadzorna_PanelLayout.setHorizontalGroup(
            nadzorna_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(nadzorna_PanelLayout.createSequentialGroup()
                .addGroup(nadzorna_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, nadzorna_PanelLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_zatvori3, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(nadzorna_PanelLayout.createSequentialGroup()
                        .addGap(107, 107, 107)
                        .addGroup(nadzorna_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 654, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(helloPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 255, Short.MAX_VALUE)))
                .addContainerGap())
        );
        nadzorna_PanelLayout.setVerticalGroup(
            nadzorna_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(nadzorna_PanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btn_zatvori3, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(helloPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(69, 69, 69)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(401, Short.MAX_VALUE))
        );

        pozadinskiPanel.add(nadzorna_Panel, "card2");

        programi_Panel.setLayout(null);

        lstArtisti.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        lstArtisti.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                lstArtistiValueChanged(evt);
            }
        });
        jScrollPane2.setViewportView(lstArtisti);

        lblOpis.setBackground(new java.awt.Color(255, 255, 255));
        lblOpis.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        lblOpis.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        lblOpis.setOpaque(true);

        jLabel8.setFont(new java.awt.Font("Ubuntu Light", 0, 18)); // NOI18N
        jLabel8.setForeground(java.awt.Color.darkGray);
        jLabel8.setText("Opis");

        jLabel31.setFont(new java.awt.Font("Ubuntu Light", 0, 18)); // NOI18N
        jLabel31.setForeground(java.awt.Color.darkGray);
        jLabel31.setText("Artist");

        jLabel32.setFont(new java.awt.Font("Ubuntu Light", 0, 18)); // NOI18N
        jLabel32.setForeground(java.awt.Color.darkGray);
        jLabel32.setText("Slika");

        lbl_img.setBackground(new java.awt.Color(255, 255, 255));
        lbl_img.setOpaque(true);

        jLabel34.setFont(new java.awt.Font("Ubuntu Light", 0, 18)); // NOI18N
        jLabel34.setForeground(java.awt.Color.darkGray);
        jLabel34.setText("Žanr");

        txtNaziv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNazivActionPerformed(evt);
            }
        });

        jLabel7.setText("Naziv");

        jLabel29.setText("Opis");

        btnObrisi.setText("Obriši");
        btnObrisi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnObrisiActionPerformed(evt);
            }
        });

        btnPromjena.setText("Promjena");
        btnPromjena.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPromjenaActionPerformed(evt);
            }
        });

        btnDodajNovi.setText("Dodaj novi");
        btnDodajNovi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDodajNoviActionPerformed(evt);
            }
        });

        jLabel38.setText("Direktorij odabrane slike");

        jCombo_zanr.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Techno", "Trance", "Edm", "Rock" }));

        lbl_Path.setBackground(new java.awt.Color(255, 255, 255));
        lbl_Path.setOpaque(true);

        jLabel35.setText("Žanr");

        javax.swing.GroupLayout pnlPodaciLayout = new javax.swing.GroupLayout(pnlPodaci);
        pnlPodaci.setLayout(pnlPodaciLayout);
        pnlPodaciLayout.setHorizontalGroup(
            pnlPodaciLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPodaciLayout.createSequentialGroup()
                .addComponent(jLabel29)
                .addGroup(pnlPodaciLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlPodaciLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(img_link, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(49, 49, 49))
                    .addGroup(pnlPodaciLayout.createSequentialGroup()
                        .addGap(375, 375, 375)
                        .addComponent(jLabel35)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(pnlPodaciLayout.createSequentialGroup()
                .addGroup(pnlPodaciLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlPodaciLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(txtOpis, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 386, Short.MAX_VALUE)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlPodaciLayout.createSequentialGroup()
                            .addComponent(btnDodajNovi)
                            .addGap(18, 18, 18)
                            .addComponent(btnPromjena)
                            .addGap(19, 19, 19)
                            .addComponent(btnObrisi, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(txtNaziv, javax.swing.GroupLayout.Alignment.LEADING))
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlPodaciLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jCombo_zanr, 0, 336, Short.MAX_VALUE)
                    .addGroup(pnlPodaciLayout.createSequentialGroup()
                        .addGroup(pnlPodaciLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel38)
                            .addComponent(lbl_Path, javax.swing.GroupLayout.PREFERRED_SIZE, 326, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 10, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pnlPodaciLayout.setVerticalGroup(
            pnlPodaciLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPodaciLayout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addGroup(pnlPodaciLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jLabel38))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlPodaciLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtNaziv, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_Path, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnlPodaciLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel29)
                    .addComponent(jLabel35))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlPodaciLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtOpis, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCombo_zanr, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(37, 37, 37)
                .addGroup(pnlPodaciLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnDodajNovi)
                    .addComponent(btnPromjena)
                    .addComponent(btnObrisi))
                .addGap(52, 52, 52)
                .addComponent(img_link, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        zanr_out.setBackground(new java.awt.Color(255, 255, 255));
        zanr_out.setOpaque(true);

        btn_slika.setBackground(new java.awt.Color(69, 202, 255));
        btn_slika.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/cloud-upload-16.png"))); // NOI18N
        btn_slika.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_slikaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel31))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addGap(201, 201, 201)
                                .addComponent(jLabel32))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(lblOpis, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(lbl_img, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel34)
                                            .addComponent(zanr_out, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addComponent(btn_slika)))))
                    .addComponent(pnlPodaci, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(96, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(63, 63, 63)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jLabel31)
                    .addComponent(jLabel32)
                    .addComponent(jLabel34))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblOpis, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 121, Short.MAX_VALUE)
                    .addComponent(lbl_img, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(zanr_out, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btn_slika)
                .addGap(29, 29, 29)
                .addComponent(pnlPodaci, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        programi_Panel.add(jPanel1);
        jPanel1.setBounds(33, 80, 838, 530);
        programi_Panel.add(lbl_ID);
        lbl_ID.setBounds(390, 30, 67, 33);

        jLabel54.setFont(new java.awt.Font("Ubuntu Light", 0, 36)); // NOI18N
        jLabel54.setForeground(java.awt.Color.gray);
        jLabel54.setText("CRUD ");
        programi_Panel.add(jLabel54);
        jLabel54.setBounds(43, 31, 200, 47);

        pozadinskiPanel.add(programi_Panel, "card3");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1048, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 915, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout artist_PanelLayout = new javax.swing.GroupLayout(artist_Panel);
        artist_Panel.setLayout(artist_PanelLayout);
        artist_PanelLayout.setHorizontalGroup(
            artist_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        artist_PanelLayout.setVerticalGroup(
            artist_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pozadinskiPanel.add(artist_Panel, "card4");

        lstPlayList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        lstPlayList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                lstPlayListValueChanged(evt);
            }
        });
        jScrollPane4.setViewportView(lstPlayList);

        jTree1.setModel(new FileSystemModel(new File(putanja)));
        jTree1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTree1MouseClicked(evt);
            }
        });
        jTree1.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent evt) {
                jTree1ValueChanged(evt);
            }
        });
        jScrollPane3.setViewportView(jTree1);

        jButton3.setText("Upload to Server");

        jButton2.setText("Download");

        btn_dodajPlaylistu.setText("Dodaj");
        btn_dodajPlaylistu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_dodajPlaylistuActionPerformed(evt);
            }
        });

        btn_openFile.setText("Otvori ");
        btn_openFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_openFileActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(btn_openFile, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_dodajPlaylistu, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addComponent(label_ajde, javax.swing.GroupLayout.PREFERRED_SIZE, 519, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(116, 116, 116)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 773, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(159, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_openFile)
                    .addComponent(btn_dodajPlaylistu)
                    .addComponent(jButton2)
                    .addComponent(jButton3))
                .addGap(63, 63, 63)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(label_ajde, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(168, 168, 168))
        );

        jLabel56.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        jLabel56.setForeground(java.awt.Color.gray);
        jLabel56.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel56.setText("Prikaz direktorija");

        javax.swing.GroupLayout records_PanelLayout = new javax.swing.GroupLayout(records_Panel);
        records_Panel.setLayout(records_PanelLayout);
        records_PanelLayout.setHorizontalGroup(
            records_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(records_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(records_PanelLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jLabel56)
                    .addContainerGap(861, Short.MAX_VALUE)))
        );
        records_PanelLayout.setVerticalGroup(
            records_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, records_PanelLayout.createSequentialGroup()
                .addContainerGap(91, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 680, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(144, 144, 144))
            .addGroup(records_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(records_PanelLayout.createSequentialGroup()
                    .addGap(36, 36, 36)
                    .addComponent(jLabel56)
                    .addContainerGap(847, Short.MAX_VALUE)))
        );

        pozadinskiPanel.add(records_Panel, "card5");

        jLabel37.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/ERATJ18.png"))); // NOI18N

        jLabel53.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        jLabel53.setForeground(java.awt.Color.gray);
        jLabel53.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel53.setText("Era dijagram");

        javax.swing.GroupLayout era_PanelLayout = new javax.swing.GroupLayout(era_Panel);
        era_Panel.setLayout(era_PanelLayout);
        era_PanelLayout.setHorizontalGroup(
            era_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, era_PanelLayout.createSequentialGroup()
                .addGap(0, 1048, Short.MAX_VALUE)
                .addComponent(jLabel20))
            .addGroup(era_PanelLayout.createSequentialGroup()
                .addGroup(era_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(era_PanelLayout.createSequentialGroup()
                        .addGap(222, 222, 222)
                        .addComponent(jLabel37))
                    .addGroup(era_PanelLayout.createSequentialGroup()
                        .addGap(81, 81, 81)
                        .addComponent(jLabel53)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        era_PanelLayout.setVerticalGroup(
            era_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(era_PanelLayout.createSequentialGroup()
                .addComponent(jLabel20)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 127, Short.MAX_VALUE)
                .addComponent(jLabel53)
                .addGap(52, 52, 52)
                .addComponent(jLabel37)
                .addGap(179, 179, 179))
        );

        pozadinskiPanel.add(era_Panel, "card6");

        audio_Panel.setBackground(new java.awt.Color(123, 123, 123));

        btnPlayme.setBackground(new java.awt.Color(69, 202, 255));
        btnPlayme.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/arrow-30-24.png"))); // NOI18N
        btnPlayme.setFocusable(false);
        btnPlayme.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPlaymeActionPerformed(evt);
            }
        });

        btn_DownP.setBackground(new java.awt.Color(69, 202, 255));
        btn_DownP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/arrow-213-24.png"))); // NOI18N
        btn_DownP.setFocusable(false);
        btn_DownP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_DownPActionPerformed(evt);
            }
        });

        btn_stopme.setBackground(new java.awt.Color(69, 202, 255));
        btn_stopme.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/stop-24.png"))); // NOI18N
        btn_stopme.setFocusable(false);
        btn_stopme.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_stopmeActionPerformed(evt);
            }
        });

        btn_nextt.setBackground(new java.awt.Color(69, 202, 255));
        btn_nextt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/arrow-30-24.png"))); // NOI18N
        btn_nextt.setFocusable(false);
        btn_nextt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_nexttActionPerformed(evt);
            }
        });

        btnPrevious.setBackground(new java.awt.Color(69, 202, 255));
        btnPrevious.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/arrow-88-24.png"))); // NOI18N
        btnPrevious.setFocusable(false);
        btnPrevious.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPreviousActionPerformed(evt);
            }
        });

        btn_addP.setBackground(new java.awt.Color(69, 202, 255));
        btn_addP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/add-file-24.png"))); // NOI18N
        btn_addP.setFocusable(false);
        btn_addP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_addPActionPerformed(evt);
            }
        });

        btn_upP.setBackground(new java.awt.Color(69, 202, 255));
        btn_upP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/arrow-151-24.png"))); // NOI18N
        btn_upP.setFocusable(false);
        btn_upP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_upPActionPerformed(evt);
            }
        });

        btn_removeP.setBackground(new java.awt.Color(69, 202, 255));
        btn_removeP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/delete-24.png"))); // NOI18N
        btn_removeP.setFocusable(false);
        btn_removeP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_removePActionPerformed(evt);
            }
        });

        jScrollPane5.setViewportView(jList_plejlist);

        btn_openP.setBackground(new java.awt.Color(69, 202, 255));
        btn_openP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/add-list-24.png"))); // NOI18N
        btn_openP.setFocusable(false);
        btn_openP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_openPActionPerformed(evt);
            }
        });

        btn_SaveP.setBackground(new java.awt.Color(69, 202, 255));
        btn_SaveP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/save-as-24.png"))); // NOI18N
        btn_SaveP.setFocusable(false);
        btn_SaveP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_SavePActionPerformed(evt);
            }
        });

        jLabel47.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        jLabel47.setForeground(java.awt.Color.gray);
        jLabel47.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel47.setText("Plejlista______________________________________");

        tgl_PauzaNastavi.setBackground(new java.awt.Color(69, 202, 255));
        tgl_PauzaNastavi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/pause-24.png"))); // NOI18N
        tgl_PauzaNastavi.setFocusable(false);
        tgl_PauzaNastavi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tgl_PauzaNastaviActionPerformed(evt);
            }
        });

        jLabel48.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        jLabel48.setForeground(java.awt.Color.gray);
        jLabel48.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel48.setText("Kontrole za plejlistu");

        jLabel49.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        jLabel49.setForeground(java.awt.Color.gray);
        jLabel49.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel49.setText("Sljedeća ili prijašnja pjesma , pauza i nastavi [toggle]");

        jLabel50.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        jLabel50.setForeground(java.awt.Color.gray);
        jLabel50.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel50.setText("Audio Kontrole");

        jLabel51.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        jLabel51.setForeground(java.awt.Color.gray);
        jLabel51.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel51.setText("PLAY");

        jLabel52.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        jLabel52.setForeground(java.awt.Color.gray);
        jLabel52.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel52.setText("STOP");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(84, 84, 84)
                                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 501, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(32, 32, 32)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(btn_stopme, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel52, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(btnPlayme, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel51, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(242, 242, 242)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(btnPrevious, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(tgl_PauzaNastavi, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(btn_nextt, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel49)))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel47)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel50, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(159, 159, 159)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel48)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(btn_removeP, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btn_addP, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(34, 34, 34)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(btn_DownP, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(27, 27, 27)
                                        .addComponent(btn_SaveP, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(btn_upP, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(27, 27, 27)
                                        .addComponent(btn_openP, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)))))))
                .addGap(0, 228, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(97, 97, 97)
                .addComponent(jLabel48)
                .addGap(37, 37, 37)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_addP, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_upP, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_openP, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(47, 47, 47)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_removeP, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_DownP, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_SaveP, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(37, 37, 37)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel50, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel47))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnPrevious, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(btnPlayme, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel51))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btn_stopme, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel52, javax.swing.GroupLayout.Alignment.TRAILING))))
                        .addGap(45, 45, 45)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btn_nextt, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tgl_PauzaNastavi, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(30, 30, 30)
                .addComponent(jLabel49)
                .addContainerGap(186, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout audio_PanelLayout = new javax.swing.GroupLayout(audio_Panel);
        audio_Panel.setLayout(audio_PanelLayout);
        audio_PanelLayout.setHorizontalGroup(
            audio_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        audio_PanelLayout.setVerticalGroup(
            audio_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pozadinskiPanel.add(audio_Panel, "card7");

        lbl_prikaziTraku.setBackground(new java.awt.Color(255, 255, 255));
        lbl_prikaziTraku.setFont(new java.awt.Font("Ubuntu", 1, 18)); // NOI18N
        lbl_prikaziTraku.setForeground(java.awt.Color.white);
        lbl_prikaziTraku.setText("  ");

        jLabel21.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/AdamBeyer.jpg"))); // NOI18N
        jLabel21.setText("jLabel21");

        jLabel22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/somguy.jpg"))); // NOI18N
        jLabel22.setText("jLabel21");

        jLabel23.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/nicole.jpg"))); // NOI18N
        jLabel23.setText("jLabel21");

        jLabel24.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/carlcox.jpg"))); // NOI18N
        jLabel24.setText("jLabel21");

        helloPanel1.setBackground(new java.awt.Color(122, 72, 221));

        jLabel27.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jLabel27.setForeground(java.awt.Color.white);
        jLabel27.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel27.setText("Pitanje");

        jLabel28.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        jLabel28.setForeground(java.awt.Color.white);
        jLabel28.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel28.setText("pogodi tko je ________________ ?");

        javax.swing.GroupLayout helloPanel1Layout = new javax.swing.GroupLayout(helloPanel1);
        helloPanel1.setLayout(helloPanel1Layout);
        helloPanel1Layout.setHorizontalGroup(
            helloPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(helloPanel1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(helloPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel27)
                    .addComponent(jLabel28))
                .addContainerGap(39, Short.MAX_VALUE))
        );
        helloPanel1Layout.setVerticalGroup(
            helloPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(helloPanel1Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jLabel27)
                .addGap(30, 30, 30)
                .addComponent(jLabel28)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(123, 123, 123));

        jLabel40.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/artworks100x100.jpg"))); // NOI18N

        jLabel33.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/arrow-88-24.png"))); // NOI18N

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/media-play-24w.png"))); // NOI18N

        jLabel36.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/arrow-88-24LLL.png"))); // NOI18N

        jProgressBar1.setBackground(new java.awt.Color(255, 255, 255));
        jProgressBar1.setForeground(new java.awt.Color(255, 255, 255));

        jLabel39.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        jLabel39.setForeground(java.awt.Color.white);
        jLabel39.setText("Now Playing");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel39)
                    .addComponent(jLabel40))
                .addGap(89, 89, 89)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(68, 68, 68)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(59, 59, 59)
                        .addComponent(jLabel36, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(294, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel39, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel40, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel4Layout.createSequentialGroup()
                            .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 6, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(98, 98, 98))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                            .addGap(50, 50, 50)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel33, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel36, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout postavke_PanelLayout = new javax.swing.GroupLayout(postavke_Panel);
        postavke_Panel.setLayout(postavke_PanelLayout);
        postavke_PanelLayout.setHorizontalGroup(
            postavke_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(postavke_PanelLayout.createSequentialGroup()
                .addGroup(postavke_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(postavke_PanelLayout.createSequentialGroup()
                        .addGap(847, 847, 847)
                        .addComponent(lbl_prikaziTraku, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(postavke_PanelLayout.createSequentialGroup()
                        .addGap(208, 208, 208)
                        .addGroup(postavke_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(helloPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(postavke_PanelLayout.createSequentialGroup()
                                .addGroup(postavke_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 292, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(postavke_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(postavke_PanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        postavke_PanelLayout.setVerticalGroup(
            postavke_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, postavke_PanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(postavke_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(postavke_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(helloPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 114, Short.MAX_VALUE)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(48, 48, 48)
                .addComponent(lbl_prikaziTraku, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pozadinskiPanel.add(postavke_Panel, "card8");

        jSplitPane1.setRightComponent(pozadinskiPanel);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane1, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void blank_PanelMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_blank_PanelMousePressed
        setLinkColor(blank_Panel);
    }//GEN-LAST:event_blank_PanelMousePressed

    private void blank_PanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_blank_PanelMouseClicked
        Desktop browser = Desktop.getDesktop();
        try{
            browser.browse (new URI ("https://github.com/lodi432"));
        }
        catch (IOException err){

        }
        catch (URISyntaxException err){

        }
    }//GEN-LAST:event_blank_PanelMouseClicked

    private void blank_Panel1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_blank_Panel1MousePressed
        
    }//GEN-LAST:event_blank_Panel1MousePressed

    private void blank_Panel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_blank_Panel1MouseClicked
       
    }//GEN-LAST:event_blank_Panel1MouseClicked

    private void btn_nadzornaMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_nadzornaMousePressed
        setColor(btn_nadzorna);
        resetColor(btn_programi);
        resetColor(btn_artists);
        resetColor(btn_music);
        resetColor(btn_era);
        resetColor(btn_playerPanel);
        resetColor(btn_postavke);
        pozadinskiPanel.removeAll();
        pozadinskiPanel.add(nadzorna_Panel);
        pozadinskiPanel.repaint();
        pozadinskiPanel.revalidate();
       

    }//GEN-LAST:event_btn_nadzornaMousePressed

    private void btn_programiMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_programiMousePressed
        setColor(btn_programi);
        resetColor(btn_artists);
        resetColor(btn_nadzorna);
        resetColor(btn_music);
        resetColor(btn_era);
        resetColor(btn_playerPanel);
        resetColor(btn_postavke);
        pozadinskiPanel.removeAll();
        pozadinskiPanel.add(programi_Panel);
        pozadinskiPanel.repaint();
        pozadinskiPanel.revalidate();
       
    }//GEN-LAST:event_btn_programiMousePressed

    private void btn_artistsMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_artistsMousePressed
        setColor(btn_artists);
        resetColor(btn_programi);
        resetColor(btn_nadzorna);
        resetColor(btn_music);
        resetColor(btn_era);
        resetColor(btn_playerPanel);
        resetColor(btn_postavke);
        pozadinskiPanel.removeAll();
        pozadinskiPanel.add(artist_Panel);
        pozadinskiPanel.repaint();
        pozadinskiPanel.revalidate();
    }//GEN-LAST:event_btn_artistsMousePressed

    private void btn_gitMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_gitMousePressed
        setLinkColor(btn_git);
    }//GEN-LAST:event_btn_gitMousePressed

    private void btn_gitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_gitMouseClicked
        Desktop browser = Desktop.getDesktop();
        try{
            browser.browse (new URI ("https://github.com/lodi432"));
        }
        catch (IOException err){

        }
        catch (URISyntaxException err){

        }
    }//GEN-LAST:event_btn_gitMouseClicked

    private void btn_eraMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_eraMousePressed
        setColor(btn_era);
        resetColor(btn_programi);
        resetColor(btn_nadzorna);
        resetColor(btn_music);
        resetColor(btn_artists);
        resetColor(btn_playerPanel);
        resetColor(btn_postavke);
        pozadinskiPanel.removeAll();
        pozadinskiPanel.add(era_Panel);
        pozadinskiPanel.repaint();
        pozadinskiPanel.revalidate();
    }//GEN-LAST:event_btn_eraMousePressed

    private void btn_zatvori3MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_zatvori3MousePressed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_btn_zatvori3MousePressed

    
    
  
    private void btn_musicMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_musicMousePressed
        setColor(btn_music);
        resetColor(btn_programi);
        resetColor(btn_nadzorna);
        resetColor(btn_artists);
        resetColor(btn_era);
        resetColor(btn_playerPanel);
        resetColor(btn_postavke);
        pozadinskiPanel.removeAll();
        pozadinskiPanel.add(records_Panel);
        pozadinskiPanel.repaint();
        pozadinskiPanel.revalidate();
    }//GEN-LAST:event_btn_musicMousePressed

    private void jTree1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTree1MouseClicked
        JTreeVar = jTree1.getSelectionPath().toString().replaceAll("[\\[\\]]" , "").replace(", ", "//");
             
        String MusicPath = JTreeVar;
       File file = new File(MusicPath);
     
       boolean isDirectory = file.isDirectory();
       if(isDirectory){
//           System.out.println(file);
           
       }else{
            new AePlayWav(MusicPath).start();
//            label_ajde.setText(MusicPath.toString());
       }
     
       
        
        

      

    }//GEN-LAST:event_jTree1MouseClicked
 
    private void btn_openFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_openFileActionPerformed

        try {
            File Selection = new File(JTreeVar);
            if(Selection.exists()){
                if(Desktop.isDesktopSupported()){
                    Desktop.getDesktop().open(Selection);

                }

            }else {
                Desktop.getDesktop().open(Selection);
            }

        }   catch (Exception e){
            e.printStackTrace();
        }

    }//GEN-LAST:event_btn_openFileActionPerformed

    private void lstPlayListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_lstPlayListValueChanged
            
  
     

    }//GEN-LAST:event_lstPlayListValueChanged

    private void jTree1ValueChanged(javax.swing.event.TreeSelectionEvent evt) {//GEN-FIRST:event_jTree1ValueChanged
        JTreeVar = jTree1.getSelectionPath().toString().replaceAll("[\\[\\]]" , "").replace(", ", "//");
          String MusicPath = JTreeVar;
       File file = new File(MusicPath);
     
       boolean isDirectory = file.isDirectory();
       if(isDirectory){
           
       }else{
//                      System.out.println(file);

       }
        
    }//GEN-LAST:event_jTree1ValueChanged

    
    DefaultListModel mod = new DefaultListModel();
    
    private void btn_dodajPlaylistuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_dodajPlaylistuActionPerformed
        label_ajde.hide();
        lstPlayList.setModel(mod);
        
      
       File file = new File(JTreeVar);
     
       boolean isDirectory = file.isDirectory();
       if(isDirectory){
//           System.out.println(file);
           
       }else{
             label_ajde.setText(JTreeVar);
             mod.addElement(label_ajde.getText());
            
       }
    }//GEN-LAST:event_btn_dodajPlaylistuActionPerformed

    private void btn_postavkeMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_postavkeMousePressed
         setColor(btn_postavke);
        resetColor(btn_programi);
        resetColor(btn_nadzorna);
        resetColor(btn_artists);
        resetColor(btn_music);
        resetColor(btn_era);
        resetColor(btn_playerPanel);
        
        pozadinskiPanel.removeAll();
        pozadinskiPanel.add(postavke_Panel);
        pozadinskiPanel.repaint();
        pozadinskiPanel.revalidate();
    }//GEN-LAST:event_btn_postavkeMousePressed

    private void btn_playerPanelMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_playerPanelMousePressed
        setColor(btn_playerPanel);
        resetColor(btn_programi);
        resetColor(btn_nadzorna);
        resetColor(btn_artists);
        resetColor(btn_music);
        resetColor(btn_era);
        resetColor(btn_postavke);
        pozadinskiPanel.removeAll();
        pozadinskiPanel.add(audio_Panel);
        pozadinskiPanel.repaint();
        pozadinskiPanel.revalidate();
    }//GEN-LAST:event_btn_playerPanelMousePressed

    private void blank_Panel2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_blank_Panel2MousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_blank_Panel2MousePressed

    private void blank_Panel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_blank_Panel2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_blank_Panel2MouseClicked

    private void lstArtistiValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_lstArtistiValueChanged
        if(evt.getValueIsAdjusting()){
            return;
        }

        Artist s = lstArtisti.getSelectedValue();
        if(s==null){
            return;
        }
        NumberFormat nf = NumberFormat.getNumberInstance(new Locale("fr","FR"));
        DecimalFormat df = (DecimalFormat)nf;
        df.applyPattern("###,###.00");

        txtNaziv.setText(s.getNaziv());
        lbl_ID.setText(df.format(s.getId()));
        lblOpis.setText(s.getOpis());
        txtOpis.setText(s.getOpis());
        zanr_out.setText(s.getZanrA());
        jCombo_zanr.setSelectedItem(s.getZanrA());
    }//GEN-LAST:event_lstArtistiValueChanged

    private void txtNazivActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNazivActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNazivActionPerformed

    private void btnObrisiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnObrisiActionPerformed

        Artist s = lstArtisti.getSelectedValue();
        if(s==null){
            JOptionPane.showMessageDialog(getRootPane(),"Prvo odaberi smjer");
            return;
        }

        try {
            izraz = veza.prepareStatement("delete from Artists where id=?");
            izraz.setInt(1, s.getId());

            if(izraz.executeUpdate()==0){
                JOptionPane.showMessageDialog(getRootPane(), "Nije obrisao niti jedan red");
            }else{
                ucitajIzBaze();
                ocistiPolja();
            }
            izraz.close();
            new AePlayWav("obrisi.wav").start();

        }catch(SQLIntegrityConstraintViolationException e){
            JOptionPane.showMessageDialog(getRootPane(), "Ne možeš obrisati ta smjer jer ima grupe");
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_btnObrisiActionPerformed

    private void btnPromjenaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPromjenaActionPerformed
        Artist s = lstArtisti.getSelectedValue();
        if(s==null){
            JOptionPane.showMessageDialog(getRootPane(),"Prvo odaberi smjer");
            return;
        }

        try {

            NamedParameterStatement izraz = new NamedParameterStatement(veza,
                "update Artists set naziv=:naziv, "
                + " opis=:opis, zanrA=:zanrA"
                + " where id=:id");
            izraz.setString("naziv", txtNaziv.getText());
            izraz.setString("opis", txtOpis.getText());
            String combo_value = jCombo_zanr.getSelectedItem().toString();

            izraz.setString("zanrA", combo_value);

            izraz.setInt("id", s.getId());
            if(izraz.izvedi()==0){
                JOptionPane.showMessageDialog(getRootPane(), "Nije promjenjeno");
            }else{
                ocistiPolja();
                ucitajIzBaze();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnPromjenaActionPerformed

    private void btnDodajNoviActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDodajNoviActionPerformed
        try {
            //doma implementiratt
            //https://www.javaworld.com/article/2077706/core-java/named-parameters-for-preparedstatement.html
            izraz=veza.prepareStatement("insert into Artists (naziv,opis,zanrA) "
                + " value (?,?,?)");
            izraz.setString(1, txtNaziv.getText());
            izraz.setString(2, txtOpis.getText());

            String combo_value = jCombo_zanr.getSelectedItem().toString();
            izraz.setString(3, combo_value);

            if(izraz.executeUpdate()==0){
                JOptionPane.showMessageDialog(getRootPane(), "Nije unio niti jedan red");
            }else{
                ucitajIzBaze();
                ocistiPolja();
            }
            izraz.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_btnDodajNoviActionPerformed

    private void btn_slikaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_slikaActionPerformed

        String filename = null;

        JFileChooser chooser = new JFileChooser();

        FileNameExtensionFilter filter = new FileNameExtensionFilter(
            "JPG, GIF, and PNG Images", "jpg", "gif", "png");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            System.out.println("You chose to open this file: "
                + file.getName());
            BufferedImage image;
            try {
                image = ImageIO.read(file);

                ImageIO.write(image, "jpg",new File(putanja +file.getName()));
            } catch (IOException ex) {
                Logger.getLogger(SaveImageFile.class.getName()).log(Level.SEVERE, null, ex);
            }

            //       chooser.showOpenDialog(null);
            //       File f = chooser.getSelectedFile();
            filename = file.getAbsolutePath();
            ImageIcon imageIcon = new ImageIcon(new ImageIcon(filename).getImage().getScaledInstance(lbl_img.getWidth(),lbl_img.getHeight(),Image.SCALE_SMOOTH));
            lbl_img.setIcon(imageIcon);
            lbl_Path.setText(filename);

        }
    }//GEN-LAST:event_btn_slikaActionPerformed

    private void btnPlaymeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPlaymeActionPerformed
        pustaj();
    }//GEN-LAST:event_btnPlaymeActionPerformed

    private void btn_DownPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_DownPActionPerformed
        down();
    }//GEN-LAST:event_btn_DownPActionPerformed

    private void btn_stopmeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_stopmeActionPerformed
        stopiraj();
    }//GEN-LAST:event_btn_stopmeActionPerformed

    private void btn_nexttActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_nexttActionPerformed
        next();
    }//GEN-LAST:event_btn_nexttActionPerformed

    private void btnPreviousActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPreviousActionPerformed
        previous();
    }//GEN-LAST:event_btnPreviousActionPerformed

    private void btn_addPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_addPActionPerformed
        add();
        
    }//GEN-LAST:event_btn_addPActionPerformed

    private void btn_upPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_upPActionPerformed
        up();
    }//GEN-LAST:event_btn_upPActionPerformed

    private void btn_removePActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_removePActionPerformed
        remove();
    }//GEN-LAST:event_btn_removePActionPerformed

    private void btn_openPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_openPActionPerformed
        open();
    }//GEN-LAST:event_btn_openPActionPerformed

    private void btn_SavePActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_SavePActionPerformed
        save();
    }//GEN-LAST:event_btn_SavePActionPerformed

    private void tgl_PauzaNastaviActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tgl_PauzaNastaviActionPerformed
        if(tgl_PauzaNastavi.isSelected()){
            pauza();
            tgl_PauzaNastavi.setIcon(new ImageIcon(getClass().getResource("/images/arrow-30-24.png")));

        }else{
            nastavi();
            tgl_PauzaNastavi.setIcon(new ImageIcon(getClass().getResource("/images/pause-24.png")));

        }
    }//GEN-LAST:event_tgl_PauzaNastaviActionPerformed

    
        void setColor(JPanel panel) {
        panel.setBackground(new Color(137, 217, 249));
    }
    
    void resetColor(JPanel panel) {
        panel.setBackground(new Color(69, 202, 255));
    }
    
      void setLinkColor(JPanel panel) {
        panel.setBackground(new Color(6,69,173));
    }
      
      
         private void ocistiPolja(){
        //loše
//        txtNaziv.setText("");
//        txtTrajanje.setText("");
//        txtUpisnina.setText("");
//        txtCijena.setText("");
// staviti sve kontrole u panel
for(Component c : pnlPodaci.getComponents()){
    if (c instanceof JTextField){
        ((JTextField) c).setText("");
    }
}
    }
      

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel artist_Panel;
    private javax.swing.JPanel audio_Panel;
    private javax.swing.JPanel blank_Panel;
    private javax.swing.JPanel blank_Panel1;
    private javax.swing.JPanel blank_Panel2;
    private javax.swing.JButton btnDodajNovi;
    private javax.swing.JButton btnObrisi;
    private javax.swing.JButton btnPlayme;
    private javax.swing.JButton btnPrevious;
    private javax.swing.JButton btnPromjena;
    private javax.swing.JButton btn_DownP;
    private javax.swing.JButton btn_SaveP;
    private javax.swing.JButton btn_addP;
    private javax.swing.JPanel btn_artists;
    private javax.swing.JButton btn_dodajPlaylistu;
    private javax.swing.JPanel btn_era;
    private javax.swing.JPanel btn_git;
    private javax.swing.JPanel btn_music;
    private javax.swing.JPanel btn_nadzorna;
    private javax.swing.JButton btn_nextt;
    private javax.swing.JButton btn_openFile;
    private javax.swing.JButton btn_openP;
    private javax.swing.JPanel btn_playerPanel;
    private javax.swing.JPanel btn_postavke;
    private javax.swing.JPanel btn_programi;
    private javax.swing.JButton btn_removeP;
    private javax.swing.JButton btn_slika;
    private javax.swing.JButton btn_stopme;
    private javax.swing.JButton btn_upP;
    private javax.swing.JLabel btn_zatvori3;
    private javax.swing.JPanel era_Panel;
    private javax.swing.JPanel helloPanel;
    private javax.swing.JPanel helloPanel1;
    private javax.swing.JLabel img_link;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JComboBox<String> jCombo_zanr;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JList<String> jList_plejlist;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JTree jTree1;
    private javax.swing.JLabel label_ajde;
    private javax.swing.JLabel lblOpis;
    private javax.swing.JLabel lbl_ID;
    private javax.swing.JLabel lbl_Path;
    private javax.swing.JLabel lbl_img;
    private javax.swing.JLabel lbl_prikaziTraku;
    private javax.swing.JList<Artist> lstArtisti;
    private javax.swing.JList<Pjesme> lstPlayList;
    private javax.swing.JPanel nadzorna_Panel;
    private javax.swing.JPanel navigacijskiPanel;
    private javax.swing.JPanel pnlPodaci;
    private javax.swing.JPanel postavke_Panel;
    private javax.swing.JPanel pozadinskiPanel;
    private javax.swing.JPanel programi_Panel;
    private javax.swing.JPanel records_Panel;
    private javax.swing.JTable table23;
    private javax.swing.JToggleButton tgl_PauzaNastavi;
    private javax.swing.JTextField txtNaziv;
    private javax.swing.JTextField txtOpis;
    private javax.swing.JLabel zanr_out;
    // End of variables declaration//GEN-END:variables
 String JTreeVar;

    private void ucitajIzBaze() {
        try {
           izraz = veza.prepareStatement("select * from Artists");
           
            ResultSet rs = izraz.executeQuery();
            //šetanje kroz redove
            List<Artist> lista = new ArrayList<>();
            Artist s;
            while(rs.next()){
                //sada smo u jednom retku
                s=new Artist();
                s.setId(rs.getInt("id"));
                s.setNaziv(rs.getString("naziv"));
                s.setOpis(rs.getString("opis"));
                s.setZanrA(rs.getString("zanrA"));
              
                
                lista.add(s);
                
            }
            rs.close();
            izraz.close();
           // veza.close();
            
           //http://panthema.net/2013/sound-of-sorting/
           
            Collections.sort(lista, new Comparator<Artist>(){
                Collator col = Collator.getInstance(new Locale("hr","HR"));
                public int compare(Artist s1, Artist s2) {
                    return col.compare(s1.getNaziv(),s2.getNaziv());
                }
            });
      
            DefaultListModel<Artist> m = new DefaultListModel<>();
            /*
            for(Smjer smjer: lista){
                m.addElement(smjer);
            }
            */
            lista.forEach((smjer)->m.addElement(smjer));
            
            lstArtisti.setModel(m); 
        } catch (Exception e) {
            e.printStackTrace();
        }
         
    }

    @Override
    public void opened(Object o, Map map) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void progress(int i, long l, byte[] bytes, Map map) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void stateUpdated(BasicPlayerEvent bpe) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setController(BasicController bc) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}

