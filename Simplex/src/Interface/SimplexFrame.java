package Interface;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import pack01.Conversor;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

public class SimplexFrame extends JFrame {
	
	public Conversor conv = new Conversor();
	
	private JPanel contentPane;

	public static void main(String[] args) {
		for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
            if ("Windows".equals(info.getName())) {       
            	try {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (UnsupportedLookAndFeelException e) {
					e.printStackTrace();
				}
            }
	    }
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SimplexFrame frame = new SimplexFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public SimplexFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1220, 374);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblSimplex = new JLabel("Simplex");
		lblSimplex.setHorizontalAlignment(SwingConstants.CENTER);
		lblSimplex.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblSimplex.setBounds(10, 0, 1064, 28);
		contentPane.add(lblSimplex);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 54, 264, 271);
		contentPane.add(scrollPane);
		
		JTextArea modeloTextArea = new JTextArea();
		scrollPane.setViewportView(modeloTextArea);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(291, 54, 903, 271);
		contentPane.add(scrollPane_1);
		
		JTextArea tabelasTextArea = new JTextArea();
		scrollPane_1.setViewportView(tabelasTextArea);
		tabelasTextArea.setEditable(false);
		
		JLabel lblModelo = new JLabel("Modelo");
		lblModelo.setBounds(10, 29, 46, 14);
		contentPane.add(lblModelo);
		
		JLabel lblTabelas = new JLabel("Tabelas");
		lblTabelas.setBounds(291, 29, 366, 14);
		contentPane.add(lblTabelas);
		
		JButton btnCalcular = new JButton("Calcular");
		btnCalcular.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(tabelasTextArea.getText().isEmpty() == false) tabelasTextArea.setText(null);
				if(modeloTextArea.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Informe um modelo");
					modeloTextArea.setText("3+5+0+0+0+0\n1+0+1+0+0+4\n0+2+0+1+0+12\n3+2+0+0+1+18");
					return;
				}	
				String [] str = modeloTextArea.getText().split("\n");
				ArrayList<String> fos = new ArrayList<String>();				
				for(int i = 0; i < str.length; i++)
					fos.add(str[i]);
				tabelasTextArea.setText(conv.passoN(fos));				
			}
		});
		btnCalcular.setBounds(203, 25, 71, 23);
		contentPane.add(btnCalcular);		
	}	
}
//3+5+0+0+0+0
//1+0+1+0+0+4
//0+2+0+1+0+12
//3+2+0+0+1+18