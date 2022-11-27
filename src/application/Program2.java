package application;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class Program2 {

	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);
		DepartmentDao departmentDao = DaoFactory.createDepartmentDao();

		System.out.println("=== TEST 1: seller findById =====");
		Department department = departmentDao.findById(10);
		System.out.println(department);

		System.out.println();
		System.out.println("=== TEST 3: seller findAll =====");
		List<Department> list = departmentDao.findAll();
		for (Department obj : list) {
			System.out.println(obj);
		}

		System.out.println();
		System.out.println("=== TEST 4: seller insert =====");
		Department newDepartment = new Department(null, "Robotics");
		departmentDao.insert(newDepartment);
		System.out.println("Inserted! New id = " + newDepartment.getId());

		System.out.println();
		System.out.println("=== TEST 5: seller update =====");
		department = departmentDao.findById(7);
		department.setName("Department Update");
		departmentDao.update(department);
		System.out.println("Update completed.");

		System.out.println();
		System.out.println("=== TEST 6: seller delete =====");
		departmentDao.deleteById(8);
		System.out.println("Delete completed");

		scanner.close();
	}

}
