/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agendacontatos;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alan
 */
public class Executavel {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        int escolha;
        String arquivo = "contents.txt", ag = "agenda.txt";
        File arq1 = new File(arquivo);
        File arq2 = new File(ag);
        Scanner entrada = new Scanner(System.in);
        ArrayList<String> agenda = new ArrayList();
        
        if(!arq1.exists()){
            try{
                arq1.createNewFile();
            } catch (IOException io) {
                System.out.printf("O Arquivo %s não existe e não pôde ser criado.\n", arquivo);
                System.exit(0);
            }
        }
        if(!arq2.exists()){
            try{
                arq2.createNewFile();
            } catch (IOException io) {
                System.out.printf("O Arquivo %s não existe e não pôde ser criado.\n", ag);
                System.exit(0);
            }
        }
        
        ManipulaArquivo.importaContatos(agenda, arquivo);
            
        do {
            System.out.println("Menu:\n"
                    + "1: Incluir novos nomes e telefones\n"
                    + "2: Consultar nomes existentes\n"
                    + "3: Alterar nomes/telefones existentes\n"
                    + "4: Excluir nomes/telefones existentes\n"
                    + "0: Sair");

            do{
                escolha = entrada.nextInt();
                if(escolha < 0 || escolha > 4) System.out.println("Escolha inválida!");
            } while(escolha < 0 || escolha > 4);
            
            switch (escolha){
                case 1:
                    //incluir                    
                    ManipulaArquivo.insereContatos(agenda, arquivo);
                    break;
                case 2:
                    //consultar
                    ManipulaArquivo.consultaNome(agenda);
                    break;
                case 3:
                    //alterar
                    ManipulaArquivo.alteraContatos(agenda, arquivo);
                    break;
                case 4:
                    //excluir
                    ManipulaArquivo.excluiContatos(agenda, arquivo);
                    break;
            }
        } while(escolha != 0);
        
        ManipulaArquivo.gerar(agenda, ag);
    }
    
}
