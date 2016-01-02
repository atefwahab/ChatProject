/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.Color;
import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import model.User;

/**
 *
 * @author Eman
 */
public class RequestRender extends DefaultListCellRenderer implements ListCellRenderer<Object>{

    @Override
    public Component getListCellRendererComponent(JList<? extends Object> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
       User user=(User)value;
       setText("<html> <h3 style=\"font-weight:bold; padding-left:5px; color:rgb(148,205,244);\">"+user.getUsername()+"</h3></html>");
       //setText(user.getState());
       
       ImageIcon request = new ImageIcon(getClass().getResource("request.png"));
       setIcon(request);
       
       if(isSelected)
       {
           setBackground(new Color(240,240,240));
           setForeground(list.getSelectionForeground());
       }
       else
       {
           setBackground(list.getBackground());
           setForeground(list.getForeground());
       }
       
        setEnabled(true);
        setFont(list.getFont());
        
        return this;
    
    
    
    
    }
    
}
