package com.codigo.unit_testing;

import com.codigo.unit_testing.aggregates.request.EmpresaRequest;
import com.codigo.unit_testing.aggregates.response.BaseResponse;
import com.codigo.unit_testing.entity.Empresa;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UnitTestingApplicationTests {

	@Test
	void testSumaAssertEquals(){
		//ARRANGE: Preparar el ambiente con las variables y/o mocks requeridos
		int resultadoEsperado = 10;
		int actual;
		//ACT: EJecutamos la prueba
		actual = 5 + 5;
		//ASSERT: Afirmar el resultado esperado
		assertEquals(resultadoEsperado,actual, "Los valores deben tanto el esperado como " +
				"el actual deben ser iguales");
	}
	@Test
	void testSumaAssertNotEquals(){
		//ARRANGE: Preparar el ambiente con las variables y/o mocks requeridos
		int resultadoEsperado = 10;
		int actual;
		//ACT: EJecutamos la prueba
		actual = 5 + 6;
		//ASSERT: Afirmar el resultado esperado
		assertNotEquals(resultadoEsperado,actual, "Los valores NO deben de ser iguales");
	}
	@Test
	void testAssertTrue(){
		//Arrange
		boolean condicion;
		//Act
		condicion = 3 > 2;
		//Assert
		assertTrue(condicion, "El primer valor debe ser mayor al segundo");
	}
	@Test
	void testAssertFalse(){
		//Arrange
		boolean condicion;
		//Act
		condicion = 1 > 2;
		//Assert
		assertFalse(condicion, "El primer valor debe ser mayor al segundo");
	}
	@Test
	void testAssertNull(){
		String dato = null;

		assertNull(dato, "El valor esperado no es NUlo");
	}
	@Test
	void testAssertNotNull(){
		String dato = null;

		dato = "Hola";

		assertNotNull(dato, "El valor esperado no es NUlo");
	}

	@Test
	void testAssertEqualsArray(){
		int[] arregloEsperado ={1,2,3};
		int[] actual ={1,2,3};

		assertArrayEquals(arregloEsperado,actual,
				"Los arreglos no son iguales");
	}

	@Test
	void testAssertThrows(){
		assertThrows(ArithmeticException.class, () ->{
			int promedio=100/0;
			System.out.println(promedio);
		}, "La exepcion generada no es la que se espera");
	}

	@Test
	void testAssertSame(){

		String dato1 = "1";
		String dato2 = "1";
		assertSame(dato1,dato2);
	}

	@Test
	void testAssertSame1(){
		EmpresaRequest instancia1 = SingleService.getInstance();
		instancia1.setDistrito("SANTA ANITA");
		EmpresaRequest instancia2 = SingleService.getInstance();

		assertSame(instancia1,instancia2, "Las instancias no son iguales");

	}

	@Test
	void testAssertSame2(){

		BaseResponse<Empresa> instancia1 = (BaseResponse<Empresa>) SingleService.getInstanceBaseResponse();
		BaseResponse<Empresa> instancia2 = (BaseResponse<Empresa>) SingleService.getInstanceBaseResponse();
		assertSame(instancia1,instancia2, "Las instancias no son iguales");

	}
}
