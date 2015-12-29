
package view;

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
       setText(user.getUsername());
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
           setBackground(list.getSelectionBackground());
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