package com.safetynet.alerts.services;

import com.safetynet.alerts.exception.DaoException;
import com.safetynet.alerts.model.Person;

public interface PersonService {

	public void createPerson(Person person);

	public void updatePerson(Person person) throws DaoException;
	
	public void deletePerson(String firstName,String lastName) throws DaoException;

}
