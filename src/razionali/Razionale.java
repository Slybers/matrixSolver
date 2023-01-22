package razionali;

// I seguenti import servono per il programma di prova 

import java.io.Serializable;
import java.util.Scanner;

/**
 * I numeri razionali. Sono dotati delle principali funzioni dei razionali, tra
 * le quali somma, differenza, rapporto, reciproco, etc.
 */
public class Razionale implements Comparable, Cloneable, Serializable {

	private static Scanner sc = new Scanner(System.in);

	/**
	 * Il numero viene rappresentato mediante una coppia di interi.
	 */
	private int num, den;

	/**
	 * Costruisce un razionale a partire da due interi.
	 * 
	 * @param n
	 *            sara' il numeratore del razionale
	 * @param d
	 *            sara' il denominatore del razionale. Deve essere diverso da 0.
	 */
	public Razionale(int n, int d) {

		if (d == 0) {
			System.out.println("Asserzione violata.");
			new Throwable().printStackTrace();
			System.exit(1);
		}

		if (d < 0) {
			n = -1 * n;
			d = -1 * d;
		}
		num = n;
		den = d;
		semplifica();
	}

	/**
	 * Costruisce un razionale a partire da un intero. Il denominatore vale 1,
	 * per default.
	 * 
	 * @param n
	 *            il numero intero con il quale costruiamo il razionale
	 */
	public Razionale(int n) {
		this(n, 1);
	}

	/**
	 * Costruttore di default. Genera il numero razionale 0.
	 */
	public Razionale() {
		this(0);
	}

	/**
	 * Costruttore per copia. Genera un razionale identico a quello ricevuto
	 * 
	 * @param r
	 *            il numero razionale identico a quello che vogliamo costruire
	 */
	public Razionale(Razionale r) {
		this(r.num, r.den);
	}

	/**
	 * Converte l'oggetto in una stringa.
	 * 
	 * @return una stringa che rappresenta il numero razionale
	 */
	public String toString() {
		if (den == 1)
			return "" + num;
		else
			return num + "/" + den;
	}
	

	/**
	 * Calcola il reciproco di un numero razionale diverso da 0.
	 * 
	 * @return il reciproco del proprietario del metodo
	 */
	public Razionale reciproco() {

		if (num == 0) {
			System.out.println("Asserzione violata.");
			new Throwable().printStackTrace();
			System.exit(1);
		}

		return new Razionale(den, num);
	}

	/**
	 * Moltiplica il proprietario del metodo per il razionale f.
	 * 
	 * @param f
	 *            il numero razionale da moltiplicare
	 */
	public void moltiplica(Razionale f) {
		num = num * f.num;
		den = den * f.den;
		semplifica();
		
	}

	/**
	 * Aggiunge il razionale f al proprietario del metodo.
	 * 
	 * @param f
	 *            il numero razionale da aggiungere
	 */
	public void aggiungi(Razionale f) {
		num = num * f.den + f.num * den;
		den = den * f.den;
		semplifica();
	}

	/**
	 * Sottrae il razionale f dal proprietario del metodo.
	 * 
	 * @param f
	 *            il numro razionale da sottrarre
	 */
	public void sottrai(Razionale f) {
		Razionale s = new Razionale(-1 * f.num, f.den);
		aggiungi(s);
	}

	/**
	 * Eleva ad una potenza intera il proprietario del metodo.
	 * 
	 * @param p
	 *            la potenza a cui elevare il razionale
	 */
	public void eleva(int p) {
		num = (int) Math.pow(num, p);
		den = (int) Math.pow(den, p);
		semplifica();
	}

	/**
	 * Genera un clone dell'oggetto.
	 * 
	 * @return un numero razionale uguale al proprietario del metodo
	 */
	public Object clone() {
		return new Razionale(this);
	}

	/**
	 * Genera un codice hash (intero) per l'oggetto proprietario del metodo.
	 * 
	 * @return un numero intero uguale al codice hash della stringa che rappresenta il proprietario del metodo
	 */
	
	@Override
	public int hashCode() {
		return this.toString().hashCode();
	}
	
	/**
	 * Verifica se il razionale e' uguale ad un altro razionale.
	 * 
	 * @param ob
	 *            un oggetto che deve essere un numero razionale
	 * @return true, se il razionale e' uguale ad ob; false, altrimenti
	 */
	@Override
	public boolean equals(Object ob) {

		if (!(ob instanceof Razionale)) {
			System.out.println("Asserzione violata.");
			new Throwable().printStackTrace();
			System.exit(1);
		}

		Razionale r = (Razionale) ob;
		return num == r.num && den == r.den;
	}

	/**
	 * Calcola il massimo comun divisore di due numeri.
	 * 
	 * @param a
	 *            uno dei numeri di cui calcolare il massimo comun divisore
	 * @param b
	 *            l'altro numero
	 * @return il massimo comun divisore di a e b
	 */
	private int mcd(int a, int b) {
		int min = a;
		boolean trovato = false;
		if (a > b)
			min = b;
		while (!trovato) {
			if ((a % min == 0) && (b % min == 0))
				trovato = true;
			else
				min--;
		}
		return min;
	}


	/**
	 * Semplifica numeratore e denominatore.
	 */
	private void semplifica() {
		
		if (num == 0)
			den = 1;
		else {
			int divisore = mcd(Math.abs(num), den);
			num = num / divisore;
			den = den / divisore;
		}
	}

	/**
	 * Confronta questo razionale (this) con il razionale ob.
	 * 
	 * @param ob
	 *            l'oggetto da confrontare con this
	 * @return un intero negativo, zero, o un intero positivo, se questo oggetto
	 *         e' piu' piccolo, uguale, o maggiore di ob, rispettivamente.
	 */
	public int compareTo(Object ob) {
		Razionale r = (Razionale) ob;
		return num * r.den - r.num * den;
	}

	// ============= METODI STATICI DELLA CLASSE RAZIONALE ==========

	/**
	 * Legge da input (mediante Console) un numero razionale.
	 * 
	 * @return il numero razionale letto da input
	 */
	public static Razionale leggi() {
		System.out.println("Numeratore = ");
		int n = sc.nextInt();
		System.out.println("Denominatore = ");
		int d = sc.nextInt();
		while (d == 0) {
			System.out.println("Il numeratore deve essere diverso da 0");
			System.out.println("Denominatore = ");
			d = sc.nextInt();
		}
		return new Razionale(n, d);
	}

	/**
	 * Trasforma una stringa in un numero razionale.
	 * 
	 * @param s
	 *            una stringa
	 * @return il numero razionale rappresentato dalla stringa s
	 */
	public static Razionale parseRaz(String s) {
		
		if (s == null)
			return null;
		int indDiv = s.indexOf('/');
		int n = 0, d = 1;
		String snum = null, sden = null;
		boolean mancaNum = false, mancaDen = false, errNum = false, errDen = false, denZero = false;

		if (indDiv > -1) // Caso in cui c'e' un denominatore
		{
			if (indDiv == 0)
				mancaNum = true;
			else
				snum = s.substring(0, indDiv);

			try {
				if (indDiv == s.length() - 1)
					mancaDen = true;
				else {
					sden = s.substring(indDiv + 1);
					d = Integer.parseInt(sden);
				}
				if (d == 0)
					denZero = true;
			} catch (NumberFormatException e) {
				errDen = true;
			}
		} else
			snum = s; // Caso in cui non e' presente un denominatore

		try {
			n = Integer.parseInt(snum);
		} catch (NumberFormatException e) {
			errNum = true;
		}

		// Gestiamo ogni possibile situazione anomala
		// lanciando una opportuna eccezione di tipo BadlyFormedRazException
		// Si noti che precedenti eventuali eccezioni vengono catturate
		// e gestite da noi e non causano quindi l'interruzione del metodo.
		if (errNum || errDen || denZero || mancaDen || mancaNum) {
			String descrizione = null, separatore;
			if (mancaNum)
				descrizione = "Numerator Expected";
			else if (errNum)
				descrizione = "Invalid Numerator";

			if (descrizione != null)
				separatore = " and ";
			else {
				descrizione = "";
				separatore = "";
			}

			if (mancaDen)
				descrizione = descrizione + separatore + "Denominator Expected";
			else if (errDen)
				descrizione = descrizione + separatore + "Invalid Denominator";
			else if (denZero)
				descrizione = descrizione + separatore + "Null Denominator";

			throw new BadlyFormedRazException(descrizione, n, d);
		}

		// Se arriviamo fin qui, il razionale � corretto!
		return new Razionale(n, d);
	}

	/**
	 * Calcola la somma di due razionali.
	 * 
	 * @param r1
	 *            un razionale
	 * @param r2
	 *            un razionale
	 * @return la somma di r1 ed r2
	 */
	public static Razionale somma(Razionale r1, Razionale r2) {
		Razionale r3 = new Razionale(r1);
		r3.aggiungi(r2);
		return r3;
	}

	/**
	 * Calcola la differenza di due razionali.
	 * 
	 * @param r1
	 *            un razionale
	 * @param r2
	 *            un razionale
	 * @return la differenza di r1 ed r2
	 */
	public static Razionale differenza(Razionale r1, Razionale r2) {
		Razionale r3 = new Razionale(r1);
		r3.sottrai(r2);
		return r3;
	}

	/**
	 * Calcola il rapporto di due razionali.
	 * 
	 * @param r1
	 *            un razionale
	 * @param r2
	 *            un razionale diverso da 0
	 * @return il rapporto r1 ed r2
	 */
	public static Razionale rapporto(Razionale r1, Razionale r2) {
		if (r2.num == 0) {
			System.out.println("Asserzione violata.");
			new Throwable().printStackTrace();
			System.exit(1);
		}

		Razionale r3 = new Razionale(r1);
		r3.moltiplica(r2.reciproco());
		return r3;
	}

	/**
	 * Calcola il prodotto di due razionali.
	 * 
	 * @param r1
	 *            un razionale
	 * @param r2
	 *            un razionale
	 * @return il prodotto di r1 ed r2
	 */
	public static Razionale prodotto(Razionale r1, Razionale r2) {
		Razionale r3 = new Razionale(r1);
		r3.moltiplica(r2);
		return r3;
	}

	/**
	 * Calcola la potenza intera di un razionale.
	 * 
	 * @param r
	 *            un razionale
	 * @param p
	 *            la potenza intera
	 * @return r elevato p
	 */
	public static Razionale eleva(Razionale r, int p) {
		Razionale ret = new Razionale(r);
		ret.eleva(p);
		return ret;
	}
	
	
	
	// ============ Un piccolo programma di prova =================
	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);

		Razionale r1 = Razionale.leggi();
		Razionale r2 = Razionale.leggi();
		Razionale r3 = Razionale.somma(r1, r2);
		Razionale r4 = r3.reciproco();
		System.out.println("Il razionale somma e\': " + r3);
		System.out.println("Il suo reciproco e\'  : " + r4);
		if (r1.compareTo(r2) > 0)
			System.out.println(r1 + " e\' piu\' grande di " + r2);

		Razionale r5 = (Razionale) r4.clone();
		System.out.println(r5.equals(r4));
		System.out.println(r5.equals(r3));

		// Prova di parseRaz
		boolean done = false;
		Razionale r = null;

		// Esempio di recupero dall'errore con richiesta
		// di nuovo tentativo di inserimento da parte dell'utente
		while (!done) {
			try {
				System.out.println("Dammi un razionale: ");
				String sraz = sc.nextLine();
				r = Razionale.parseRaz(sraz);
				done = true;
			} catch (BadlyFormedRazException e) {
				System.out.println("Eccezione: " + e);
				System.out.println("Riprova");
			}
		}
		System.out.println("Il razionale letto e': " + r);

		// Esempio di recupero che sfrutta le informazioni
		// nell'eccezione per suggerire un valore
		try {
			System.out.println("Dammi un razionale: ");
			String sraz = sc.nextLine();
			r = Razionale.parseRaz(sraz);
		} catch (BadlyFormedRazException e) {
			System.out.println("Eccezione: " + e);
			System.out.println("Grazie alle informazioni contenute nell'eccezione,");
			System.out.println("e' possibile riparare il razionale " + e.num + '/' + e.den);
		}

	} // Fine main

} // fine class Razionale