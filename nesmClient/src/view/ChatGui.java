/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.ClientController;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.logging.Level;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import model.User;

/**
 *
 * @author Eman
 */
public class ChatGui extends javax.swing.JFrame {

    User user;
    ClientController clientController;
    
    Font font = new Font(Font.SERIF,Font.PLAIN,18);
    Color color= Color.getColor("BLUE");
    /**
     * Creates new form ChatGui
     */
    public ChatGui(User user,ClientController c) {
        initComponents();
        
        this.user=user;
        this.setTitle(user.getUsername());
        clientUserNameLabel.setText(user.getUsername());
        clientStateLabel.setText(User.getStringState(user.getState()));
        clientController = c;
        
        ImageIcon logo=new ImageIcon(getClass().getResource("/view/logo.png"));
        this.setIconImage(logo.getImage());
      
        this.setVisible(true);
        
        /**
         * Adding window listener for action on close.
         */
        this.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
                
            }

            @Override
            public void windowClosing(WindowEvent e) {
                ChatGui.this.clientController.close(ChatGui.this.user.getId());
            }

            @Override
            public void windowClosed(WindowEvent e) {
                
            }

            @Override
            public void windowIconified(WindowEvent e) {
                
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
                
            }

            @Override
            public void windowActivated(WindowEvent e) {
                
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
               
            }
        });
        
        
    }
    
    /**
     * this method is used for display a message on a screen .
     * @param msg 
     */
    public void recieve(String msg)
    {
        if(msg.equals("<nudge>")){
        
         
               new PlayaudioFile(getClass().getResource("/sounds/nudge.wav"));
            
            
            }
            
        
        else{
       // String screen = clientOutputTextArea.getText();
       String screen ="  "+user.getUsername()+" : "+msg+ "\n";
        clientOutputTextArea.setFont(font);
        clientOutputTextArea.setForeground(color);
        clientOutputTextArea.append(screen);
       
        //new PlayaudioFile("src\\sounds\\new_message.wav");
        System.out.println(getClass().getResource("/view/logo.png").getPath()+" File is"+getClass().getResource("/sounds/new_message.wav").getFile());
        new PlayaudioFile(getClass().getResource("/sounds/new_message.wav"));
        }
    }
    
     public void recieveFile(byte[] file, String fileName) {
        // String screen = clientOutputTextArea.getText();
        clientOutputTextArea.append(user.getUsername() + " : send " + fileName + "\n");
        int option = JOptionPane.showConfirmDialog(this, user.getUsername() + " send " + fileName + "\n" + "Do you want to save this file?");
        switch (option) {
            case 0:
                JFileChooser fC = new JFileChooser();
                fC.setSelectedFile(new File(fileName));
                if (fC.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                    String path = fC.getSelectedFile().getPath();
                    try {

                        FileOutputStream fos = new FileOutputStream(path);
                        fos.flush();

                        fos.write(file);
                        fos.close();
                    } catch (Exception e) {
                    }

                }
                break;
            case 1:
                break;
            case 2:
                break;

        }

    }

    public void recieveImage(byte[] image, String imageName) {
        // String screen = clientOutputTextArea.getText();
        clientOutputTextArea.append(user.getUsername() + " : send " + imageName + "\n");
        int option = JOptionPane.showConfirmDialog(this, user.getUsername() + " send " + imageName + "\n" + "Do you want to save this file?");
        switch (option) {
            case 0:
                JFileChooser fC = new JFileChooser();
                fC.setSelectedFile(new File(imageName));
                if (fC.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                    String path = fC.getSelectedFile().getPath();
                    try {

                        FileOutputStream fos = new FileOutputStream(path);
                        fos.flush();

                        fos.write(image);
                        fos.close();
                    } catch (Exception e) {
                    }

                }
                break;
            case 1:
                break;
            case 2:
                break;

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

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        clientOutputTextArea = new javax.swing.JTextArea();
        clientUserNameLabel = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        sendButton = new javax.swing.JButton();
        photoButton = new javax.swing.JButton();
        fileButton = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        clientInputTextField = new javax.swing.JTextField();
        clientStateLabel = new javax.swing.JLabel();
        nudgeButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(52, 152, 219));
        jPanel1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jPanel1KeyPressed(evt);
            }
        });

        clientOutputTextArea.setEditable(false);
        clientOutputTextArea.setColumns(20);
        clientOutputTextArea.setRows(5);
        clientOutputTextArea.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        jScrollPane1.setViewportView(clientOutputTextArea);

        clientUserNameLabel.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        clientUserNameLabel.setForeground(new java.awt.Color(255, 255, 255));
        clientUserNameLabel.setText("Eman Kamal");

        sendButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/send .png"))); // NOI18N
        sendButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendButtonActionPerformed(evt);
            }
        });

        photoButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/photo.png"))); // NOI18N
        photoButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                photoButtonActionPerformed(evt);
            }
        });

        fileButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/file.png"))); // NOI18N
        fileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fileButtonActionPerformed(evt);
            }
        });

        clientInputTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                clientInputTextFieldKeyReleased(evt);
            }
        });
        jScrollPane2.setViewportView(clientInputTextField);

        clientStateLabel.setForeground(new java.awt.Color(255, 255, 255));
        clientStateLabel.setText("available");

        nudgeButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/nudge.png"))); // NOI18N
        nudgeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nudgeButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(clientUserNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(295, 295, 295))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 349, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(sendButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(photoButton, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(fileButton, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(nudgeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jSeparator3)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addComponent(clientStateLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator2)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(clientUserNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(clientStateLabel)
                    .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 32, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(sendButton, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(photoButton)
                                    .addComponent(fileButton)
                                    .addComponent(nudgeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(47, 47, 47))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void sendButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendButtonActionPerformed
        String msg;
        synchronized(this){
        String screen = clientOutputTextArea.getText();
        msg = clientInputTextField.getText();
        clientOutputTextArea.setText(screen+"  Me : "+msg+ "\n");
        clientInputTextField.setText(" ");
        }
      
       clientController.sendMessage(user.getId() , msg);
        
    }//GEN-LAST:event_sendButtonActionPerformed

    private void photoButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_photoButtonActionPerformed
          JFileChooser fM = new JFileChooser();
        FileNameExtensionFilter imageFilter = new FileNameExtensionFilter("images ", "jpg", "png", "gif", "jpeg");
        fM.setFileFilter(imageFilter);
        if (fM.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {

            String path = fM.getSelectedFile().getPath();
            String name = fM.getSelectedFile().getName();

            System.out.println(path);
            try {

                FileInputStream fi = new FileInputStream(path);
                int size = fi.available();
                byte[] b = new byte[size];
                fi.read(b);
                clientController.sendImage(user.getId(), b, name);
                fi.close();
            } catch (Exception e) {

            }
        }
    }//GEN-LAST:event_photoButtonActionPerformed

    private void jPanel1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jPanel1KeyPressed
       
        if(evt.getKeyCode()==KeyEvent.VK_ENTER)
        sendButton.doClick();
    }//GEN-LAST:event_jPanel1KeyPressed

    private void clientInputTextFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_clientInputTextFieldKeyReleased
        // TODO add your handling code here:
        if(evt.getKeyCode()==KeyEvent.VK_ENTER || evt.getKeyCode()==KeyEvent.VK_ACCEPT){
            sendButton.doClick();
        }
    }//GEN-LAST:event_clientInputTextFieldKeyReleased

    private void nudgeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nudgeButtonActionPerformed
       
        clientController.sendMessage(user.getId() ,"<nudge>");
    }//GEN-LAST:event_nudgeButtonActionPerformed

    private void fileButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fileButtonActionPerformed
        FileThread f = new FileThread();
    }//GEN-LAST:event_fileButtonActionPerformed
public void updateState(String State){

    clientStateLabel.setText(State);
}
    
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField clientInputTextField;
    private javax.swing.JTextArea clientOutputTextArea;
    private javax.swing.JLabel clientStateLabel;
    private javax.swing.JLabel clientUserNameLabel;
    private javax.swing.JButton fileButton;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JButton nudgeButton;
    private javax.swing.JButton photoButton;
    private javax.swing.JButton sendButton;
    // End of variables declaration//GEN-END:variables

class FileThread extends Thread
{

        public FileThread() {
        
            this.start();
        }
   
    
    
   @Override
   public void run(){
   
        JFileChooser fC = new JFileChooser();
        FileNameExtensionFilter txtFilter = new FileNameExtensionFilter("files ", "txt", "pdf","ZIP","RAR","mp4","mp3","pptx","docx");
        fC.setFileFilter(txtFilter);
        if (fC.showOpenDialog(ChatGui.this) == JFileChooser.APPROVE_OPTION) {

            String path = fC.getSelectedFile().getPath();
            String name = fC.getSelectedFile().getName();

            System.out.println(path);
            try {

                FileInputStream fis = new FileInputStream(path);
                int size = fis.available();
                byte[] b = new byte[size];
                fis.read(b);
                clientController.sendFile(user.getId(), b, name);
                fis.close();
            } catch (Exception e) {

            }
        } 
   
   }
   
}





}
