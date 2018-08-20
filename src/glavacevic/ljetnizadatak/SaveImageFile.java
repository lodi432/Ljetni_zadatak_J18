/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package glavacevic.ljetnizadatak;

/**
 *
 * @author domagoj
 */
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;

public class SaveImageFile {

    public SaveImageFile() {
        

        
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
                        ImageIO.write(image, "jpg",new File("/home/domagoj/Dpath/" + file.getName()));
                    } catch (IOException ex) {
                        Logger.getLogger(SaveImageFile.class.getName()).log(Level.SEVERE, null, ex);
                    }             
                }
            }
       
        
    }

    
