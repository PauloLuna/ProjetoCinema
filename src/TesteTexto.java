import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class TesteTexto {

	private final String ARQUIVO = "texto.txt";
	private File file;
	
	public TesteTexto(){
		file = new File(ARQUIVO);
		FileWriter fwTeste;
		BufferedWriter bwTeste;
		try {
			fwTeste = new FileWriter(file);
			bwTeste = new BufferedWriter(fwTeste);
			bwTeste.write("teste\n\rteste\njkhhfgffghjk\nkj");
			bwTeste.newLine();
			bwTeste.write(1234);
			bwTeste.write("laksjhvhhh");
			bwTeste.close();
			fwTeste.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
