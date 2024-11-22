package com.codigo.unit_testing.service;

import com.codigo.unit_testing.aggregates.request.EmpresaRequest;

public class SingleService {

    private static EmpresaRequest instanceEmpresa;

    public static EmpresaRequest getInstance(){
        if(instanceEmpresa==null){
            instanceEmpresa=new EmpresaRequest();
        }
        return instanceEmpresa;
    }
}

