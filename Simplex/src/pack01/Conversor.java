package pack01;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Conversor {	
	
	private static String varCut = "[A-z]\\d*";
	private static String finalCut =  "-?\\d*\\.?\\d+";		
	
	public static ArrayList<Double> vetorCon(String str) {
		ArrayList<Double> numbers = new ArrayList<Double>();
		Matcher matcher = Pattern.compile(finalCut).matcher(str.replaceAll(varCut, ""));
		while (matcher.find()) numbers.add(Double.parseDouble(matcher.group()));				
		return numbers;		
	}
	
	public static ArrayList<ArrayList<Double>> tabelinha (ArrayList<String> fo){
		ArrayList<ArrayList<Double>> matriz = new ArrayList<ArrayList<Double>>();		
		for (int i = 0; i < fo.size(); i++) {
			ArrayList<Double> aux = new ArrayList<Double>();
			aux = vetorCon(fo.get(i));
			if(i == 0) aux.replaceAll(x -> x * -1);	
			matriz.add(aux);
		}
		return matriz;	
	}
	
	public static int passo1(ArrayList<ArrayList<Double>> modelo) {
		Double menor = 0.0;
		int coluna = -1;
		for (int n = 0; n < modelo.get(0).size(); n++) {
			if (modelo.get(0).get(n) < menor) {
				menor = modelo.get(0).get(n); 
				coluna = n;				
			}
		}
		return coluna;
	}	
	
	public static int passo2(ArrayList<ArrayList<Double>> modelo, int coluna) {
		Double menor =0.0;
		int constante = modelo.get(0).size()-1, linha = 0;
		for (int i = 0; i < modelo.size(); i++) {
			Double valor =	modelo.get(i).get(coluna);
			if(valor > 0.0 && modelo.get(i).get(constante) > 0.0) {
				if (menor == 0.0) menor = modelo.get(i).get(constante);
				if (modelo.get(i).get(constante)/valor < menor) {
					menor = modelo.get(i).get(constante)/valor;
					linha = i;
					}
			}
		}
		return linha;
	}
	
	public static void passo3I(ArrayList<ArrayList<Double>> modelo, int linha, int coluna) {
		Double pivo = modelo.get(linha).get(coluna); 
		if (pivo == 0) return; // ? ? ?
		if(pivo != 1) {
			for(int i = 0; i < modelo.get(linha).size(); i++)
				if(modelo.get(linha).get(i) != 0) modelo.get(linha).set(i, modelo.get(linha).get(i)/pivo);
		}		
	}
	
	public static void passo3(ArrayList<ArrayList<Double>> modelo, int linha, int coluna) {
		Double valor = null, pivo = null;
		for(int i = 0; i < modelo.size(); i++) {
			pivo = modelo.get(i).get(coluna)*-1;
			for (int j = 0; j < modelo.get(i).size(); j++) {
				if(i ==  linha) continue;
				modelo.get(i).set(j, modelo.get(linha).get(j)*pivo + modelo.get(i).get(j));
			}
		}
	}
	
	public static String passoN(ArrayList<String> fos) {
		ArrayList<ArrayList<Double>> matriz = new ArrayList<ArrayList<Double>>();
		ArrayList<ArrayList<ArrayList<Double>>> listaMatriz = new ArrayList<ArrayList<ArrayList<Double>>>();		
		String nova = "";
		matriz = tabelinha(fos);		
		ArrayList<String> zeta = new ArrayList<String>();
		zeta.add(" Z");
		for(int i = 0; i < fos.size(); i++) zeta.add("X"+(fos.size()+i-1));		 
		while(true) {
			int coluna = passo1(matriz);
			if (coluna == -1) break;
			int linha = passo2(matriz, coluna);
			passo3I(matriz, linha, coluna);
			nova = nova.concat(powerString(matriz, zeta));
			listaMatriz.add(matriz);
			passo3(matriz, linha, coluna);
			zeta.set(linha, "X"+(coluna+1));
			nova = nova.concat(powerString(matriz, zeta));
			listaMatriz.add(matriz);
		}		
		return nova;
	}

	// Retornar String com tudo formatado
	public static String powerString(ArrayList<ArrayList<Double>> matriz, ArrayList<String> zeta) {
		String huge = "  ";
		for(int x = 0 ; x < matriz.get(0).size(); x++) {
			if(x == matriz.get(0).size()-1) huge = huge.concat("             LD |\n");
			else huge = huge.concat(String.format("             X%d |", x+1));
		}		
		
		for(int i = 0; i < matriz.size(); i++) {
			huge = huge.concat(String.format(zeta.get(i)+" "));
			for (int j = 0; j < matriz.get(i).size(); j++)
				huge = huge.concat(String.format("% 14.2f | ", matriz.get(i).get(j)));
			huge = huge.concat("\n");
		}
		huge = huge.concat("\n");		
		return huge;
	}		
}