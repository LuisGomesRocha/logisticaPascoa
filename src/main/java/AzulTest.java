import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.zup.pascoaZup.service.AzulCargoService;

public class AzulTest {
	
	@Autowired
	AzulCargoService azulService;
	
	@Test
	public void test() {
		AzulCargoService azulService = new AzulCargoService();
		azulService.consultaAzulCargo("15060-100", "12570-000", "CargoAzul", "1", "80", "30", "60", "APARECIDA - SP", "SAO JOSE DO RIO PRETO - SP");
		
	}
	
	
}
