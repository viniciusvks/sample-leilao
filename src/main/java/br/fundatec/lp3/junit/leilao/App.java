package br.fundatec.lp3.junit.leilao;

public class App {
	
    public static void main( String[] args ) {
       
    	Usuario joao = new Usuario("Joao");
    	Usuario maria = new Usuario("Maria");
    	Usuario jose = new Usuario("Jose");
    	
    	Leilao leilao = new Leilao("Playstation 4 Novo");
    	
    	Lance lanceDoJoao = new Lance(joao, 300.0);
    	Lance lanceDaMaria = new Lance(maria, 400.0);
    	Lance lanceDoJose = new Lance(jose, 250.0);
    	
    	leilao.propoe(lanceDoJoao);
    	leilao.propoe(lanceDaMaria);
    	leilao.propoe(lanceDoJose);
    	
    	Avaliador avaliador = new Avaliador();
    	avaliador.avalia(leilao);
    	
    	System.out.println(leilao.toString());        	
    	System.out.println("Maior lance: ");
    	System.out.println(avaliador.getMaiorLance().toString());

    }
}
