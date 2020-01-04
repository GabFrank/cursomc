package com.franco.dev.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.franco.dev.domain.Pedido;
import com.franco.dev.repository.PedidoRepository;

import javassist.tools.rmi.ObjectNotFoundException;

@Service
public class PedidoService {
	
	@Autowired
	private PedidoRepository repo;
	
	public Pedido buscar(Integer id) throws ObjectNotFoundException{
		Optional<Pedido> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Pedido não encontrada! ID : " + id + ", Tipo: " + Pedido.class.getName()));
	}

}
