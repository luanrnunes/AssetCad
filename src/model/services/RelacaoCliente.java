package model.services;

import java.util.ArrayList;
import java.util.List;

import model.entities.Department;

public class RelacaoCliente {

	public List <Department> findAll()
	{
		List<Department> list = new ArrayList<>();
		list.add(new Department(1, "Ciser"));
		list.add(new Department(2, "Tupy"));
		list.add(new Department(3, "Hospital"));
		return list;
	}
	
}
