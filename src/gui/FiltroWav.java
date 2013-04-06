package gui;

import java.io.*; 
class FiltroWave implements FilenameFilter{ 

      public boolean accept(File dir, String name) { 
         boolean a = name.endsWith(".wav") ; // método que realmente faz o trabalho :-P 
         
          return a; 
       
      } 

   }