package grafica;

import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.UIManager;

import razionali.*;
import java.util.*;
import javax.swing.*;

import java.awt.event.*;

public class PannelloGrafico {
	private JFrame frame;
	private JTextField[][] matriceA;
	private JTextField[][] matriceB;
	private JButton[] bottoni;
	private JLabel messaggio;
	private String titoloFinestra;
	private int numeroRigheA;
	private int numeroColonneA;
	private int numeroRigheB;
	private int numeroColonneB;
	private int numeroBottoni;

	private String[][] convertiDaMatriceRazionale(Razionale[][] m) {
		int nRighe = m.length;
		int nColonne = m[0].length;
		String[][] ret = new String[nRighe][nColonne];
		for (int i = 0; i < nRighe; i++)
			for (int j = 0; j < nColonne; j++)
				ret[i][j] = "" + m[i][j];
		return ret;
	}

	private Razionale[][] convertiDaMatriceStringa(String[][] m) {
		if (m.length == 0 || m[0].length == 0)
			throw new IllegalArgumentException("Valore non valido");
		Razionale[][] ret = new Razionale[m.length][m[0].length];
		for (int i = 0; i < m.length; i++)
			for (int j = 0; j < m[0].length; j++)
				if (m[i][j].equals(""))
					ret[i][j] = new Razionale(0, 1);
				else
					ret[i][j] = Razionale.parseRaz(m[i][j]);

		return ret;
	}

	private void Somma() {
		String[][] A = getMatriceA();
		String[][] B = getMatriceB();
		Razionale[][] e = new Razionale[A.length][A[0].length];
		Razionale[][] d = new Razionale[B.length][B[0].length];
		e = convertiDaMatriceStringa(A);
		d = convertiDaMatriceStringa(B);
		for (int c = 0; c < numeroRigheA; c++) {
			for (int g = 0; g < numeroColonneA; g++) {
				d[c][g] = Razionale.somma(e[c][g], d[c][g]);
			}

		}
		B = convertiDaMatriceRazionale(d);
		setMatriceB(B);
	}

	private void Sottrazione() {
		String[][] A = getMatriceA();
		String[][] B = getMatriceB();
		Razionale[][] e = new Razionale[A.length][A[0].length];
		Razionale[][] d = new Razionale[B.length][B[0].length];
		e = convertiDaMatriceStringa(A);
		d = convertiDaMatriceStringa(B);
		for (int c = 0; c < numeroRigheA; c++) {
			for (int g = 0; g < numeroRigheA; g++) {
				d[c][g] = Razionale.differenza(e[c][g], d[c][g]);
			}
		}
		B = convertiDaMatriceRazionale(d);
		setMatriceB(B);
	}

	private void Moltiplicazione() {

		String[][] A = getMatriceA();
		String[][] B = getMatriceB();
		Razionale[][] f = new Razionale[A.length][A[0].length];
		Razionale[][] e = new Razionale[A.length][A[0].length];
		Razionale[][] d = new Razionale[B.length][B[0].length];
		e = convertiDaMatriceStringa(A);
		d = convertiDaMatriceStringa(B);
		Razionale prodotto = new Razionale(1, 1);
		Razionale somma = new Razionale(0, 1);
		for (int i = 0; i < numeroRigheA; i++) {
			for (int j = 0; j < numeroRigheA; j++) {
				for (int k = 0; k < numeroRigheA; k++) {
					prodotto = Razionale.prodotto(e[i][k], d[k][j]);
					somma = Razionale.somma(somma, prodotto);
				}
				f[i][j] = somma;
				prodotto = new Razionale(1, 1);
				somma = new Razionale(0, 1);
			}
		}
		B = convertiDaMatriceRazionale(f);
		setMatriceB(B);
	}

	private void Trasposta() {

		String[][] A = getMatriceA();
		String[][] B = getMatriceB();
		Razionale[][] e = new Razionale[A.length][A[0].length];
		Razionale[][] d = new Razionale[B.length][B[0].length];
		e = convertiDaMatriceStringa(A);
		d = convertiDaMatriceStringa(B);

		for (int i = 0; i < numeroRigheA; i++) {
			for (int j = 0; j < numeroRigheA; j++) {
				d[j][i] = e[i][j];
			}
		}
		B = convertiDaMatriceRazionale(d);
		setMatriceB(B);

	}

	private Razionale DeterminanteSarrus(Razionale[][] b) {

		Razionale a = new Razionale(0, 1);
		Razionale c = new Razionale(0, 1);
		Razionale d = new Razionale(0, 1);
		Razionale e = new Razionale(0, 1);
		Razionale f = new Razionale(0, 1);
		a = Razionale.prodotto(b[2][2], Razionale.prodotto(b[0][0], b[1][1]));
		c = Razionale.prodotto(b[0][1], Razionale.prodotto(b[1][2], b[2][0]));
		d = Razionale.somma(a, Razionale.somma(c, Razionale.prodotto(b[0][2], Razionale.prodotto(b[1][0], b[2][1]))));
		e = Razionale.prodotto(b[0][1], Razionale.prodotto(b[1][0], b[2][2]));
		f = Razionale.prodotto(b[0][0], Razionale.prodotto(b[1][2], b[2][1]));
		d = Razionale.differenza(d, e);
		d = Razionale.differenza(d, f);
		d = Razionale.differenza(d, Razionale.prodotto(b[0][2], Razionale.prodotto(b[1][1], b[2][0])));

		return d;
	}

	private Razionale Determinante(int n, String[][] A) {
		Razionale det1 = new Razionale(0, 1);
		Razionale det = new Razionale(0, 1);
		Razionale[][] b = convertiDaMatriceStringa(A);
		Razionale[][] t = new Razionale[b.length - 1][b[0].length - 1];

		if (b.length != b[0].length)
			throw new IllegalArgumentException("La regione scelta non è quadrata");
		if (b.length == 1 && b[0].length == 1) {
			return det = b[0][0];

		}
		if (b.length == 2 && b[0].length == 2) {
			return det = (Razionale.differenza(Razionale.prodotto(b[0][0], b[1][1]),
					Razionale.prodotto(b[0][1], b[1][0])));
		}
		if (b.length == 3 && b[0].length == 3) {

			det = DeterminanteSarrus(b);
		} else
			for (int c = 0; c < n; c++) { // matrice dei minori
				int im = 0;
				for (int i = 1; i < n; i++) {
					int jm = 0;

					for (int j = 0; j < n; j++) {

						if (c == j)
							continue;// salta
						t[im][jm] = b[i][j];
						jm++;

					}
					im++;

				}
				String[][] Y = new String[t.length][t[0].length];
				Y = convertiDaMatriceRazionale(t);
				Razionale f = Razionale.eleva(new Razionale(-1, 1), c);

				det1 = Razionale.prodotto(f, Razionale.prodotto(b[0][c], Determinante(n - 1, Y)));
				det = Razionale.somma(det, det1);
				f = new Razionale(0, 1);
			}
		return det;

	}

	private Razionale DeterminanteA() {
		String[][] C = getMatriceA();
		int n = C.length;

		return Determinante(n, C);
		
	}

	/* FINE PARTE DA PERSONALIZZARE */
	public PannelloGrafico() { /* INIZIO PARTE DA PERSONALIZZARE */
		titoloFinestra = "Algebra Lineare";
		numeroRigheA = 10;
		numeroColonneA = 10;
		numeroRigheB = 10;
		numeroColonneB = 10;
		numeroBottoni = 6;
		/* FINE PARTE DA PERSONALIZZARE */

		inizializza();
	}

	/* INIZIO PARTE DA PERSONALIZZARE */
	private void bottonePremuto(int numeroBottone) {

		if (numeroBottone == 1)
			Somma();
		if (numeroBottone == 2)
			Sottrazione();
		if (numeroBottone == 3)
			Moltiplicazione();
		if (numeroBottone == 4) {
			Scanner scanner = new Scanner(System.in);
			System.out.println("Numero di righe");
			int righe = scanner.nextInt();
			System.out.println("Numero di colonne");
			int colonne = scanner.nextInt();
			numeroRigheA = righe;
			numeroColonneA = colonne;

			setMessaggio("Il determinante è " + DeterminanteA());
			System.out.println("Il determinante è " + DeterminanteA());
			numeroRigheA = 10;
			numeroColonneA = 10;

		}
		if (numeroBottone == 5) {
			String[][] Vuoto = new String[10][10];
			setMatriceA(Vuoto);
			setMatriceB(Vuoto);
			setMessaggio("Matrici Pulite!");
		}
		if (numeroBottone == 6) {
			Trasposta();
		}
		
	}

	/* FINE PARTE DA PERSONALIZZARE */

	public static void main(String[] args) {
		PannelloGrafico p = new PannelloGrafico();
		/* INIZIO PARTE DA PERSONALIZZARE */
		p.setEtichettaBottone(1, "Somma");
		p.setEtichettaBottone(2, "Sottrazione");
		p.setEtichettaBottone(3, "Moltiplicazione");
		p.setEtichettaBottone(4, "Determinante");
		p.setEtichettaBottone(5, "Pulisci Matrici");
		p.setEtichettaBottone(6, "Trasposta");
	

		/* FINE PARTE DA PERSONALIZZARE */
	}

	private void setMessaggio(String m) {
		messaggio.setText(m);
	}

	private void setEtichettaBottone(int numeroBottone, String etichetta) {
		bottoni[numeroBottone - 1].setText(etichetta);
	}

	private String[][] getMatriceA() {
		String[][] ret = new String[numeroRigheA][numeroColonneA];
		for (int i = 0; i < numeroRigheA; i++)
			for (int j = 0; j < numeroColonneA; j++)
				ret[i][j] = matriceA[i][j].getText();
		return ret;
	}

	private String[][] getMatriceB() {
		String[][] ret = new String[numeroRigheB][numeroColonneB];
		for (int i = 0; i < numeroRigheB; i++)
			for (int j = 0; j < numeroColonneB; j++)
				ret[i][j] = matriceB[i][j].getText();
		return ret;
	}

	private void setMatriceA(String[][] A) {
		for (int i = 0; i < numeroRigheA; i++)
			for (int j = 0; j < numeroColonneA; j++)
				matriceA[i][j].setText(A[i][j]);
	}

	private void setMatriceB(String[][] B) {
		for (int i = 0; i < numeroRigheB; i++)
			for (int j = 0; j < numeroColonneB; j++)
				matriceB[i][j].setText(B[i][j]);
	}

	private void bloccaMatriceA() {
		for (int i = 0; i < numeroRigheA; i++)
			for (int j = 0; j < numeroColonneA; j++)
				matriceA[i][j].setEditable(false);
		;
	}

	private void sbloccaMatriceA() {
		for (int i = 0; i < numeroRigheA; i++)
			for (int j = 0; j < numeroColonneA; j++)
				matriceA[i][j].setEditable(true);
		;
	}

	private void bloccaMatriceB() {
		for (int i = 0; i < numeroRigheB; i++)
			for (int j = 0; j < numeroColonneB; j++)
				matriceB[i][j].setEditable(false);
		;
	}

	private void sbloccaMatriceB() {
		for (int i = 0; i < numeroRigheB; i++)
			for (int j = 0; j < numeroColonneB; j++)
				matriceB[i][j].setEditable(true);
		;
	}

	private int[][] convertiInMatriceIntera(String[][] m) {
		int nRighe = m.length;
		int nColonne = m[0].length;
		int[][] ret = new int[nRighe][nColonne];
		for (int i = 0; i < nRighe; i++)
			for (int j = 0; j < nColonne; j++)
				if (m[i][j].equals(""))
					ret[i][j] = 0;
				else
					ret[i][j] = Integer.parseInt(m[i][j]);
		return ret;
	}

	private String[][] convertiDaMatriceIntera(int[][] m) {
		int nRighe = m.length;
		int nColonne = m[0].length;
		String[][] ret = new String[nRighe][nColonne];
		for (int i = 0; i < nRighe; i++)
			for (int j = 0; j < nColonne; j++)
				ret[i][j] = "" + m[i][j];
		return ret;
	}

	private void inizializza() {
		frame = new JFrame();
		frame.getContentPane().setLayout(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			System.out.println(e);
			System.exit(0);
		}
		frame.setVisible(true);
		frame.setBounds(100, 100, 780, 360 + 30 * numeroBottoni);
		frame.setTitle(titoloFinestra);

		messaggio = new JLabel("");
		frame.getContentPane().add(messaggio);
		messaggio.setBounds(240, 270, 300, 30);
		messaggio.setHorizontalAlignment(JLabel.CENTER);

		bottoni = new JButton[numeroBottoni];
		ActionListener listener = new PressioneBottoni();
		for (int i = 0; i < numeroBottoni; i++) {
			JButton bottone = new JButton();
			if (i < 3) {
				bottone.setBounds(50, 300 + 30 * i, 300, 30);
				bottoni[i] = bottone;
				bottone.addActionListener(listener);
				frame.getContentPane().add(bottone);
			} else {
				bottone.setBounds(430, 210 + 30 * i, 300, 30);
				bottoni[i] = bottone;
				bottone.addActionListener(listener);
				frame.getContentPane().add(bottone);
			}
		}

		matriceA = new JTextField[numeroRigheA][numeroColonneA];
		for (int i = 0; i < numeroRigheA; i++)
			for (int j = 0; j < numeroColonneA; j++) {
				JTextField campoTesto = new JTextField("");
				frame.getContentPane().add(campoTesto);
				campoTesto.setBounds(50 + 30 * j, 60 + 20 * i, 30, 20);
				campoTesto.setHorizontalAlignment(JTextField.CENTER);
				matriceA[i][j] = campoTesto;

			}
		for (int i = 0; i < numeroRigheA; i++) {
			JLabel numeroRigaA = new JLabel("" + i);
			frame.getContentPane().add(numeroRigaA);
			numeroRigaA.setBounds(30, 60 + 20 * i, 30, 20);
		}

		for (int j = 0; j < numeroColonneA; j++) {
			JLabel numeroColonnaA = new JLabel("" + j);
			frame.getContentPane().add(numeroColonnaA);
			numeroColonnaA.setBounds(60 + 30 * j, 40, 30, 20);
		}

		if (numeroRigheB != 0 || numeroColonneB != 0) {
			matriceB = new JTextField[numeroRigheB][numeroColonneB];
			for (int i = 0; i < numeroRigheB; i++)
				for (int j = 0; j < numeroColonneB; j++) {
					JTextField campoTesto = new JTextField("");
					frame.getContentPane().add(campoTesto);
					campoTesto.setBounds(430 + 30 * j, 60 + 20 * i, 30, 20);
					campoTesto.setHorizontalAlignment(JTextField.CENTER);
					matriceB[i][j] = campoTesto;
				}
			for (int i = 0; i < numeroRigheB; i++) {
				JLabel numeroRigaB = new JLabel("" + i);
				frame.getContentPane().add(numeroRigaB);
				numeroRigaB.setBounds(410, 60 + 20 * i, 30, 20);
			}
			for (int j = 0; j < numeroColonneB; j++) {
				JLabel numeroColonnaB = new JLabel("" + j);
				frame.getContentPane().add(numeroColonnaB);
				numeroColonnaB.setBounds(440 + 30 * j, 40, 30, 20);

			}

		}

	}

	private class PressioneBottoni implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			int numeroBottonePremuto = -1;
			for (int i = 0; i < numeroBottoni; i++)
				if (e.getSource() == bottoni[i])
					numeroBottonePremuto = i + 1;
			bottonePremuto(numeroBottonePremuto);
		}
	}
}
