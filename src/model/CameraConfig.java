package model;

/**
 * Klasa sadrzi sve osobine kamere:
 * <ul>
 * <li> g</li>
 * <li> f</li>
 * <li> d</li>
 * <li> s</li>
 * <li> a</li>
 * </ul> 
 * @author Aleksandar
 *
 */
public class CameraConfig {
	/** Brzina okretanja - na svakih koliko otkucaja tajmera se mijenja ofset ugla. */
	public enum RotationSpeed {
	    FAST   (1),  //calls constructor with value 1
	    MEDIUM (2),  //calls constructor with value 2
	    SLOW   (3)   //calls constructor with value 3
	    ; // semicolon needed when fields / methods follow

	    public int speed;

	    private RotationSpeed(int speed) {
	        this.speed = speed;
	    }
	}
	
	/** Smjer kretanja kamere:<br> - 1 za smjer kazaljke,<br> +1 za "unazad". */
	public enum RotationDirection {
		CLOCKWISE   (-1),
		COUNTER_CLK (1);
		
		public int direction;
		
		private RotationDirection(int direction) {
			this.direction = direction;
		}		
	}
	
	/**
	 * Domet kamere (na njega uticu vremenski uslovi i vrsta kamere).
     * Predstavljen dijagonalom kvadrata - mozda ostaviti kao stranicu kvadrata,
     * jer je samo drugacije za * sqrt(2), a korisniku to ne znaci nista..
     */
    public static double sightRange = 20;
    
    /** za vise informacija..<br>
     * @see RotationSpeed */
    public RotationSpeed rotationSpeed;
    
    /** @see RotationDirection */
    public RotationDirection rotationDirection;
    
    /** Sloboda okretanja - ugao u kom pocinje opseg i sirina luka koji obuhvata; */
    public int limitStart, limitWidth;
    
    /** Trenutni ugao od kog pocinje i sirina luka koji obuhvata */
    public int sightStart, sightWidth; // uglovi u stepenima

    // trajanje pauze u krajnjim tackama - dodati   K A S N I J E
    // private double pause1, pause2;
	
	public CameraConfig() {
	}
	
	public CameraConfig(int sightStart, int sightWidth) {
		this.limitStart = 0;
		this.limitWidth = 200;
		this.sightStart = sightStart;
		this.sightWidth = sightWidth;
		this.rotationSpeed = RotationSpeed.FAST;
		this.rotationDirection = RotationDirection.COUNTER_CLK;
	}
	
	public CameraConfig(RotationSpeed rotationSpeed,
			RotationDirection rotationDirection, int limitStart,
			int limitWidth, int sightStart, int sightWidth) {
		this.limitStart = limitStart;
		this.limitWidth = limitWidth;
		// dodati provjeru za ogranicenja i vidno polje
		//  if (notValid(sightStart,sightWidth, limitStart,limitWidth) )
		//    { this.sightStart = limitStart; this.sightWidth = limitWidth/3; } // recimo...
		this.sightStart = sightStart;
		this.sightWidth = sightWidth;
		this.rotationSpeed = rotationSpeed;
		this.rotationDirection = rotationDirection;
	}

	/** Promjena smjera okretanja kamere. */
	public void turnAround() {
		if (this.rotationDirection.equals(RotationDirection.CLOCKWISE))
			this.rotationDirection = RotationDirection.COUNTER_CLK;
		
		else this.rotationDirection = RotationDirection.CLOCKWISE;
	}
	
	/**
	 * Pomijeranje kamere.<br>
	 * Ako smo dosli do granice u kretanju, mijenja smjer.
	 */
	public void move() {
		if (sightStart == limitStart || sightStart+sightWidth == limitStart+limitWidth)
			this.turnAround(); // okrece se u drugu stranu
		//sta sad? povecaj ugao
		this.sightStart += this.rotationDirection.direction;
	}
	
	@Override
	public String toString() {
		return "CameraAtrs [sightRange= " + sightRange + ", limitStart= "
				+ limitStart + ", limitWidth= " + limitWidth + ", rotationSpeed= "
				+ rotationSpeed + ", rotationDirection= " + rotationDirection
				+ ", sightStart= "+ sightStart + ", sightWidth= " + sightWidth + " ]";
	}
	
	public static void main(String[] args) {
		/*CameraConfig cat = new CameraConfig(RotationSpeed.FAST, RotationDirection.CLOCKWISE, -10, 180, 0, 60);
		System.out.println("brzina: " + cat.rotationSpeed + " " + cat.rotationSpeed.speed);
		System.out.println("smjer:  " + cat.rotationDirection+ " " + cat.rotationDirection.direction);
		cat.move();
		System.out.println("promjena!");
		//System.out.println("ugao:  " + cat.rotatio + " " + cat.rotationSpeed.speed);
		System.out.println("smjer: " + cat.rotationDirection+ " " + cat.rotationDirection.direction);*/
	}
	
}