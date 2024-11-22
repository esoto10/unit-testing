package com.codigo.unit_testing.service.impl;

import com.codigo.unit_testing.aggregates.constants.Constants;
import com.codigo.unit_testing.aggregates.request.EmpresaRequest;
import com.codigo.unit_testing.aggregates.response.BaseResponse;
import com.codigo.unit_testing.dao.EmpresaRepository;
import com.codigo.unit_testing.entity.Empresa;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class EmpresaServiceImplTest {
    @Mock
    private EmpresaRepository empresaRepository;
    @InjectMocks
    private EmpresaServiceImpl empresaServiceImpl;

    //Variables Generales: La sque vamos a poder usar en toda nuestra clase
    private Empresa empresa;
    private EmpresaRequest empresaRequest;
    List<Empresa> listEmpresa= new ArrayList<>();


    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        empresa = new Empresa(4l,"ezetta inc","283839390393");
        empresaRequest = new EmpresaRequest();
        empresaRequest.setNumeroDocumento("");
        listEmpresa= Arrays.asList(new Empresa(1l,"yolu original",
                "22222222222"),new Empresa(2l,"yolu original2",
                "32323232322"),new Empresa(3l,"yolu original3",
                "4445454545"));

    }

    @Test
    void  testCrearEmpresaExiste(){
        //ARRANGE
            //Configurar el comportamiento del mock
            when(empresaRepository.existsByNumeroDocumento(anyString()))
                    .thenReturn(true);
        //ACT -> EJECUTAR NUESTRO METODO ESPECIFICO
        ResponseEntity<BaseResponse<Empresa>> response =
                empresaServiceImpl.crear(empresaRequest);

        //ASSERT
        assertEquals(Constants.CODE_EXIST, response.getBody().getCode());
    }

    @Test
    void testCrearEmpresaNueva(){
        //Configurar el comportamiento del mock
        when(empresaRepository.existsByNumeroDocumento(anyString()))
                .thenReturn(false);
        when(empresaRepository.save(any())).thenReturn(empresa);

        //ACT -> EJECUTAR NUESTRO METODO ESPECIFICO
        ResponseEntity<BaseResponse<Empresa>> response = empresaServiceImpl.crear(empresaRequest);

        //ASSERT
        assertEquals(Constants.CODE_OK, response.getBody().getCode());
        assertEquals(Constants.MSJ_OK, response.getBody().getMessage());
        assertFalse(response.getBody().getObjeto().isEmpty());
        assertSame(empresa,response.getBody().getObjeto().get());

    }

    @Test
    void testActualizarEmpresaSuccess(){
       //test para actualizar caso empresa sucess
        Long id = 1L;
        when(empresaRepository.existsById(id)).thenReturn(true);
        when(empresaRepository.findById(id)).thenReturn(Optional.of(empresa));
        when(empresaRepository.save(any())).thenReturn(empresa);

        ResponseEntity<BaseResponse<Empresa>> response =
                empresaServiceImpl.actualizar(id,empresaRequest);

        assertNotNull(response);
        assertEquals(2001, response.getBody().getCode());
        assertEquals(Constants.MSJ_OK, response.getBody().getMessage());
        assertFalse(response.getBody().getObjeto().isEmpty());
        assertTrue(response.getBody().getObjeto().isPresent());
        assertSame(empresa, response.getBody().getObjeto().get());

    }

    @Test
    void testActualizarEmpresaUnsuccess(){
        Long id = 1L;
        when(empresaRepository.existsById(id))
                .thenReturn(false);

        ResponseEntity<BaseResponse<Empresa>> response =
                empresaServiceImpl.actualizar(id, empresaRequest);

        assertNotNull(response);
        assertEquals(Constants.CODE_EMPRESA_NO_EXIST, response.getBody().getCode());

    }

    @Test
    void obtenerEmpresaExiste(){
        long identificador=1l;
       when(empresaRepository.findById(identificador)).thenReturn(Optional.of(empresa));
        ResponseEntity<BaseResponse<Empresa>> response= empresaServiceImpl.obtenerEmpresa(identificador);
        assertEquals(Constants.CODE_OK, response.getBody().getCode());
        assertEquals(Constants.MSJ_OK, response.getBody().getMessage());
        assertFalse(response.getBody().getObjeto().isEmpty());
        assertSame(empresa,response.getBody().getObjeto().get());

    }

    @Test
    void obtenerEmpresaNoexiste(){
        Long identificador=555l;
        when(empresaRepository.findById(identificador)).thenReturn(Optional.ofNullable(null));
        ResponseEntity<BaseResponse<Empresa>> response= empresaServiceImpl.obtenerEmpresa(identificador);
        assertEquals(Constants.CODE_EMPRESA_NO_EXIST, response.getBody().getCode());
        assertEquals(Constants.MSJ_EMPRESA_NO_EXIST, response.getBody().getMessage());
        assertTrue(response.getBody().getObjeto().isEmpty());
    }

    @Test
    void obtenerTodosTest(){
        when(empresaRepository.findAll())
                .thenReturn(listEmpresa);
        ResponseEntity<BaseResponse<Empresa>> res= empresaServiceImpl.obtenerTodos();
        assertNotNull(res.getBody());
        assertEquals(Constants.CODE_OK, res.getBody().getCode());
        assertEquals(Constants.MSJ_OK, res.getBody().getMessage());
        //verify(empresaRepository,times(1)).findAll();

    }

    @Test
    void obtenerTodosVacioTest(){
        when(empresaRepository.findAll())
                .thenReturn(Collections.EMPTY_LIST);
        ResponseEntity<BaseResponse<Empresa>> res= empresaServiceImpl.obtenerTodos();
        assertEquals(Constants.CODE_EMPRESA_NO_EXIST, res.getBody().getCode());
        assertEquals(Constants.MSJ_EMPRESA_NO_EXIST, res.getBody().getMessage());
        assertTrue(res.getBody().getObjeto().isEmpty());
    }

    @Test
    void eliminarEmpresaExisteTest(){
        when(empresaRepository.existsById(any()))
                .thenReturn(true);
        Long identificador=1l;
        when(empresaRepository.findById(any())).thenReturn(Optional.of(empresa));
        when(empresaRepository.save(any())).thenReturn(empresa);
        ResponseEntity<BaseResponse<Empresa>> res= empresaServiceImpl.delete(identificador);
        assertEquals(Constants.CODE_OK, res.getBody().getCode());
        assertEquals(Constants.MSJ_OK, res.getBody().getMessage());
        assertTrue(res.getBody().getObjeto().isPresent());
    }

    @Test
    void eliminarEmpresaNoExisteTest(){
        when(empresaRepository.existsByNumeroDocumento(anyString()))
                .thenReturn(false);
        ResponseEntity<BaseResponse<Empresa>> res= empresaServiceImpl.delete(777l);
        assertEquals(Constants.CODE_EMPRESA_NO_EXIST, res.getBody().getCode());
        assertEquals(Constants.MSJ_EMPRESA_NO_EXIST, res.getBody().getMessage());
        assertTrue(res.getBody().getObjeto().isEmpty());
    }

}