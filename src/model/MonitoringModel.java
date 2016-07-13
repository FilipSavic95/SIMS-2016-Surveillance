package model;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Timer;

import view.CameraGUI;
import view.MonitoringView;
import controler.Observer;
import controler.Subject;

public class MonitoringModel implements Subject, ActionListener {
	ArrayList< ArrayList<Observer> > observerLists;
	
	/** Referenca na View dio sablona. */ 
	private MonitoringView mv;
	
	public void setSD (MonitoringView mView) {
		this.mv = mView;
	}
	
	/** Svakih DELAY[ms] tajmer 'zvoni'.*/
	private final int DELAY = 500;
	
	private Timer timer;
	
	/** Vrijeme pokretanja tajmera. */
	private static long timerStarted;
	
	/** Zaokruzeno vrijeme okidanja tajmera. */
	private static long roundTime;

	private void initTimer() {
		timer = new Timer(DELAY, this);
		timer.start();
		timerStarted = System.currentTimeMillis();
		System.out.println("staro: " + timerStarted);
		timerStarted = timerStarted - (timerStarted % DELAY); // DODAJ JOS + DELAY ako bude falilo !
		System.out.println("novo:  " + timerStarted);
	}

	public Timer getTimer() {
		return timer;
	}
	
	public void stopTimer()
	{
		timer.stop();
	}
	
	private long otkucaj; // broj otkucaja
	private final Object MUTEX = new Object();
	
	public MonitoringModel() {
		observerLists = new ArrayList<ArrayList<Observer>>();
		
		this.observerLists.add(new ArrayList<Observer>());
		this.observerLists.add(new ArrayList<Observer>());
		this.observerLists.add(new ArrayList<Observer>());
		
		initTimer(); // pokrece tajmer
	}
	
	/** Dodavanje kamere u liste:
	 * <ul>
	 * <li> za upravljanje,</li>
	 * <li> za prikaz. </li>
	 * </ul>
	 * @param obj - posmatrač koji se dodaje,
	 * @param queueNo - indeks reda u koji se dodaje (== brzina-1).
	 */
	public void register(Observer obj, int queueNo) {
		if (obj == null) throw new NullPointerException("Null Observer");
		
		synchronized (MUTEX) {
			if(!observerLists.get(queueNo).contains(obj))
				observerLists.get(queueNo).add(obj);
		}
		obj.setSubject(this);
	}

	public void unregister(Observer obj, int queueNo) {
		synchronized (MUTEX) {
			observerLists.get(queueNo).remove(obj);
		}
	}

	public void notifyObservers() {
		List<Observer> fastCams = null;		// brzina = 1
		List<Observer> mediumCams = null;	// brzina = 2
		List<Observer> slowCams = null;		// brzina = 3
		
		/**
		 * Zaključavamo da bismo obavijestili samo one
		 * koji su se registrovali prije pocetka notify-a.
		 */
		synchronized (MUTEX) {
			// izbačena provjera otkucaj == -1 ..
			
			//	if (otkucaj % 1 == 0) // svaki otkucaj
					fastCams = new ArrayList<Observer>(this.observerLists.get(0));
				if (otkucaj % 2 == 0) // svaki drugi otkucaj
					mediumCams = new ArrayList<Observer>(this.observerLists.get(1));
				if (otkucaj % 3 == 0) // svaki treci otkucaj
					slowCams = new ArrayList<Observer>(this.observerLists.get(2));
			}
			// obavijestimo odgovarajuće posmatrače
			for (Observer obj : fastCams)
				// kada kamere budu mogle biti isključene, samo
				// dodamo   =====|||   if ( ! iskljucena )   |||=====
				obj.update();
			
			if (mediumCams != null)
				for (Observer obj : mediumCams)
					obj.update();
			
			if (slowCams != null)
				for (Observer obj : slowCams)
					obj.update();
			
			otkucaj = -1; // ovo ce najvjerovatnije biti izbaceno.... !! {#$%!@%?
		}

	public Object getUpdate(Observer obj) {
		return this.otkucaj;
	}
	
	// Tajmer "zvoni" govoreci koji put je otkucao.
	public void ring(long tickNo) {
		this.otkucaj = tickNo;
		notifyObservers();
		// Obavještavanje View-a
		mv.reactToTimer();
	}
	
	/** Reakcija na okidanje tajmera. */
	public void actionPerformed(ActionEvent e) {
		// Izračunavanje proteklog vremena. Sad vidim da je moglo samo 
		// da se krene od otkucaj = 0, pa "otkucaj += 1"..možda kasnije zamijenim
		roundTime = e.getWhen() - (e.getWhen() % DELAY);
		long result = (long) Math.ceil((roundTime - timerStarted) / DELAY);
		System.out.println("rezultat: " + result);
		ring(result);
	}

}
