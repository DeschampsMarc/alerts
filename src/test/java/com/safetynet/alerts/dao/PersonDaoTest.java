package com.safetynet.alerts.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.safetynet.alerts.exception.DaoException;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.services.LoadPersonService;

@SpringBootTest
@ActiveProfiles("test")

class PersonDaoTest {

	List<Person> listPerson = new ArrayList<Person>();

	@Autowired
	Person person;

	@Autowired
	PersonDao personDao;

	@Autowired
	LoadPersonService loadpersonService;

	@BeforeEach
	private void setUpPerTest() throws JsonMappingException, ClassNotFoundException, IOException, SQLException {
		personDao.deleteAllPerson();
		loadpersonService.loadData();
		listPerson = personDao.getAllPerson();
	}

	@Test
	void loadDataTest() {
		assertEquals(6, personDao.getAllPerson().size());
		assertEquals("Toto1", personDao.getAllPerson().get(0).getFirstName());
	}

	@Test
	void createPersonTest() {

		person.setAllAttributes("Totocreate", "Toto3create name", "Totocreate adress", "Totocreate city",
				"Totocreate zip", "Totocreate phone", "Totocreate email");
		personDao.createPerson(person);
		assertEquals("Totocreate", listPerson.get(6).getFirstName());
		assertEquals(7, listPerson.size());
	}

	@Test
	void updatePersonTest() throws DaoException {

		person = personDao.getPerson("Toto1", "Toto1 name");
		person.setAddress("updateadresse1");
		person.setEmail("updateToto1@email.com");
		person = personDao.updatePerson(person);

		assertEquals("updateadresse1", person.getAddress());
		assertEquals("updateToto1@email.com", person.getEmail());
	}

	@Test
	void deletePersonTest() throws DaoException {
		personDao.deletePerson(listPerson.get(0));
		assertEquals(5, listPerson.size());
		assertEquals("Toto2", listPerson.get(0).getFirstName());
	}

	@Test
	void getPersonTest() throws DaoException {

		person = personDao.getPerson("Toto4", "Toto4 name");
		assertEquals(6, personDao.getAllPerson().size());
		assertEquals("Toto4", person.getFirstName());
		assertEquals("Toto4 name", person.getLastName());
	}

	@Test
	void deleteAllPersonTest() throws DaoException {
		personDao.deleteAllPerson();
		assertEquals(0, listPerson.size());
	}

	@Test
	void getAllPersonTest() throws DaoException {
		assertEquals(6, listPerson.size());
		assertEquals("Toto6", personDao.getAllPerson().get(5).getFirstName());
	}
}
