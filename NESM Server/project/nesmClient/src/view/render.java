
package view;

import java.awt.Color;
import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import model.User;


public class render extends DefaultListCellRenderer implements ListCellRenderer<Object>{

    @Override
    public Component getListCellRendererComponent(JList<? extends Object> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
       User user=(User)value;
       setText("<html> <h3 style=\"font-weight:bold; padding-left:5px; color:rgb(148,205,244);\">"+user.getUsername()+"</h3><h5 style=\"padding-left:8px; color:grey;\">"+User.getStringState(user.getState())+"</h5></html>");
       //setText(user.getState());
       
       ImageIcon available = new ImageIcon(getClass().getResource("available.png"));
       ImageIcon busy = new ImageIcon(getClass().getResource("busy.png"));
       ImageIcon away = new ImageIcon(getClass().getResource("away.png"));
       ImageIcon offline = new ImageIcon(getClass().getResource("offline.png"));
       
       if(user.getState()==User.Available)
       {
       setIcon(available);
       }
       else if(user.getState()==User.Busy)
       {
        setIcon(busy);   
       }
       else if(user.getState()==User.Away)
       {
        setIcon(away);   
       }
       else{
         setIcon(offline);
       }
       
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
