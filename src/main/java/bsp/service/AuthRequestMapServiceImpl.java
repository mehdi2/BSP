package bsp.service;

import bsp.model.AuthRequestMap;
import bsp.repo.AuthRequestMapRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

@Service(value = "authRequestMapService")
public class AuthRequestMapServiceImpl implements AuthRequestMapService {
	@Autowired
	private AuthRequestMapRepository invProductRepository;


	@Override
	public List<AuthRequestMap> findAll() {
		return invProductRepository.findAll();
	}

	@Override
	public AuthRequestMap findById(BigInteger id) {
		return invProductRepository.findOne(id);
	}

	@Override
	public AuthRequestMap save(AuthRequestMap invProduct) {
		return invProductRepository.save(invProduct);
	}

	@Override
	public void delete(BigInteger id) {
		invProductRepository.delete(id);
	}
}
