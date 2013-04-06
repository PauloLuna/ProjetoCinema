package gui;

import java.io.*; 

import javax.swing.JFileChooser; 
import javax.swing.filechooser.FileFilter;

public class EscolhePasta { 
    
    public static String escolheArquivo(){ 
          
          String arquivoWave;  
          
          JFileChooser arquivo = new JFileChooser();   
          arquivo.setFileFilter(new FileFilter() {
			
			@Override
			public String getDescription() {
				
				return ".txt";
			}
			
			@Override
			public boolean accept(File f) {
				String nome = f.getName();
				return nome.endsWith(".txt");
			}
		});
          //arquivo.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);  
          
          if(arquivo.showOpenDialog(arquivo) == JFileChooser.APPROVE_OPTION){ 
              arquivoWave = arquivo.getSelectedFile().getPath(); 
          }        
          
          else{ 
              arquivoWave = ""; 
              
          } 
          
          
          return arquivoWave; 
          } 

    
    public static void main(String[] args){ 
       
      if(!escolheArquivo().equals("")){ 
          
         File arq = new File(escolheArquivo()); 

         File[] arquivos = arq.listFiles(new FiltroWave()); 
          
         for(int i=0;i<arquivos.length;i+=10){ 
            String s = arquivos[i].getName(); 
            s = s.substring(0,(s.length()-6)); 
            System.out.println(s); 

         } 
      } 

       
    } 
    
  
}