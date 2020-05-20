/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agendacontatos;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Collections;
import java.util.Iterator;

/**
 *
 * @author alan
 */
public class ManipulaArquivo {       
    public static void importaContatos(ArrayList<String> agenda, String caminho){
        try {            
            BufferedReader br = new BufferedReader(new FileReader(caminho));
            String linha = br.readLine();
            
            while(linha != null){
                agenda.add(linha);
                linha = br.readLine();
            }
            
            br.close();
        } catch (IOException ex){
            System.out.printf("Erro ao abrir o arquivo! %s", ex);
        }
    }
    
    public static void exportaContatos(ArrayList<String> agenda, String caminho) throws IOException{
        FileWriter fw = new FileWriter(caminho, false);
        String conteudo = "";
                
        Collections.sort(agenda);
        
        Iterator<String> iterator = agenda.iterator();
            while(iterator.hasNext()){
                conteudo += iterator.next() + "\n";
            }                       
            
        fw.write(conteudo);
        fw.close();
        
        gerar(agenda, "agenda.txt");
    }

    public static void consultaNome(ArrayList<String> agenda) throws IOException{        
        Scanner entrada = new Scanner(System.in);
        String[] contato = new String[2];
        String linha = "", pesquisa;
        
        System.out.println("Qual o nome que deseja pesquisar? ");
        pesquisa = entrada.nextLine();
        
        Iterator<String> iterator = agenda.iterator();
        boolean encontrou = false;
        
        while(iterator.hasNext()){
            linha = iterator.next();
            
            contato = linha.split(";");
            
            String nome = contato[0];
            String telefone = contato[1];
            
            if(nome.equals(pesquisa)){
                System.out.println("Contato encontrado!");
                System.out.printf("Nome: %s | Telefone: %s\n\n", nome, telefone);
                encontrou = true;
                break;
            }
        }
        
        if(!encontrou){
            System.out.println("Contato não encontrado!");
        }
    }        
    
    public static void insereContatos(ArrayList<String> agenda, String caminho) throws IOException{
        Scanner entrada = new Scanner(System.in);
        String nome, telefone, conteudo;
       
        System.out.printf("Nome do contato: ");
        nome = entrada.nextLine();
        System.out.printf("Telefone do contato: ");
        telefone = entrada.nextLine();
        
        nome = nome.substring(0, 1).toUpperCase() + nome.substring(1);
        
        conteudo = nome + ";" + telefone;
        
        agenda.add(conteudo);
        
        exportaContatos(agenda, caminho);
    }
    
    public static void alteraContatos(ArrayList<String> agenda, String caminho) throws IOException{
        Scanner entrada = new Scanner(System.in);        
        String nome;
        String[] contato = new String[2];
        
        System.out.println("Qual contato você deseja alterar?");
        nome = entrada.nextLine();
                
        Iterator<String> iterator = agenda.iterator();
            while(iterator.hasNext()){
                String linha = iterator.next();                
                
                contato = linha.split(";");
                
                int posicao;
                
                if(contato[0].equals(nome)){
                    posicao = agenda.indexOf(linha);
                    
                    System.out.println("Contato encontrado!");
                    System.out.println("Você deseja alterar o nome ou o telefone?\n1: Nome\n2: Telefone");
                    String novoValor = "";
                    
                    int escolha = Integer.parseInt(entrada.nextLine());                  
                    switch(escolha){
                        case 1:
                            System.out.printf("Digite o novo nome: ");
                            novoValor = entrada.nextLine();
                            contato[0] = novoValor;
                            break;
                        case 2:
                            System.out.printf("Digite o novo telefone: ");
                            novoValor = entrada.nextLine();                            
                            contato[1] = novoValor;                    
                            break;
                    }
                    
                    linha = contato[0] + ";" + contato[1];
                    
                    agenda.set(posicao, linha);                                                                             
                }
            }        
            
            exportaContatos(agenda, caminho);
    }
    
    public static void excluiContatos(ArrayList<String> agenda, String caminho) throws IOException{
        Scanner entrada = new Scanner(System.in);
        String nome, linha;
        String[] contato;
        
        System.out.println("Qual contato você deseja excluir? ");
        nome = entrada.nextLine();
        
        Iterator<String> iterator = agenda.iterator();
            while(iterator.hasNext()){
                linha = iterator.next();
                
                contato = linha.split(";");
                
                if(contato[0].equals(nome)){
                    int posicao = agenda.indexOf(linha);
                    agenda.remove(posicao);
                    break;
                }
            }
            
        exportaContatos(agenda, caminho);        
    }
    
    //gera txt com os contatos formatados
    public static void gerar(ArrayList<String> agenda, String caminho) throws IOException{
        FileWriter fw = new FileWriter(caminho, false);
        String conteudo = "", linha, nome, telefone;
        String[] contato = new String[2];
        char letra;
        char primeiraLetra;
        Iterator<String> iterator = agenda.iterator();
        
        linha = iterator.next();
        primeiraLetra = linha.charAt(0);
        
        contato = linha.split(";");
        nome = contato[0];
        telefone = contato[1];
        
        conteudo += "Letra " + primeiraLetra + "\n\n" + nome + "\n" + telefone + "\n";
        
        while(iterator.hasNext()){
            linha = iterator.next();            
            
            if(linha.charAt(0) != primeiraLetra){
                primeiraLetra = linha.charAt(0);
                conteudo += "\n" + "Letra " + primeiraLetra + "\n\n";
            }
            
            contato = linha.split(";");
            nome = contato[0];
            telefone = contato[1];
            
            conteudo += nome + "\n" + telefone + "\n";
        }
        
        fw.write(conteudo);
        fw.close();
    }
}
