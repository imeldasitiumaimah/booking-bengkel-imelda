package com.bengkel.booking.services;

import java.util.List;

import com.bengkel.booking.models.Car;
import com.bengkel.booking.models.Customer;
import com.bengkel.booking.models.MemberCustomer;
import com.bengkel.booking.models.Vehicle;

public class PrintService {
	private static Customer currentLoggedInCustomer;

	public static void setCurrentLoggedInCustomer(Customer customer) {
		currentLoggedInCustomer = customer;
	}

	public static void printMenu(String[] listMenu, String title) {
		String line = "+---------------------------------+";
		int number = 1;
		String formatTable = " %-2s. %-25s %n";

		System.out.printf("%-25s %n", title);
		System.out.println(line);

		for (String data : listMenu) {
			if (number < listMenu.length) {
				System.out.printf(formatTable, number, data);
			} else {
				System.out.printf(formatTable, 0, data);
			}
			number++;
		}
		System.out.println(line);
		System.out.println();
	}

	public static void printVechicle(List<Vehicle> listVehicle) {
		String formatTable = "| %-2s | %-15s | %-10s | %-15s | %-15s | %-5s | %-15s |%n";
		String line = "+----+-----------------+------------+-----------------+-----------------+-------+-----------------+%n";
		System.out.format(line);
		System.out.format(formatTable, "No", "Vechicle Id", "Warna", "Brand", "Transmisi", "Tahun", "Tipe Kendaraan");
		System.out.format(line);
		int number = 1;
		String vehicleType = "";
		for (Vehicle vehicle : listVehicle) {
			if (vehicle instanceof Car) {
				vehicleType = "Mobil";
			} else {
				vehicleType = "Motor";
			}
			System.out.format(formatTable, number, vehicle.getVehiclesId(), vehicle.getColor(), vehicle.getBrand(), vehicle.getTransmisionType(), vehicle.getYearRelease(), vehicleType);
			number++;
		}
		System.out.printf(line);
	}

	public static void showCustomerInfo() {
		if (currentLoggedInCustomer != null) {
			System.out.println("Informasi Customer/Profile:");
			System.out.println("Customer Id: " + currentLoggedInCustomer.getCustomerId());
			System.out.println("Nama: " + currentLoggedInCustomer.getName());
			System.out.println("Customer Status: " + (isMember(currentLoggedInCustomer) ? "Member" : "Non Member"));
			System.out.println("Alamat: " + currentLoggedInCustomer.getAddress());
			if (isMember(currentLoggedInCustomer)) {
				System.out.println("Saldo Koin: " + ((MemberCustomer) currentLoggedInCustomer).getSaldoCoin());
			}
			System.out.println();
			System.out.println("List Kendaraan: ");
			List<Vehicle> vehicles = currentLoggedInCustomer.getVehicles();
			if (!vehicles.isEmpty()) {
				System.out.println("+----+-----------------+------------+-------+-----------------+");
				System.out.println("| No | Vehicle Id      | Warna      | Tahun | Tipe Kendaraan  |");
				System.out.println("+----+-----------------+------------+-------+-----------------+");
				int i = 1;
				for (Vehicle vehicle : vehicles) {
					String type = (vehicle instanceof Car) ? "Mobil" : "Motor";
					System.out.printf("| %-2d | %-15s | %-10s | %-5s | %-15s |%n",
							i, vehicle.getVehiclesId(), vehicle.getColor(), vehicle.getYearRelease(), type);
					i++;
				}
				System.out.println("+----+-----------------+------------+-------+-----------------+");
			} else {
				System.out.println("Belum ada kendaraan terdaftar.");
			}
		} else {
			System.out.println("Anda belum login.");
		}
	}

	private static boolean isMember(Customer customer) {
		if (customer instanceof MemberCustomer) {
			MemberCustomer memberCustomer = (MemberCustomer) customer;
			return memberCustomer.getSaldoCoin() > 0;
		} else {
			return false;
		}
	}

	// Silahkan Tambahkan function print sesuai dengan kebutuhan.

}
