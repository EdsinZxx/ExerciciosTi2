package exercicio01soma;
import java.util.*;

public class SomaDoisNumeros {
	public static Scanner scanner =  new Scanner(System.in);
	public static void main(String[]args) {
		
		//declaração de variaveis
		int n1,n2,soma;
		
		//entrada do primeiro numero
		System.out.print("Digite um numero: ");
		n1 = scanner.nextInt();
		
		//entrada do segundo numero
		System.out.print("Digite um numero: ");
		n2 = scanner.nextInt();
		
		//soma dos numeros
		soma = n1 + n2;
		
		//Escrita da soma na tela
		System.out.println("Soma: "+soma);

	}
}
