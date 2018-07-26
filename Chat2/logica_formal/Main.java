
import java.util.*;

public class Main {

	private static Scanner numero;

	public static void main(String[] args) {

		numero = new Scanner(System.in);

		int p = 0,q,r,a=0,b=0,c=0,g=0,A=0,R=0,tip=0;

		int []tipo = new int[5];

		System.out.println("Original : (~q -> r) v (~r^~p <-> ~~r) -> (p^~~r)");

		System.out.println("A = (~q -> r) v (~r^~p <-> ~~r)");

		System.out.println("R = A -> (p^~~r)");

		System.out.println("0 : Falso\n1 : Verdad");

		System.out.println("\t\tLOGICA FORMAL");

		System.out.println("p|q|r \t(~q -> r) | (~r^~p <-> ~~r) | (p^~~r)| A | R |");

		System.out.println("-------------------------------------------------------");

		do{

		//	System.out.println("p|q|r \t(~q -> r) | (~r^~p <-> ~~r) | (p^~~r)| A | R |");

		//	System.out.println("-------------------------------------------------------");

				p = numero.nextInt();

				q = numero.nextInt();

				r = numero.nextInt();	

				a = QimplicaR(p,q,r);

				b = BimplicaR(p,q,r);

				c = PydolnegR(p,q,r);

				A = QR_y_BR(p,q,r);

				R = UltQR_BRimplicaPR(p,q,r);

				tipo[g]=R;

				System.out.println(p+" "+q+" "+r+"\t     "+a+"\t\t   "+b+"\t\t"+c+"\t"+A+"  "+R);

		/*	System.out.println("\n[1] Continuar Introduciendo");

			System.out.println("[2] Detener");

			System.out.printf("::> ");

			opc = numero.nextInt();*/

				tip+=tipo[g];

		g++;

		}while(g<5);

		//g=0;

		//do{		tip+=tipo[g];	g++;	}while(g<5);

		if(tip==5){

			System.out.println("\t---------------------");

			System.out.println("\t-   Es Tautologia   -");

			System.out.println("\t---------------------");

		}else if(tip == 0){

			System.out.println("\t---------------------");

			System.out.println("\t-   Es Negacion     -");

			System.out.println("\t---------------------");

		}else{

			System.out.println("\t---------------------");

			System.out.println("\t-   Es Contingencia -");

			System.out.println("\t---------------------");

		}

	}

	



	/* (non-Java-doc)

	 * @see java.lang.Object#Object()

	 */

	

	

	//(~q -> r)

	public static int QimplicaR(int p,int q,int r){

		int a = 0;

		if(q==1){

			q = 0;

		}else{

			q = 1;

		}

		if(q == 1 && r == 0){

			a = 0;

		}else{

			a = 1;

		}  

		return a;

	}

	

	//(~r^~p <-> ~~r)

	//Solo nos reflerimos a la R y a P negativos

	public static int RyP(int p,int q,int r){   

		int b = 0;

		if(r==1){

			r = 0;

		}else{

			r = 1;

		}

		if(p==1){

			p = 0;

		}else{

			p = 1;

		}

		if(r == 1 && p == 1){

			b = 1;

		}else{

			b = 0;

		}

		return b;

	}

	

	//(~r^~p <-> ~~r)

	//Esta ves nos referimos a la implicasion doble de R

	public static int BimplicaR(int p,int q,int r){

		int c,b;

		b = RyP(p,q,r); //funcion de R y P negativos

		if(b == r){

			c = 1;

		}else{

			c = 0; 

		}

		return c;

	}

	

	//(p^~~r)

	public static int PydolnegR(int p,int q,int r){

		int d;

	/*	if(r==1){

			r = 0;

		}else{

			r = 1;

		}   */

		if(p == 1 && r==1){

			d = 1;

		}else{

			d = 0;

		}

		return d;

	}

	

	//A = (~q -> r) v (~r^~p <-> ~~r)

	//

	public static int QR_y_BR(int p,int q,int r){

		int A = 0,a1=0,a2=0;

		a1 = QimplicaR(p,q,r);

		a2 = BimplicaR(p,q,r);

		if(a1 == 1 || a2 == 1){

			A = 1;

		}

		return A;

	}

	//A -> (p^~~r)

	//Este seri el resultado total y donde demostrariamos cualseria la tabla

	//tautologia,contingencia o negacion

	public static int UltQR_BRimplicaPR(int p,int q,int r){

		int R=0,ult1=0,ult2=0;

		ult1 = QR_y_BR(p,q,r);

		ult2 = PydolnegR(p,q,r);

		if(ult1 == 1 && ult2 == 0){

			R = 0;

		}else{

			R = 1;

		}

		return R;

	}

	

	public Main() {

		super();

	}



}