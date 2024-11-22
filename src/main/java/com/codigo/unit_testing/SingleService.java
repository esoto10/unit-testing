package com.codigo.unit_testing;

import com.codigo.unit_testing.aggregates.request.EmpresaRequest;
import com.codigo.unit_testing.aggregates.response.BaseResponse;
import com.codigo.unit_testing.entity.Empresa;

public class SingleService {
    private  SingleService() {

    }

    private static EmpresaRequest instanceEmpresa;
    private static BaseResponse<Empresa>instanceBaseResponse;

    public static EmpresaRequest getInstance(){
        if(instanceEmpresa == null){
            instanceEmpresa = new EmpresaRequest();
        }
        return instanceEmpresa;
    }

    public static BaseResponse<Empresa> getInstanceBaseResponse(){
        if(instanceBaseResponse == null){
            instanceBaseResponse = new BaseResponse<>();
        }
        return instanceBaseResponse;
    }

}
