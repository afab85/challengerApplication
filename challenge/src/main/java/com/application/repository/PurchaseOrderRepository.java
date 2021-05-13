package com.application.repository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.application.models.PurchaseOrder;
//Esta classe eh responsavel por cria, deletar, listar e editar ordens de compras
@Repository
public interface PurchaseOrderRepository extends CrudRepository<PurchaseOrder, Long>{

	PurchaseOrder findAllById(Long id);
}
