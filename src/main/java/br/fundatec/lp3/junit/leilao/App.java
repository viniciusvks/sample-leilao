package br.fundatec.lp3.junit.leilao;

public class App {
	
    public static void main( String[] args ) throws Exception {
       
    	Usuario joao = new Usuario("Joao");
    	Usuario maria = new Usuario("Maria");
    	Usuario jose = new Usuario("Jose");
    	
    	Leilao leilao = new Leilao("Playstation 4 Novo");
    	
    	Lance lanceDoJoao = new Lance(joao, 300.0);
    	Lance lanceDaMaria = new Lance(maria, 400.0);
    	Lance lanceDoJose = new Lance(jose, 250.0);
    	
    	leilao.propoe(lanceDoJose);
    	leilao.propoe(lanceDoJoao);
    	leilao.propoe(lanceDaMaria);
    	
    	Avaliador avaliador = new Avaliador();
    	avaliador.avalia(leilao);
    	
    	double menorValorEsperado = 250.0;
    	double maiorValorEsperado = 400.0;
    	
    	double valorMenorLance = avaliador.getMenorLance().getValor();
    	double valorMaiorLance = avaliador.getMaiorLance().getValor();
    	
    	
    	boolean valorMaiorEstaCorreto = (valorMaiorLance == maiorValorEsperado);
    	boolean valorMenorEstaCorreto = (valorMenorLance == menorValorEsperado);
    	
    	System.out.println(leilao.toString());   
    	System.out.println("Retorna maior lance correto: " + valorMaiorEstaCorreto);
    	System.out.println("Retorna menor lance correto: " + valorMenorEstaCorreto);
//    	System.out.println("Maior lance: ");
//    	System.out.println(avaliador.getMaiorLance().toString());
//    	System.out.println("Menor lance: ");
//    	System.out.println(avaliador.getMenorLance().toString());

    }
}
