package com.example.ManagementSystem.service;

import com.example.ManagementSystem.dto.Response;
import com.example.ManagementSystem.dto.SupplierDTO;

public interface SupplierService {
    
    Response addSupplier(SupplierDTO supplierDTO);

    Response updateSupplier(Long id, SupplierDTO supplierDTO);

    Response getAllSupplier();

    Response getSupplierById(Long id);

    Response deleteSupplier(Long id);
}
